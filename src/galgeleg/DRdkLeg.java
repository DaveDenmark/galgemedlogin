package galgeleg;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONObject;


public class DRdkLeg {
    public String getWordsDR(){
        
    String svar = hitEmUp("http://dr.dk/mu-online/api/1.3/list/view/mostviewed?channel=dr1&channeltype=TV&limit=3&offset=0");
    //System.out.println(svar);
    
    String streng;
    
    streng = "";
    
    try {
    //Parse svar som et JSON-objekt
    JSONObject json = new JSONObject(svar);
    //System.out.println("json=" + json);

    String slug1 = json.getJSONArray("Items").getJSONObject(0).get("Slug").toString();
    String slug2 = json.getJSONArray("Items").getJSONObject(1).get("Slug").toString();
    String slug3 = json.getJSONArray("Items").getJSONObject(2).get("Slug").toString();
  
    String svar1 = hitEmUp("http://www.dr.dk/mu-online/api/1.3/programcard/" + slug1);
    String svar2 = hitEmUp("http://www.dr.dk/mu-online/api/1.3/programcard/" + slug2);
    String svar3 = hitEmUp("http://www.dr.dk/mu-online/api/1.3/programcard/" + slug3);
    
    JSONObject json1 = new JSONObject(svar1);
    JSONObject json2 = new JSONObject(svar2);
    JSONObject json3 = new JSONObject(svar3);
    
    
    streng = (json1.get("Description").toString() + json2.get("Description").toString() + json3.get("Description").toString());
    
    
    
    } catch (Exception e) {
    e.printStackTrace();
  }
    return streng;
    
    }
    
    private static String hitEmUp(String url) {
        Client client = ClientBuilder.newClient();
    Response res = client.target(url)
            .request(MediaType.APPLICATION_JSON).get();
    return res.readEntity(String.class);
    }
    
}
