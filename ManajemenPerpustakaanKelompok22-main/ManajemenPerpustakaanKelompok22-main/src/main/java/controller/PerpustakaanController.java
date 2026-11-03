/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import model.Buku;
import model.Mahasiswa;
import model.Transaksi;

/**
 *
 * @author LENOVO
 */
public class PerpustakaanController {
    
    private String[] kategoriBuku = {
        "Pemrograman",
        "Matematika",
        "Jaringan Komputer",
        "Basis Data"
    };

    private int[][] statistikPeminjaman = new int[4][7];

    private String[][][] lokasiBuku = new String[2][3][4];

    private ArrayList<Buku> daftarBuku = new ArrayList<>();

    private Stack<Transaksi> riwayatTransaksi = new Stack<>();

    private Queue<Mahasiswa> antreanMahasiswa = new LinkedList<>();
    
    private int nomorTransaksi = 1;

    public PerpustakaanController() {
        inisialisasiDataAwal();
    }

    private void inisialisasiDataAwal() {
        daftarBuku.add(new Buku(
            "B001", "Dasar Pemrograman Java", "Pemrograman", 5
        ));

        daftarBuku.add(new Buku(
            "B002", "Matematika Diskrit", "Matematika", 3
        ));

        daftarBuku.add(new Buku(
            "B003", "Jaringan Komputer Dasar", "Jaringan Komputer", 4
        ));

        daftarBuku.add(new Buku(
            "B004", "Perancangan Basis Data", "Basis Data", 2
        ));

        lokasiBuku[0][0][0] = "B001";
        lokasiBuku[0][0][1] = "B002";
        lokasiBuku[0][1][0] = "B003";
        lokasiBuku[1][0][0] = "B004";
    }
    
    public void tampilkanKategori() {
        System.out.println("\n=== KATEGORI BUKU ===");

        for (int i = 0; i < kategoriBuku.length; i++) {
            System.out.println((i + 1) + ". " + kategoriBuku[i]);
        }
    }

    private String[] namaHari = {
        "Senin", "Selasa", "Rabu", "Kamis",
        "Jumat", "Sabtu", "Minggu"
    };
    
    public void tampilkanStatistik() {
        System.out.println("\n=== STATISTIK PEMINJAMAN ===");

        System.out.printf("%-22s", "Kategori");

        for (String hari : namaHari) {
            System.out.printf("%-10s", hari);
        }

        System.out.println();

        for (int i = 0; i < kategoriBuku.length; i++) {
            System.out.printf("%-22s", kategoriBuku[i]);

            for (int j = 0; j < namaHari.length; j++) {
                System.out.printf(
                    "%-10d", statistikPeminjaman[i][j]
                );
            }

            System.out.println();
        }
    }
    
    
    public Buku cariBukuBerdasarkanJudul(String judul) {
        for (Buku buku : daftarBuku) {
            if (buku.getJudul().equalsIgnoreCase(judul)) {
                return buku;
            }
        }

        return null;
    }    

    public void cariLokasiBuku(String judul) {
        Buku buku = cariBukuBerdasarkanJudul(judul);

        if (buku == null) {
            System.out.println("Buku tidak ditemukan.");
            return;
        }

        String idBuku = buku.getId();

        for (int i = 0; i < lokasiBuku.length; i++) {
            for (int j = 0; j < lokasiBuku[i].length; j++) {
                for (int k = 0; k < lokasiBuku[i][j].length; k++) {

                    if (idBuku.equals(lokasiBuku[i][j][k])) {
                        System.out.println("\n=== LOKASI BUKU ===");
                        System.out.println("Judul    : " + buku.getJudul());
                        System.out.println("Kategori : " + buku.getKategori());
                        System.out.println("Stok     : " + buku.getStok());
                        System.out.println("Lantai   : " + (i + 1));
                        System.out.println("Rak      : " + (j + 1));
                        System.out.println("Posisi   : " + (k + 1));
                        return;
                    }
                }
            }
        }

        System.out.println("Lokasi buku belum ditentukan.");
    }
    
    public void tampilkanDaftarBuku() {
        System.out.println("\n=== DAFTAR KOLEKSI BUKU ===");

        if (daftarBuku.isEmpty()) {
            System.out.println("Belum ada buku dalam koleksi.");
            return;
        }

        for (Buku buku : daftarBuku) {
            System.out.println("----------------------------");
            System.out.println("Judul    : " + buku.getJudul());
            System.out.println("Kategori : " + buku.getKategori());
            System.out.println("Stok     : " + buku.getStok());
        }
    }
    

    public boolean tambahBuku(Buku buku) {
        for (Buku bukuLama : daftarBuku) {
            if (bukuLama.getJudul().equalsIgnoreCase(buku.getJudul())) {
                System.out.println("Judul buku sudah terdaftar.");
                return false;
            }

            if (bukuLama.getId().equalsIgnoreCase(buku.getId())) {
                System.out.println("ID buku sudah digunakan.");
                return false;
            }
        }

        if (!alokasikanLokasi(buku.getId())) {
            return false;
        }

        daftarBuku.add(buku);

        System.out.println("Buku berhasil ditambahkan.");
        return true;
    }

