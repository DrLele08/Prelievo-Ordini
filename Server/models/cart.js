const Prodotto=require("./prodotto");
const sql=require("./database.js");

const Cart=new Object();



async function createCart(idUtente)
{
    return new Promise((resolve,reject)=>{
        let query="INSERT INTO Carrello(ksUtente,DataCreazione,DataModifica) VALUES(?,NOW(),NOW())";
        sql.query(query,[idUtente],(errQ,risQ)=>{
            if(errQ)
                resolve(false);
            else
            {
                resolve(true);
            }
        });
    });
}

async function clearCart(idUtente)
{
    return new Promise((resolve,reject)=>{
        let query="SELECT prodottocarrello.idProdottoCarrello AS ID FROM utente,articolo,carrello,prodottocarrello WHERE carrello.idCarrello=prodottocarrello.ksCarrello AND prodottocarrello.ksArticolo=articolo.idArticolo AND articolo.QntDisponibile<=0 AND utente.idUtente=carrello.ksUtente AND carrello.ksUtente=?";
        sql.query(query,[idUtente],(errQ,risQ)=>{
            if(errQ)
            {
                resolve(false);
            }
            else
            {
                let vettEliminare=new Array();
                for(let i=0;i<risQ.length;i++)
                {
                    let id=risQ[i].ID;
                    vettEliminare.push(id);
                }
                if(vettEliminare.length>0)
                {
                    let queryDel="DELETE FROM ProdottoCarrello WHERE idProdottoCarrello=?";
                    sql.query(queryDel,[vettEliminare],(errDel,risDel)=>{
                        if(errDel)
                            resolve(false);
                        else
                            resolve(true);
                    });
                }
                else
                    resolve(true);
            }
        });
    });
}

async function hasCart(idUtente)
{
    return new Promise((resolve,reject)=>{
        let query="SELECT * FROM  Carrello WHERE ksUtente=?";
        sql.query(query,[idUtente],(errQ,risQ)=>{
            if(errQ)
                resolve(false);
            else
            {
                resolve(risQ.length>0);
            }
        });
    });
}

async function getIdCartByUser(idUtente)
{
    return new Promise((resolve,reject)=>{
        let query="SELECT idCarrello FROM  Carrello WHERE ksUtente=?";
        sql.query(query,[idUtente],(errQ,risQ)=>{
            if(errQ)
                resolve(-1);
            else
            {
                if(risQ.length>0)
                    resolve(risQ[0].idCarrello);
                else
                    resolve(-1);
            }
        });
    });
}

async function updateDataCart(idCarrello)
{
    return new Promise((resolve,reject)=>{
        let query="UPDATE Carrello SET DataModifica=NOW() WHERE idCarrello=?";
        sql.query(query,[idCarrello],(errQ,risQ)=>{
            if(errQ)
            {
                resolve(false);
            }
            else
            {
                resolve(false);
            }
        });
    });
}

Cart.getIdByUtente=(idUtente)=>{
    return new Promise((resolve,reject)=>{
        let query="SELECT idCarrello FROM  Carrello WHERE ksUtente=?";
        sql.query(query,[idUtente],(errQ,risQ)=>{
            if(errQ)
                resolve(-1);
            else
            {
                if(risQ.length>0)
                    resolve(risQ[0].idCarrello);
                else
                    resolve(-1);
            }
        });
    });
};

