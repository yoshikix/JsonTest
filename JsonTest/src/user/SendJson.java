package user;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.app.Application;
import android.os.StrictMode;
import android.widget.Toast;

public class SendJson extends Application {
	 String page="";
	public JSONObject getJsonObject(JSONObject obj , String url){
		//Toast.makeText(getBaseContext(), url, Toast.LENGTH_SHORT).show();
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        StringEntity se;
        
        JSONObject returnJson = null;
		try {
			se = new StringEntity(obj.toString(),"UTF-8");
			httpPost.setEntity(se);
			httpPost.setHeader("Accept", "application/json;");
		    httpPost.setHeader("Content-type", "application/json;");
		    httpPost.setHeader("Accept-Charset", "UTF-8");
		    
		    HttpResponse httpResponse = client.execute(httpPost);
		    
		    
		    
		    
		    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(),"UTF-8"));
		    String line = "";
		   
		    
		    while((line = bufferedReader.readLine())!=null){
		    	page+=line;
		    }
		    returnJson = new JSONObject(page);
	                
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR");
			e.printStackTrace();
		}
		
		return returnJson;
	}
}
