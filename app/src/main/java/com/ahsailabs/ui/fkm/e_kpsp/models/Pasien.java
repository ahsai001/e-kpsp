package com.ahsailabs.ui.fkm.e_kpsp.models;

public class Pasien {
    private long id;
    private String namaAnak;
    private String namaAyah;
    private String namaIbu;
    private String alamat;
    private String tanggalPeriksa;
    private String tanggalLahir;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNamaAnak() {
        return namaAnak;
    }

    public void setNamaAnak(String namaAnak) {
        this.namaAnak = namaAnak;
    }

    public String getNamaAyah() {
        return namaAyah;
    }

    public void setNamaAyah(String namaAyah) {
        this.namaAyah = namaAyah;
    }

    public String getNamaIbu() {
        return namaIbu;
    }

    public void setNamaIbu(String namaIbu) {
        this.namaIbu = namaIbu;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTanggalPeriksa() {
        return tanggalPeriksa;
    }

    public void setTanggalPeriksa(String tanggalPeriksa) {
        this.tanggalPeriksa = tanggalPeriksa;
    }

    public String getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(String tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }
}