Cart.seeCart=async(idUtente,result)=>{
    let exist=await hasCart(idUtente);
    if(!exist)
        exist=await createCart(idUtente);
    if(exist)
    {
        let clear=await clearCart(idUtente);
        if(clear)
        {
            let query="SELECT articolo.idArticolo,articolo.Descrizione,articolo.PrezzoIvato,articolo.Peso,articolo.Volume,prodottocarrello.Qnt AS QntCart FROM articolo,prodottocarrello,carrello,utente WHERE carrello.idCarrello=prodottocarrello.ksCarrello AND articolo.idArticolo=prodottocarrello.ksArticolo AND carrello.ksUtente=utente.idUtente AND utente.idUtente=?;";
            sql.query(query,[idUtente],(errQ,risQ)=>{
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
            result("Errore durante il controllo del carrello",null);
        }
    }
    else
    {
        result("Errore durante la creazione del carrello",null);
    }
};

Cart.addItem=async(idUtente,idProdotto,qnt,result)=>{
    let exist=await hasCart(idUtente);
    if(!exist)
        exist=await createCart(idUtente);
    if(exist)
    {
        let idCart=await getIdCartByUser(idUtente);
        if(idCart != -1)
        {
            let queryFind="SELECT idProdottoCarrello,Qnt FROM ProdottoCarrello WHERE ksCarrello=? AND ksArticolo=?";
            sql.query(queryFind,[idCart,idProdotto],async(errF,risF)=>{
                if(errF)
                {
                    result(errF,null);
                }
                else
                {
                    maxQnt=await Prodotto.getQntDisponibileById(idProdotto);
                    if(maxQnt>0)
                    {
                        if(risF.length>0)
                        {
                            let idP=risF[0].idProdottoCarrello;
                            let qntOld=parseInt(risF[0].Qnt);
                            let newQnt=qntOld+parseInt(qnt);
                            if(newQnt>maxQnt)
                                newQnt=maxQnt;
                            let query="UPDATE ProdottoCarrello SET Qnt=? WHERE idProdottoCarrello=?";
                            sql.query(query,[newQnt,idP],async(errQ,risQ)=>{
                                if(errQ)
                                {
                                    result(errQ,null);
                                }
                                else
                                {
                                    await updateDataCart(idCart);
                                    result(null,risQ);
                                }
                            });
                        }
                        else
                        {
                            if(qnt>maxQnt)
                                qnt=maxQnt;
                            let query="INSERT INTO ProdottoCarrello(ksCarrello,ksArticolo,Qnt,DataInserimento) VALUES(?,?,?,NOW())";
                            sql.query(query,[idCart,idProdotto,qnt],(errQ,risQ)=>{
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
                    }
                    else
                    {
                        result("Prodotto terminato",null);
                    }
                }
            });
        }
        else
        {
            result("Errore durante la creazione del carrello",null);
        }
    }
    else
    {
        result("Errore durante la creazione del carrello",null);
    }
};

Cart.removeItem=async(idUtente,idProdotto,result)=>{
    let exist=await hasCart(idUtente);
    if(!exist)
        exist=await createCart(idUtente);
    if(exist)
    {
        let idCart=await getIdCartByUser(idUtente);
        if(idCart != -1)
        {
            let query="DELETE FROM ProdottoCarrello WHERE ksCarrello=? AND ksArticolo=?";
            sql.query(query,[idCart,idProdotto],async(errQ,risQ)=>{
                if(errQ)
                {
                    result(errQ,null);
                }
                else
                {
                    await updateDataCart(idCart);
                    result(null,risQ);
                }
            });
        }
        else
        {
            result("Errore durante la creazione del carrello",null);
        }
    }
    else
    {
        result("Errore durante la creazione del carrello",null);
    }
};

Cart.deleteCart=async(idUtente,result)=>{
    let exist=await hasCart(idUtente);
    if(!exist)
        exist=await createCart(idUtente);
    if(exist)
    {
        let idCart=await getIdCartByUser(idUtente);
        if(idCart != -1)
        {
            sql.beginTransaction((errT)=>{
                if(errT)
                {
                    connection.rollback(function() {
                        connection.release();
                    });
                    result(errT,null);
                }
                else
                {
                    let query="DELETE FROM ProdottoCarrello WHERE ksCarrello=?";
                    sql.query(query,[idCart],(errQ,risQ)=>{
                        if(errQ)
                        {
                            sql.rollback(function(){
                                result(errQ,null);
                            });
                        }
                        else
                        {
                            let queryCart="DELETE FROM Carrello WHERE idCarrello=?";
                            sql.query(queryCart,[idCart],(errCart,risCart)=>{
                                if(errCart)
                                {
                                    sql.rollback(function(){
                                        result(errCart,null);
                                    });
                                }
                                else
                                {
                                    sql.commit((errComm)=>{
                                        if(errComm)
                                        {
                                            sql.rollback(function(){
                                                result(errComm,null);
                                            });
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
            });
        }
        else
        {
            result("Errore durante la creazione del carrello",null);
        }
    }
    else
    {
        result("Errore durante la creazione del carrello",null);
    }
};

module.exports=Cart;