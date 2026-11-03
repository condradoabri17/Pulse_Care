/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author LENOVO
 */
public class Transaksi {

    private String idTransaksi;
    private Mahasiswa mahasiswa;
    private Buku buku;
    private LocalDateTime waktu;

    public Transaksi(String idTransaksi, Mahasiswa mahasiswa, Buku buku) {
        this.idTransaksi = idTransaksi;
        this.mahasiswa = mahasiswa;
        this.buku = buku;
        this.waktu = LocalDateTime.now();
    }

    public String getIdTransaksi() {
        return idTransaksi;
    }

    public Mahasiswa getMahasiswa() {
        return mahasiswa;
    }

    public Buku getBuku() {
        return buku;
    }

    public LocalDateTime getWaktu() {
        return waktu;
    }

    public void tampilkanInfo() {
        DateTimeFormatter format =
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        System.out.println("ID Transaksi : " + idTransaksi);
        System.out.println("Mahasiswa    : " + mahasiswa.getNama());
        System.out.println("Buku         : " + buku.getJudul());
        System.out.println("Waktu        : " + waktu.format(format));
    }
}
