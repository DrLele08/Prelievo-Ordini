module.exports=app=>{
    const categoria=require("../controllers/categoria.js");
    var router=require("express").Router();
    router.get("/categorie",categoria.allCategorie);
    app.use("/api/categoria",router);
};