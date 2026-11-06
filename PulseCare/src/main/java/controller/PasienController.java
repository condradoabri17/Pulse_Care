package controller;

import dao.PasienDAO;
import model.Pasien;
import java.sql.SQLException;
import java.util.List;

public class PasienController {

    private final PasienDAO pasienDAO = new PasienDAO();
   
    public String idBerikutnya() throws SQLException {
        return pasienDAO.idBerikutnya();
    }
    // Mengambil seluruh data pasien
    public List<Pasien> semua() throws SQLException {
        return pasienDAO.semua();
    }
    // Mencari pasien
    public List<Pasien> cari(String kataKunci) throws SQLException {

        if (kataKunci == null || kataKunci.trim().isEmpty()) {
            return pasienDAO.semua();
        }

        return pasienDAO.cari(kataKunci.trim());
    }
    // Menambahkan pasien baru
    public boolean tambah(Pasien pasien) throws SQLException {

        if (pasien == null
                || pasien.getIdPasien() == null
                || pasien.getNamaLengkap() == null
                || pasien.getNik() == null
                || pasien.getTanggalLahir() == null
                || pasien.getJenisKelamin() == null
                || pasien.getStatusBpjs() == null) {
            return false;
        }
        return pasienDAO.tambah(pasien);
    }
    // Mengubah data pasien
    public boolean ubah(Pasien pasien) throws SQLException {

        if (pasien == null
                || pasien.getIdPasien() == null
                || pasien.getNamaLengkap() == null
                || pasien.getNik() == null
                || pasien.getTanggalLahir() == null) {
            return false;
        }
        return pasienDAO.ubah(pasien);
    }
}