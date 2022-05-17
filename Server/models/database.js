const mysql=require("mysql");
const connection = mysql.createConnection({
  host: process.env.DB_HOST,
  user: process.env.DB_USER,
  password: process.env.DB_PWD,
  database: process.env.DB_NAME,
  multipleStatements: true
});

//Connessione al database
connection.connect(error =>{
  if (error) throw error;
  console.log("Collegamento al database stabilito");
});
module.exports=connection;