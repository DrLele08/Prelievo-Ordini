const sql=require("./database.js");
const Cart=require("./cart.js");
const Prodotto=require("./prodotto.js");
const SqlString = require("mysql/lib/protocol/SqlString");

const Ordine=new Object();

Ordine.showDue=(result)=>{
    let vett=new Array();
    for(i=12;i<30;i+=2)
    {
        let obj=new Object();
        obj.id="1532"+i;
        obj.dataArrivo=i+"/11/2022";
        obj.numProd=6;
        obj.Descrizione="Snack e Detersivi";
        vett.push(obj);
    }
    result(vett);
};

Ordine.showDetailDue=(idDue,result)=>{
    let vett=new Array();
    let obj=new Object();

    obj.idProdotto=2;
    obj.nome="Milka Bar TUC";
    obj.qntArrivo=108;
    obj.prezzo=0.6;
    vett.push(obj);

    let obj1=new Object();
    obj1.idProdotto=3;
    obj1.nome="KitKat White";
    obj1.qntArrivo=48;
    obj1.prezzo=0.22;
    vett.push(obj1);

    let obj2=new Object();
    obj2.idProdotto=6;
    obj2.nome="Ace Detersivo Lavatrice 25 Lavaggi Colorati";
    obj2.qntArrivo=200;
    obj2.prezzo=1.99;
    vett.push(obj2);

    let obj3=new Object();
    obj3.idProdotto=7;
    obj3.nome="Ace Detersivo Lavatrice 25 Lavaggi Classico";
    obj3.qntArrivo=200;
    obj3.prezzo=1.99;
    vett.push(obj3);

    let obj4=new Object();
    obj4.idProdotto=9;
    obj4.nome="Svelto Detersivo 1L Limone";
    obj4.qntArrivo=408;
    obj4.prezzo=0.99;
    vett.push(obj4);

    let obj5=new Object();
    obj5.idProdotto=10;
    obj5.nome="Svelto Detersivo 1L Antibatterico";
    obj5.qntArrivo=408;
    obj5.prezzo=0.99;
    vett.push(obj5);

    result(vett);
};

Ordine.ordersByCell=(cell,result)=>{
    let query="SELECT ordine.idOrdine,statoordine.Stato,ordine.Data FROM ordine,statoordine,articoloordine,utente WHERE ordine.idOrdine=articoloordine.ksOrdine AND ordine.ksUtente=utente.idUtente AND utente.Cellulare=? AND statoordine.idStato=ordine.ksStato GROUP BY idOrdine ORDER BY ordine.Data DESC LIMIT 5;";
    sql.query(query,[cell],(errQ,risQ)=>{
        if(errQ)
        {
            result(null);
        }
        else
        {
            if(risQ.length)
                result(risQ);
            else
                result(null);
        }
    });
};

Ordine.doOrdine=(idUtente,note,result)=>{
    Cart.seeCart(idUtente,async(errC,risC)=>{
        if(errC)
        {
            result(errC,null);
        }
        else
        {
            let vettProd=new Array();
            for(let i=0;i<risC.length;i++)
            {
                let obj=new Object();
                let tmp=risC[i];
                obj.idProdotto=tmp.idArticolo;
                obj.Prezzo=tmp.PrezzoIvato;
                let maxQnt=await Prodotto.getQntDisponibileById(tmp.idArticolo);
                if(tmp.QntCart>maxQnt)
                    tmp.QntCart=maxQnt;
                obj.Qnt=tmp.QntCart;
                vettProd.push(obj);
            }
            sql.beginTransaction((errT)=>{
                if(errT)
                    result(errT,null);
                else
                {
                    let query="INSERT INTO Ordine(ksUtente,ksStato,Data,NoteExtra) VALUES(?,1,NOW(),?)";
                    sql.query(query,[idUtente,note],(errQ,risQ)=>{
                        if(errQ)
                        {
                            sql.rollback();
                            result(errQ,null);
                        }
                        else
                        {
                            let idOrdine=risQ.insertId;
                            let queryRiga="INSERT INTO RigaOrdine(ksOrdine,ksArticolo,Prezzo,Qnt) VALUES("+sql.escape(idOrdine)+",?,?,?)";
                            sql.query(queryRiga,[vettProd],(errRiga,risRiga)=>{
                                if(errRiga)
                                {
                                    sql.rollback();
                                    result(errRiga,null);
                                }
                                else
                                {
                                    //Delete Cart
                                    sql.commit((errComm)=>{
                                        if(errComm)
                                        {
                                            sql.rollback();
                                            result(errComm,null);
                                        }
                                        else
                                            result(null,true);
                                    });
                                }
                            });
                        }
                    });
                }
            });
        }
    });
};

Ordine.seeOrdini=(pagina,stato,result)=>{
    const qntPage=parseInt(process.env.ORDINE_PAGINA);
    if(stato>0)
    {
        const offset=pagina*qntPage;
        let query="SELECT idOrdine,statoordine.Stato,utente.Nome,IFNULL(NoteExtra,'') AS NoteExtra,DATE_FORMAT(Ordine.Data,'%e-%m-%Y %H:%i') AS Data FROM ordine,statoordine,utente WHERE ordine.ksUtente=utente.idUtente AND statoordine.idStato=ordine.ksStato AND ksStato=? ORDER BY Data DESC LIMIT ? OFFSET ?";
        sql.query(query,[stato,qntPage,offset],(errQ,risQ)=>{
            if(errQ)
            {
                result(errQ,null);
            }
            else
            {
                result(null,risQ);
            }
        });
    }
    else
    {
        const offset=pagina*qntPage;
        let query="SELECT idOrdine,statoordine.Stato,utente.Nome,IFNULL(NoteExtra,'') AS NoteExtra,DATE_FORMAT(Ordine.Data,'%e-%m-%Y %H:%i') AS Data FROM ordine,statoordine,utente WHERE ordine.ksUtente=utente.idUtente AND statoordine.idStato=ordine.ksStato ORDER BY Data DESC LIMIT ? OFFSET ?";
        sql.query(query,[qntPage,offset],(errQ,risQ)=>{
            if(errQ)
            {
                result(errQ,null);
            }
            else
            {
                result(null,risQ);
            }
        });
    }    
};

Ordine.detailOrdine=(idOrdine,result)=>{
    let query="SELECT idRigaOrdine,idArticolo,Descrizione,Prezzo AS PrezzoAcquisto,PrezzoIvato AS PrezzoAttuale,Qnt AS QntOrdine,QntEvasa FROM rigaordine,articolo WHERE rigaordine.ksArticolo=articolo.idArticolo AND rigaordine.ksOrdine=?;";
    sql.query(query,[idOrdine],(errQ,risQ)=>{
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

Ordine.statoOrdine=(idOrdine,newStato,result)=>{
    let query="UPDATE Ordine SET ksStato=? WHERE idOrdine=?";
    sql.query(query,[newStato,idOrdine],(errQ,risQ)=>{
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

module.exports=Ordine;