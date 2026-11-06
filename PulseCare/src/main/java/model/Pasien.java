package model;

import java.time.LocalDate;

public class Pasien {
    private String idPasien;
    private String nik;
    private String namaLengkap;
    private LocalDate tanggalLahir;
    private String jenisKelamin;
    private String statusBpjs;

    public Pasien() {
    }

    public Pasien(String idPasien, String namaLengkap, String nik,
            LocalDate tanggalLahir, String jenisKelamin,
            String statusBpjs) {

        this.idPasien = idPasien;
        this.namaLengkap = namaLengkap;
        this.nik = nik;
        this.tanggalLahir = tanggalLahir;
        this.jenisKelamin = jenisKelamin;
        this.statusBpjs = statusBpjs;
    }

    public String getIdPasien() {
        return idPasien;
    }

    public void setIdPasien(String idPasien) {
        this.idPasien = idPasien;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
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

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getStatusBpjs() {
        return statusBpjs;
    }

    public void setStatusBpjs(String statusBpjs) {
        this.statusBpjs = statusBpjs;
    }
}
