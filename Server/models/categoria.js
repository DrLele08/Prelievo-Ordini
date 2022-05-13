const sql=require("./database.js");
const Categoria=new Object();

Categoria.getAll=(result)=>{
    let query="SELECT * FROM Categoria";
    sql.query(query,(errQ,risQ)=>{
        result(risQ);
    });
};

Categoria.getById=(idCat,result)=>{
    let query="SELECT * FROM Categoria WHERE idReparto=?";
    sql.query(query,[idCat],(errQ,risQ)=>{
        result(risQ);
    });
};

module.exports=Categoria;