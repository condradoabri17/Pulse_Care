package dao;

import config.KoneksiDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AntreanDokterDAO {

    public List<Object[]> antreanDokter() throws SQLException {

        List<Object[]> daftar = new ArrayList<>();

        String sql = "SELECT r.nomor_antrian, "
                + "p.nama_lengkap, "
                + "r.waktu_pendaftaran, "
                + "h.keluhan, "
                + "r.status_kunjungan "
                + "FROM riwayat_kunjungan r "
                + "JOIN pasien p ON r.id_pasien = p.id_pasien "
                + "LEFT JOIN hasil_kunjungan h "
                + "ON r.id_kunjungan = h.id_kunjungan "
                + "WHERE r.tanggal_pendaftaran = CURDATE() "
                + "AND r.status_kunjungan IN ('menunggu', 'diperiksa', 'selesai') "
                + "ORDER BY r.nomor_antrian ASC";

        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Object[] data = {
                    rs.getString("nomor_antrian"),
                    rs.getString("nama_lengkap"),
                    rs.getTime("waktu_pendaftaran"),
                    rs.getString("keluhan"),
                    rs.getString("status_kunjungan")
                };

                daftar.add(data);
            }
        }

        return daftar;
    }
}