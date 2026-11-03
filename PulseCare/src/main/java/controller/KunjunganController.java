package controller;

import dao.KunjunganDAO;
import model.RiwayatKunjungan;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class KunjunganController {

    private final KunjunganDAO kunjunganDAO = new KunjunganDAO();

    // Mengambil seluruh data kunjungan
    public List<RiwayatKunjungan> semua() throws SQLException {
        return kunjunganDAO.semua();
    }
    // Mengambil kunjungan berdasarkan tanggal
    public List<RiwayatKunjungan> berdasarkanTanggal(LocalDate tanggal)
            throws SQLException {

        if (tanggal == null) {
            return kunjunganDAO.semua();
        }
        return kunjunganDAO.berdasarkanTanggal(tanggal);
    }
    // Memeriksa kunjungan aktif pasien
    public boolean adaKunjunganAktif(String idPasien)
            throws SQLException {

        if (idPasien == null || idPasien.trim().isEmpty()) {
            return false;
        }
        return kunjunganDAO.adaKunjunganAktif(idPasien.trim());
    }
    // Mendaftarkan kunjungan baru
    public boolean tambah(RiwayatKunjungan kunjungan)
            throws SQLException {

        if (kunjungan == null
                || kunjungan.getIdKunjungan() == null
                || kunjungan.getIdPasien() == null
                || kunjungan.getNipPetugas() == null
                || kunjungan.getIdPoli() == null
                || kunjungan.getWaktuPendaftaran() == null
                || kunjungan.getTanggalPendaftaran() == null
                || kunjungan.getNomorAntrian() == null) {
            return false;
        }

        if (adaKunjunganAktif(kunjungan.getIdPasien())) {
            return false;
        }
        return kunjunganDAO.tambah(kunjungan);
    }
    // Memperbarui status kunjungan
    public boolean ubahStatus(String idKunjungan, String status)
            throws SQLException {

        if (idKunjungan == null || idKunjungan.trim().isEmpty() || status == null || status.trim().isEmpty()) {
            return false;
        }
        return kunjunganDAO.ubahStatus(
            idKunjungan.trim(), status.trim()
        );
    }
}