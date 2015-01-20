package com.example.jsontest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ViewContent extends Activity {
	TextView tv;
	Button bt ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_content);
		
		bt = (Button)findViewById(R.id.button1);
		
		OnClickListener ol = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				JSONObject json = new JSONObject();
				try {
					json.put("EXEC", "DELETE ALL");
					
					HttpClient httpclient = new DefaultHttpClient();
					HttpPost httppost = new HttpPost("http://172.28.25.212/getExecute.php");
					
					
					StringEntity se = new StringEntity(json.toString(),"UTF-8");
					httppost.setEntity(se);
					
					httppost.setHeader("Accept","application/json;");
					httppost.setHeader("Content-type","application/json;");
					httppost.setHeader("Accept-Charset","UTF-8;");
					
					HttpResponse  httpResponse = httpclient.execute(httppost);
					
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(),"UTF-8"));
				    String line = "";
				    String page="";
				    
				    while((line = bufferedReader.readLine())!=null){
				    	page+=line;
				    }
				    
				    JSONObject jObj = new JSONObject(page);
				    String suc = jObj.getString("SUCCESS");
				    
				    
				    
				    
				    //Toast.makeText(getBaseContext(), suc.toString(), Toast.LENGTH_SHORT).show();
				    
				    if(!suc.equals("")){
				    	Toast.makeText(getBaseContext(), "로그삭제성공", Toast.LENGTH_SHORT).show();				    	
				    }else{
				    	Toast.makeText(getBaseContext(), "로그삭제실패", Toast.LENGTH_SHORT).show();
				    }
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		};
		
		bt.setOnClickListener(ol);
		
		
		tv = (TextView)findViewById(R.id.textView1);
		
		Intent intent = getIntent();
		
		String memo = intent.getExtras().getString("MEMO");
		memo = memo.replace("\\", "");
		String idx = intent.getExtras().getString("IDX");
		
		
		tv.setText("IDX = " + idx + "\r\n"+memo);
	}
}
