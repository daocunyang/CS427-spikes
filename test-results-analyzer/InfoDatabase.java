package org.jenkinsci.plugins.testresultsanalyzer;

import java.util.LinkedList;
import org.bson.Document;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.*;

import hudson.model.User;

public class InfoDatabase {

        String host;
        int port;
        String project;
      //  MongoClient mongoClient;        

        /**
 *       * The constructor, takes a hostname, portnumber, and projectname for storage when accessing the database
 *               */ 
        public InfoDatabase(String hostName, int portNumber, String projectName) {
                host = hostName;
                port = portNumber;
                project = projectName;
        //        mongoClient = new MongoClient(host, port);
    
        }


    public String submitEmail(String jsonData) {

          try {
                JSONObject data = JSONObject.fromObject(jsonData);
                String user_netID = data.getString("netid");
                String user_email = data.getString("email");
            String projectName = project;

                MongoClient mongoClient = new MongoClient(host, port);


                MongoDatabase mongoDatabase = mongoClient.getDatabase("emailDatabaseName");
                MongoCollection mongoCollection = mongoDatabase.getCollection(projectName);

            Document infoDoc = new Document(data);
            infoDoc.append("email", user_email).append("netid", user_netID);

                mongoCollection.insertOne(infoDoc);
                mongoClient.close();

          }  catch (Exception e) {
           //  return "failure";
             return e.getMessage();
           }
           return "success";
        }


  public String getEmail(String data) { // given netID, find email
    try {
        MongoClient mongoClient = new MongoClient(host, port);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("emailDatabaseName");
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(project);

    Document result = mongoCollection.find(eq("netid", data)).first();
    //return result;
    //MongoCursor<Document> cursor = mongoCollection.find(eq("net_id", data)).iterator();
    String val = result.getString("email");
   mongoClient.close();
   return val;
 } catch(Exception e) {
    return e.getMessage();
  }

}

