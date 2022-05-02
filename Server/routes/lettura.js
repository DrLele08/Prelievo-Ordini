module.exports=app=>{
    const lettura=require("../controllers/lettura.js");
    var router=require("express").Router();
    router.get("/getLetture",lettura.findInevase);
    app.use("/api/lettura",router);
};