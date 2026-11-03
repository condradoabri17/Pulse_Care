/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author LENOVO
 */
public class Mahasiswa extends Pengguna {

    public Mahasiswa(String id, String nama) {
        super(id, nama);
    }

    @Override
    public void tampilkanPeran() {
        System.out.println("Peran: Mahasiswa");
        System.out.println("ID Mahasiswa: " + getId());
        System.out.println("Nama Mahasiswa: " + getNama());
    }
}
