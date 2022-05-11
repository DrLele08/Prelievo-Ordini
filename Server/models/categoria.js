const sql=require("./database.js");
const Categoria=new Object();

Categoria.getAll=(result)=>{
    let query="SELECT * FROM Reparto";
    sql.query(query,(errQ,risQ)=>{
        result(risQ);
    });
};

Categoria.getById=(idCat,result)=>{
    let query="SELECT * FROM Reparto WHERE idReparto=?";
    sql.query(query,[idCat],(errQ,risQ)=>{
        result(risQ);
    });
};

module.exports=Categoria;