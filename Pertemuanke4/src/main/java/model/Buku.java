package model;

public abstract class Buku {
    private final String idBuku;
    protected String judul;
    protected String penulis;
    protected int tahunTerbit;
    
    public Buku(String idBuku, String judul, String penulis, int tahunTerbit) {
        this.idBuku = idBuku;
        setJudul(judul);
        setPenulis(penulis);
        setTahunTerbit(tahunTerbit);
    }

    public String getIdBuku() {
        return idBuku;
    }

    public String getJudul() {
        return judul;
    }

    public String getPenulis() {
        return penulis;
    }

    public int getTahunTerbit() {
        return tahunTerbit;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public void setTahunTerbit(int tahunTerbit) {
        this.tahunTerbit = tahunTerbit;
    }

    public void tampilkanInfo() {
        System.out.println("ID Buku: " + idBuku);
        System.out.println("Judul: " + judul);
        System.out.println("Penulis: " + penulis);
        System.out.println("Tahun Terbit: " + tahunTerbit);
    }
    public void tampilkanInfo(boolean ringkas) {
        if (ringkas) {
            System.out.println(judul + " [" + idBuku + "]");
        } else {
            tampilkanInfoCetak();
        }
    }

    public final void cetakStatusAset() {
        System.out.println("Status Aset: Terdaftar sebagai koleksi berharga");
    }

    private void tampilkanInfoCetak() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    public abstract void caraAkses(); 
}