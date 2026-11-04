package controller;

import dao.HasilKunjunganDAO;
import model.HasilKunjungan;
import java.sql.SQLException;

public class PemeriksaanController {

    private final HasilKunjunganDAO hasilDAO = new HasilKunjunganDAO();

    // Mengambil hasil pemeriksaan berdasarkan kunjungan
    public HasilKunjungan berdasarkanKunjungan(String idKunjungan) throws SQLException {
        if (idKunjungan == null || idKunjungan.trim().isEmpty()) {
            return null;
        }
        return hasilDAO.berdasarkanKunjungan(idKunjungan.trim());
    }
    // Mengambil seluruh hasil pemeriksaan
    public java.util.List<HasilKunjungan> semua() throws SQLException {
        return hasilDAO.semua();
    }
    // Menyimpan hasil pemeriksaan baru
    public boolean tambah(HasilKunjungan hasil) throws SQLException {
        if (!dataValid(hasil)) {
            return false;
        }

        // Memastikan kunjungan belum memiliki hasil pemeriksaan
        if (hasilDAO.berdasarkanKunjungan(hasil.getIdKunjungan()) != null) {
            return false;
        }
        return hasilDAO.tambah(hasil);
    }
    // Mengubah hasil pemeriksaan
    public boolean ubah(HasilKunjungan hasil) throws SQLException {

        if (!dataValid(hasil)) {
            return false;
        }
        // Memastikan hasil pemeriksaan sudah tersedia
        if (hasilDAO.berdasarkanKunjungan(hasil.getIdKunjungan()) == null) {
            return false;
        }
        return hasilDAO.ubah(hasil);
    }
    // Memeriksa kelengkapan data pemeriksaan
    private boolean dataValid(HasilKunjungan hasil) {

        return hasil != null
                && hasil.getIdKunjungan() != null
                && !hasil.getIdKunjungan().trim().isEmpty()
                && hasil.getNipDokter() != null
                && !hasil.getNipDokter().trim().isEmpty()
                && hasil.getKeluhan() != null
                && !hasil.getKeluhan().trim().isEmpty()
                && hasil.getHasilPemeriksaan() != null
                && !hasil.getHasilPemeriksaan().trim().isEmpty()
                && hasil.getDiagnosa() != null
                && !hasil.getDiagnosa().trim().isEmpty()
                && hasil.getResepObat() != null;
    }
}