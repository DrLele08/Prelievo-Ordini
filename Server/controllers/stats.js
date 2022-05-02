const Stats=require("../models/stats.js");
const Utils=require("../models/utils");
const Utente=require("../models/utente.js");

exports.statsGeneric=(req,ris)=>{
    let json=new Object();
    Utils.checkTokenAndParameter(req.query,["idUtente","TokenAuth"],(risCheck)=>{
        if(risCheck)
        {
            let idUtente=req.query.idUtente;
            let tokenAuth=req.query.TokenAuth;
            Utente.getTipoUtente(idUtente,tokenAuth,(tipo)=>{
                if(tipo==1)
                {
                    Stats.statsGeneric(risStat=>{
                        ris.json(risStat);
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

exports.statsById=(req,ris)=>{
    let json=new Object();
    Utils.checkTokenAndParameter(req.query,["idUtente","TokenAuth","userStat"],(risCheck)=>{
        if(risCheck)
        {
            let idUtente=req.query.idUtente;
            let tokenAuth=req.query.TokenAuth;
            let utenteScelto=req.query.userStat
            Utente.getTipoUtente(idUtente,tokenAuth,(tipo)=>{
                if(tipo==1 || (idUtente==utenteScelto))
                {
                    Stats.statsById(utenteScelto,risStat=>{
                        ris.json(risStat);
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