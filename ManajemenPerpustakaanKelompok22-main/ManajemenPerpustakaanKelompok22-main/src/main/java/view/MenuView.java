/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.util.Scanner;
import controller.PerpustakaanController;
import model.Buku;
import model.Mahasiswa;

/**
 *
 * @author LENOVO
 */

public class MenuView {

    private Scanner input = new Scanner(System.in);
    private PerpustakaanController controller;

    public MenuView() {
        controller = new PerpustakaanController();
    }

    public void tampilkanMenu() {
        int pilihan;

        do {
            System.out.println("\n=== SISTEM MANAJEMEN PERPUSTAKAAN ===");
            System.out.println("1. Tampilkan kategori buku");
            System.out.println("2. Tampilkan daftar buku");
            System.out.println("3. Cari lokasi buku");
            System.out.println("4. Tampilkan statistik peminjaman");
            System.out.println("5. Tambah buku");
            System.out.println("6. Hapus buku");
            System.out.println("7. Tambah mahasiswa ke antrean");
            System.out.println("8. Tampilkan antrean mahasiswa");
            System.out.println("9. Layani antrean dan pinjam buku");
            System.out.println("10. Tampilkan transaksi terakhir");
            System.out.println("11. Batalkan transaksi terakhir");
            System.out.println("0. Keluar");
            System.out.print("Pilih menu: ");

            pilihan = input.nextInt();
            input.nextLine();

            switch (pilihan) {
                case 1:
                    controller.tampilkanKategori();
                    break;

                case 2:
                    controller.tampilkanDaftarBuku();
                    break;

                case 3:
                    System.out.print("Masukkan judul buku: ");
                    controller.cariLokasiBuku(input.nextLine());
                    break;

                case 4:
                    controller.tampilkanStatistik();
                    break;

                case 5:
                    tambahBuku();
                    break;

                case 6:
                    System.out.print("Judul buku yang dihapus: ");
                    controller.hapusBuku(input.nextLine());
                    break;

                case 7:
                    tambahMahasiswaKeAntrean();
                    break;

                case 8:
                    controller.tampilkanAntrean();
                    break;

                case 9:
                    layaniMahasiswa();
                    break;

                case 10:
                    controller.tampilkanTransaksiTerakhir();
                    break;

                case 11:
                    controller.batalkanTransaksiTerakhir();
                    break;

                case 0:
                    System.out.println("Program selesai.");
                    break;

                default:
                    System.out.println("Pilihan tidak valid.");
            }

        } while (pilihan != 0);
    }
    
    private void tambahBuku() {
        System.out.print("ID buku: ");
        String id = input.nextLine();

        System.out.print("Judul buku: ");
        String judul = input.nextLine();

        controller.tampilkanKategori();
        System.out.print("Masukkan nama kategori: ");
        String kategori = input.nextLine();

        System.out.print("Jumlah stok: ");
        int stok = input.nextInt();
        input.nextLine();

        if (stok <= 0) {
            System.out.println("Stok harus lebih dari 0.");
            return;
        }

        Buku buku = new Buku(id, judul, kategori, stok);
        controller.tambahBuku(buku);
    }

    private void tambahMahasiswaKeAntrean() {
        System.out.print("ID mahasiswa: ");
        String id = input.nextLine();

        System.out.print("Nama mahasiswa: ");
        String nama = input.nextLine();

        Mahasiswa mahasiswa = new Mahasiswa(id, nama);
        controller.tambahAntrean(mahasiswa);
    }

    private void layaniMahasiswa() {
        Mahasiswa mahasiswa = controller.layaniAntrean();

        if (mahasiswa == null) {
            return;
        }

        System.out.print("Judul buku yang dipinjam: ");
        String judul = input.nextLine();

        controller.prosesPeminjaman(mahasiswa, judul);
    }


}