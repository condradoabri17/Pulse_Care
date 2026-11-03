package controller;

import dao.LoginDAO;
import model.Akun;
import java.sql.SQLException;

public class LoginController {

    private final LoginDAO loginDAO = new LoginDAO();
    private int percobaanGagal = 0;
    private long waktuKunciSampai = 0;

    // Memeriksa apakah login sedang dikunci
    public boolean isTerkunci() {
        return System.currentTimeMillis() < waktuKunciSampai;
    }
    // Menghitung sisa waktu penguncian dalam detik
    public long sisaWaktuKunci() {
        long sisa = waktuKunciSampai - System.currentTimeMillis();
        
        if (sisa <= 0) {
            return 0;
        }
        return (sisa + 999) / 1000;
    }
    // Memproses login pengguna
    public Akun login(String id, String password) throws SQLException {

        if (isTerkunci()) {
            return null;
        }
        // Memeriksa input kosong
        if (id == null || id.trim().isEmpty() || password == null || password.isEmpty()) {
            return null;
        }
        // Memeriksa akun melalui LoginDAO
        Akun akun = loginDAO.login(id.trim(), password);

        if (akun != null) {
            percobaanGagal = 0;
            waktuKunciSampai = 0;
            return akun;
        }
        // Menghitung percobaan login yang gagal
        percobaanGagal++;

        // Mengunci login setelah 3 kali gagal
        if (percobaanGagal >= 3) {
            waktuKunciSampai =
                System.currentTimeMillis() + 10_000;
            percobaanGagal = 0;
        }
        return null;
    }
}