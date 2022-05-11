module.exports=app=>{
    const cart=require("../controllers/cart.js");
    var router=require("express").Router();
    router.get("/seeCart",cart.seeCart);
    router.post("/itemCart",cart.addCart);
    router.post("/removeItemCart",cart.removeItem);
    router.post("/removeCart",cart.deleteCart);
    app.use("/api/cart",router);
};