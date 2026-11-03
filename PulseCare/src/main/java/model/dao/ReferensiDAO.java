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
}