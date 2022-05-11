const Utils=require("../models/utils.js");
const Utente=require("../models/utente.js");
const Categoria=require("../models/categoria.js");

exports.allCategorie=(req,ris)=>{
    let json=new Object();
    Utils.checkTokenAndParameter(req.query,["idUtente","TokenAuth"],(contiene)=>{
        if(contiene)
        {
            let idUtente=req.query.idUtente;
            let tokenAuth=req.query.TokenAuth;
            Utente.getTipoUtente(idUtente,tokenAuth,(tipo)=>{
                if(tipo != 4)
                {
                    Categoria.getAll((cate)=>{
                        if(cate)
                        {
                            json.Ris=1;
                            json.Mess="Fatto";
                            json.Vett=cate;
                            ris.json(json);
                        }
                        else
                        {
                            json.Ris=0;
                            json.Mess="Errore DB";
                            ris.json(json);
                        }
                    });
                }
                else
                {
                    json.Ris=0;
                    json.Mess="Account bannato";
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