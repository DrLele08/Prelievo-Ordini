module.exports=app=>{
    const lettura=require("../controllers/lettura.js");
    var router=require("express").Router();
    router.get("/getLetture",lettura.findInevase);
    router.post("/sceltaLettura",lettura.sceltaLettura);
    router.post("/updateLettura",lettura.updateLettura);
    app.use("/api/lettura",router);
};