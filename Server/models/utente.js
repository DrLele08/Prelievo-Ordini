const sql=require("./database.js");
const bcrypt=require('bcrypt');

const Utente=new Object();

//Login Email e Password
Utente.findByEmailAndPwd=(Email,Pwd,result)=>{
    let json=new Object();
    let query="SELECT idUtente,ksTipo,Nome,Email,Password,TokenAuth,Cellulare FROM Utente WHERE Email=? AND ksTipo!=4";
    sql.query(query,Email,(errQ,risQ)=>{
        if(errQ)
        {
            result(errQ,null);
        }
        else if(risQ.length==1)
        {
            let pwdHash=risQ[0].Password;
            bcrypt.compare(Pwd,pwdHash,(errHash,risHash)=>{
                if(errHash)
                {
                    result(errHash,null);
                }
                else if(risHash)
                {
                    json.idUtente=risQ[0].idUtente;
                    json.ksTipo=risQ[0].ksTipo;
                    json.Nome=risQ[0].Nome;
                    json.Email=risQ[0].Email;
                    json.tokenAuth=risQ[0].TokenAuth;
                    json.Cellulare=risQ[0].Cellulare;
                    result(null,json);
                }
                else
                {
                        json.Ris=0;
                        json.Mess="Password errata";
                        result(null,json);
                }
            });
        }
        else
        {
            json.Ris=0;
            json.Mess="Utente non trovato";
            result(json,null);
        }
    });
};

//Login idUtente e Token
Utente.findByIdAndAuth=(idUtente,tokenAuth,result)=>{
    let json=new Object();
    let query="SELECT ksTipo,Nome,Email,Cellulare FROM Utente WHERE idUtente=? AND TokenAuth=? AND ksTipo!=4";
    sql.query(query,[idUtente,tokenAuth],(errQ,risQ)=>{
        if(errQ)
        {
            result(errQ,null);
        }
        else if(risQ.length==1)
        {
                json.idUtente=idUtente;
                json.ksTipo=risQ[0].ksTipo;
                json.Nome=risQ[0].Nome;
                json.Email=risQ[0].Email;
                json.tokenAuth=tokenAuth;
                json.Cellulare=risQ[0].Cellulare;
                result(null,json);
        }
        else
        {
            json.Ris=0;
            json.Mess="Utente non trovato";
            result(json,null);
        }
    });
};

//Tipo permessi di un utente
Utente.getTipoUtente=(idUtente,tokenAuth,result)=>{
    let query="SELECT ksTipo FROM Utente WHERE idUtente=? AND TokenAuth=?";
    sql.query(query,[idUtente,tokenAuth],(errQ,risQ)=>{
        if(errQ)
        {
            result(-1);
        }
        else
        {
            if(risQ.length)
            {
                result(risQ[0].ksTipo)
            }
            else
            {
                result(-1);
            }
        }
    });
};

Utente.registrazione=(nome,email,password,tokenAuth,ide,cell,result)=>{
    let query="INSERT INTO Utente(ksTipo,Nome,Email,Password,TokenAuth,Identificativo,Cellulare) VALUES(3,?,?,?,?,?,?)";
    sql.query(query,[nome,email,password,tokenAuth,ide,cell],(errQ,risQ)=>{
        if(errQ)
        {
            result(errQ,null);
        }
        else
        {
            result(null,risQ.insertId);
        }
    });
};

Utente.getNomeByCell=(cell,result)=>{
    let query="SELECT Nome FROM Utente WHERE Cellulare=?";
    sql.query(query,[cell],(errQ,risQ)=>{
        if(errQ)
        {
            result(null);
        }
        else
        {
            if(risQ.length)
            {
                result(risQ[0].Nome);
            }
            else
            {
                result(null);
            }
        }
    });
}

Utente.getUtenti=(result)=>{
    let query="SELECT utente.idUtente,utente.Nome,utente.ksTipo AS Ruolo FROM utente";
    sql.query(query,(errQ,risQ)=>{
        if(errQ)
            result(errQ,null);
        else
            result(null,risQ);
    });
};

Utente.getUtenteById=(idUtente,result)=>{
    let query="SELECT idUtente,ksTipo,Nome,Email,Identificativo,Cellulare FROM utente WHERE idUtente=?;";
    sql.query(query,[idUtente],(errQ,risQ)=>{
        if(errQ)
            result(true,null);
        else
        {
            if(risQ.length>0)
                result(null,risQ);
            else
                result(true,null);
        }
    });
};

Utente.esisteByEmail=(email,result)=>{
    let query="SELECT email FROM utente WHERE email=?;";
    sql.query(query,[email],(errQ,risQ)=>{
        if(errQ)
            result(errQ,null);
        else
        {
            if(risQ.length>0)
                result(null,true);
            else
                result(null,false);
        }
    });
};

module.exports=Utente;