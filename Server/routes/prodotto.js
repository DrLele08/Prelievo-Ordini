module.exports=app=>{
    const prodotto=require("../controllers/prodotto.js");
    var router=require("express").Router();
    router.get("/prodotti",prodotto.showProdotti);
    router.get("/preOrder",prodotto.showPreOrdine);
    router.get("/prodottoByEan",prodotto.prodByEan);
    router.get("/prodottoById",prodotto.prodById);
    router.get("/prodottoByTags",prodotto.prodByTags);
    app.use("/api/prodotto",router);
};