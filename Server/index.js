const express=require('express');
const app=express();
const port=8000;
require('dotenv').config();
app.use(express.json());
app.use(express.static('public'));
app.use(express.urlencoded({extended: true}));


//Routes
require("./routes/utente.js")(app);
require("./routes/lettura.js")(app);
require("./routes/ordine.js")(app);
require("./routes/stats.js")(app);
require("./routes/prodotto.js")(app);
require("./routes/categoria.js")(app);
require("./routes/cart.js")(app);

//BOT WhatsApp
//require("./models/wa.js");

//Avvio server
app.listen(port,()=>{
  console.log(`Server API avviato sulla porta ${port}.`);
});