const whatsApp=new Object();

const wa = require('@open-wa/wa-automate');
const Ordine=require("./ordine.js");
const Utente=require("./utente.js");

wa.create({
  sessionId: process.env.WA_AUTH,
  multiDevice: true,
  authTimeout: 60,
  blockCrashLogs: true,
  disableSpins: true,
  headless: true,
  hostNotificationLang: 'it_IT',
  logConsole: false,
  popup: true,
  qrTimeout: 0,
}).then(client => start(client));

function start(client){
  client.onMessage(async message=>{
        let mess=message.body.toLowerCase();
        if(mess==='/comandi') 
        {
            let cell=message.from.substring(2).split("@")[0];
            Utente.getNomeByCell(cell,async(ris)=>{
                if(ris)
                {
                    await client.sendText(message.from, `👋 Ciao ${ris}, ecco i comandi disponbili:\n/Ordini -> Visualizza i tuoi ordini`);
                }
                else
                {
                    await client.sendText(message.from, '👋 Ciao, sembra che tu non sei registrato alla nostra piattaforma.');
                }
            })
        }
        else if(mess==="/ordini")
        {
            let cell=message.from.substring(2).split("@")[0];
            Ordine.ordersByCell(cell,async(ris)=>{
                if(ris)
                {
                    let msg="";
                    for(i=0;i<ris.length;i++)
                    {
                        let ob=ris[i];
                        msg+="Codice: "+ob.idOrdine+"\n";
                        msg+="Stato: "+ob.Stato+"\n";
                        msg+="Data: "+ob.Data.toLocaleString()+"\n";
                        msg+="------\n";
                    }
                    await client.sendText(message.from, 'Elenco ultimi 5 ordini:\n'+msg);
                }
                else
                {
                    await client.sendText(message.from, 'Nessun ordine trovato');
                }
            });
        }
  });
}

module.exports=whatsApp;