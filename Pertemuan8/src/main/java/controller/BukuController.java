package controller;

import java.util.List;
import model.Buku;
import model.dao.BukuDAO;

public class BukuController {

    private final BukuDAO dao = new BukuDAO();

    public List<Buku> ambilSemuaBuku() {
        return dao.ambilSemuaBuku();
    }

    public void tambahBuku(String id, String judul, String stokText) {
        Buku buku = buatBuku(id, judul, stokText);
        dao.tambahBuku(buku);
    }

    public void ubahBuku(String id, String judul, String stokText) {
        Buku buku = buatBuku(id, judul, stokText);
        dao.updateBuku(buku);
    }

    public void hapusBuku(String id) {
        dao.hapusBuku(id);
    }

    private Buku buatBuku(String id, String judul, String stokText) {
        if (id.trim().isEmpty() || judul.trim().isEmpty() || stokText.trim().isEmpty()) {
            throw new IllegalArgumentException("Semua data harus diisi!");
        }
        try {
            int stok = Integer.parseInt(stokText.trim());
            return new Buku(id.trim(), judul.trim(), stok);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Stok harus berupa angka!");
        }
    }
}