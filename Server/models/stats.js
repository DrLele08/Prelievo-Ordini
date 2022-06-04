const sql=require("./database.js");
const Lettura=require("./lettura.js");

const Stats=new Object();

Stats.statsById=(idUtente,result)=>{
    let json=new Object();
    json.Ris=1;
    json.Mess="Fatto";
    let stat=new Array();
    //Lettura aperta
    let query="SELECT COUNT(*) AS N FROM OperatoriLettura WHERE ksUtente=?";
    sql.query(query,[idUtente],(errQ,risQ)=>{
        if(risQ.length)
        {
            let lettAp=new Object();
            lettAp.Nome="Letture aperte";
            lettAp.Valore=risQ[0].N;
            stat.push(lettAp);
        }
        json.Stat=stat;
        //Letture totali
        let queryTotale="SELECT COUNT(*) AS N FROM EventoLettura,OperatoriLettura,Utente WHERE EventoLettura.ksOperatore=OperatoriLettura.idOperatoriLettura AND OperatoriLettura.ksUtente=Utente.idUtente AND Utente.idUtente=? AND ksEvento IN(1,2);";
        sql.query(queryTotale,[idUtente],(errTot,risTot)=>{
            if(risTot.length)
            {
                let lettAp=new Object();
                lettAp.Nome="Letture totali";
                lettAp.Valore=risTot[0].N;
                stat.push(lettAp);
            }
            //Letture corrette
            //Letture errate
            Lettura.countLettureByTipo(idUtente,(risStat)=>{
                if(risStat)
                {
                    json.Dettagli=risStat
                }
                result(json);
            });
        });
    });
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
                vett.push(obj);
            }
            json.MostLetture=vett;

            //Utente con più errori
            let queryMostError="SELECT Utente.Nome,COUNT(*) AS numLetture FROM OperatoriLettura,Utente,EventoLettura WHERE EventoLettura.ksOperatore=OperatoriLettura.idOperatoriLettura AND Utente.idUtente=OperatoriLettura.ksUtente AND EventoLettura.ksEvento=2 GROUP BY ksUtente ORDER BY numLetture DESC LIMIT 1;";
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