const Prodotto=require("../models/prodotto.js");
const Utente=require("../models/utente.js");
const Utils=require("../models/utils.js");

exports.showProdotti=(req,ris)=>{
    let json=new Object();
    Utils.checkTokenAndParameter(req.query,["idUtente","TokenAuth","Pagina","Filtro"],(risCon)=>{
        if(risCon)
        {
            let idUtente=req.query.idUtente;
            let tokenAuth=req.query.TokenAuth;
            Utente.getTipoUtente(idUtente,tokenAuth,(tipo)=>{
                let soloInGiacenza=true;
                if(tipo==1 || tipo==2)
                    soloInGiacenza=false;
                if(tipo!=4)
                {
                    let pagina=req.query.Pagina;
                    let filtro=req.query.Filtro;
                    let descrizione=req.query.Descrizione;
                    let filtroNome="";
                    if(descrizione !== undefined)
                        filtroNome=descrizione;
                    Prodotto.getProdotti(pagina,filtro,filtroNome,(result)=>{
                        if(result)
                        {
                            json.Ris=1;
                            json.Mess="Fatto";
                            json.Prodotti=result;
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
                    json.Mess="L'account è stato bannato";
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

exports.showPreOrdine=(req,ris)=>{
    let json=new Object();
    Utils.checkTokenAndParameter(req.query,["idUtente","TokenAuth","Pagina","Filtro"],(risCon)=>{
        if(risCon)
        {
            let idUtente=req.query.idUtente;
            let tokenAuth=req.query.TokenAuth;
            Utente.getTipoUtente(idUtente,tokenAuth,(tipo)=>{
                let soloInGiacenza=true;
                if(tipo==1 || tipo==2)
                    soloInGiacenza=false;
                if(tipo!=4)
                {
                    let pagina=req.query.Pagina;
                    let filtro=req.query.Filtro;
                    let descrizione=req.query.Descrizione;
                    let filtroNome="";
                    if(descrizione !== undefined)
                        filtroNome=descrizione;
                    Prodotto.getProdottiPreOrder(pagina,filtro,filtroNome,(result)=>{
                        if(result)
                        {
                            json.Ris=1;
                            json.Mess="Fatto";
                            json.Prodotti=result;
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
                    json.Mess="L'account è stato bannato";
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

exports.prodByEan=(req,ris)=>{
    let json=new Object();
};