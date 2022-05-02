const Utente=require("../models/utente.js");
const Utils=require("../models/utils.js");

exports.findByEmailAndPwd=(req,ris)=>{
    let json=new Object();
    Utils.checkTokenAndParameter(req.query,["Email","Password"],(risCheck)=>{
        if(risCheck)
        {
            let email=req.query.Email;
            let pwd=req.query.Password;
            if(email.length>0 && pwd.length>0)
            {
                Utente.findByEmailAndPwd(email,pwd,(err,data)=>{
                    if(err)
                        ris.json(err);
                    else
                        ris.json(data);
                });
            }
            else
            {
                json.Ris=0;
                json.Mess="Parametri errati";
                ris.json(json);
            }
        }
        else
        {
            json.Ris=-1;
            json.Mess="Parametri errati";
            ris.json(json);
        }
    });
};

exports.doRegistrazione=(req,ris)=>{
    let json=new Object();
    Utils.checkTokenAndParameter(req.body,["Nome","Email","Password"],(risCheck)=>{
        if(risCheck)
        {
            const bcrypt=require('bcrypt');
            const randomstring=require("randomstring");
            let nome=req.body.Nome;
            let email=req.body.Email;
            let pwd=req.body.Password;
            if(nome.length>0 && email.length>0 && pwd.length>0)
            {
                let tokenAuth=randomstring.generate(45);
                bcrypt.hash(pwd,0,(errHash, hash)=>{
                    if(errHash)
                    {
                        json.Ris=0;
                        json.Mess="Errore durante l'hash della password";
                        ris.json(json);
                    }
                    else
                    {
                        Utente.registrazione(nome,email,hash,tokenAuth,(errReg,risReg)=>{
                            if(errReg)
                            {
                                json.Ris=0;
                                json.Mess="Errore: "+errReg;
                                ris.json(json);
                            }
                            else
                            {
                                let utente=new Object();
                                utente.idUtente=risReg;
                                utente.ksTipo=3;
                                utente.Nome=nome;
                                utente.Email=email;
                                utente.tokenAuth=tokenAuth;
                                json.Ris=1;
                                json.Mess="Fatto";
                                json.Utente=utente;
                                ris.json(json);
                            }
                        });
                    }
                });
            }
            else
            {
                json.Ris=0;
                json.Mess="Parametri errati";
                ris.json(json);
            }
        }
        else
        {
            json.Ris=-1;
            json.Mess="Parametri errati";
            ris.json(json);
        }
    });
};

exports.findByIdAndAuth=(req,ris)=>{
    let json=new Object();
    Utils.checkTokenAndParameter(req.query,["idUtente","TokenAuth"],(risCheck)=>{
        if(risCheck)
        {
            let idUtente=req.query.idUtente;
            let tokenAuth=req.query.TokenAuth;
            Utente.findByIdAndAuth(idUtente,tokenAuth,(err,data)=>{
                if(err)
                    ris.json(err);
                else
                    ris.json(data);
            });
        }
        else
        {
            json.Ris=-1;
            json.Mess="Parametri errati";
            ris.json(json);
        }
    });
};