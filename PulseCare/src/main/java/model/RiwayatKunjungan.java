package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class RiwayatKunjungan {
    private String idKunjungan;
    private String nipPetugas;
    private String idPasien;
    private String idPoli;
    private LocalDateTime waktuPendaftaran;
    private String nomorAntrian;
    private String statusKunjungan;
    private LocalDate tanggalPendaftaran;
    private String keluhan;

    public RiwayatKunjungan() {
    }

    public RiwayatKunjungan(String idKunjungan, String nipPetugas, String idPasien, String idPoli, LocalDateTime waktuPendaftaran,
            String nomorAntrian, String statusKunjungan, LocalDate tanggalPendaftaran, String keluhan) {
        this.idKunjungan = idKunjungan;
        this.nipPetugas = nipPetugas;
        this.idPasien = idPasien;
        this.idPoli = idPoli;
        this.waktuPendaftaran = waktuPendaftaran;
        this.nomorAntrian = nomorAntrian;
        this.statusKunjungan = statusKunjungan;
        this.tanggalPendaftaran = tanggalPendaftaran;
        this.keluhan = keluhan;
    }

    public String getIdKunjungan() {
        return idKunjungan;
    }

    public void setIdKunjungan(String idKunjungan) {
        this.idKunjungan = idKunjungan;
    }

    public String getNipPetugas() {
        return nipPetugas;
    }

    public void setNipPetugas(String nipPetugas) {
        this.nipPetugas = nipPetugas;
    }

    public String getIdPasien() {
        return idPasien;
    }

    public void setIdPasien(String idPasien) {
        this.idPasien = idPasien;
    }

    public String getIdPoli() {
        return idPoli;
    }

    public void setIdPoli(String idPoli) {
        this.idPoli = idPoli;
    }

    public LocalDateTime getWaktuPendaftaran() {
        return waktuPendaftaran;
    }

    public void setWaktuPendaftaran(LocalDateTime waktuPendaftaran) {
        this.waktuPendaftaran = waktuPendaftaran;
    }

    public String getNomorAntrian() {
        return nomorAntrian;
    }

    public void setNomorAntrian(String nomorAntrian) {
        this.nomorAntrian = nomorAntrian;
    }

    public String getStatusKunjungan() {
        return statusKunjungan;
    }

    public void setStatusKunjungan(String statusKunjungan) {
        this.statusKunjungan = statusKunjungan;
    }

    public LocalDate getTanggalPendaftaran() {
        return tanggalPendaftaran;
    }

    public void setTanggalPendaftaran(LocalDate tanggalPendaftaran) {
        this.tanggalPendaftaran = tanggalPendaftaran;
    }

    public String getKeluhan() {
        return keluhan;
    }

    public void setKeluhan(String keluhan) {
        this.keluhan = keluhan;
    }
}