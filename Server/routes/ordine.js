module.exports=app=>{
    const order=require("../controllers/ordine.js");
    var router=require("express").Router();
    router.get("/showDueIn",order.showDueIn);
    router.get("/detailDueIn",order.detailDueIn);
    app.use("/api/ordine",router);
};