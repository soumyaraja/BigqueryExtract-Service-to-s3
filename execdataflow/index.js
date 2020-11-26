const projectId = "project name";
const TEMPLATE_PATH = "dataflow template JSONPath";
const dateFormat = require('dateformat');
const now = new Date();
const dirName = dateFormat(now, 'isoDateTime');


exports.execdataflow = function(event, context) {

 const pubsubMessage = event.data;
 var decodedMsg = Buffer.from(pubsubMessage, 'base64').toString();
 var res = decodedMsg.split("|");
console.log(res[0]);
console.log(res[1]);



    google.auth.getApplicationDefault(function (err, authClient, projectId) {
     if (err) {
      throw err;
     }

      if (authClient.createScopedRequired && authClient.createScopedRequired()) {
                authClient = authClient.createScoped([
                    'https://www.googleapis.com/auth/cloud-platform',
                    'https://www.googleapis.com/auth/userinfo.email'
                ]);
            }



     console.log("Mesage exists and client function is authenticated");
     //console.log(authClient);




     const dataflow = google.dataflow({ version: 'v1b3', auth: authClient });
     console.log(`dataflow api call initiated`);

     dataflow.projects.locations.templates.create({
       projectId: projectId,
       location: 'europe-west1',

       resource: {
          parameters: {
               
                  extractQuery: res[0],
                
                  outPut:  res[1]+'out'

            },
         "environment": {
            "zone": 'europe-west1-b'
        },
            jobName: 'extract-' +Math.floor(100000 + Math.random() * 900000).toString(),
            
        }
     }, function(err, response) {
       if (err) {
         console.error("problem running dataflow template, error was: ", err);
       }
       console.log("Dataflow template response: ", response);

     })

    });

};
