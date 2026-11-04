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

    // Mengambil data dokter beserta nama polikliniknya
    public List<Object[]> dokterPerPoliklinik() throws SQLException {
        List<Object[]> daftar = new ArrayList<>();

        String sql = "SELECT d.nama_lengkap, p.nama_poli "
                   + "FROM dokter d "
                   + "LEFT JOIN poliklinik p ON d.id_poli = p.id_poli "
                   + "ORDER BY d.nama_lengkap";

        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                daftar.add(new Object[]{
                    rs.getString("nama_lengkap"),
                    rs.getString("nama_poli")
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
}