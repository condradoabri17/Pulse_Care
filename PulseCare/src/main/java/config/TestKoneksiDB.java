package config;

import java.sql.Connection;
import java.sql.SQLException;

public class TestKoneksiDB {

    public static void main(String[] args) {
        System.out.println("Test Koneksi");

        try {
            Connection conn = KoneksiDB.getConnection();

            if (conn != null) {
                System.out.println("Koneksi Berhasil!");
                conn.close();
            }

        } catch (SQLException e) {
            System.out.println("Koneksi Gagal!");
            System.out.println("Pesan error: " + e.getMessage());
        }
    }
}
