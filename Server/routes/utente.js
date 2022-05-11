module.exports=app=>{
    const utente=require("../controllers/utente.js");
    var router=require("express").Router();
    router.get("/loginByEmail",utente.findByEmailAndPwd);
    router.get("/loginById",utente.findByIdAndAuth);
    router.post("/signUp",utente.doRegistrazione);
    router.get("/utenti",utente.getUtenti);
    router.get("/utenteById",utente.getUtenteById);
    app.use("/api/utente",router);
};