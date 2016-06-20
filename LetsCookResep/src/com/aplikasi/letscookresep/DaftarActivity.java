package com.aplikasi.letscookresep;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
 
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
 
public class DaftarActivity extends Activity {
 
    ProgressDialog pDialog;
 
    JSONParser jsonParser = new JSONParser();
    EditText email, passwd,name; 
    Button regist;
    private static String url = "http://192.168.43.146/letscook/daftar.php?";
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);
 
        email   =(EditText)findViewById(R.id.email);
        passwd=(EditText)findViewById(R.id.passwd);
        name    =(EditText)findViewById(R.id.name);
        regist = (Button)findViewById(R.id.regist);
        final EmailValidator emailValid = new EmailValidator();
        regist.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View v) {
 
                if(!emailValid.validate(email.getText().toString()))
                {
                    Toast.makeText(DaftarActivity.this, "Emailnya ga valid", Toast.LENGTH_LONG).show();
                    email.setText("");
                }
                else
                {
                    new daftarAku().execute();
                }
 
            }
        });
    }
 
    public class daftarAku extends AsyncTask<String, String, String>
    {
 
        String success;
 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DaftarActivity.this);
            pDialog.setMessage("Proses mendaftar...");
            pDialog.setIndeterminate(false);
            pDialog.show();
        }
 
        @Override
        protected String doInBackground(String... params) {
 
            String strnama      = name.getText().toString();
            String stremail     = email.getText().toString();
            String strpassword  = passwd.getText().toString();
 
            List<NameValuePair> nvp = new ArrayList<NameValuePair>();
            nvp.add(new BasicNameValuePair("nama", strnama));
            nvp.add(new BasicNameValuePair("email", stremail));
            nvp.add(new BasicNameValuePair("password", strpassword));
 
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
                Toast.makeText(getApplicationContext(), "Regitrasi sukses", Toast.LENGTH_LONG).show();
 
            } else {
                Toast.makeText(getApplicationContext(), "Registrasi gagal", Toast.LENGTH_LONG).show();
            }
        }
 
    }
    public class EmailValidator{
 
          private Pattern pattern;
          private Matcher matcher;
 
          private static final String EMAIL_PATTERN = 
                         "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
 
          public EmailValidator()
          {
              pattern = Pattern.compile(EMAIL_PATTERN);
          }
 
          public boolean validate(final String hex)
          {
              matcher = pattern.matcher(hex);
              return matcher.matches();  
          }
      }
 
}
