package model.dao;

import com.mycompany.testkoneksi.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.Buku;

public class BukuDAO {
    private final Connection conn;
    
    public BukuDAO(){
        this.conn = Koneksi.getConnection();
    }
    
    public void tambahBuku(Buku buku){
        String sql = "INSERT INTO buku (id_buku, judul, stok) VALUE(?,?,?)";
        
        try (PreparedStatement pstmt = conn.prepareStatement (sql)){
            pstmt.setString(1, buku.getIdBuku());
            pstmt.setString(2, buku.getJudul());
            pstmt.setInt(3, buku.getStok());
            
            pstmt.executeUpdate();
            System.out.println("Buku berhasil ditambahkan");
        }catch (SQLException e){
            System.out.println("Gagal menambah buku " + e.getMessage());
        }
    }
}
