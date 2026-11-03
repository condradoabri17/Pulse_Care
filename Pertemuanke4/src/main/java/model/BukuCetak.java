package model;

public class BukuCetak extends Buku implements Pinjamable {
    private int jumlahHalaman;
    private String lokasiRak;
    
    public BukuCetak(String idBuku, String judul, String penulis, int tahunTerbit, int jumlahHalaman, String lokasiRak) {
        super(idBuku, judul, penulis, tahunTerbit);
        this.jumlahHalaman = jumlahHalaman;
        this.lokasiRak = lokasiRak;
    }

    public void setJumlahHalaman(int jumlahHalaman) {
        this.jumlahHalaman = jumlahHalaman;
    }

    public void setLokasiRak(String lokasiRak) {
        this.lokasiRak = lokasiRak;
    }

    public int getJumlahHalaman() {
        return jumlahHalaman;
    }

    public String getLokasiRak() {
        return lokasiRak;
    }
    
//    public void tampilkanInfoBukuCetak() {
//        super.tampilkanInfo();
//        super.cetakStatusAset();
//        System.out.println("Jumlah Halaman: " + jumlahHalaman);
//        System.out.println("Lokasi Rak: " + lokasiRak);
//    }
    @Override
    public void tampilkanInfo(){
        super.tampilkanInfo();
        System.out.println("Jumlah Halaman: " + jumlahHalaman);
        System.out.println("Lokasi: " + lokasiRak);
    }
    
    @Override
    public void caraAkses(){
        System.out.println("Cara Akses: Ambil buku langsung di" + lokasiRak);
    }
    
    @Override
    public void prosesPinjam(){
        System.out.println("Berhasil dipinjam" + judul);
    }
    
    @Override
    public void prosesKembali(){
        System.out.println("Buku berhasil dikembalikan" + judul);
    }
}
