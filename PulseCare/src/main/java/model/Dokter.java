package model;

import java.time.LocalDate;

public class Dokter {
    private String nip;
    private String namaLengkap;
    private LocalDate tanggalLahir;
    private String spesialisasi;
    private String password;
    private String statusAkun;
    private String idPoli;

    public Dokter() {
    }
    public Dokter(String nip, String namaLengkap, LocalDate tanggalLahir, String spesialisasi, String password, String statusAkun, String idPoli) {
        this.nip = nip;
        this.namaLengkap = namaLengkap;
        this.tanggalLahir = tanggalLahir;
        this.spesialisasi = spesialisasi;
        this.password = password;
        this.statusAkun = statusAkun;
        this.idPoli = idPoli;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public LocalDate getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(LocalDate tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getSpesialisasi() {
        return spesialisasi;
    }

    public void setSpesialisasi(String spesialisasi) {
        this.spesialisasi = spesialisasi;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatusAkun() {
        return statusAkun;
    }

    public void setStatusAkun(String statusAkun) {
        this.statusAkun = statusAkun;
    }

    public String getIdPoli() {
        return idPoli;
    }

    public void setIdPoli(String idPoli) {
        this.idPoli = idPoli;
    }
}