package dao;

import config.KoneksiDB;
import model.Akun;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {

    // Menentukan tabel berdasarkan role
    private String tabel(String role) {
        switch (role) {
            case "Admin":
                return "admin";
            case "Dokter":
                return "dokter";
            default:
                return "petugas";
        }
    }

    // Menampilkan daftar akun
    public List<Akun> semua(String kata, String role) throws SQLException {

        String sql = "SELECT * FROM ("
                + "SELECT 'Admin' AS role, nip, nama_lengkap, "
                + "CONCAT('Username: ', username) AS detail "
                + "FROM admin "
                + "UNION ALL "
                + "SELECT 'Dokter', nip, nama_lengkap, spesialisasi "
                + "FROM dokter "
                + "UNION ALL "
                + "SELECT 'Petugas', p.nip, p.nama_lengkap, "
                + "CONCAT('Shift ', s.nama_shift) "
                + "FROM petugas p "
                + "JOIN shift s ON s.id_shift = p.id_shift"
                + ") AS akun "
                + "WHERE (nama_lengkap LIKE ? OR nip LIKE ?) "
                + "AND (? = 'Semua role' OR role = ?) "
                + "ORDER BY role, nama_lengkap";

        List<Akun> hasil = new ArrayList<>();

        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + kata + "%");
            ps.setString(2, "%" + kata + "%");
            ps.setString(3, role);
            ps.setString(4, role);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Akun akun = new Akun();
                    akun.setRole(rs.getString("role"));
                    akun.setNip(rs.getString("nip"));
                    akun.setNama(rs.getString("nama_lengkap"));
                    akun.setDetail(rs.getString("detail"));
                    hasil.add(akun);
                }
            }
        }
        return hasil;
    }

    // Menghitung jumlah akun
    public int[] hitung() throws SQLException {

        int[] jumlah = new int[3];
        String[] tabel = {"admin", "dokter", "petugas"};

        try (Connection conn = KoneksiDB.getConnection()) {

            for (int i = 0; i < tabel.length; i++) {
                String sql = "SELECT COUNT(*) FROM " + tabel[i];

                try (PreparedStatement ps = conn.prepareStatement(sql);
                     ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {
                        jumlah[i] = rs.getInt(1);
                    }
                }
            }
        }
        return jumlah;
    }

    // Menambahkan akun baru
    public boolean tambah(String role, String nip, String nama,
            LocalDate tanggalLahir, String extra,
            String refId, String password) throws SQLException {

        String sql;

        if (role.equals("Admin")) {
            sql = "INSERT INTO admin "
                    + "(nip, nama_lengkap, username, password) "
                    + "VALUES (?, ?, ?, ?)";

            try (Connection conn = KoneksiDB.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, nip);
                ps.setString(2, nama);
                ps.setString(3, extra);
                ps.setString(4, password);

                return ps.executeUpdate() > 0;
            }

        } else if (role.equals("Dokter")) {
            sql = "INSERT INTO dokter "
                    + "(nip, nama_lengkap, tanggal_lahir, spesialisasi, password, id_poli) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";

            try (Connection conn = KoneksiDB.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, nip);
                ps.setString(2, nama);
                ps.setDate(3, Date.valueOf(tanggalLahir));
                ps.setString(4, extra);
                ps.setString(5, password);
                ps.setString(6, refId);

                return ps.executeUpdate() > 0;
            }

        } else {
            sql = "INSERT INTO petugas "
                    + "(nip, nama_lengkap, tanggal_lahir, password, id_shift) "
                    + "VALUES (?, ?, ?, ?, ?)";

            try (Connection conn = KoneksiDB.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, nip);
                ps.setString(2, nama);
                ps.setDate(3, Date.valueOf(tanggalLahir));
                ps.setString(4, password);
                ps.setString(5, refId);

                return ps.executeUpdate() > 0;
            }
        }
    }

    // Memeriksa apakah NIP sudah digunakan
    public boolean nipAda(String role, String nip) throws SQLException {

        String sql = "SELECT COUNT(*) FROM " + tabel(role)
                + " WHERE nip = ?";

        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nip);

            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        }
    }

    // Memeriksa apakah username Admin sudah digunakan
    public boolean usernameAdminAda(String username) throws SQLException {

        String sql = "SELECT COUNT(*) FROM admin WHERE username = ?";

        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        }
    }

    // Mengubah nama dan password akun
    public boolean ubah(String role, String nip, String nama,
            String passBaru) throws SQLException {

        boolean gantiPass = passBaru != null && !passBaru.isEmpty();

        String sql = "UPDATE " + tabel(role)
                + " SET nama_lengkap = ?"
                + (gantiPass ? ", password = ?" : "")
                + " WHERE nip = ?";

        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            int i = 1;
            ps.setString(i++, nama);

            if (gantiPass) {
                ps.setString(i++, passBaru);
            }

            ps.setString(i, nip);
            return ps.executeUpdate() > 0;
        }
    }
}