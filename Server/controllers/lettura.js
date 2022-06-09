const Lettura=require("../models/lettura.js");
const Utente=require("../models/utente.js");
const Utils=require("../models/utils.js");

exports.findInevase=(req,ris)=>{
    let json=new Object();
    Utils.checkTokenAndParameter(req.query,["idUtente","TokenAuth"],(risCheck)=>{
        if(risCheck)
        {
            let idUtente=req.query.idUtente;
            let tokenAuth=req.query.TokenAuth;
            Utente.getTipoUtente(idUtente,tokenAuth,(tipo)=>{
                if(tipo==1 || tipo==2)
                {
                    Lettura.getLettureInevase((errL,risL)=>{
                        if(errL)
                        {
                            json.Ris=0;
                            json.Mess="Errore: "+errL;
                            ris.json(json);
                        }
                        else
                        {
                            json.Ris=1;
                            json.Mess="Fatto";
                            json.Letture=risL;
                            ris.json(json);
                        }
                    });
                }
                else
                {
                    json.Ris=0;
                    json.Mess="Non hai i permessi";
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

exports.letturaInCorso=(req,ris)=>{
    let json=new Object();
    Utils.checkTokenAndParameter(req.query,["idUtente","TokenAuth"],(risCheck)=>{
        if(risCheck)
        {
            let idUtente=req.query.idUtente;
            let tokenAuth=req.query.TokenAuth;
            Utente.getTipoUtente(idUtente,tokenAuth,(tipo)=>{
                if(tipo==1 || tipo==2)
                {
                    Lettura.letturaInCorso(idUtente,(errC,idOp,vettProd)=>{
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
                            json.idOperatore=idOp
                            json.Prodotti=vettProd;
                            ris.json(json);
                        }
                    });
                }
                else
                {
                    json.Ris=0;
                    json.Mess="Non hai i permessi";
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

exports.sceltaLettura=(req,ris)=>{
    let json=new Object();
    Utils.checkTokenAndParameter(req.body,["idUtente","TokenAuth","idOrdine"],(risCheck)=>{
        if(risCheck)
        {
            let idUtente=req.body.idUtente;
            let tokenAuth=req.body.TokenAuth;
            Utente.getTipoUtente(idUtente,tokenAuth,(tipo)=>{
                if(tipo==1 || tipo==2)
                {
                    let idOrdine=req.body.idOrdine;
                    Lettura.sceltaLettura(idUtente,idOrdine,(errScelta,risScelta,vettProd)=>{
                        if(errScelta)
                        {
                            json.Ris=0;
                            json.Mess=errScelta;
                            ris.json(json);
                        }
                        else
                        {
                            json.Ris=1;
                            json.Mess="Fatto";
                            json.idOperatore=risScelta;
                            json.Prodotti=vettProd;
                            ris.json(json);
                        }
                    });
                }
                else
                {
                    json.Ris=0;
                    json.Mess="Non hai i permessi";
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

exports.updateLettura=(req,ris)=>{
    let json=new Object();
    Utils.checkTokenAndParameter(req.body,["idUtente","TokenAuth","idOperatoreLettura","Letture"],(risCheck)=>{
        if(risCheck)
        {
            let idUtente=req.body.idUtente;
            let tokenAuth=req.body.TokenAuth;
            Utente.getTipoUtente(idUtente,tokenAuth,(tipo)=>{
                if(tipo==1 || tipo==2)
                {
                    let idOperatore=req.body.idOperatoreLettura
                    let letture=JSON.parse(req.body.Letture);
                    if(letture.length>0 && idOperatore>0)
                    {
                        Lettura.updateLettura(idOperatore,letture,(errUp,risUp)=>{
                            if(errUp)
                            {
                                json.Ris=0;
                                json.Mess=errUp;
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
                        json.Mess="Eventi vuoto";
                        ris.json(json);
                    }
                }
                else
                {
                    json.Ris=0;
                    json.Mess="Non hai i permessi";
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