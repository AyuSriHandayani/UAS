package com.aplikasi.letscookresep;


import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aplikasi.letscookresep.Resep;

public class ResepAdapter extends BaseAdapter {
    private Activity activity;
    //private ArrayList<HashMap<String, String>> data;
    private ArrayList<Resep> data_rsp=new ArrayList<Resep>();

    private static LayoutInflater inflater = null;

    public ResepAdapter(Activity a, ArrayList<Resep> d) {
        activity = a; data_rsp = d;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public int getCount() {
        return data_rsp.size();
    }
    public Object getItem(int position) {
        return data_rsp.get(position);
    }
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.activity_list_resep, null);
        TextView namaLengkap = (TextView) vi.findViewById(R.id.nama);
        TextView nama_resep = (TextView) vi.findViewById(R.id.nama_resep);
        TextView categori = (TextView) vi.findViewById(R.id.kategori);
        TextView deskripsi= (TextView) vi.findViewById(R.id.deskripsi);
        
        Resep daftar_rsp = data_rsp.get(position);
        namaLengkap.setText(daftar_rsp.getRspNama());
        nama_resep.setText(daftar_rsp.getRspJudul());
        categori.setText(daftar_rsp.getRspCategori());
        deskripsi.setText(daftar_rsp.getRspDeskripsi());
        
        return vi;
    }
}

