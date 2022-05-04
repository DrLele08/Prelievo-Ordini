const sql=require("./database.js");

const Lettura=new Object();

Lettura.getLettureInevase=(result)=>{
    let query='SELECT ordine.idOrdine,utente.Nome,statoordine.Stato,DATE_FORMAT(ordine.Data,"%d-%m-%Y") AS Data,DATE_FORMAT(ordine.Data,"%H:%i") AS Orario FROM utente,ordine,statoordine WHERE utente.idUtente=ordine.ksUtente AND statoordine.idStato=ordine.ksStato AND ordine.ksStato IN(1,2) ORDER BY ordine.Data;';
    sql.query(query,(errQ,risQ)=>{
        if(errQ)
        {
            result(errQ,null);
        }
        else
        {
            result(null,risQ);
        }
    });
};

Lettura.countLettureByTipo=(idUtente,result)=>{
    let query="SELECT tipoevento.Evento,COUNT(*) AS Valore FROM eventolettura,operatorilettura,utente,tipoevento WHERE tipoevento.idTipoEvento=eventolettura.ksEvento AND eventolettura.ksOperatore=operatorilettura.idOperatoriLettura AND operatorilettura.ksUtente=utente.idUtente AND utente.idUtente=? GROUP BY ksEvento;";
    sql.query(query,[idUtente],(errQ,risQ)=>{
        result(risQ);
    });
};

module.exports=Lettura;