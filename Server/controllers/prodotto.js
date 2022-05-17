const Prodotto=require("../models/prodotto.js");
const Utente=require("../models/utente.js");
const Utils=require("../models/utils.js");

exports.showProdotti=(req,ris)=>{
    let json=new Object();
    Utils.checkTokenAndParameter(req.query,["idUtente","TokenAuth","Pagina","Filtro","Categoria"],(risCon)=>{
        if(risCon)
        {
            let idUtente=req.query.idUtente;
            let tokenAuth=req.query.TokenAuth;
            Utente.getTipoUtente(idUtente,tokenAuth,(tipo)=>{
                let soloInGiacenza=true;
                if(tipo==1 || tipo==2)
                    soloInGiacenza=false;
                if(tipo != 4)
                {
                    let pagina=req.query.Pagina;
                    let filtro=req.query.Filtro;
                    let descrizione=req.query.Descrizione;
                    let cate=req.query.Categoria;
                    let filtroNome="";
                    if(descrizione !== undefined)
                        filtroNome=descrizione;
                    Prodotto.getProdotti(pagina,filtro,filtroNome,cate,soloInGiacenza,(result)=>{
                        if(result)
                        {
                            json.Ris=1;
                            json.Mess="Fatto";
                            if(tipo == -1)
                            {
                                for(let i=0;i<result.length;i++)
                                {
                                    delete result[i].PrezzoIvato;
                                    delete result[i].PrezzoConsigliato;
                                    delete result[i].Lunghezza;
                                    delete result[i].Altezza;
                                    delete result[i].Profondita;
                                    delete result[i].Volume;
                                    delete result[i].Peso;
                                }
                            }
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
    Utils.checkTokenAndParameter(req.query,["idUtente","TokenAuth","Pagina","Filtro","Categoria"],(risCon)=>{
        if(risCon)
        {
            let idUtente=req.query.idUtente;
            let tokenAuth=req.query.TokenAuth;
            Utente.getTipoUtente(idUtente,tokenAuth,(tipo)=>{
                if(tipo !=4)
                {
                    let pagina=req.query.Pagina;
                    let filtro=req.query.Filtro;
                    let descrizione=req.query.Descrizione;
                    let cate=req.query.Categoria;
                    let filtroNome="";
                    if(descrizione !== undefined)
                        filtroNome=descrizione;
                    Prodotto.getProdottiPreOrder(pagina,filtro,filtroNome,cate,(result)=>{
                        if(result)
                        {
                            json.Ris=1;
                            json.Mess="Fatto";
                            if(tipo == -1)
                            {
                                for(let i=0;i<result.length;i++)
                                {
                                    delete result[i].PrezzoPreOrder;
                                    delete result[i].PrezzoConsigliato;
                                    delete result[i].Lunghezza;
                                    delete result[i].Altezza;
                                    delete result[i].Profondita;
                                    delete result[i].Volume;
                                    delete result[i].Peso;
                                }
                            }
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
    Utils.checkTokenAndParameter(req.query,["idUtente","TokenAuth","EAN"],(esistono)=>{
        if(esistono)
        {
            let idUtente=req.query.idUtente;
            let tokenAuth=req.query.TokenAuth;
            Utente.getTipoUtente(idUtente,tokenAuth,(tipo)=>{
                if(tipo != 4)
                {
                    let EAN=req.query.EAN;
                    Prodotto.getProdottoByEAN(EAN,(prodotto)=>{
                        if(prodotto.length>0)
                        {
                            json.Ris=1;
                            json.Mess="Fatto";
                            if(tipo == -1)
                            {
                                delete prodotto[0].PrezzoIvato;
                                delete prodotto[0].PrezzoConsigliato;
                                delete prodotto[0].Lunghezza;
                                delete prodotto[0].Altezza;
                                delete prodotto[0].Profondita;
                                delete prodotto[0].Volume;
                                delete prodotto[0].Peso;
                            }
                            json.Prodotto=prodotto;
                            ris.json(json);
                        }
                        else
                        {
                            json.Ris=0;
                            json.Mess="Prodotto non trovato";
                            ris.json(json);
                        }
                    })
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
    })
};