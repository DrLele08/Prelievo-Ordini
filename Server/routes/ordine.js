module.exports=app=>{
    const order=require("../controllers/ordine.js");
    var router=require("express").Router();
    router.get("/showDueIn",order.showDueIn);
    router.get("/detailDueIn",order.detailDueIn);
    router.post("/doOrdine",order.doOrdine);
    router.get("/seeOrdini",order.seeOrdini);
    router.get("/detailOrdine",order.detailOrdine);
    router.post("/statoOrdine",order.statoOrdine);
    app.use("/api/ordine",router);
};