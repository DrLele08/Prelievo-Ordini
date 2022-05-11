const sql=require("./database.js");
const Categoria=require("./categoria.js");
const Prodotto=new Object();

const PROD_PAGINA=8;

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

Prodotto.getProdotti=(pagina,filtro,desc,categoria,result)=>{
    let sqlCat="";
    if(categoria>0)
        sqlCat=" AND ksRepartoPreferito="+sql.escape(categoria)+" ";
    let query="SELECT articolo.idArticolo,articolo.Descrizione,articolo.QntDisponibile,articolo.PrezzoIvato,articolo.PrezzoConsigliato,Lunghezza,Altezza,Profondita,Volume,Peso FROM articolo WHERE QntDisponibile>0 "+sqlCat+" ORDER BY "+getFiltro(filtro)+" LIMIT ? OFFSET ?";
    if(desc != "")
        query="SELECT articolo.idArticolo,articolo.Descrizione,articolo.QntDisponibile,articolo.PrezzoIvato,articolo.PrezzoConsigliato,Lunghezza,Altezza,Profondita,Volume,Peso FROM articolo,EAN WHERE EAN.ksArticolo=Articolo.idArticolo AND QntDisponibile>0 AND (Descrizione LIKE "+sql.escape("%"+desc+"%")+" OR EAN="+sql.escape(desc)+") "+sqlCat+" ORDER BY "+getFiltro(filtro)+" LIMIT ? OFFSET ?";
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

Prodotto.getProdottiPreOrder=(pagina,filtro,desc,categoria,result)=>{
    let sqlCat="";
    if(categoria>0)
        sqlCat=" AND ksRepartoPreferito="+sql.escape(categoria)+" ";
    let query="SELECT articolo.idArticolo,articolo.Descrizione,articolo.QntDisponibile,articolo.PrezzoPreOrder,articolo.PrezzoConsigliato,Lunghezza,Altezza,Profondita,Volume,Peso FROM articolo WHERE PrezzoPreOrder IS NOT NULL "+sqlCat+" ORDER BY "+getFiltro(filtro)+" LIMIT ? OFFSET ?";
    if(desc != "")
        query="SELECT articolo.idArticolo,articolo.Descrizione,articolo.QntDisponibile,articolo.PrezzoPreOrder,articolo.PrezzoConsigliato,Lunghezza,Altezza,Profondita,Volume,Peso FROM articolo,ean WHERE ean.ksArticolo=idArticolo AND PrezzoPreOrder IS NOT NULL "+sqlCat+" AND (Descrizione LIKE "+sql.escape("%"+desc+"%")+" OR EAN="+sql.escape(desc)+") ORDER BY "+getFiltro(filtro)+" LIMIT ? OFFSET ?";
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
    let query="SELECT articolo.idArticolo,articolo.Descrizione,articolo.QntDisponibile,articolo.PrezzoIvato,articolo.PrezzoConsigliato,Lunghezza,Altezza,Profondita,Volume,Peso FROM articolo,ean WHERE EAN.ksArticolo=articolo.idArticolo AND EAN.EAN=?;";
    sql.query(query,[ean],(errQ,risQ)=>{
        result(risQ);
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

module.exports=Prodotto;