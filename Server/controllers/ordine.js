const Ordine=require("../models/ordine.js");
const Utils=require("../models/utils.js");
const Utente=require("../models/utente.js");

exports.showDueIn=(req,ris)=>{
    let json=new Object();
    Utils.checkTokenAndParameter(req.query,[],(risCheck)=>{
        if(risCheck)
        {
            Ordine.showDue((vett)=>{
                json.Ris=1;
                json.Mess="Fatto";
                json.Vett=vett;
                ris.json(json);
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

exports.detailDueIn=(req,ris)=>{
    let json=new Object();
    Utils.checkTokenAndParameter(req.query,["idDue"],(risCheck)=>{
        if(risCheck)
        {
            let idDue=req.query.idDue;
            Ordine.showDetailDue(idDue,(risDue)=>{
                json.Ris=1;
                json.Mess="Fatto";
                json.Vett=risDue
                ris.json(json);
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

exports.doOrdine=(req,ris)=>{
    let json=new Object();
    Utils.checkTokenAndParameter(req.body,["idUtente","TokenAuth","Note"],(risCheck)=>{
        if(risCheck)
        {
            let idUtente=req.body.idUtente;
            let tokenAuth=req.body.TokenAuth;
            Utente.getTipoUtente(idUtente,tokenAuth,(tipo)=>{
                if(tipo==1)
                {
                    let note=req.body.Note;
                    Ordine.doOrdine(idUtente,note,(errO,risO)=>{
                        if(errO)
                        {
                            json.Ris=0;
                            json.Mess=errO;
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
                    json.Mess="Non hai i permessi";
                    ris.json(json);
                }
            });
        }
        else
        {
            json.Ris=0;
            json.Mess="Inserisci tutti i parametri";
            ris.json(json);
        }
    });
};

exports.seeOrdini=(req,ris)=>{
    let json=new Object();
    Utils.checkTokenAndParameter(req.query,["idUtente","TokenAuth","Pagina","Stato"],(risCheck)=>{
        if(risCheck)
        {
            let idUtente=req.query.idUtente;
            let tokenAuth=req.query.TokenAuth;
            Utente.getTipoUtente(idUtente,tokenAuth,(tipo)=>{
                if(tipo==1)
                {
                    let pagina=req.query.Pagina;
                    let stato=req.query.Stato;
                    if(pagina>=0)
                    {
                        Ordine.seeOrdini(pagina,stato,(errO,risO)=>{
                            if(errO)
                            {
                                json.Ris=0;
                                json.Mess=errO;
                                ris.json(json);
                            }
                            else
                            {
                                json.Ris=1;
                                json.Mess="Fatto";
                                json.Vett=risO;
                                ris.json(json);
                            }
                        });
                    }
                    else
                    {
                        json.Ris=0;
                        json.Mess="Pagina negativa";
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
            json.Ris=0;
            json.Mess="Inserisci tutti i parametri";
            ris.json(json);
        }
    });
};

exports.detailOrdine=(req,ris)=>{
    let json=new Object();
    Utils.checkTokenAndParameter(req.query,["idUtente","TokenAuth","idOrdine"],(risCheck)=>{
        if(risCheck)
        {
            let idUtente=req.query.idUtente;
            let tokenAuth=req.query.TokenAuth;
            Utente.getTipoUtente(idUtente,tokenAuth,(tipo)=>{
                if(tipo==1)
                {
                    let idOrdine=req.query.idOrdine;
                    Ordine.detailOrdine(idOrdine,(errO,risO)=>{
                        if(errO)
                        {
                            json.Ris=0;
                            json.Mess=errO;
                            ris.json(json);
                        }
                        else
                        {
                            json.Ris=1;
                            json.Mess="Fatto";
                            json.Ordine=risO;
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
            json.Ris=0;
            json.Mess="Inserisci tutti i parametri";
            ris.json(json);
        }
    });
};

exports.statoOrdine=(req,ris)=>{
    let json=new Object();
    Utils.checkTokenAndParameter(req.body,["idUtente","TokenAuth","idOrdine","newStato"],(risCheck)=>{
        if(risCheck)
        {
            let idUtente=req.body.idUtente;
            let tokenAuth=req.body.TokenAuth;
            Utente.getTipoUtente(idUtente,tokenAuth,(tipo)=>{
                if(tipo==1)
                {
                    let idOrdine=req.body.idOrdine;
                    let newStato=req.body.newStato;
                    Ordine.statoOrdine(idOrdine,newStato,(errO,risO)=>{
                        if(errO)
                        {
                            json.Ris=0;
                            json.Mess=errO;
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
                    json.Mess="Non hai i permessi";
                    ris.json(json);
                }
            });
        }
        else
        {
            json.Ris=0;
            json.Mess="Inserisci tutti i parametri";
            ris.json(json);
        }
    });
};