package dao;

import config.KoneksiDB;
import model.Pasien;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class PasienDAO {

    // Menampilkan semua data pasien
    public List<Pasien> semua() throws SQLException {
        List<Pasien> daftar = new ArrayList<>();

        String sql = "SELECT * FROM pasien ORDER BY id_pasien";

        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Pasien pasien = ambilData(rs);
                daftar.add(pasien);
            }
        }

        return daftar;
    }

    // Mencari pasien berdasarkan ID, nama, atau NIK
    public List<Pasien> cari(String kataKunci) throws SQLException {
        List<Pasien> daftar = new ArrayList<>();

        String sql = "SELECT * FROM pasien "
                   + "WHERE id_pasien LIKE ? "
                   + "OR nama_lengkap LIKE ? "
                   + "OR nik LIKE ? "
                   + "ORDER BY nama_lengkap";

        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String keyword = "%" + kataKunci + "%";

            ps.setString(1, keyword);
            ps.setString(2, keyword);
            ps.setString(3, keyword);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Pasien pasien = ambilData(rs);
                    daftar.add(pasien);
                }
            }
        }

        return daftar;
    }

    // Menambahkan data pasien
    public boolean tambah(Pasien pasien) throws SQLException {
        String sql = "INSERT INTO pasien "
                   + "(id_pasien, nama_lengkap, nik, tanggal_lahir, "
                   + "jenis_kelamin, status_bpjs) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pasien.getIdPasien());
            ps.setString(2, pasien.getNamaLengkap());
            ps.setString(3, pasien.getNik());
            ps.setDate(4, Date.valueOf(pasien.getTanggalLahir()));
            ps.setString(5, pasien.getJenisKelamin());
            ps.setString(6, pasien.getStatusBpjs());

            return ps.executeUpdate() > 0;
        }
    }

    // Mengubah data pasien
    public boolean ubah(Pasien pasien) throws SQLException {
        String sql = "UPDATE pasien SET "
                   + "nama_lengkap = ?, nik = ?, tanggal_lahir = ?, "
                   + "jenis_kelamin = ?, status_bpjs = ?"
                   + "WHERE id_pasien = ?";

        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pasien.getNamaLengkap());
            ps.setString(2, pasien.getNik());
            ps.setDate(3, Date.valueOf(pasien.getTanggalLahir()));
            ps.setString(4, pasien.getJenisKelamin());
            ps.setString(5, pasien.getStatusBpjs());
            ps.setString(6, pasien.getIdPasien());

            return ps.executeUpdate() > 0;
        }
    }

    // Mengubah hasil query menjadi objek Pasien
    private Pasien ambilData(ResultSet rs) throws SQLException {
        return new Pasien(
            rs.getString("id_pasien"),
            rs.getString("nama_lengkap"),
            rs.getString("nik"),
            rs.getDate("tanggal_lahir").toLocalDate(),
            rs.getString("jenis_kelamin"),
            rs.getString("status_bpjs")
        );
    }
    // Membuat ID pasien berikutnya
    public String idBerikutnya() throws SQLException {
        String sql = "SELECT MAX(CAST(SUBSTRING(id_pasien, 3) "
                   + "AS UNSIGNED)) AS nomor FROM pasien";

        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                int nomor = rs.getInt("nomor") + 1;
                return String.format("PS%03d", nomor);
            }
        }

        return "PS001";
    }
    // Menghapus data pasien
    public boolean hapus(String idPasien) throws SQLException {
        String sql = "DELETE FROM pasien WHERE id_pasien = ?";

        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, idPasien);

            return ps.executeUpdate() > 0;
        }
    }
}