// ===========================================================
// Topik: GUI
// Letakkan file ini pada src/model/Buku.java
// ===========================================================

package model;

public class Buku {
    private String idBuku;
    private String judul;
    private int stok;
    
    public Buku(String idBuku, String judul, int stok) {
        this.idBuku = idBuku;
        this.judul = judul;
        this.stok = stok;
    }

    public void setIdBuku(String idBuku) {
        this.idBuku = idBuku;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public String getIdBuku() {
        return idBuku;
    }

    public String getJudul() {
        return judul;
    }

    public int getStok() {
        return stok;
    }
}