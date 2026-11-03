package model;

public class Poliklinik {
    private String idPoli;
    private String namaPoli;
    private String deskripsi;

    public Poliklinik() {
    }

    public Poliklinik(String idPoli, String namaPoli, String deskripsi) {
        this.idPoli = idPoli;
        this.namaPoli = namaPoli;
        this.deskripsi = deskripsi;
    }

    public String getIdPoli() {
        return idPoli;
    }

    public void setIdPoli(String idPoli) {
        this.idPoli = idPoli;
    }

    public String getNamaPoli() {
        return namaPoli;
    }

    public void setNamaPoli(String namaPoli) {
        this.namaPoli = namaPoli;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}