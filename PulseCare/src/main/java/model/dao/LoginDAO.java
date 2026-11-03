package dao;

import config.KoneksiDB;
import model.Akun;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {

    public Akun login(String id, String password) throws SQLException {

        try (Connection conn = KoneksiDB.getConnection()) {

            // 1. Memeriksa akun Admin
            String sqlAdmin = "SELECT nip, nama_lengkap, username, status_akun "
                    + "FROM admin "
                    + "WHERE username = ? AND password = ? "
                    + "AND status_akun = 'aktif'";

            try (PreparedStatement ps = conn.prepareStatement(sqlAdmin)) {
                ps.setString(1, id);
                ps.setString(2, password);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        Akun akun = buatAkun(
                                "Admin",
                                rs.getString("nip"),
                                rs.getString("nama_lengkap"),
                                rs.getString("status_akun")
                        );

                        akun.setDetail("username: " + rs.getString("username"));
                        return akun;
                    }
                }
            }
            // 2. Memeriksa akun Petugas
            String sqlPetugas = "SELECT p.nip, p.nama_lengkap, s.nama_shift, "
                    + "p.status_akun FROM petugas p "
                    + "JOIN shift s ON s.id_shift = p.id_shift "
                    + "WHERE p.nip = ? AND p.password = ? "
                    + "AND p.status_akun = 'aktif'";

            try (PreparedStatement ps = conn.prepareStatement(sqlPetugas)) {
                ps.setString(1, id);
                ps.setString(2, password);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        Akun akun = buatAkun(
                                "Petugas",
                                rs.getString("nip"),
                                rs.getString("nama_lengkap"),
                                rs.getString("status_akun")
                        );

                        akun.setDetail("Shift " + rs.getString("nama_shift"));
                        return akun;
                    }
                }
            }
            // 3. Memeriksa akun Dokter
            String sqlDokter = "SELECT d.nip, d.nama_lengkap, d.spesialisasi, "
                    + "d.id_poli, po.nama_poli, d.status_akun "
                    + "FROM dokter d "
                    + "LEFT JOIN poliklinik po ON po.id_poli = d.id_poli "
                    + "WHERE d.nip = ? AND d.password = ? "
                    + "AND d.status_akun = 'aktif'";

            try (PreparedStatement ps = conn.prepareStatement(sqlDokter)) {
                ps.setString(1, id);
                ps.setString(2, password);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        Akun akun = buatAkun(
                                "Dokter",
                                rs.getString("nip"),
                                rs.getString("nama_lengkap"),
                                rs.getString("status_akun")
                        );
                        akun.setIdPoli(rs.getString("id_poli"));
                        akun.setNamaPoli(rs.getString("nama_poli"));

                        String namaPoli = akun.getNamaPoli();

                        akun.setDetail(namaPoli != null ? namaPoli: rs.getString("spesialisasi"));
                        return akun;
                    }
                }
            }
        }
        return null;
    }
    private Akun buatAkun(String role, String nip, String nama, String status) {

        Akun akun = new Akun();
        akun.setRole(role);
        akun.setNip(nip);
        akun.setNama(nama);
        akun.setStatus(status);
        return akun;
    }
}