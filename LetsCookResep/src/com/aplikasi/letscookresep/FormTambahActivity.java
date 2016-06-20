package com.aplikasi.letscookresep;

import java.util.ArrayList;
import java.util.List;
 
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
 
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
 
public class FormTambahActivity extends Activity {
 
    ProgressDialog pDialog;
 
    JSONParser jsonParser = new JSONParser();
    EditText nama_lengkap, judul_resep,category,deskripsi; 
    Button btnSimpan;
    private static String url = "http://192.168.43.146/letscook/tambah.php?";
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form__tambah);
 
        nama_lengkap   =(EditText)findViewById(R.id.nama_lengkap);
        judul_resep =(EditText)findViewById(R.id.judul_resep);
        category    =(EditText)findViewById(R.id.category);
        deskripsi    =(EditText)findViewById(R.id.deskripsi);
        btnSimpan = (Button)findViewById(R.id.btnSimpan);
        
        btnSimpan.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View v) {
                
                    new daftarAku().execute();
            }
        });
    }
 
    public class daftarAku extends AsyncTask<String, String, String>
    {
 
        String success;
 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(FormTambahActivity.this);
            pDialog.setMessage("Proses Pengisian Data");
            pDialog.setIndeterminate(false);
            pDialog.show();
        }
 
        @Override
        protected String doInBackground(String... params) {
 
            String strnama      = nama_lengkap.getText().toString();
            String strnamaresep = judul_resep.getText().toString();
            String strkategori  = category.getText().toString();
            String strdeskripsi  = deskripsi.getText().toString();
 
            List<NameValuePair> nvp = new ArrayList<NameValuePair>();
            nvp.add(new BasicNameValuePair("nama_lengkap", strnama));
            nvp.add(new BasicNameValuePair("judul_resep", strnamaresep));
            nvp.add(new BasicNameValuePair("category", strkategori));
            nvp.add(new BasicNameValuePair("deskripsi", strdeskripsi));
 
            JSONObject json = jsonParser.makeHttpRequest(url, "POST", nvp);
            try { 
                success = json.getString("success");
 
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
 
            return null;
        }
 
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
 
            if (success.equals("1")) {
                Toast.makeText(getApplicationContext(), "Data Sukses", Toast.LENGTH_LONG).show();
 
            } else {
                Toast.makeText(getApplicationContext(), "Data Tidak Terinput", Toast.LENGTH_LONG).show();
            }
        }
 
    }
 
}
