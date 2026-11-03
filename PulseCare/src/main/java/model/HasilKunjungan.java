package model;

public class HasilKunjungan {
    private String idHasil;
    private String idKunjungan;
    private String nipDokter;
    private String keluhan;
    private String hasilPemeriksaan;
    private String diagnosa;
    private String resepObat;
    private String catatanTindakLanjut;

    public HasilKunjungan() {
    }

    public HasilKunjungan(String idHasil, String idKunjungan, String nipDokter, String keluhan,
            String hasilPemeriksaan, String diagnosa, String resepObat, String catatanTindakLanjut) {
        this.idHasil = idHasil;
        this.idKunjungan = idKunjungan;
        this.nipDokter = nipDokter;
        this.keluhan = keluhan;
        this.hasilPemeriksaan = hasilPemeriksaan;
        this.diagnosa = diagnosa;
        this.resepObat = resepObat;
        this.catatanTindakLanjut = catatanTindakLanjut;
    }

    public String getIdHasil() {
        return idHasil;
    }

    public void setIdHasil(String idHasil) {
        this.idHasil = idHasil;
    }

    public String getIdKunjungan() {
        return idKunjungan;
    }

    public void setIdKunjungan(String idKunjungan) {
        this.idKunjungan = idKunjungan;
    }

    public String getNipDokter() {
        return nipDokter;
    }

    public void setNipDokter(String nipDokter) {
        this.nipDokter = nipDokter;
    }

    public String getKeluhan() {
        return keluhan;
    }

    public void setKeluhan(String keluhan) {
        this.keluhan = keluhan;
    }

    public String getHasilPemeriksaan() {
        return hasilPemeriksaan;
    }

    public void setHasilPemeriksaan(String hasilPemeriksaan) {
        this.hasilPemeriksaan = hasilPemeriksaan;
    }

    public String getDiagnosa() {
        return diagnosa;
    }

    public void setDiagnosa(String diagnosa) {
        this.diagnosa = diagnosa;
    }

    public String getResepObat() {
        return resepObat;
    }

    public void setResepObat(String resepObat) {
        this.resepObat = resepObat;
    }

    public String getCatatanTindakLanjut() {
        return catatanTindakLanjut;
    }

    public void setCatatanTindakLanjut(String catatanTindakLanjut) {
        this.catatanTindakLanjut = catatanTindakLanjut;
    }
}