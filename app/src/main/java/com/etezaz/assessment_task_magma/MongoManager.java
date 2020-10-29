package com.etezaz.assessment_task_magma;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

/**
 * Created by Etezaz Abo Al-Izam on 10/28/2020.
 * Automata4Group
 * etezazizam44@gmail.com
 */
public class MongoManager {

    static String host, dbname, user, password;

    public void addData(int repeats) {
        try {

            Properties prop = new Properties();
            prop.load(new FileInputStream(System.getProperty("user.home") + "/mydb.cfg"));

            host = prop.getProperty("35.222.197.123").toString();
            dbname = prop.getProperty("waseet_data").toString();
            user = prop.getProperty("waseet_scraper").toString();
            password = prop.getProperty("WaS33t-sCraPper").toString();

            System.out.println("host: " + host + "\ndbname: " + dbname + "\nuser: " + user + "\npassword: " + password);

            MongoCredential credential = MongoCredential.createCredential(user, dbname, password.toCharArray());
            MongoClient mongoClient = new MongoClient(new ServerAddress(host), Arrays.asList(credential));

            MongoDatabase db = mongoClient.getDatabase(dbname);

            try {
                db.getCollection("mycollection");
            } catch (Exception e) {
                db.createCollection("mycollection", null);
                System.out.println("Repeats: " + repeats);
                for (int i = 1; i <= repeats; i++) {
                    Document document = new Document("data", new Date());
                    db.getCollection("mycollection").insertOne(document);
                    System.out.println("INFO: row added " + document);
                }
            } finally {
                mongoClient.close();
            }
        } catch (IOException ex) {
        }
    }
}