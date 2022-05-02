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
        console.log("Response:");
        console.log(JSON.parse(data));
      });
    });
    
    req.on('error', function(e) {
      console.log("ERROR:");
      console.log(e);
    });
    
    req.write(JSON.stringify(data));
    req.end();
  };
  
Notifica.sendNotifica=(email)=>{
    var message = { 
        app_id: "2fef20cf-7fae-43f0-b50e-3c52f309dbfa",
        contents: {"en": "English Message"},
        channel_for_external_user_ids: "push",
        include_external_user_ids: [email]
    };
    sendNotification(message);
};

module.exports=Notifica;
