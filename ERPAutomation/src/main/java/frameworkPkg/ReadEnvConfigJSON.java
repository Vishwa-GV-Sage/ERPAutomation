package frameworkPkg;

//ReadEnvConfigJSON.java
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ReadEnvConfigJSON {

 private JSONObject json;

 public ReadEnvConfigJSON(String filePath) {
     try {
         String jsonString = new String(Files.readAllBytes(Paths.get(filePath)));
         JSONParser parser = new JSONParser();
         this.json = (JSONObject) parser.parse(jsonString);
     } catch (Exception e) {
         e.printStackTrace();
     }
 }

 public String getJsonValue(String key) {
     String[] keys = key.split("\\.");
     JSONObject currentJson = json;

     for (String k : keys) {
         if (currentJson.containsKey(k)) {
             Object obj = currentJson.get(k);
             if (obj instanceof JSONObject) {
                 currentJson = (JSONObject) obj;
             } else {
                 return obj.toString();
             }
         } else {
             return null; // Key not found
         }
     }

     return null; // Key not found
 }
}

