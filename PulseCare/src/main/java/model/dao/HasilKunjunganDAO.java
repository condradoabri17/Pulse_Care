package dao;

import config.KoneksiDB;
import model.HasilKunjungan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HasilKunjunganDAO {

    // Mengambil hasil pemeriksaan berdasarkan ID kunjungan
    public HasilKunjungan berdasarkanKunjungan(String idKunjungan)
            throws SQLException {
        String sql = "SELECT * FROM hasil_kunjungan "
                   + "WHERE id_kunjungan = ?";

        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, idKunjungan);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return ambilData(rs);
                }
            }
        }
        return null;
    }
    // Mengambil seluruh hasil pemeriksaan
    public List<HasilKunjungan> semua() throws SQLException {
        List<HasilKunjungan> daftar = new ArrayList<>();

        String sql = "SELECT * FROM hasil_kunjungan ORDER BY id_hasil";

        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                daftar.add(ambilData(rs));
            }
        }
        return daftar;
    }
    // Menyimpan hasil pemeriksaan baru
    public boolean tambah(HasilKunjungan hasil) throws SQLException {

        String sql = "INSERT INTO hasil_kunjungan "
                   + "(id_hasil, id_kunjungan, nip_dokter, keluhan, "
                   + "hasil_pemeriksaan, diagnosa, resep_obat, "
                   + "catatan_tindak_lanjut) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, hasil.getIdHasil());
            ps.setString(2, hasil.getIdKunjungan());
            ps.setString(3, hasil.getNipDokter());
            ps.setString(4, hasil.getKeluhan());
            ps.setString(5, hasil.getHasilPemeriksaan());
            ps.setString(6, hasil.getDiagnosa());
            ps.setString(7, hasil.getResepObat());
            ps.setString(8, hasil.getCatatanTindakLanjut());
            return ps.executeUpdate() > 0;
        }
    }
    // Memperbarui hasil pemeriksaan
    public boolean ubah(HasilKunjungan hasil) throws SQLException {

        String sql = "UPDATE hasil_kunjungan SET "
                   + "keluhan = ?, hasil_pemeriksaan = ?, "
                   + "diagnosa = ?, resep_obat = ?, "
                   + "catatan_tindak_lanjut = ? "
                   + "WHERE id_kunjungan = ?";

        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, hasil.getKeluhan());
            ps.setString(2, hasil.getHasilPemeriksaan());
            ps.setString(3, hasil.getDiagnosa());
            ps.setString(4, hasil.getResepObat());
            ps.setString(5, hasil.getCatatanTindakLanjut());
            ps.setString(6, hasil.getIdKunjungan());
            return ps.executeUpdate() > 0;
        }
    }
    // Mengubah hasil query menjadi objek HasilKunjungan
    private HasilKunjungan ambilData(ResultSet rs)
            throws SQLException {
        return new HasilKunjungan(
            rs.getString("id_hasil"),
            rs.getString("id_kunjungan"),
            rs.getString("nip_dokter"),
            rs.getString("keluhan"),
            rs.getString("hasil_pemeriksaan"),
            rs.getString("diagnosa"),
            rs.getString("resep_obat"),
            rs.getString("catatan_tindak_lanjut")
        );
    }
}