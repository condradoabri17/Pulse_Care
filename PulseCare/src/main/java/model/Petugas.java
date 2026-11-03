package model;

import java.time.LocalDate;

public class Petugas {
    private String nip;
    private String namaLengkap;
    private LocalDate tanggalLahir;
    private String password;
//    private String statusAkun;
    private String idShift;

    public Petugas() {
    }

    public Petugas(String nip, String namaLengkap, LocalDate tanggalLahir, String password, String statusAkun, String idShift) {
        this.nip = nip;
        this.namaLengkap = namaLengkap;
        this.tanggalLahir = tanggalLahir;
        this.password = password;
//        this.statusAkun = statusAkun;
        this.idShift = idShift;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public String getStatusAkun() {
//        return statusAkun;
//    }
//
//    public void setStatusAkun(String statusAkun) {
//        this.statusAkun = statusAkun;
//    }

    public String getIdShift() {
        return idShift;
    }

    public void setIdShift(String idShift) {
        this.idShift = idShift;
    }
}