    public boolean hapusBuku(String judul) {
        Buku buku = cariBukuBerdasarkanJudul(judul);

        if (buku == null) {
            System.out.println("Buku tidak ditemukan.");
            return false;
        }

        if (buku.getStok() <= 0) {
            System.out.println("Buku sedang tidak tersedia.");
            return false;
        }

        for (Transaksi transaksi : riwayatTransaksi) {
            if (transaksi.getBuku().getId().equals(buku.getId())) {
                System.out.println(
                    "Buku memiliki riwayat transaksi dan tidak dapat dihapus."
                );
                return false;
            }
        }

        for (int i = 0; i < lokasiBuku.length; i++) {
            for (int j = 0; j < lokasiBuku[i].length; j++) {
                for (int k = 0; k < lokasiBuku[i][j].length; k++) {
                    if (buku.getId().equals(lokasiBuku[i][j][k])) {
                        lokasiBuku[i][j][k] = null;
                    }
                }
            }
        }

        daftarBuku.remove(buku);
        System.out.println("Buku berhasil dihapus.");
        return true;
    }

    private void catatTransaksi(Mahasiswa mahasiswa, Buku buku) {
        String idTransaksi = String.format(
            "TRX%03d", nomorTransaksi++
        );

        Transaksi transaksi = new Transaksi(
            idTransaksi, mahasiswa, buku
        );

        riwayatTransaksi.push(transaksi);

        int indeksKategori = -1;

        for (int i = 0; i < kategoriBuku.length; i++) {
            if (kategoriBuku[i].equalsIgnoreCase(buku.getKategori())) {
                indeksKategori = i;
                break;
            }
        }

        int indeksHari = transaksi.getWaktu().getDayOfWeek().getValue() - 1;

        if (indeksKategori != -1) {
            statistikPeminjaman[indeksKategori][indeksHari]++;
        }
    }
    
    public void tampilkanTransaksiTerakhir() {
        System.out.println("\n=== TRANSAKSI TERAKHIR ===");

        if (riwayatTransaksi.isEmpty()) {
            System.out.println("Belum ada transaksi peminjaman.");
            return;
        }

        Transaksi transaksi = riwayatTransaksi.peek();
        transaksi.tampilkanInfo();
    }
    
    public void batalkanTransaksiTerakhir() {
        System.out.println("\n=== PEMBATALAN TRANSAKSI ===");

        if (riwayatTransaksi.isEmpty()) {
            System.out.println("Tidak ada transaksi yang dapat dibatalkan.");
            return;
        }

        Transaksi transaksi = riwayatTransaksi.pop();

        Buku buku = transaksi.getBuku();
        buku.kembalikanBuku();

        int indeksKategori = -1;

        for (int i = 0; i < kategoriBuku.length; i++) {
            if (kategoriBuku[i].equalsIgnoreCase(buku.getKategori())) {
                indeksKategori = i;
                break;
            }
        }

        int indeksHari = transaksi.getWaktu().getDayOfWeek().getValue() - 1;

        if (indeksKategori != -1
                && statistikPeminjaman[indeksKategori][indeksHari] > 0) {
            statistikPeminjaman[indeksKategori][indeksHari]--;
        }

        System.out.println("Transaksi berhasil dibatalkan.");
        System.out.println("Buku dikembalikan ke stok: " + buku.getJudul());
    }
   
    public void tambahAntrean(Mahasiswa mahasiswa) {
        for (Mahasiswa m : antreanMahasiswa) {
            if (m.getId().equalsIgnoreCase(mahasiswa.getId())) {
                System.out.println(
                    "Mahasiswa sudah berada dalam antrean."
                );
                return;
            }
        }

        antreanMahasiswa.offer(mahasiswa);
        System.out.println(
            mahasiswa.getNama() + " berhasil masuk antrean."
        );
    }

    public void tampilkanAntrean() {
        System.out.println("\n=== ANTREAN MAHASISWA ===");

        if (antreanMahasiswa.isEmpty()) {
            System.out.println("Antrean masih kosong.");
            return;
        }

        int nomor = 1;

        for (Mahasiswa mahasiswa : antreanMahasiswa) {
            System.out.println(
                nomor + ". " + mahasiswa.getNama()
                + " (ID: " + mahasiswa.getId() + ")"
            );
            nomor++;
        }
    }

    public Mahasiswa layaniAntrean() {
        if (antreanMahasiswa.isEmpty()) {
            System.out.println("Tidak ada mahasiswa dalam antrean.");
            return null;
        }

        Mahasiswa mahasiswa = antreanMahasiswa.poll();

        System.out.println("\nMahasiswa sedang dilayani:");
        mahasiswa.tampilkanPeran();

        return mahasiswa;
    }

    public void prosesPeminjaman(Mahasiswa mahasiswa, String judul) {
        if (mahasiswa == null) {
            System.out.println("Mahasiswa tidak valid.");
            return;
        }

        Buku buku = cariBukuBerdasarkanJudul(judul);

        if (buku == null) {
            System.out.println("Buku tidak ditemukan.");
            return;
        }

        if (!buku.pinjamBuku()) {
            System.out.println("Stok buku sedang habis.");
            return;
        }

        catatTransaksi(mahasiswa, buku);

        System.out.println("\nPeminjaman berhasil!");
        System.out.println("Mahasiswa : " + mahasiswa.getNama());
        System.out.println("Buku      : " + buku.getJudul());
        System.out.println("Sisa stok : " + buku.getStok());
    }
    
    
    private boolean alokasikanLokasi(String idBuku) {
        for (int i = 0; i < lokasiBuku.length; i++) {
            for (int j = 0; j < lokasiBuku[i].length; j++) {
                for (int k = 0; k < lokasiBuku[i][j].length; k++) {
                    if (lokasiBuku[i][j][k] == null) {
                        lokasiBuku[i][j][k] = idBuku;
                        return true;
                    }
                }
            }
        }

        System.out.println("Rak buku sudah penuh.");
        return false;
    }


}
