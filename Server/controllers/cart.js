const Cart=require("../models/cart.js");
const Prodotto=require("../models/prodotto.js");
const Utils=require("../models/utils.js");
const Utente=require("../models/utente.js");

exports.seeCart=(req,ris)=>{
    let json=new Object();
    Utils.checkTokenAndParameter(req.query,["idUtente","TokenAuth"],(contiene)=>{
        if(contiene)
        {
            let idUtente=req.query.idUtente;
            let tokenAuth=req.query.TokenAuth;
            Utente.getTipoUtente(idUtente,tokenAuth,(tipo)=>{
                if(tipo != 4 && tipo != -1)
                {
                    Cart.seeCart(idUtente,(errC,risC)=>{
                        if(errC)
                        {
                            json.Ris=0;
                            json.Mess=errC;
                            ris.json(json);
                        }
                        else
                        {
                            json.Ris=1;
                            json.Mess="Fatto";
                            json.Cart=risC;
                            ris.json(json);
                        }
                    });
                }
                else
                {
                    json.Ris=0;
                    json.Mess="Impossibile effettuare l'operazione";
                    ris.json(json);
                }
            });
        }
        else
        {
            json.Ris=-1;
            json.Mess="Parametri errati";
            ris.json(json);
        }
    });
};

exports.addCart=(req,ris)=>{
    let json=new Object();
    Utils.checkTokenAndParameter(req.body,["idUtente","TokenAuth","idArticolo","Qnt"],(contiene)=>{
        if(contiene)
        {
            let idUtente=req.body.idUtente;
            let tokenAuth=req.body.TokenAuth;
            Utente.getTipoUtente(idUtente,tokenAuth,(tipo)=>{
                if(tipo != 4 && tipo != -1)
                {
                    let idArticolo=req.body.idArticolo;
                    let qnt=req.body.Qnt;
                    if(qnt>0)
                    {
                        Prodotto.hasProductById(idArticolo,(errA,risA)=>{
                            if(errA)
                            {
                                json.Ris=0;
                                json.Mess=errA;
                                ris.json(json);
                            }
                            else
                            {
                                if(risA)
                                {
                                    Cart.addItem(idUtente,idArticolo,qnt,(errC,risC)=>{
                                        if(errC)
                                        {
                                            json.Ris=0;
                                            json.Mess=errC;
                                            ris.json(json);
                                        }
                                        else
                                        {
                                            json.Ris=1;
                                            json.Mess="Fatto";
                                            ris.json(json);
                                        }
                                    });
                                }
                                else
                                {
                                    json.Ris=0;
                                    json.Mess="Articolo non esistente";
                                    ris.json(json);
                                }
                            }
                        });
                    }
                    else
                    {
                        json.Ris=0;
                        json.Mess="Inserisci una quantità corretta";
                        ris.json(json);
                    }
                }
                else
                {
                    json.Ris=0;
                    json.Mess="Impossibile effettuare l'operazione";
                    ris.json(json);
                }
            });
        }
        else
        {
            json.Ris=-1;
            json.Mess="Parametri errati";
            ris.json(json);
        }
    });
};


exports.removeItem=(req,ris)=>{
    let json=new Object();
    Utils.checkTokenAndParameter(req.body,["idUtente","TokenAuth","idArticolo"],(contiene)=>{
        if(contiene)
        {
            let idUtente=req.body.idUtente;
            let tokenAuth=req.body.TokenAuth;
            Utente.getTipoUtente(idUtente,tokenAuth,(tipo)=>{
                if(tipo != 4 && tipo != -1)
                {
                    let idArticolo=req.body.idArticolo;
                    Cart.removeItem(idUtente,idArticolo,(errR,risR)=>{
                        if(errR)
                        {
                            json.Ris=0;
                            json.Mess=errR;
                            ris.json(json);
                        }
                        else
                        {
                            json.Ris=1;
                            json.Mess="Fatto";
                            ris.json(json);
                        }
                    });
                }
                else
                {
                    json.Ris=0;
                    json.Mess="Impossibile effettuare l'operazione";
                    ris.json(json);
                }
            });
        }
        else
        {
            json.Ris=-1;
            json.Mess="Parametri errati";
            ris.json(json);
        }
    });
};

exports.deleteCart=(req,ris)=>{
    let json=new Object();
    Utils.checkTokenAndParameter(req.body,["idUtente","TokenAuth"],(contiene)=>{
        if(contiene)
        {
            let idUtente=req.body.idUtente;
            let tokenAuth=req.body.TokenAuth;
            Utente.getTipoUtente(idUtente,tokenAuth,(tipo)=>{
                if(tipo != 4 && tipo != -1)
                {
                    Cart.deleteCart(idUtente,(errD,risD)=>{
                        if(errD)
                        {
                            json.Ris=0;
                            json.Mess=risD;
                            ris.json(json);
                        }
                        else
                        {
                            json.Ris=1;
                            json.Mess="Fatto";
                            ris.json(json);
                        }
                    });
                }
                else
                {
                    json.Ris=0;
                    json.Mess="Impossibile effettuare l'operazione";
                    ris.json(json);
                }
            });
        }
        else
        {
            json.Ris=-1;
            json.Mess="Parametri errati";
            ris.json(json);
        }
    });
};