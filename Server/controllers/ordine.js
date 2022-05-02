const Ordine=require("../models/ordine.js");
const Utils=require("../models/utils.js");

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