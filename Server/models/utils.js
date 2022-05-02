const Utils=new Object();

Utils.checkTokenAndParameter=(req,vettParam,result)=>{
    let token=req.Token;
    if(token==process.env.TOKEN_AUTH)
    {
        let contiene=true;
        vettParam.forEach(element => {
            if(req[element] == undefined)
                contiene=false;
        });
        result(contiene);
    }
    else
    {
        result(false);
    }
};

module.exports=Utils;