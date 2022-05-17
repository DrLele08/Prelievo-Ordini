const Utils=require("../models/utils.js");
const Categoria=require("../models/categoria.js");

exports.allCategorie=(req,ris)=>{
    let json=new Object();
    Utils.checkTokenAndParameter(req.query,[],(contiene)=>{
        if(contiene)
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
            json.Ris=-1;
            json.Mess="Parametri errati";
            ris.json(json);
        }
    });
};