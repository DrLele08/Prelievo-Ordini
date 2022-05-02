module.exports=app=>{
    const stats=require("../controllers/stats.js");
    var router=require("express").Router();
    router.get("/byId",stats.statsById);
    router.get("/generic",stats.statsGeneric);
    app.use("/api/stats",router);
};