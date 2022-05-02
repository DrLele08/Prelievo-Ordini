module.exports=app=>{
    const utente=require("../controllers/utente.js");
    var router=require("express").Router();
    router.get("/loginByEmail",utente.findByEmailAndPwd);
    router.get("/loginById",utente.findByIdAndAuth);
    router.post("/signUp",utente.doRegistrazione);
    app.use("/api/utente",router);
};