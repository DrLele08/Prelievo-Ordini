const sql=require("./database.js");
const Stats=new Object();

Stats.statsById=(idUtente,result)=>{
    let json=new Object();
    result(json);
    //Lettura aperta
    //Letture totali
    //Letture corrette
    //Letture errate
};

Stats.statsGeneric=(result)=>{
    let json=new Object();
    
    //Utente con più letture
    let queryMostLetture="SELECT Utente.Nome,COUNT(*) AS numLetture FROM OperatoriLettura,Utente WHERE Utente.idUtente=OperatoriLettura.ksUtente GROUP BY ksUtente ORDER BY numLetture DESC LIMIT 2;";
    sql.query(queryMostLetture,(errQ,risQ)=>{
        if(errQ)
        {
            json.Ris=0;
            json.Mess="Errore: "+errQ;
            result(json);
        }
        else
        {
            json.Ris=1;
            json.Mess="Fatto";
            let vett=new Array();
            for(i=0;i<risQ.length;i++)
            {
                let obj=new Object();
                obj.Nome=risQ[i].Nome;
                obj.Letture=risQ[i].numLetture;
            }
            json.MostLetture=vett;

            //Utente con più errori
            let queryMostError="SELECT Utente.Nome,COUNT(*) AS numLetture FROM OperatoriLettura,Utente,eventolettura WHERE eventolettura.ksOperatore=operatorilettura.idOperatoriLettura AND Utente.idUtente=OperatoriLettura.ksUtente AND eventolettura.ksEvento=2 GROUP BY ksUtente ORDER BY numLetture DESC LIMIT 1;";
            sql.query(queryMostError,(errQ2,risQ2)=>{
                if(!errQ2)
                {
                    if(risQ2.length>0)
                    {
                        let obj=new Object();
                        obj.Nome=risQ2[0].Nome;
                        obj.Letture=risQ2[0].numLetture;
                        json.MostError=obj;
                    }
                }
                result(json);
            });
        }
    });
};

module.exports=Stats;