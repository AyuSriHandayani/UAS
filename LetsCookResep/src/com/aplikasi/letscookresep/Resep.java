package com.aplikasi.letscookresep;

public class Resep {

    private String nama_lengkap;
    private String judul_resep;
    private String category;
    private String tanggal_update;
    private String deskripsi;
    
    public void setNama(String nama_lengkap)
    {
        this.nama_lengkap = nama_lengkap;
    }

    public String getRspNama()
    {
        return nama_lengkap;
    }

    public void setNamaResep (String judul_resep)
    {
        this.judul_resep = judul_resep;
    }

    public String getRspJudul()
    {
        return judul_resep;
    }

    public void setKategori (String category)
    {
        this.category = category;
    }

    public String getRspCategori()
    {
        return category;
    }
    
    public void setTanggal (String tanggal_update)
    {
        this.tanggal_update = tanggal_update;
    }

    public String getRspTanggal()
    {
        return tanggal_update;
    }
    
    public void setDeskripsi (String deskripsi)
    {
        this.deskripsi = deskripsi;
    }

    public String getRspDeskripsi()
    {
        return deskripsi;
    }
}
