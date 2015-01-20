package com.example.jsontest;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class SubActivity extends Activity {

	ListView lv;
	HashMap map = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sub);
		
		
		lv = (ListView)findViewById(R.id.listView1);
		
		Intent intent = getIntent();
		String arr1 = intent.getExtras().getString("IDX");
		String arr2 = intent.getExtras().getString("SUBJECT");
		String content = intent.getExtras().getString("CONTENTS");
		
		
		try {
			JSONObject json = new JSONObject(content);
			
			JSONArray idx = json.getJSONArray("IDX");
			JSONArray subject = json.getJSONArray("SUBJECT");
			JSONArray memo = json.getJSONArray("MEMO");
			
			
			ArrayList lists = new ArrayList();
			
			for(int i = 0 ; i < idx.length(); i++){
				map = new HashMap();
				map.put("idx", idx.get(i));
				map.put("subject" , subject.get(i));
				map.put(idx.get(i) , memo.get(i));
				
				
				//lists.add(subject.get(i));
				lists.add(idx.get(i));
				
				
			}
			
			ArrayAdapter adapter = new ArrayAdapter<String>(this , android.R.layout.simple_list_item_1 , lists);
			
			lv.setAdapter(adapter);
			
			
			lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					
					String tv = (String)parent.getAdapter().getItem(position);
					
					JSONObject json = new JSONObject();
					
					try {
						json.put("IDX", tv);
						
						HttpClient httpclient = new DefaultHttpClient();
						HttpPost httpPost = new HttpPost("http://172.28.25.212/getData2.php");
						
						StringEntity se = new StringEntity(json.toString(),"UTF-8");
						httpPost.setEntity(se);
						
						httpPost.setHeader("Accept", "application/json;");
					    httpPost.setHeader("Content-type", "application/json;");
					    httpPost.setHeader("Accept-Charset", "UTF-8");
					    
					    HttpResponse httpResponse = httpclient.execute(httpPost);
					    
					    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(),"UTF-8"));
					    String line = "";
					    String page="";
					    
					    while((line = bufferedReader.readLine())!=null){
					    	page+=line;
					    }
					    
					    //Toast.makeText(getBaseContext(), page, Toast.LENGTH_SHORT).show();
					    

					    JSONObject returnJson = new JSONObject(page);
					    JSONArray memo = returnJson.getJSONArray("CONTENTS");
					    String str = (String)memo.get(0);
					    
					    str = str.replaceAll("//", "");

					    
					    
					    Intent intent = new Intent(SubActivity.this, ViewContent.class);
					    intent.putExtra("MEMO", str);
					    intent.putExtra("IDX", tv);
					    startActivity(intent);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}catch(Exception e){
						
					}
				}
				
			});
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sub, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
