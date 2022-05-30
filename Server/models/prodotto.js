const sql=require("./database.js");
const Prodotto=new Object();


function getFiltro(filtro,isPreOrder)
{
    //1->Descrizione ASC
    //2->Descrizione DESC
    //3->Qnt ASC
    //4->Qnt DESC
    //5->Prezzo ASC
    //6->Prezzo DESC
    filtro=parseInt(filtro);
    switch(filtro)
    {
        case 1:
            return "Descrizione ASC";
        case 2:
            return "Descrizione DESC";
        case 3:
            return "QntDisponibile ASC";
        case 4:
            return "QntDisponibile DESC";
        case 5:
            if(isPreOrder)
                return "PrezzoPreOrder ASC";
            else
                return "PrezzoIvato ASC";
        default:
            if(isPreOrder)
                return "PrezzoPreOrder DESC";
            else
                return "PrezzoIvato DESC";
    }
}

Prodotto.getProdotti=(pagina,filtro,desc,categoria,soloInGiacenza,result)=>{
    const PROD_PAGINA=parseInt(process.env.PROD_PAGINA);
    let sqlCat="";
    let where="WHERE QntDisponibile>=0";
    if(soloInGiacenza)
        where="WHERE QntDisponibile>0"
    if(categoria>0)
        sqlCat=" AND ksRepartoPreferito="+sql.escape(categoria)+" ";
    let query="SELECT DISTINCT articolo.idArticolo,articolo.Descrizione,articolo.QntDisponibile,articolo.PrezzoIvato,articolo.PrezzoConsigliato,Lunghezza,Altezza,Profondita,Volume,Peso FROM articolo "+where+" "+sqlCat+" ORDER BY "+getFiltro(filtro)+" LIMIT ? OFFSET ?";
    if(desc != "")
        query="SELECT DISTINCT articolo.idArticolo,articolo.Descrizione,articolo.QntDisponibile,articolo.PrezzoIvato,articolo.PrezzoConsigliato,Lunghezza,Altezza,Profondita,Volume,Peso FROM articolo,EAN "+where+" AND EAN.ksArticolo=Articolo.idArticolo AND (Descrizione LIKE "+sql.escape("%"+desc+"%")+" OR EAN="+sql.escape(desc)+" OR Tag LIKE "+sql.escape("%"+desc+"%")+") "+sqlCat+" ORDER BY "+getFiltro(filtro)+" LIMIT ? OFFSET ?";
    sql.query(query,[PROD_PAGINA,PROD_PAGINA*pagina],(errQ,risQ)=>{
        if(errQ)
        {
            result(null);
        }
        else
        {
            result(risQ);
        }
    });
};

Prodotto.prodById=(idProdotto,result)=>{
    let query="SELECT articolo.idArticolo,articolo.Descrizione,articolo.QntDisponibile,articolo.PrezzoIvato,articolo.PrezzoConsigliato,Lunghezza,Altezza,Profondita,Volume,Peso FROM articolo WHERE idArticolo=?;";
    sql.query(query,[idProdotto],(errQ,risQ)=>{
        if(errQ)
        {
            result(errQ,null);
        }
        else
        {
            if(risQ.length>0)
                result(null,risQ[0]);
            else
                result("Non trovato",null);
        }
    });
};

Prodotto.getProdottiByTags=(tags,result)=>{
    let likePart="";
    for(let i=0;i<tags.length;i++)
    {
        likePart+="Descrizione LIKE '%"+tags[i]+"%' OR Tag LIKE '%"+tags[i]+"%' ";
        if(i!=(tags.length-1))
            likePart+="OR "
    }
    let query="SELECT DISTINCT articolo.idArticolo,articolo.Descrizione,articolo.QntDisponibile,articolo.PrezzoIvato,articolo.PrezzoConsigliato,Lunghezza,Altezza,Profondita,Volume,Peso FROM articolo WHERE "+likePart;
    sql.query(query,[],(errQ,risQ)=>{
        if(errQ)
        {
            result(errQ,null);
        }
        else
        {
            if(risQ.length>0)
                result(null,risQ);
            else
                result("Nessun prodotto trovato",null);
        }
    });
};

Prodotto.getProdottiPreOrder=(pagina,filtro,desc,categoria,result)=>{
    const PROD_PAGINA=parseInt(process.env.PROD_PAGINA);
    let sqlCat="";
    if(categoria>0)
        sqlCat=" AND ksRepartoPreferito="+sql.escape(categoria)+" ";
    let query="SELECT DISTINCT articolo.idArticolo,articolo.Descrizione,articolo.QntDisponibile,articolo.PrezzoPreOrder,articolo.PrezzoConsigliato,Lunghezza,Altezza,Profondita,Volume,Peso FROM articolo WHERE PrezzoPreOrder IS NOT NULL "+sqlCat+" ORDER BY "+getFiltro(filtro)+" LIMIT ? OFFSET ?";
    if(desc != "")
        query="SELECT DISTINCT articolo.idArticolo,articolo.Descrizione,articolo.QntDisponibile,articolo.PrezzoPreOrder,articolo.PrezzoConsigliato,Lunghezza,Altezza,Profondita,Volume,Peso FROM articolo,ean WHERE ean.ksArticolo=idArticolo AND PrezzoPreOrder IS NOT NULL "+sqlCat+" AND (Descrizione LIKE "+sql.escape("%"+desc+"%")+" OR EAN="+sql.escape(desc)+" OR Tag LIKE "+sql.escape("%"+desc+"%")+") ORDER BY "+getFiltro(filtro)+" LIMIT ? OFFSET ?";
        sql.query(query,[PROD_PAGINA,PROD_PAGINA*pagina],(errQ,risQ)=>{
        if(errQ)
        {
            result(null);
        }
        else
        {
            result(risQ);
        }
    });
};

Prodotto.getProdottoByEAN=(ean,result)=>{
    let query="SELECT DISTINCT articolo.idArticolo,articolo.Descrizione,articolo.QntDisponibile,articolo.PrezzoIvato,articolo.PrezzoConsigliato,Lunghezza,Altezza,Profondita,Volume,Peso,ean.QntConfezione FROM articolo,ean WHERE EAN.ksArticolo=articolo.idArticolo AND EAN.EAN=?;";
    sql.query(query,[ean],(errQ,risQ)=>{
        if(errQ)
        {
            result(errQ,null);
        }
        else
        {
            if(risQ.length>0)
                result(null,risQ);
            else
                result("Non trovato",null);
        }
    });
};

Prodotto.hasProductById=(idProd,result)=>{
    let query="SELECT * FROM Articolo WHERE idArticolo=?";
    sql.query(query,[idProd],(errQ,risQ)=>{
        if(errQ)
            result(errQ,null);
        else
            result(null,risQ.length>0);
    });
};

Prodotto.getQntDisponibileById=(idProd)=>{
    return new Promise((resolve,reject)=>{
        let query="SELECT QntDisponibile FROM Articolo WHERE idArticolo=?";
        sql.query(query,[idProd],(errQ,risQ)=>{
            if(errQ || risQ.length==0)
                resolve(-1);
            else
                resolve(risQ[0].QntDisponibile);
        });
    });
};

module.exports=Prodotto;