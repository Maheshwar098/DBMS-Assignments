import com.mongodb.MongoClientURI;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

public class Test3
{
    static void displayLine()
    {
        System.out.println("----------------------------------------------------------");
    }
    public static void main(String [] args)
    {
        // connecting to server
        String url = "mongodb://localhost:27017";
        MongoClientURI mongoClientUri = new MongoClientURI(url);
        MongoClient mongoClient = new MongoClient(mongoClientUri);

        // using database
        String databaseName = "test_db";
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        System.out.println("Successfully connected to database : " + database.getName());
        displayLine();

        // using collection
        String collectionName = "test_collection";
        MongoCollection<Document>collection = database.getCollection(collectionName);
        System.out.println("Using collection : "+collection);
        displayLine();

        // inserting document
        Document newDocument = new Document();
        newDocument.append("field_1","val_1_1");
        newDocument.append("field_2","val_1_2");
        newDocument.append("field_3","val_1_3");
        collection.insertOne(newDocument);
        System.out.println("Inserted document : " + newDocument.toJson());
        displayLine();

        newDocument = new Document();
        newDocument.append("field_1","val_2_1");
        newDocument.append("field_2","val_2_2");
        newDocument.append("field_3","val_2_3");
        collection.insertOne(newDocument);
        System.out.println("Inserted document : "+newDocument.toJson());
        displayLine();

        // Displaying all inserted documents
        MongoCursor<Document>iterator = collection.find().iterator();
        System.out.println("Displaying documents : ");
        while(iterator.hasNext())
        {
            Document currDoc = iterator.next();
            System.out.println(currDoc.toJson());
        }
        displayLine();

        // finding document
        Document queryDocument = new Document();
        String fieldToFind = "field_2";
        String valToFind = "val_2_2";
        queryDocument.append(fieldToFind,valToFind);

        Document foundDocument = collection.find(queryDocument).first();
        System.out.println("Found Document : "+foundDocument.toJson());
        displayLine();

        // updating document
        Document findQuery = new Document("field_1", "val_2_1");
        Document updateDoc = new Document("$set", new Document("field_2","updated_val_2_2"));
        collection.updateOne(findQuery,updateDoc);
        Document updatedDoc = collection.find(findQuery).first();
        System.out.println("updated Document : "+updatedDoc.toJson());
        displayLine();

        // Deleting document
        Document docToDel = new Document("field_3","val_1_3");
        collection.deleteOne(docToDel);
        System.out.println("Document deleted successfully");
        displayLine();

        collection.drop();
        mongoClient.close();
    }
}
