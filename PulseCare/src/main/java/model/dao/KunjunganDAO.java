package dao;

import config.KoneksiDB;
import model.RiwayatKunjungan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class KunjunganDAO {

    // Menampilkan seluruh data kunjungan
    public List<RiwayatKunjungan> semua() throws SQLException {
        List<RiwayatKunjungan> daftar = new ArrayList<>();

        String sql = "SELECT * FROM riwayat_kunjungan "
                   + "ORDER BY tanggal_pendaftaran DESC, waktu_pendaftaran DESC";

        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                RiwayatKunjungan kunjungan = ambilData(rs);
                daftar.add(kunjungan);
            }
        }
        return daftar;
    }
    // Menampilkan kunjungan berdasarkan tanggal
    public List<RiwayatKunjungan> berdasarkanTanggal(LocalDate tanggal)
            throws SQLException {
        List<RiwayatKunjungan> daftar = new ArrayList<>();

        String sql = "SELECT * FROM riwayat_kunjungan "
                   + "WHERE tanggal_pendaftaran = ? "
                   + "ORDER BY waktu_pendaftaran";

        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(tanggal));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RiwayatKunjungan kunjungan = ambilData(rs);
                    daftar.add(kunjungan);
                }
            }
        }
        return daftar;
    }
    // Menambahkan pendaftaran kunjungan
    public boolean tambah(RiwayatKunjungan kunjungan)
            throws SQLException {

        String sql = "INSERT INTO riwayat_kunjungan "
                   + "(id_kunjungan, nip_petugas, id_pasien, id_poli, "
                   + "waktu_pendaftaran, nomor_antrian, status_kunjungan, "
                   + "tanggal_pendaftaran, keluhan) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, kunjungan.getIdKunjungan());
            ps.setString(2, kunjungan.getNipPetugas());
            ps.setString(3, kunjungan.getIdPasien());
            ps.setString(4, kunjungan.getIdPoli());
            ps.setTimestamp(5, Timestamp.valueOf(kunjungan.getWaktuPendaftaran()));
            ps.setString(6, kunjungan.getNomorAntrian());
            ps.setString(7, kunjungan.getStatusKunjungan());
            ps.setDate(8, Date.valueOf(kunjungan.getTanggalPendaftaran()));
            ps.setString(9, kunjungan.getKeluhan());
            return ps.executeUpdate() > 0;
        }
    }
    // Memperbarui status kunjungan
    public boolean ubahStatus(String idKunjungan, String status)
            throws SQLException {

        String sql = "UPDATE riwayat_kunjungan "
                   + "SET status_kunjungan = ? "
                   + "WHERE id_kunjungan = ?";

        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status.toLowerCase());
            ps.setString(2, idKunjungan);

            return ps.executeUpdate() > 0;
        }
    }
    // Memeriksa apakah pasien masih memiliki kunjungan aktif
    public boolean adaKunjunganAktif(String idPasien)
            throws SQLException {

        String sql = "SELECT COUNT(*) FROM riwayat_kunjungan "
                   + "WHERE id_pasien = ? "
                   + "AND status_kunjungan IN ('menunggu', 'diperiksa') "
                   + "AND tanggal_pendaftaran = CURDATE()";

        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, idPasien);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
    // Mengubah hasil query menjadi objek RiwayatKunjungan
    private RiwayatKunjungan ambilData(ResultSet rs)
            throws SQLException {

        RiwayatKunjungan kunjungan = new RiwayatKunjungan();

        kunjungan.setIdKunjungan(rs.getString("id_kunjungan"));
        kunjungan.setNipPetugas(rs.getString("nip_petugas"));
        kunjungan.setIdPasien(rs.getString("id_pasien"));
        kunjungan.setIdPoli(rs.getString("id_poli"));
        kunjungan.setWaktuPendaftaran(rs.getTimestamp("waktu_pendaftaran").toLocalDateTime());
        kunjungan.setNomorAntrian(rs.getString("nomor_antrian"));
        kunjungan.setStatusKunjungan(rs.getString("status_kunjungan"));
        kunjungan.setTanggalPendaftaran(rs.getDate("tanggal_pendaftaran").toLocalDate());
        kunjungan.setKeluhan(rs.getString("keluhan"));
        return kunjungan;
    }
}