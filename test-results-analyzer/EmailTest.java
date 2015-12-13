package org.jenkinsci.plugins.testresultsanalyzer;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Test;
import org.bson.Document;
import org.junit.Before;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import hudson.model.User;
import org.mockito.Mockito;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;

import hudson.model.AbstractProject;
import hudson.model.AbstractBuild;

public class EmailTest{
        private String testProjName = "testProjectName";
        private TestResultsAnalyzerAction traa;
        private AbstractBuild build;
        private AbstractProject project;

    @Before
    public void init(){
        build = Mockito.mock(AbstractBuild.class);
        project = Mockito.mock(AbstractProject.class);
        Mockito.when(project.getName()).thenReturn(testProjName);
    }


        @Test
        public void testSubmittingInfo1(){
        traa = new TestResultsAnalyzerAction(project);

        // connect to MongoDB
                MongoClient mongoClient = new MongoClient("localhost",27017);
                MongoDatabase mongoDatabase = mongoClient.getDatabase("emailDatabaseName");
                MongoCollection mongoCollection = mongoDatabase.getCollection(testProjName);

                mongoCollection.drop();

                String netID = "darkoo";
                String email = "darkoo@illinois.edu";

                // jsonobject should have following fields
                // netID, email
                JSONObject jobj = new JSONObject();
                jobj.put("netid", netID);
                jobj.put("email", email);

                traa.submitEmail(jobj.toString());

        Document result = (Document) mongoCollection.find(eq("netid", "darkoo")).first();

                mongoClient.close();
                assertEquals(email, result.getString("email"));
        }

        
        @Test
        public void testSubmittingInfo2(){
        traa = new TestResultsAnalyzerAction(project);

        // connect to MongoDB
                MongoClient mongoClient = new MongoClient("localhost",27017);
                MongoDatabase mongoDatabase = mongoClient.getDatabase("emailDatabaseName");
                MongoCollection mongoCollection = mongoDatabase.getCollection(testProjName);

                mongoCollection.drop();

                String netID = "yang";
                String email = "yang@illinois.edu";

                // jsonobject should have following fields
                // netID, email
                JSONObject jobj = new JSONObject();
                jobj.put("netid", netID);
                jobj.put("haha", email);

                assertNotEquals("success", traa.submitEmail(jobj.toString()));
        }

        @Test
        public void testSubmittingInfo3() {
        traa = new TestResultsAnalyzerAction(project);
        MongoClient mongoClient = new MongoClient("localhost",27017);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("emailDatabaseName");
        MongoCollection mongoCollection = mongoDatabase.getCollection(testProjName);

         String netID = "a";
         String email = "a@123.com";

         JSONObject jobj = new JSONObject();
         jobj.put("netid", netID);
         jobj.put("email", email);

       traa.submitEmail(jobj.toString());

        String query = "a";
        String result = traa.getEmail(query);
        mongoClient.close();
        assertEquals(result, email);
     }


    @Test
    public void testGettingEmail1() {
      traa = new TestResultsAnalyzerAction(project);
        MongoClient mongoClient = new MongoClient("localhost",27017);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("emailDatabaseName");
        MongoCollection mongoCollection = mongoDatabase.getCollection(testProjName);

         String netID = "abcde";
         String email = "abcde@illinois.edu";

         JSONObject jobj = new JSONObject();
         jobj.put("netid", netID);
         jobj.put("email", email);

       traa.submitEmail(jobj.toString());

        String query = "abcde";
        String result = traa.getEmail(query);
        mongoClient.close();
        assertEquals(result, email);
   }

    @Test
    public void testGettingEmail2() {
      traa = new TestResultsAnalyzerAction(project);
        MongoClient mongoClient = new MongoClient("localhost",27017);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("emailDatabaseName");
        MongoCollection mongoCollection = mongoDatabase.getCollection(testProjName);

         String netID = "verylongtextwithverylongname";
         String email = "verylongtextwithverylongname@illinois.edu";

         JSONObject jobj = new JSONObject();
         jobj.put("netid", netID);
         jobj.put("email", email);

       traa.submitEmail(jobj.toString());

        String query = "verylongtextwithverylongname";
        String result = traa.getEmail(query);
        mongoClient.close();
        assertEquals(result, email);
   }

      @Test
      public void testGettingEmail3() {
      traa = new TestResultsAnalyzerAction(project);
        MongoClient mongoClient = new MongoClient("localhost",27017);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("emailDatabaseName");
        MongoCollection mongoCollection = mongoDatabase.getCollection(testProjName);

        String query = "hahahahahahahahahahahahahahahahahahahahahahahahahahahahiahiahiahia";
        String result = traa.getEmail(query);
        mongoClient.close();
        assertNull(result);
   }


}