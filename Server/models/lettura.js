const sql=require("./database.js");

const Lettura=new Object();

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
    let query='SELECT ordine.idOrdine,utente.Nome,statoordine.Stato,DATE_FORMAT(ordine.Data,"%d-%m-%Y") AS Data,DATE_FORMAT(ordine.Data,"%H:%i") AS Orario FROM utente,ordine,statoordine WHERE utente.idUtente=ordine.ksUtente AND statoordine.idStato=ordine.ksStato AND ordine.ksStato IN(1,2) ORDER BY ordine.Data;';
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

Lettura.sceltaLettura=(idOrdine,result)=>{
    result(null,true);
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