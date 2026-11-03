// ===========================================================
// Topik: GUI
// Letakkan file ini pada src/model.dao/BukuDAO.java
// ===========================================================

package model.dao; 

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import config.Koneksi;
import model.Buku;

public class BukuDAO {
    
    private final Connection conn;

    public BukuDAO() {
        this.conn = Koneksi.getConnection();
    }

    // CREATE
    public void tambahBuku(Buku buku) {
        String sql = "INSERT INTO buku (id_buku, judul, stok) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, buku.getIdBuku());
            pstmt.setString(2, buku.getJudul());
            pstmt.setInt(3, buku.getStok());

            pstmt.executeUpdate();
            System.out.println("Buku berhasil ditambahkan!");
        } catch (SQLException e) {
            System.out.println("Gagal menambah buku - " + e.getMessage());
        }
    }

    // READ
    public List<Buku> ambilSemuaBuku() {
        List<Buku> daftarBuku = new ArrayList<>();
        String sql = "SELECT * FROM buku";

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String id = rs.getString("id_buku");
                String judul = rs.getString("judul");
                int stok = rs.getInt("stok");
                
                // Masukkan data dari database ke dalam objek Model Buku
                Buku buku = new Buku(id, judul, stok);
                daftarBuku.add(buku);
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengambil data buku - " + e.getMessage());
        }
        return daftarBuku;
    }

    // UPDATE
    public void updateBuku(Buku buku) {
        String sql = "UPDATE buku SET judul=?, stok=? WHERE id_buku=?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, buku.getJudul());
            pstmt.setInt(2, buku.getStok());
            pstmt.setString(3, buku.getIdBuku());

            int baris = pstmt.executeUpdate();
            if (baris > 0) {
                System.out.println("Data buku berhasil diubah!");
            } else {
                System.out.println("ID buku tidak ditemukan.");
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengubah data buku - " + e.getMessage());
        }
    }

    // DELETE
    public void hapusBuku(String idBuku) {
        String sql = "DELETE FROM buku WHERE id_buku=?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, idBuku);

            int baris = pstmt.executeUpdate();
            if (baris > 0) {
                System.out.println("Buku berhasil dihapus!");
            } else {
                System.out.println("ID buku tidak ditemukan.");
            }
        } catch (SQLException e) {
            System.out.println("Gagal menghapus buku - " + e.getMessage());
        }
    }
}