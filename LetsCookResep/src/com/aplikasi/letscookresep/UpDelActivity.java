package com.aplikasi.letscookresep;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Afifatul on 6/18/2015.
 */
public class UpDelActivity extends Activity {

    JSONParser jParser = new JSONParser();
    String url_update= "http://192.168.43.146/letscook/update.php";
    String url_hapus= "http://192.168.43.146/letscook/hapus.php";
    // JSON Node names, ini harus sesuai yang di API
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_NAMA = "nama";
    public static final String TAG_JUDUL = "nama_resep";
    public static final String TAG_KATEGORI = "kategori";
    public static final String TAG_DESKRIPSI = "deskripsi";
    public static final String TAG_TANGGAL = "tanggal_update";

    EditText EditTxtNama, EditTxtNamaResep, EditTxtKategori, EditTxtDeskripsi;
    Button buttonUpdate, buttonDelete;
    String namaLengkapStr, judulStr, categoriStr, deskripsiStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_del);

        EditTxtNama = (EditText) findViewById(R.id.editTextNama);
        EditTxtNamaResep = (EditText) findViewById(R.id.editTextNamaResep);
        EditTxtKategori = (EditText) findViewById(R.id.editTextKategori);
        EditTxtDeskripsi = (EditText) findViewById(R.id.editTextDeskripsi);

        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonDelete = (Button) findViewById(R.id.buttonDelete);

        //menangkap bundle yang telah di-parsed dari MainActivity
        Bundle b = getIntent().getExtras();
        String isi_nama = b.getString("nama");
        String isi_namaresep= b.getString("nama_resep");
        String isi_kategori= b.getString("nim_mhs");
        String isi_deskripsi= b.getString("deskripsi");
        //meng-set bundle tersebut
        EditTxtNama.setText(isi_nama);
        EditTxtNamaResep.setText(isi_namaresep);
        EditTxtKategori.setText(isi_kategori);
        EditTxtDeskripsi.setText(isi_deskripsi);

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                namaLengkapStr = EditTxtNama.getText().toString();
                judulStr = EditTxtNamaResep.getText().toString();
                categoriStr = EditTxtKategori.getText().toString();
                deskripsiStr = EditTxtDeskripsi.getText().toString();
                
                new UpdateMhsTask().execute();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                namaLengkapStr = EditTxtNama.getText().toString();
                new DeleteMhsTask().execute();
            }
        });
    }

    class UpdateMhsTask extends AsyncTask<String, Void, String>
    {
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(UpDelActivity.this);
            pDialog.setMessage("Mohon Tunggu..");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... sText) {

            List<NameValuePair> parameter = new ArrayList<NameValuePair>();
            parameter.add(new BasicNameValuePair(TAG_NAMA, namaLengkapStr));
            parameter.add(new BasicNameValuePair(TAG_JUDUL, judulStr));
            parameter.add(new BasicNameValuePair(TAG_KATEGORI, categoriStr));
            parameter.add(new BasicNameValuePair(TAG_DESKRIPSI, deskripsiStr));

            try {
                JSONObject json = jParser.makeHttpRequest(url_update,"POST", parameter);

                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {

                    return "OK";
                }
                else {

                    return "FAIL";

                }

            } catch (Exception e) {
                e.printStackTrace();
                return "Exception Caught";
            }

        }

        @Override
        protected void onPostExecute(String result) {

            super.onPostExecute(result);
            pDialog.dismiss();

            if(result.equalsIgnoreCase("Exception Caught"))
            {

                Toast.makeText(UpDelActivity.this, "Unable to connect to server,please check your internet connection!", Toast.LENGTH_LONG).show();
            }

            if(result.equalsIgnoreCase("FAIL"))
            {
                Toast.makeText(UpDelActivity.this, "Fail.. Try Again", Toast.LENGTH_LONG).show();
            }

            else {
                Intent i = null;
                i = new Intent(UpDelActivity.this, MainActivity.class);
                startActivity(i);
            }

        }
    }


    class DeleteMhsTask extends AsyncTask<String, Void, String>
    {
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(UpDelActivity.this);
            pDialog.setMessage("Mohon Tunggu..");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... sText) {

            List<NameValuePair> parameter = new ArrayList<NameValuePair>();
            parameter.add(new BasicNameValuePair(TAG_NAMA, namaLengkapStr));

            try {
                JSONObject json = jParser.makeHttpRequest(url_hapus,"POST", parameter);

                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {

                    return "OK";
                }
                else {

                    return "FAIL";

                }

            } catch (Exception e) {
                e.printStackTrace();
                return "Exception Caught";
            }

        }

        @Override
        protected void onPostExecute(String result) {

            super.onPostExecute(result);
            pDialog.dismiss();

            if(result.equalsIgnoreCase("Exception Caught"))
            {

                Toast.makeText(UpDelActivity.this, "Unable to connect to server,please check your internet connection!", Toast.LENGTH_LONG).show();
            }

            if(result.equalsIgnoreCase("FAIL"))
            {
                Toast.makeText(UpDelActivity.this, "Fail.. Try Again", Toast.LENGTH_LONG).show();
            }

            else {
                Intent i = null;
                i = new Intent(UpDelActivity.this, PostinganActivity.class);
                startActivity(i);
            }

        }
    }
}

