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
                if(tipo == -1)
                {
                    json.Ris=0;
                    json.Mess="Credenziali non valide";
                    ris.json(json);
                }
                else if(tipo==1 || tipo==2)
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