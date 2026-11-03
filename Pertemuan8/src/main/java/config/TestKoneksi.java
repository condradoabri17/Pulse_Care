// ===========================================================
// Topik: GUI
// Letakkan file ini pada src/config/TestKoneksi.java
// ===========================================================

package config;

import java.sql.Connection;

public class TestKoneksi {
    public static void main(String[] args) {
        System.out.println("Mengecek koneksi ke database...");
        Connection conn = Koneksi.getConnection();

        if (conn != null) {
            System.out.println("STATUS: BERHASIL! Database siap digunakan.");
        } else {
            System.out.println("STATUS: GAGAL! Periksa URL, USER, PASS, dan pastikan service MySQL menyala.");
        }
    }
}