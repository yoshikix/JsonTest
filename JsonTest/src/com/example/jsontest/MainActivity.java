package com.example.jsontest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import BackPressCloseHandler.BackPressCloseHandler;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	Button bn , bn2 , bn3 , bn4;
	EditText Et;
	TextView Tv;
	private BackPressCloseHandler backpress;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		bn = (Button)findViewById(R.id.button1);
		bn2 = (Button)findViewById(R.id.button2);
		bn3 = (Button)findViewById(R.id.button3);
		bn4 = (Button)findViewById(R.id.button4);
		
		Et = (EditText)findViewById(R.id.editText1);
		
		
		Tv = (TextView)findViewById(R.id.textView1);
		
		OnClickListener ol4 = new OnClickListener() {
			
			@SuppressWarnings("unused")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		        StrictMode.setThreadPolicy(policy);
				JSONObject json = new JSONObject();
				try {
					json.put("idx", Et.getText());
					HttpClient httpclient =  new DefaultHttpClient();
					HttpPost httpPost = new HttpPost("http://yoshikix.meximas.com/admin/card/json/getCardInfo.php"); 
					
					StringEntity se = new StringEntity(json.toString(),"UTF-8");
					httpPost.setEntity(se);
					httpPost.setHeader("Accept", "application/json;");
				    httpPost.setHeader("Content-type", "application/json;");
				    httpPost.setHeader("Accept-Charset", "UTF-8");
				    
				    
				    HttpResponse httpResponse = httpclient.execute(httpPost);
				    
				    Toast.makeText(getBaseContext(), Et.getText(), Toast.LENGTH_SHORT).show();
				    
				    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(),"UTF-8"));
				    String line = "";
				    String page="";
				    
				    while((line = bufferedReader.readLine())!=null){
				    	page+=line;
				    }
				    
				    
				    if(page!=null){
					    JSONObject returnJson = new JSONObject(page);
					    
					    String card_name = String.valueOf(returnJson.getString("card_name"));
					    
					    
					    
	//				    Toast.makeText(getBaseContext(), arr1.toString(), Toast.LENGTH_SHORT).show();
	
					    Intent intent = new Intent(MainActivity.this, ViewContent.class);
					    intent.putExtra("MEMO", card_name);
					    
		                startActivity(intent);
				    }else{
				    	Toast.makeText(getBaseContext(), "데이터가 없습니다.", Toast.LENGTH_SHORT).show();
				    }
				    
				    
				    
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			}
		};
		bn4.setOnClickListener(ol4);
		
		
		OnClickListener ol3 = new OnClickListener() {
			
			@SuppressWarnings("unused")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		        StrictMode.setThreadPolicy(policy);
				try{
					JSONObject json = new JSONObject();
					json.put("STR", Et.getText());
					
					//Toast.makeText(getBaseContext(), json.toString(), Toast.LENGTH_SHORT).show();
					
					HttpClient httpclient =  new DefaultHttpClient();
					HttpPost httpPost = new HttpPost("http://172.28.25.212/getData.php"); 
					
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
				    
				    if(page!=null){
					    
					    Tv.setText(page);
					    
					    JSONObject returnJson = new JSONObject(page);
					    
					    JSONArray arr1 = returnJson.getJSONArray("IDX");
					    JSONArray arr2 = returnJson.getJSONArray("SUBJECT");
					    JSONArray arr3 = returnJson.getJSONArray("MEMO");
					    
					    
	//				    Toast.makeText(getBaseContext(), arr1.toString(), Toast.LENGTH_SHORT).show();
	
					    Intent intent = new Intent(MainActivity.this, SubActivity.class);
					    
					    intent.putExtra("IDX", arr1.toString());
					    intent.putExtra("SUBJECT", arr2.toString());
					    intent.putExtra("MEMO", arr3.toString());
					    
					    intent.putExtra("CONTENTS", page);
					    
		                startActivity(intent);
				    }else{
				    	Toast.makeText(getBaseContext(), "데이터가 없습니다.", Toast.LENGTH_SHORT).show();
				    }
	                
					
				}catch(Exception e){
					
				}
								
			}
		};
		
		bn3.setOnClickListener(ol3);
		
		
		
		
		OnClickListener ol2 = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		        StrictMode.setThreadPolicy(policy);
		        
		        Tv.setText("");
		        
		        try{
		        	JSONObject json = new JSONObject();
		        	json.put("FRC_CD","1");
				  	json.put("BRND_CD", "2");
				  	json.put("STR_CD","3");
				  	json.put("TRAN_ID","4");
				  	json.put("SVC_CD", "5");

				  	json.put("BOOK_DT", "20150113");
				  	json.put("BOOK_TM", "1343");
				  	json.put("BOOK_TP","H");
				  	json.put("BOOK_MEM_NM","나이스정보");
				  	json.put("BOOK_MEM_TEL", "02-123-1234");
				  	json.put("MEM_QTY", "5");
				  	json.put("BOOK_RMK","안녕하세요?");
				  	json.put("MENU_CNT", "1");
				  	json.put("APPR_AMT", "11");
				  	json.put("APPR_CNT","12");
				  	
				  	ArrayList orderList = new ArrayList();
					
					JSONObject orderListMap = new JSONObject();
					orderListMap.put("MENU_CD", "AAA1");
					orderListMap.put("MENU_QTY", "BBB1");
					orderListMap.put("SET_MENU_CNT", 2);
					
					ArrayList setMenuList = new ArrayList();

					JSONObject setMenuMap = new JSONObject();
					setMenuMap.put("MST_CD", "MST_CD~~");
					setMenuMap.put("MNSD_CD", "MNSD_CD");
					setMenuMap.put("MNSD_QTY", "MNSD_QTY");
					setMenuList.add(setMenuMap);
					
					JSONObject setMenuMap2 = new JSONObject();
					setMenuMap2.put("MST_CD", "MST_CD~~");
					setMenuMap2.put("MNSD_CD", "MNSD_CD");
					setMenuMap2.put("MNSD_QTY", "MNSD_QTY");
					setMenuList.add(setMenuMap2);
					
					orderListMap.put("SET_MENU_LIST", setMenuList);
					
					
					orderList.add(orderListMap);
					
					json.put("ORDER_LIST", new JSONArray(orderList));
					
					
					ArrayList APPR_LIST = new ArrayList();
					
					JSONObject apprListMap = new JSONObject();
					apprListMap.put("APPR_SEQ", "APPR_SEQ~~");
					apprListMap.put("APPR_TP", "APPR_SEQ~~");
					apprListMap.put("APPR_AMT", "APPR_SEQ~~");
					
					APPR_LIST.add(apprListMap);
					
					JSONObject apprListMap2 = new JSONObject();
					apprListMap2.put("APPR_SEQ", "APPR_SEQ~~2");
					apprListMap2.put("APPR_TP", "APPR_SEQ~~2");
					apprListMap2.put("APPR_AMT", "APPR_SEQ~~2");
					
					APPR_LIST.add(apprListMap2);
					
					json.put("APPR_LIST", new JSONArray(APPR_LIST));
					
					
					HttpClient httpclient =  new DefaultHttpClient();
					//HttpPost httpPost = new HttpPost("http://172.28.25.212:8080/wisestruts/MENU_TEST.jsp");//테스트서버
					//HttpPost httpPost = new HttpPost("https://adm.nicepos.co.kr/MENU_ORDER_REQ.jsp"); //실서버
					HttpPost httpPost = new HttpPost("http://172.28.25.212:8080/wisestruts/MENU_RESERVATION_REQ.jsp"); 
					
					StringEntity se = new StringEntity(json.toString(),"UTF-8");
					httpPost.setEntity(se);
					
					HttpEntity entity = se;
					
					
					httpPost.setHeader("Accept", "application/json;");
				    httpPost.setHeader("Content-type", "application/json;");
				    httpPost.setHeader("Accept-Charset", "UTF-8");
				    
				    
				    //서버로 전송후에 서버에서 생성된 JSON 을 받아온다.~
				    HttpResponse httpResponse = httpclient.execute(httpPost);
				    
				    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(),"UTF-8"));
				    String line = "";
				    String page="";
				    
				    while((line = bufferedReader.readLine())!=null){
				    	page+=line;
				    }
				    
				    
				    //JSONObject jObj = new JSONObject(page);
				    
				    Tv.setText(page);
				    
