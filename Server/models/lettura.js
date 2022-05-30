const sql=require("./database.js");

const Lettura=new Object();

async function getPosizioniProdotti(idProdotto)
{
    return new Promise((resolve,reject)=>{
        let query="SELECT reparto.idReparto,reparto.Nome,postoscaffale.Posto,postoscaffale.Qnt FROM reparto,postoscaffale WHERE postoscaffale.ksReparto=reparto.idReparto AND postoscaffale.ksArticolo=?;";
        sql.query(query,[idProdotto],(errQ,risQ)=>{
            if(errQ)
            {
                resolve([]);
            }
            else
            {
                let vett=new Array();
                for(let i=0;i<risQ.length;i++)
                {
                    let tmp=risQ[i];
                    let obj=new Object();
                    obj.idReparto=tmp.idReparto;
                    obj.NomeReparto=tmp.Nome;
                    obj.Scaffale=tmp.Posto;
                    obj.Qnt=tmp.Qnt;
                    vett.push(obj);
                }
                resolve(vett);
            }
        });
    });
}

async function getProdottiByOrdine(idOrdine)
{
    return new Promise((resolve,reject)=>{
        let query="SELECT rigaordine.idRigaOrdine,articolo.idArticolo,articolo.Descrizione,rigaordine.Qnt AS QntOrdinata FROM rigaordine,articolo,ordine WHERE ordine.idOrdine=rigaordine.ksOrdine AND articolo.idArticolo=rigaordine.ksArticolo AND QntEvasa=-1 AND ksOrdine=?;";
        sql.query(query,[idOrdine],async(errQ,risQ)=>{
            if(errQ)
            {
                resolve([]);
            }
            else
            {
                let vettProd=new Array();
                for(let i=0;i<risQ.length;i++)
                {
                    let tmp=risQ[i];
                    let obj=new Object();
                    obj.idRigaOrdine=tmp.idRigaOrdine;
                    obj.idArticolo=tmp.idArticolo;
                    obj.Descrizione=tmp.Descrizione;
                    obj.QntOrdinata=tmp.QntOrdinata;
                    let vettPos=await getPosizioniProdotti(tmp.idArticolo);
                    if(vettPos.length>0)
                    {
                        obj.Posizioni=vettPos;
                        vettProd.push(obj);
                    }
                }
                vettProd=vettProd.sort((x,y)=>{
                    return x.Posizioni[0].idReparto-y.Posizioni[0].idReparto
                });
                resolve(vettProd);
            }
        });
    });
}

async function getIdOrdineByOperatore(idOperatore)
{
    return new Promise((resolve,reject)=>{
        let query="SELECT ksOrdine FROM OperatoriLettura WHERE idOperatoriLettura=?";
        sql.query(query,[idOperatore],(errQ,risQ)=>{
            if(errQ)
                resolve(-1);
            else
            {
                if(risQ.length>0)
                    resolve(risQ[0].ksOrdine);
                else
                    resolve(-1);   
            }     
        });
    });
}

Lettura.getLettureInevase=(result)=>{
    let query='SELECT ordine.idOrdine,utente.Nome,statoordine.Stato,DATE_FORMAT(ordine.Data,"%d-%m-%Y") AS Data,DATE_FORMAT(ordine.Data,"%H:%i") AS Orario,NoteExtra,SUM(rigaordine.Qnt) AS PzTotali,SUM(CASE WHEN rigaordine.QntEvasa>0 THEN rigaordine.QntEvasa ELSE 0 END) AS PzEvasi FROM utente,ordine,statoordine,rigaordine WHERE utente.idUtente=ordine.ksUtente AND statoordine.idStato=ordine.ksStato AND rigaordine.ksOrdine=ordine.idOrdine AND ordine.ksStato=1 GROUP BY idOrdine ORDER BY ordine.Data;';
    sql.query(query,(errQ,risQ)=>{
        if(errQ)
        {
            result(errQ,null);
        }
        else
        {
            result(null,risQ);
        }
    });
};

Lettura.countLettureByTipo=(idUtente,result)=>{
    let query="SELECT tipoevento.Evento,COUNT(*) AS Valore FROM eventolettura,operatorilettura,utente,tipoevento WHERE tipoevento.idTipoEvento=eventolettura.ksEvento AND eventolettura.ksOperatore=operatorilettura.idOperatoriLettura AND operatorilettura.ksUtente=utente.idUtente AND utente.idUtente=? GROUP BY ksEvento;";
    sql.query(query,[idUtente],(errQ,risQ)=>{
        result(risQ);
    });
};

Lettura.letturaInCorso=(idUtente,result)=>{
    let query="SELECT idOperatoriLettura,ksOrdine FROM OperatoriLettura WHERE ksUtente=? AND DataFine IS NULL";
    sql.query(query,[idUtente],async(errQ,risQ)=>{
        if(errQ)
        {
            result(errQ,null,[]);
        }
        else
        {
            if(risQ.length>0)
            {
                let vett=await getProdottiByOrdine(risQ[0].ksOrdine);
                if(vett.length>0)
                    result(null,risQ[0].idOperatoriLettura,vett);
                else
                    result("Impossibile recuperare se ci sono letture in corso",null,[]);
            }
            else
            {
                result(null,-1,[]);
            }
        }
    });
};

