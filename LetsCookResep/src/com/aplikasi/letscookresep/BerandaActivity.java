package com.aplikasi.letscookresep;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class BerandaActivity extends Activity {

	Button resep, tambahResep, postingan;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_beranda);
		
		resep = (Button) findViewById(R.id.resep);
        resep.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(BerandaActivity.this, ResepActivity.class);
				startActivity(i);
			}
		});
        
        tambahResep = (Button) findViewById(R.id.tambahResep);
        tambahResep.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(BerandaActivity.this, TambahActivity.class);
				startActivity(i);
			}
		});
		
        postingan = (Button) findViewById(R.id.postingan);
        postingan.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(BerandaActivity.this, PostinganActivity.class);
				startActivity(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.beranda, menu);
		return true;
	}

}
