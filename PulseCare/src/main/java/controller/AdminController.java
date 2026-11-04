package controller;

import dao.AdminDAO;
import model.Akun;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class AdminController {

    private final AdminDAO adminDAO = new AdminDAO();

    // Mengambil daftar akun
    public List<Akun> semua(String kataKunci, String role)
            throws SQLException {
        return adminDAO.semua(kataKunci, role);
    }

    // Mengambil jumlah akun berdasarkan role
    public int[] hitungAkun() throws SQLException {
        return adminDAO.hitung();
    }

    // Menambahkan akun baru
    public boolean tambah(
            String role,
            String nip,
            String nama,
            LocalDate tanggalLahir,
            String extra,
            String refId,
            String password) throws SQLException {

        if (role == null || nip == null || nip.trim().isEmpty()
                || nama == null || nama.trim().isEmpty()
                || password == null || password.isEmpty()) {
            return false;
        }

        if (adminDAO.nipAda(role, nip.trim())) {
            return false;
        }

        if ("Admin".equalsIgnoreCase(role)
                && adminDAO.usernameAdminAda(extra)) {
            return false;
        }

        return adminDAO.tambah(
                role, nip.trim(), nama.trim(),
                tanggalLahir, extra, refId, password
        );
    }

    // Mengubah data akun
    public boolean ubah(
            String role,
            String nip,
            String nama,
            String passwordBaru) throws SQLException {

        if (role == null || nip == null || nip.trim().isEmpty() || nama == null || nama.trim().isEmpty()) {
            return false;
        }
        return adminDAO.ubah(
                role, nip.trim(), nama.trim(), passwordBaru
        );
    }
}