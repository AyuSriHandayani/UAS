package com.aplikasi.letscookresep;

import java.util.ArrayList;
import java.util.HashMap;
 
import org.json.JSONArray;
import org.json.JSONObject;
 
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.*;
 
public class MainActivity extends Activity  {
 
    Button login, daftar;
    Intent a;
    EditText nama,password;
    String url, success;
    SessionManager session;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        daftar = (Button) findViewById(R.id.daftar);
        daftar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this, DaftarActivity.class);
				startActivity(i);
			}
		});
        
        session = new SessionManager(getApplicationContext());
        login = (Button) findViewById(R.id.btnLogin);
             
        nama = (EditText) findViewById(R.id.nama);
        password = (EditText) findViewById(R.id.password);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = "http://192.168.43.146/letscook/login.php?" + "nama="
                        + nama.getText().toString() + "&password="
                        + password.getText().toString();
 
                if (nama.getText().toString().trim().length() > 0
                        && password.getText().toString().trim().length() > 0) 
                {
                    new Masuk().execute();
                } 
                else
                {
                    Toast.makeText(getApplicationContext(), "Username/password masih kosong gan.!!", Toast.LENGTH_LONG).show();
                }
            }
        });
        

    }
 
    public class Masuk extends AsyncTask<String, String, String> 
    {
        ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();
        ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
 
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Tunggu Bentar ya...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        
        @Override
        protected String doInBackground(String... arg0) {
            JSONParser jParser = new JSONParser();
 
            JSONObject json = jParser.getJSONFromUrl(url);
 
            try {
                success = json.getString("success");
 
                Log.e("error", "nilai sukses=" + success);
 
                JSONArray hasil = json.getJSONArray("btnLogin");
 
                if (success.equals("1")) {
 
                    for (int i = 0; i < hasil.length(); i++) {
 
                        JSONObject c = hasil.getJSONObject(i);
 
                        String nama = c.getString("nama").trim();
                        String email = c.getString("email").trim();
                        session.createLoginSession(nama, email);
                        Log.e("ok", " ambil data");
 
                    }
                } else {
                    Log.e("error", "tidak bisa ambil data 0");
                }
 
            } catch (Exception e) {
                // TODO: handle exception
                Log.e("error", "tidak bisa ambil data 1");
            }
 
            return null;
        }
        
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pDialog.dismiss();
            if (success.equals("1")) {
                a = new Intent(MainActivity.this, FormTambahActivity.class);
                startActivity(a);
                finish();
            } 
            else {
 
                Toast.makeText(getApplicationContext(), "Username/password salah gan.!!", Toast.LENGTH_LONG).show();
            }
        }
    } 
}