//				    Toast.makeText(getBaseContext(), page, Toast.LENGTH_SHORT).show();
					
				  	
		        }catch(Exception e){
		        	e.printStackTrace();
		        }
		        
								
			}
		}; 
		
		bn2.setOnClickListener(ol2);
		
		//BUTTON1 ##########################################################################################################
		
		OnClickListener ol = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		        StrictMode.setThreadPolicy(policy);
				
		        Tv.setText("");
				try {
					/*
					*/
					  	JSONObject json = new JSONObject();
					  	json.put("FRC_CD","1");
					  	json.put("BRND_CD", "2");
					  	json.put("STR_CD","3");
					  	json.put("TRAN_ID","4");
					  	json.put("SVC_CD", "5");
					  	json.put("SELLS_DT", "6");
					  	json.put("TRAN_TP", "7");
					  	json.put("ORDER_TP","8");
					  	json.put("ORDER_NO", "9");
					  	json.put("MENU_CNT", "1");
					  	json.put("APPR_AMT", "11");
					  	json.put("APPR_CNT","12");
						
						
						ArrayList orderList = new ArrayList();
						
						JSONObject orderListMap = new JSONObject();
						orderListMap.put("MENU_CD", "AAA1");
						orderListMap.put("MENU_QTY", "BBB1");
						orderListMap.put("SET_MENU_CNT", 2);
						
						ArrayList setMenuList = new ArrayList();

						JSONObject setMenuMap = new JSONObject();
						setMenuMap.put("MST_CD", "MST_CD~~");
						setMenuMap.put("MNSD_CD", "MNSD_CD");
						setMenuMap.put("MNSD_QTY", "MNSD_QTY");
						setMenuList.add(setMenuMap);
						
						JSONObject setMenuMap2 = new JSONObject();
						setMenuMap2.put("MST_CD", "MST_CD~~");
						setMenuMap2.put("MNSD_CD", "MNSD_CD");
						setMenuMap2.put("MNSD_QTY", "MNSD_QTY");
						setMenuList.add(setMenuMap2);
						
						orderListMap.put("SET_MENU_LIST", setMenuList);
						
						
						orderList.add(orderListMap);
						
						json.put("ORDER_LIST", new JSONArray(orderList));
						
						
						ArrayList APPR_LIST = new ArrayList();
						
						JSONObject apprListMap = new JSONObject();
						apprListMap.put("APPR_SEQ", "APPR_SEQ~~");
						apprListMap.put("APPR_TP", "APPR_SEQ~~");
						apprListMap.put("APPR_AMT", "APPR_SEQ~~");
						
						APPR_LIST.add(apprListMap);
						
						JSONObject apprListMap2 = new JSONObject();
						apprListMap2.put("APPR_SEQ", "APPR_SEQ~~2");
						apprListMap2.put("APPR_TP", "APPR_SEQ~~2");
						apprListMap2.put("APPR_AMT", "APPR_SEQ~~2");
						
						APPR_LIST.add(apprListMap2);
						
						json.put("APPR_LIST", new JSONArray(APPR_LIST));
						
						HttpClient httpclient =  new DefaultHttpClient();
						//HttpPost httpPost = new HttpPost("http://172.28.25.212:8080/wisestruts/MENU_TEST.jsp");//테스트서버
						//HttpPost httpPost = new HttpPost("https://adm.nicepos.co.kr/MENU_ORDER_REQ.jsp"); //실서버
						HttpPost httpPost = new HttpPost("http://172.28.25.212:8080/wisestruts/MENU_ORDER_REQ.jsp"); //실서버
						
						StringEntity se = new StringEntity(json.toString(),"UTF-8");
						httpPost.setEntity(se);
						
						HttpEntity entity = se;
						
						
						httpPost.setHeader("Accept", "application/json;");
					    httpPost.setHeader("Content-type", "application/json;");
					    httpPost.setHeader("Accept-Charset", "UTF-8");
					    
					    
					    //서버로 전송후에 서버에서 생성된 JSON 을 받아온다.~
					    HttpResponse httpResponse = httpclient.execute(httpPost);
					    
					    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(),"UTF-8"));
					    String line = "";
					    String page="";
					    
					    while((line = bufferedReader.readLine())!=null){
					    	page+=line;
					    }
					    
					    
					    //JSONObject jObj = new JSONObject(page);
					    
					    Tv.setText(page);
					    
					    //Toast.makeText(getBaseContext(), page, Toast.LENGTH_SHORT).show();
					   // Toast.makeText(getBaseContext(), jObj.toString(), Toast.LENGTH_SHORT).show();
					    
					    
		        } catch (Exception e){
		        	// 에러메시지 출력 
		        	//et_webpage_src.setText(e.getMessage());
		        	e.printStackTrace();
				} finally {   
					// URL 연결 해제
				}	
                

			}
		};
		bn.setOnClickListener(ol);
		
		backpress = new BackPressCloseHandler(this);
		
	}
	
	@Override
    public void onBackPressed() {
        //super.onBackPressed();
        backpress.onBackPressed();
    }

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
