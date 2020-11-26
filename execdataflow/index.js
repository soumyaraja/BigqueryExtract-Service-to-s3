const google = require('googleapis');
const projectId = "project name";
const TEMPLATE_PATH = "dataflow template JSONPath";
const JOB_NAME = "job name of the data flow";
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
       projectId: 'comm-forecastservice-uat',
       location: 'europe-west1',

       resource: {
          parameters: {
               // extractQuery: Buffer.from(pubsubMessage, 'base64').toString(),
                  extractQuery: res[0],
                //outPut: 'gs://comm-forecastservice-dev-poc/'+dirName+'/output'
                  outPut:  res[1]+'out'

            },
         "environment": {
            "zone": 'europe-west1-b'
        },
            jobName: 'extract-' +Math.floor(100000 + Math.random() * 900000).toString(),
            gcsPath: 'gs://comm-forecastservice-sit/extractTemp/GCSdataflow_extraction.json'
        }
     }, function(err, response) {
       if (err) {
         console.error("problem running dataflow template, error was: ", err);
       }
       console.log("Dataflow template response: ", response);

     })

    });

};
