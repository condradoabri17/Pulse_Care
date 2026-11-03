/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author LENOVO
 */
public class Buku implements DapatDipinjam {

    private String id;
    private String judul;
    private String kategori;
    private int stok;

    public Buku(String id, String judul, String kategori, int stok) {
        this.id = id;
        this.judul = judul;
        this.kategori = kategori;
        this.stok = stok;
    }

    public String getId() {
        return id;
    }

    public String getJudul() {
        return judul;
    }

    public String getKategori() {
        return kategori;
    }

    public int getStok() {
        return stok;
    }

    @Override
    public boolean cekKetersediaan() {
        return stok > 0;
    }

    @Override
    public boolean pinjamBuku() {
        if (cekKetersediaan()) {
            stok--;
            return true;
        }

        return false;
    }

    @Override
    public void kembalikanBuku() {
        stok++;
    }

    public void tampilkanInfo() {
        System.out.println("ID       : " + id);
        System.out.println("Judul    : " + judul);
        System.out.println("Kategori : " + kategori);
        System.out.println("Stok     : " + stok);
    }
}
