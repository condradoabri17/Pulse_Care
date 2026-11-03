package main;

import model.Buku;
import model.BukuCetak;
import model.EBook;
import model.Pinjamable;
import mode.Unduhable;

public class Main {
    public static void main(String[] args) {
        BukuCetak bukuCetak1 = new BukuCetak("BC001", "Pemrograman Java", "Rizki", 2023, 350, "Rak A-1");
        EBook ebook1 = new EBook("EB001", "Belajar Python", "Fikri", 2024, 12.5, "PDF");

//        System.out.println("=== INFO BUKU CETAK ===");
////        bukuCetak1.tampilkanInfoBukuCetak();
////        bukuCetak1.cetakStatusAset();
//                bukuCetak1.tampilkanInfo();
//
//        System.out.println("=== INFO EBOOK ===");
////        ebook1.tampilkanInfoEBook();
////        ebook1.cetakStatusAset();
//                ebook1.tampilkanInfo();
//        System.out.println("=== OVERLOADING ===");
//        bukuCetak1.tampilkanInfo();
//        bukuCetak1.tampilkanInfo(true);

        System.out.println("=== ABSCTRACT CLASS: caraAkses()===");
        bukuCetak1.caraAkses();
        ebook1.caraAkses();
        
        System.out.println("=== INTERFACE ===");
        bukuCetak1.prosesPinjam();
        bukuCetak1.prosesKembali();
        ebook1.prosesUnduh();
    }
}