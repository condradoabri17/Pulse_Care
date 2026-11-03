package com.mycompany.testKoneksi;

import model.Buku;
import model.dao.BukuDAO;

public class main { 
    public static void main(String[] args) {
        BukuDAO bukuDAO = new BukuDAO();

        bukuDAO.tambahBuku(new Buku("B004", "Belajar Basis Data", 8));
//        tampilkanBuku(bukuDAO);
//
//        bukuDAO.updateBuku(new Buku("B004", "Belajar Basis Data Lanjutan", 20));
//        tampilkanBuku(bukuDAO);
//
//        bukuDAO.hapusBuku("B004");
//        tampilkanBuku(bukuDAO);
//    }
//
//    static void tampilkanBuku(BukuDAO bukuDAO) {
//        System.out.println("--- DATA BUKU ---");
//        
//        List<Buku> daftarBuku = bukuDAO.ambilSemuaBuku();
//        
//        if (daftarBuku.isEmpty()) {
//            System.out.println("Data buku kosong.");
//        } else {
//            for (Buku buku : daftarBuku) {
//                System.out.println("ID Buku: " + buku.getIdBuku());
//                System.out.println("Judul: " + buku.getJudul());
//                System.out.println("Stok: " + buku.getStok());
//                System.out.println("----------------------------");
//            }
//            }
        }
    }