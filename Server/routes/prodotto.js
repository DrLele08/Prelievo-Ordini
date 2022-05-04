module.exports=app=>{
    const prodotto=require("../controllers/prodotto.js");
    var router=require("express").Router();
    router.get("/prodotti",prodotto.showProdotti);
    router.get("/preOrder",prodotto.showPreOrdine);
    router.get("/prodottoByEan",prodotto.prodByEan);
    app.use("/api/prodotto",router);
};