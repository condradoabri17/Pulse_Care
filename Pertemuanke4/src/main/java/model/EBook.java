package model;

public class EBook extends Buku implements Unduhable {
    private double ukuranFileMB;
    private String formatFile;
    
    public EBook(String idBuku, String judul, String penulis, int tahunTerbit, double ukuranFileMB, String formatFile) {
        super(idBuku, judul, penulis, tahunTerbit);
        this.ukuranFileMB = ukuranFileMB;
        this.formatFile = formatFile;
    }

    public double getUkuranFileMB() {
        return ukuranFileMB;
    }

    public String getFormatFile() {
        return formatFile;
    }

    public void setUkuranFileMB(double ukuranFileMB) {
        this.ukuranFileMB = ukuranFileMB;
    }

    public void setFormatFile(String formatFile) {
        this.formatFile = formatFile;
    }
    
//    public void tampilkanInfoEBook() {
//        super.tampilkanInfo();
//        System.out.println("Ukuran File: " + ukuranFileMB);
//        System.out.println("Format File: " + formatFile);
//    }
    
    @Override
    public void tampilkanInfo(){
        super.tampilkanInfo();
        System.out.println("Ukuran File: " + ukuranFileMB);
        System.out.println("Format File: " + formatFile);
    }
    
    @Override
    public void caraAkses() {
        System.out.println("Cara Akses: Silahkan unduh buku ini dengan ukuran format"+ formatFile);
    }
    
    @Override
    public void prosesUnduh(){
        System.out.println("Proses mengunduh berhasil" + judul);
    }

}