Lettura.sceltaLettura=(idUtente,idOrdine,result)=>{
    let query="SELECT idOperatoriLettura FROM OperatoriLettura WHERE ksUtente=? AND DataFine IS NULL";
        sql.query(query,[idUtente],(errQ,risQ)=>{
            if(errQ)
            {
                result(errQ,null,[]);
            }
            else
            {
                if(risQ.length==0)
                {
                    sql.beginTransaction((errT)=>{
                        if(errT)
                        {
                            result(errT,null,[]);
                        }
                        else
                        {
                            let queryStato="UPDATE Ordine SET ksStato=2 WHERE idOrdine=?";
                            sql.query(queryStato,[idOrdine],(errS,risS)=>{
                                if(errS)
                                {
                                    sql.rollback();
                                    result(errS,null,[]);
                                }
                                else
                                {
                                    let queryOp="INSERT INTO OperatoriLettura(ksOrdine,ksUtente,DataInizio) VALUES(?,?,NOW())";
                                    sql.query(queryOp,[idOrdine,idUtente],async(errOp,risOp)=>{
                                        if(errOp)
                                        {
                                            sql.rollback();
                                            result(errOp,null,[]);
                                        }
                                        else
                                        {
                                            let vettPro=await getProdottiByOrdine(idOrdine);
                                            if(vettPro.length>0)
                                            {
                                                sql.commit((errC)=>{
                                                    if(errC)
                                                    {
                                                        result(errC,null,[]);
                                                    }
                                                    else
                                                    {
                                                        let idOp=risOp.insertId;
                                                        result(null,idOp,vettPro);
                                                    }
                                                });
                                            }
                                            else
                                            {
                                                result("Impossibile recuperare i dettagli della lettura",null,[]);
                                            }
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
                else
                {
                    result("Hai gia una lettura in corso",null,[]);
                }
            }
        });
    
};

Lettura.updateLettura=(idOperatore,eventiLetture,result)=>{
    let queryCheck="SELECT COUNT(*) AS N FROM OperatoriLettura WHERE idOperatoriLettura=? AND DataFine IS NULL";
    sql.query(queryCheck,[idOperatore],async(errCheck,risCheck)=>{
        if(errCheck)
            result(errCheck,null);
        else
        {
            let idOrdine=await getIdOrdineByOperatore(idOperatore);
            if(idOrdine != -1)
            {
                let letturaTerminata=false;
                let vettSql=new Array();
                let vettUpdate=new Array();
                eventiLetture.map(x=>{
                    let obj=new Array();
                    obj.push(x.TipoEvento);
                    if(x.TipoEvento==6)
                        letturaTerminata=true;
                    obj.push(x.ksArticolo);
                    if(x.ksArticolo !== null)
                    {
                        let upTmp=new Array();
                        upTmp.push(x.Qnt);
                        upTmp.push(x.idRigaOrdine);
                        upTmp.push(idOrdine);
                        vettUpdate.push(upTmp);
                    }
                    obj.push(idOperatore);
                    obj.push(x.Note);
                    vettSql.push(obj);
                });
                sql.beginTransaction((errT)=>{
                    if(!errT)
                    {
                        let queryEventi="INSERT INTO EventoLettura(ksEvento,ksArticolo,ksOperatore,Note) VALUES ?";
                        sql.query(queryEventi,[vettSql],(errL,risL)=>{
                            if(errL)
                            {
                                sql.rollback();
                                result(errL,null);
                            }
                            else
                            {
                                if(vettUpdate.length>0)
                                {
                                    let queryUpFin="";
                                    vettUpdate.forEach((item)=>{
                                        queryUpFin+=sql.format("UPDATE RigaOrdine SET QntEvasa=? WHERE idRigaOrdine=? AND ksOrdine=?;",item);
                                    });
                                    sql.query(queryUpFin,(errUp,risUp)=>{
                                        if(errUp)
                                        {
                                            sql.rollback();
                                            result(errUp,null);
                                        }
                                        else
                                        {
                                            if(letturaTerminata)
                                            {
                                                let queryUpOp="UPDATE OperatoriLettura SET DataFine=NOW() WHERE idOperatoriLettura=?";
                                                sql.query(queryUpOp,[idOperatore],(errOp,risOp)=>{
                                                    if(errOp)
                                                    {
                                                        sql.rollback();
                                                        result(errOp,null);
                                                    }
                                                    else
                                                    {
                                                        let queryOrdine="UPDATE Ordine SET ksStato=3 WHERE idOrdine=?";
                                                        sql.query(queryOrdine,[idOrdine],(errOrd,risOrd)=>{
                                                            if(errOrd)
                                                            {
                                                                sql.rollback();
                                                                result(errOrd,null);
                                                            }
                                                            else
                                                            {
                                                                sql.commit((errComm)=>{
                                                                    if(errComm)
                                                                    {
                                                                        sql.rollback();
                                                                        result(errComm,null);
                                                                    }
                                                                    else
                                                                    {
                                                                        result(null,true);
                                                                    }
                                                                });
                                                            }
                                                        });
                                                    }
                                                });
                                            }
                                            else
                                            {
                                                sql.commit((errComm)=>{
                                                    if(errComm)
                                                    {
                                                        sql.rollback();
                                                        result(errComm,null);
                                                    }
                                                    else
                                                    {
                                                        result(null,true);
                                                    }
                                                });
                                            }
                                        }
                                    });
                                }
                                else
                                {
                                    sql.commit((errComm)=>{
                                        if(errComm)
                                        {
                                            sql.rollback();
                                            result(errComm,null);
                                        }
                                        else
                                        {
                                            result(null,true);
                                        }
                                    });
                                }
                            }
                        });
                    }
                    else
                    {
                        result(errT,null);
                    }
                });
            }
            else
            {
                result("Errore ordine",null);
            }
        }
    });
};

module.exports=Lettura;