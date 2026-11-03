package model;

import java.time.LocalTime;

public class Shift {
    private String idShift;
    private String namaShift;
    private LocalTime jamMulai;
    private LocalTime jamSelesai;

    public Shift() {
    }

    public Shift(String idShift, String namaShift, LocalTime jamMulai, LocalTime jamSelesai) {
        this.idShift = idShift;
        this.namaShift = namaShift;
        this.jamMulai = jamMulai;
        this.jamSelesai = jamSelesai;
    }

    public String getIdShift() {
        return idShift;
    }

    public void setIdShift(String idShift) {
        this.idShift = idShift;
    }

    public String getNamaShift() {
        return namaShift;
    }

    public void setNamaShift(String namaShift) {
        this.namaShift = namaShift;
    }

    public LocalTime getJamMulai() {
        return jamMulai;
    }

    public void setJamMulai(LocalTime jamMulai) {
        this.jamMulai = jamMulai;
    }

    public LocalTime getJamSelesai() {
        return jamSelesai;
    }

    public void setJamSelesai(LocalTime jamSelesai) {
        this.jamSelesai = jamSelesai;
    }
}