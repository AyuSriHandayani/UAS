package com.aplikasi.letscookresep;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;


public class ResepActivity extends Activity {

    ListView list;

    JSONParser jParser = new JSONParser();
    ArrayList<Resep> daftar_rsp = new ArrayList<Resep>();
    JSONArray daftarRsp = null;
    String url_rsp = "http://192.168.56.1/letscook/tampil.php";
    // JSON Node names, ini harus sesuai yang di API
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_TABEL = "resep";
    public static final String TAG_NAMA = "nama_lengkap";
    public static final String TAG_JUDUL = "judul_resep";
    public static final String TAG_KATEGORI = "category";
    public static final String TAG_DESKRIPSI = "deskripsi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resep);
        
        list = (ListView) findViewById(R.layout.activity_list_resep);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int urutan, long id) {
                //dapatkan data id, nama, nim mahasiswa dan simpan dalam variable string
                String nama_lengkap = ((TextView) view.findViewById(R.id.nama_lengkap)).getText().toString();
                String judul_resep = ((TextView) view.findViewById(R.id.nama_resep)).getText().toString();
                String category = ((TextView) view.findViewById(R.id.category)).getText().toString();
                String deskripsi= ((TextView) view.findViewById(R.id.deskripsi)).getText().toString();
                
                //varible string tadi kita simpan dalam suatu Bundle, dan kita parse bundle tersebut bersama intent menuju kelas UpdateDeleteActivity
                Intent i = null;
                i = new Intent(ResepActivity.this,ListResepActivity.class);
                Bundle b = new Bundle();
                b.putString("nama_lengkap", nama_lengkap);
                b.putString("judul_resep", judul_resep);
                b.putString("category", category);
                b.putString("deskripsi", deskripsi);
                
                i.putExtras(b);
                startActivity(i);
            }
        });
    }


  

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.action_settings) {
            Intent i = new Intent(ResepActivity.this, ListResepActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    class ReadMhsTask extends AsyncTask<String, Void, String>
    {
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ResepActivity.this);
            pDialog.setMessage("Mohon Tunggu..");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... sText) {
            String returnResult = getRspList(); //memanggil method getMhsList()
            return returnResult;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pDialog.dismiss();
            if(result.equalsIgnoreCase("Exception Caught"))
            {
                Toast.makeText(ResepActivity.this, "Unable to connect to server,please check your internet connection!", Toast.LENGTH_LONG).show();
            }

            if(result.equalsIgnoreCase("no results"))
            {
                Toast.makeText(ResepActivity.this, "Data empty", Toast.LENGTH_LONG).show();
            }
            else
            {
                list.setAdapter(new ResepAdapter(ResepActivity.this, daftar_rsp)); //Adapter menampilkan data mahasiswa ke dalam listView
            }
        }


        //method untuk memperoleh daftar mahasiswa dari JSON
        public String getRspList()
        {
            Resep tempRsp = new Resep();
            List<NameValuePair> parameter = new ArrayList<NameValuePair>();
            try {
                JSONObject json = jParser.makeHttpRequest(url_rsp,"POST", parameter);

                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) { //Ada record Data (SUCCESS = 1)
                    //Getting Array of daftar_mhs
                    daftarRsp = json.getJSONArray(TAG_TABEL);
                    // looping through All daftar_mhs
                    for (int i = 0; i < daftarRsp.length() ; i++){
                        JSONObject c = daftarRsp.getJSONObject(i);
                        tempRsp = new Resep();
                        tempRsp.setNama(c.getString(TAG_NAMA));
                        tempRsp.setNamaResep(c.getString(TAG_JUDUL));
                        tempRsp.setKategori(c.getString(TAG_KATEGORI));
                        tempRsp.setDeskripsi(c.getString(TAG_DESKRIPSI));
                        daftar_rsp.add(tempRsp);
                    }
                    return "OK";
                }
                else {
                    //Tidak Ada Record Data (SUCCESS = 0)
                    return "no results";
                }

            } catch (Exception e) {
                e.printStackTrace();
                return "Exception Caught";
            }
        }

    }
}



