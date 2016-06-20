package com.aplikasi.letscookresep;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ListResepActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_resep);
		
		ArrayList<HashMap<String, String>> myList = new ArrayList<HashMap<String,String>>();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_resep, menu);
		return true;
	}

}
