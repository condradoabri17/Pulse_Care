package dao;

import config.KoneksiDB;
import model.Poliklinik;
import model.Shift;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReferensiDAO {

    // Mengambil seluruh data poliklinik
    public List<Poliklinik> semuaPoliklinik() throws SQLException {
        List<Poliklinik> daftar = new ArrayList<>();
        String sql = "SELECT * FROM poliklinik ORDER BY nama_poli";

        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Poliklinik poli = new Poliklinik();
                poli.setIdPoli(rs.getString("id_poli"));
                poli.setNamaPoli(rs.getString("nama_poli"));
                poli.setDeskripsi(rs.getString("deskripsi"));
                daftar.add(poli);
            }
        }
        return daftar;
    }

    // Mengambil seluruh data shift
    public List<Shift> semuaShift() throws SQLException {
        List<Shift> daftar = new ArrayList<>();

        String sql = "SELECT * FROM shift ORDER BY id_shift";

        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Shift shift = new Shift();
                shift.setIdShift(rs.getString("id_shift"));
                shift.setNamaShift(rs.getString("nama_shift"));
                shift.setJamMulai(rs.getTime("jam_mulai").toLocalTime());
                shift.setJamSelesai(rs.getTime("jam_selesai").toLocalTime());
                daftar.add(shift);
            }
        }
        return daftar;
    }

    // Mengambil nama dan spesialisasi dokter
    public List<Object[]> dokterDanSpesialisasi() throws SQLException {
        List<Object[]> daftar = new ArrayList<>();

        String sql = "SELECT nama_lengkap, spesialisasi "
                   + "FROM dokter "
                   + "ORDER BY nama_lengkap";

        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                daftar.add(new Object[]{
                    rs.getString("nama_lengkap"),
                    rs.getString("spesialisasi")
                });
            }
        }
        return daftar;
    }

    // Menghitung jumlah petugas berdasarkan shift
    public List<Object[]> jumlahPetugasPerShift() throws SQLException {
        List<Object[]> daftar = new ArrayList<>();

        String sql = "SELECT s.nama_shift, s.jam_mulai, s.jam_selesai, "
                   + "COUNT(p.nip) AS jumlah_petugas "
                   + "FROM shift s "
                   + "LEFT JOIN petugas p ON s.id_shift = p.id_shift "
                   + "GROUP BY s.id_shift, s.nama_shift, "
                   + "s.jam_mulai, s.jam_selesai "
                   + "ORDER BY s.id_shift";

        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                daftar.add(new Object[]{
                    rs.getString("nama_shift"),
                    rs.getTime("jam_mulai"),
                    rs.getTime("jam_selesai"),
                    rs.getInt("jumlah_petugas")
                });
            }
        }
        return daftar;
    }
    // Mengambil ID shift berdasarkan NIP petugas
    public String shiftPetugas(String nip) throws SQLException {

        String sql = "SELECT id_shift FROM petugas WHERE nip = ?";

        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nip);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("id_shift");
                }
            }
        }

        return null;
    }
}