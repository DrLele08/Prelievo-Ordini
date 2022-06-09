const sql=require("./database.js");
const Notifica=new Object();

var sendNotification=function(data){
    var headers={
      "Content-Type": "application/json; charset=utf-8",
      "Authorization": "Basic "+process.env.TOKEN_NOTIFICA
    };
    
    var options={
      host: "onesignal.com",
      port: 443,
      path: "/api/v1/notifications",
      method: "POST",
      headers: headers
    };
    
    var https = require('https');
    var req = https.request(options, function(res) {  
      res.on('data', function(data) {

      });
    });
    
    req.on('error', function(e) {

    });
    
    req.write(JSON.stringify(data));
    req.end();
  };
  
Notifica.sendNotifica=(idUtente,titolo,mess)=>{
  let query="SELECT Email FROM Utente WHERE idUtente=?";
  sql.query(query,[idUtente],(errQ,risQ)=>{
    if(!errQ && risQ.length>0)
    {
        let email=risQ[0].Email;
        var message = { 
          app_id: "2fef20cf-7fae-43f0-b50e-3c52f309dbfa",
          headers:{"en":titolo},
          contents: {"en":mess},
          channel_for_external_user_ids: "push",
          include_external_user_ids: [email]
        };
        sendNotification(message);
    }
  });
};

module.exports=Notifica;
