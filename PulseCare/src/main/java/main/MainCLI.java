package main;

import config.KoneksiDB;
import controller.LoginController;
import controller.AdminController;
import controller.PasienController;
import controller.KunjunganController;
import controller.PemeriksaanController;
import dao.ReferensiDAO;
import model.Akun;
import model.Pasien;
import model.Poliklinik;
import model.Shift;
import model.RiwayatKunjungan;
import model.HasilKunjungan;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class MainCLI {

    static Scanner input = new Scanner(System.in);

    static LoginController loginController = new LoginController();
    static AdminController adminController = new AdminController();
    static PasienController pasienController = new PasienController();
    static KunjunganController kunjunganController = new KunjunganController();
    static PemeriksaanController pemeriksaanController = new PemeriksaanController();
    static ReferensiDAO referensiDAO = new ReferensiDAO();

    public static void main(String[] args) {

        System.out.println("================================");
        System.out.println("          PULSECARE CLI");
        System.out.println("================================");

        try (Connection conn = KoneksiDB.getConnection()) {
            System.out.println("Koneksi database berhasil!");
        } catch (SQLException e) {
            System.out.println("Koneksi database gagal!");
            System.out.println(e.getMessage());
            return;
        }

        int pilihan;

        do {
            System.out.println("\n========== MENU UTAMA ==========");
            System.out.println("1. Login");
            System.out.println("2. Keluar");

            pilihan = bacaInt("Pilih menu: ");

            try {
                switch (pilihan) {
                    case 1:
                        menuLogin();
                        break;
                    case 2:
                        System.out.println("Terima kasih telah menggunakan PulseCare!");
                        break;
                    default:
                        System.out.println("Pilihan tidak tersedia!");
                }
            } catch (SQLException e) {
                System.out.println("Terjadi kesalahan database: " + e.getMessage());
            }

        } while (pilihan != 2);
    }

    // ==================== LOGIN ====================

    private static void menuLogin() throws SQLException {

        System.out.println("\n========== LOGIN ==========");

        if (loginController.isTerkunci()) {
            System.out.println("Login terkunci. Silakan tunggu "
                    + loginController.sisaWaktuKunci() + " detik.");
            return;
        }

        System.out.print("Username / NIP: ");
        String username = input.nextLine();

        System.out.print("Password: ");
        String password = input.nextLine();

        Akun akun = loginController.login(username, password);

        if (akun == null) {
            System.out.println("Login gagal! Periksa username/NIP dan password.");

            if (loginController.isTerkunci()) {
                System.out.println("Login dikunci selama 10 detik.");
            }

            return;
        }

        System.out.println("\nLogin berhasil!");
        System.out.println("Selamat datang, " + akun.getNama());
        System.out.println("Role: " + akun.getRole());

        switch (akun.getRole()) {
            case "Admin":
                menuAdmin(akun);
                break;
            case "Petugas":
                menuPetugas(akun);
                break;
            case "Dokter":
                menuDokter(akun);
                break;
            default:
                System.out.println("Role tidak dikenali!");
        }
    }

    // ==================== MENU ADMIN ====================

    private static void menuAdmin(Akun akun) throws SQLException {

        int pilihan;

        do {
            System.out.println("\n========== MENU ADMIN ==========");
            System.out.println("Selamat datang, " + akun.getNama());
            System.out.println("1. Ringkasan akun");
            System.out.println("2. Tampilkan semua akun");
            System.out.println("3. Cari akun");
            System.out.println("4. Tambah akun");
            System.out.println("5. Ubah akun");
            System.out.println("0. Logout");

            pilihan = bacaInt("Pilih menu: ");

            switch (pilihan) {
                case 1:
                    ringkasanAkun();
                    break;
                case 2:
                    tampilAkun("", "Semua role");
                    break;
                case 3:
                    cariAkun();
                    break;
                case 4:
                    tambahAkun();
                    break;
                case 5:
                    ubahAkun();
                    break;
                case 0:
                    System.out.println("Logout berhasil.");
                    break;
                default:
                    System.out.println("Pilihan tidak tersedia!");
            }

        } while (pilihan != 0);
    }

    private static void ringkasanAkun() throws SQLException {

        int[] jumlah = adminController.hitungAkun();

        System.out.println("\n========== RINGKASAN AKUN ==========");
        System.out.println("Jumlah Admin   : " + jumlah[0]);
        System.out.println("Jumlah Dokter  : " + jumlah[1]);
        System.out.println("Jumlah Petugas : " + jumlah[2]);
        System.out.println("Total akun     : "
                + (jumlah[0] + jumlah[1] + jumlah[2]));
    }

    private static void tampilAkun(String kata, String role) throws SQLException {

        List<Akun> daftar = adminController.semua(kata, role);

        System.out.println("\n========== DAFTAR AKUN ==========");

        if (daftar.isEmpty()) {
            System.out.println("Data akun tidak ditemukan.");
            return;
        }

        for (Akun a : daftar) {
            System.out.println("--------------------------------");
            System.out.println("Role : " + a.getRole());
            System.out.println("NIP  : " + a.getNip());
            System.out.println("Nama : " + a.getNama());
            System.out.println("Info : " + a.getDetail());
        }
    }

    private static void cariAkun() throws SQLException {

        System.out.print("Masukkan nama atau NIP: ");
        String kata = input.nextLine();

        System.out.println("Pilih role:");
        System.out.println("1. Semua role");
        System.out.println("2. Admin");
        System.out.println("3. Dokter");
        System.out.println("4. Petugas");

        int pilihan = bacaInt("Pilihan: ");
        String role;

        switch (pilihan) {
            case 2:
                role = "Admin";
                break;
            case 3:
                role = "Dokter";
                break;
            case 4:
                role = "Petugas";
                break;
            default:
                role = "Semua role";
        }

        tampilAkun(kata, role);
    }

    private static void tambahAkun() throws SQLException {

        System.out.println("\n========== TAMBAH AKUN ==========");
        System.out.println("1. Admin");
        System.out.println("2. Dokter");
        System.out.println("3. Petugas");

        int pilihan = bacaInt("Pilih role: ");

        String role;

        switch (pilihan) {
            case 1:
                role = "Admin";
                break;
            case 2:
                role = "Dokter";
                break;
            case 3:
                role = "Petugas";
                break;
            default:
                System.out.println("Role tidak tersedia.");
                return;
        }

        System.out.print("NIP: ");
        String nip = input.nextLine();

        System.out.print("Nama lengkap: ");
        String nama = input.nextLine();

        LocalDate tanggalLahir = null;
        String extra = null;
        String refId = null;

        if (!role.equals("Admin")) {
            tanggalLahir = bacaTanggal("Tanggal lahir (YYYY-MM-DD): ");
        }

        if (role.equals("Admin")) {

            System.out.print("Username: ");
            extra = input.nextLine();

        } else if (role.equals("Dokter")) {

            System.out.print("Spesialisasi: ");
            extra = input.nextLine();

            List<Poliklinik> daftar = referensiDAO.semuaPoliklinik();

            if (daftar.isEmpty()) {
                System.out.println("Data poliklinik belum tersedia.");
                return;
            }

            System.out.println("\nDaftar poliklinik:");
            for (Poliklinik p : daftar) {
                System.out.println(p.getIdPoli() + " - " + p.getNamaPoli());
            }

            System.out.print("Masukkan ID poliklinik: ");
            refId = input.nextLine();

            if (!poliklinikAda(daftar, refId)) {
                System.out.println("ID poliklinik tidak ditemukan.");
                return;
            }

        } else {

            List<Shift> daftar = referensiDAO.semuaShift();

            if (daftar.isEmpty()) {
                System.out.println("Data shift belum tersedia.");
                return;
            }

            System.out.println("\nDaftar shift:");
            for (Shift s : daftar) {
                System.out.println(s.getIdShift() + " - " + s.getNamaShift()
                        + " (" + s.getJamMulai() + " - " + s.getJamSelesai() + ")");
            }

            System.out.print("Masukkan ID shift: ");
            refId = input.nextLine();

            if (!shiftAda(daftar, refId)) {
                System.out.println("ID shift tidak ditemukan.");
                return;
            }
        }

        System.out.print("Password: ");
        String password = input.nextLine();

        boolean berhasil = adminController.tambah(
                role, nip, nama, tanggalLahir, extra, refId, password
        );

        System.out.println(berhasil
                ? "Akun berhasil ditambahkan."
                : "Akun gagal ditambahkan. Periksa data atau kemungkinan NIP/username sudah digunakan.");
    }

    private static void ubahAkun() throws SQLException {

        System.out.println("\n========== UBAH AKUN ==========");

        System.out.print("Role akun (Admin/Dokter/Petugas): ");
        String role = input.nextLine();

        if (!role.equals("Admin") && !role.equals("Dokter")
                && !role.equals("Petugas")) {
            System.out.println("Role tidak valid.");
            return;
        }

        System.out.print("NIP akun: ");
        String nip = input.nextLine();

        System.out.print("Nama baru: ");
        String nama = input.nextLine();

        System.out.print("Password baru (kosongkan jika tidak diubah): ");
        String password = input.nextLine();

        boolean berhasil = adminController.ubah(role, nip, nama, password);

        System.out.println(berhasil
                ? "Data akun berhasil diubah."
                : "Data akun gagal diubah atau NIP tidak ditemukan.");
    }

    // ==================== MENU PETUGAS ====================

    private static void menuPetugas(Akun akun) throws SQLException {

        int pilihan;

        do {
            System.out.println("\n========== MENU PETUGAS ==========");
            System.out.println("Selamat datang, " + akun.getNama());
            System.out.println("1. Tampilkan semua pasien");
            System.out.println("2. Cari pasien");
            System.out.println("3. Tambah pasien");
            System.out.println("4. Ubah pasien");
            System.out.println("5. Daftarkan kunjungan");
            System.out.println("6. Riwayat kunjungan");
            System.out.println("0. Logout");

            pilihan = bacaInt("Pilih menu: ");

            switch (pilihan) {
                case 1:
                    tampilPasien(pasienController.semua());
                    break;
                case 2:
                    cariPasien();
                    break;
                case 3:
                    tambahPasien();
                    break;
                case 4:
                    ubahPasien();
                    break;
                case 5:
                    daftarKunjungan(akun);
                    break;
                case 6:
                    riwayatKunjungan();
                    break;
                case 0:
                    System.out.println("Logout berhasil.");
                    break;
                default:
                    System.out.println("Pilihan tidak tersedia!");
            }

        } while (pilihan != 0);
    }

    private static void tampilPasien(List<Pasien> daftar) {

        System.out.println("\n========== DATA PASIEN ==========");

        if (daftar.isEmpty()) {
            System.out.println("Data pasien tidak ditemukan.");
            return;
        }

        for (Pasien p : daftar) {
            cetakPasien(p);
        }
    }

    private static void cetakPasien(Pasien p) {

        System.out.println("--------------------------------");
        System.out.println("ID Pasien      : " + p.getIdPasien());
        System.out.println("Nama           : " + p.getNamaLengkap());
        System.out.println("NIK            : " + p.getNik());
        System.out.println("Tanggal lahir  : " + p.getTanggalLahir());
        System.out.println("Jenis kelamin  : " + p.getJenisKelamin());
        System.out.println("Status BPJS    : " + p.getStatusBpjs());
        System.out.println("Alamat         : " + p.getAlamat());
    }

    private static void cariPasien() throws SQLException {

        System.out.print("Masukkan ID, nama, atau NIK: ");
        String kata = input.nextLine();

        tampilPasien(pasienController.cari(kata));
    }

    private static void tambahPasien() throws SQLException {

        System.out.println("\n========== TAMBAH PASIEN ==========");

        String id = buatId("PS", 8);

        System.out.println("ID Pasien baru: " + id);

        System.out.print("Nama lengkap: ");
        String nama = input.nextLine();

        System.out.print("NIK: ");
        String nik = input.nextLine();

        LocalDate tanggalLahir = bacaTanggal("Tanggal lahir (YYYY-MM-DD): ");

        System.out.println("Jenis kelamin:");
        System.out.println("1. laki-laki");
        System.out.println("2. perempuan");

        int jk = bacaInt("Pilih: ");
        String jenisKelamin;

        if (jk == 1) {
            jenisKelamin = "laki-laki";
        } else if (jk == 2) {
            jenisKelamin = "perempuan";
        } else {
            System.out.println("Pilihan jenis kelamin tidak valid.");
            return;
        }

        System.out.println("Status BPJS:");
        System.out.println("1. aktif");
        System.out.println("2. tidak-aktif");

        int bpjs = bacaInt("Pilih: ");
        String statusBpjs;

        if (bpjs == 1) {
            statusBpjs = "aktif";
        } else if (bpjs == 2) {
            statusBpjs = "tidak-aktif";
        } else {
            System.out.println("Pilihan status BPJS tidak valid.");
            return;
        }

        System.out.print("Alamat: ");
        String alamat = input.nextLine();

        Pasien pasien = new Pasien(
                id, nama, nik, tanggalLahir,
                jenisKelamin, statusBpjs, alamat
        );

        boolean berhasil = pasienController.tambah(pasien);

        System.out.println(berhasil
                ? "Pasien berhasil ditambahkan."
                : "Pasien gagal ditambahkan.");
    }

    private static void ubahPasien() throws SQLException {

        System.out.println("\n========== UBAH PASIEN ==========");

        System.out.print("Masukkan ID pasien: ");
        String id = input.nextLine();

        List<Pasien> daftar = pasienController.cari(id);
        Pasien pasien = cariPasienDariDaftar(daftar, id);

        if (pasien == null) {
            System.out.println("Pasien tidak ditemukan.");
            return;
        }

        System.out.println("Data saat ini:");
        cetakPasien(pasien);

        System.out.print("Nama baru: ");
        pasien.setNamaLengkap(input.nextLine());

        System.out.print("NIK baru: ");
        pasien.setNik(input.nextLine());

        pasien.setTanggalLahir(bacaTanggal("Tanggal lahir baru (YYYY-MM-DD): "));

        System.out.println("Jenis kelamin:");
        System.out.println("1. laki-laki");
        System.out.println("2. perempuan");

        int jk = bacaInt("Pilih: ");

        if (jk == 1) {
            pasien.setJenisKelamin("laki-laki");
        } else if (jk == 2) {
            pasien.setJenisKelamin("perempuan");
        } else {
            System.out.println("Pilihan tidak valid.");
            return;
        }

        System.out.println("Status BPJS:");
        System.out.println("1. aktif");
        System.out.println("2. tidak-aktif");

        int bpjs = bacaInt("Pilih: ");

        if (bpjs == 1) {
            pasien.setStatusBpjs("aktif");
        } else if (bpjs == 2) {
            pasien.setStatusBpjs("tidak-aktif");
        } else {
            System.out.println("Pilihan tidak valid.");
            return;
        }

        System.out.print("Alamat baru: ");
        pasien.setAlamat(input.nextLine());

        boolean berhasil = pasienController.ubah(pasien);

        System.out.println(berhasil
                ? "Data pasien berhasil diubah."
                : "Data pasien gagal diubah.");
    }

    private static Pasien cariPasienDariDaftar(List<Pasien> daftar, String id) {

        for (Pasien p : daftar) {
            if (p.getIdPasien().equalsIgnoreCase(id)) {
                return p;
            }
        }

        return null;
    }

    // ==================== PENDAFTARAN KUNJUNGAN ====================

    private static void daftarKunjungan(Akun akun) throws SQLException {

        System.out.println("\n========== PENDAFTARAN KUNJUNGAN ==========");

        System.out.print("Masukkan ID, nama, atau NIK pasien: ");
        String kata = input.nextLine();

        List<Pasien> daftarPasien = pasienController.cari(kata);

        if (daftarPasien.isEmpty()) {
            System.out.println("Pasien tidak ditemukan.");
            System.out.println("Silakan tambahkan pasien terlebih dahulu.");
            return;
        }

        tampilPasien(daftarPasien);

        System.out.print("\nMasukkan ID pasien yang dipilih: ");
        String idPasien = input.nextLine();

        Pasien pasien = cariPasienDariDaftar(daftarPasien, idPasien);

        if (pasien == null) {
            System.out.println("Pasien tidak ditemukan dalam hasil pencarian.");
            return;
        }

        if (kunjunganController.adaKunjunganAktif(idPasien)) {
            System.out.println("Pasien masih memiliki kunjungan aktif hari ini.");
            return;
        }

        List<Poliklinik> daftarPoli = referensiDAO.semuaPoliklinik();

        if (daftarPoli.isEmpty()) {
            System.out.println("Data poliklinik belum tersedia.");
            return;
        }

        System.out.println("\nDaftar poliklinik:");
        for (Poliklinik p : daftarPoli) {
            System.out.println(p.getIdPoli() + " - " + p.getNamaPoli());
        }

        System.out.print("Masukkan ID poliklinik: ");
        String idPoli = input.nextLine();

        if (!poliklinikAda(daftarPoli, idPoli)) {
            System.out.println("Poliklinik tidak ditemukan.");
            return;
        }

        System.out.print("Keluhan pasien: ");
        String keluhan = input.nextLine();

        LocalDate tanggal = LocalDate.now();

        List<RiwayatKunjungan> semua = kunjunganController.semua();

        int nomor = 1;

        for (RiwayatKunjungan k : semua) {
            if (tanggal.equals(k.getTanggalPendaftaran())
                    && idPoli.equals(k.getIdPoli())) {
                nomor++;
            }
        }

        String nomorAntrian = String.format("A%03d", nomor);
        String idKunjungan = buatIdKunjungan(semua);

        RiwayatKunjungan kunjungan = new RiwayatKunjungan(
                idKunjungan,
                akun.getNip(),
                pasien.getIdPasien(),
                idPoli,
                LocalDateTime.now(),
                nomorAntrian,
                "menunggu",
                tanggal,
                keluhan
        );

        System.out.println("\n========== KONFIRMASI KUNJUNGAN ==========");
        System.out.println("ID Kunjungan : " + idKunjungan);
        System.out.println("Pasien       : " + pasien.getNamaLengkap());
        System.out.println("Poliklinik   : " + idPoli);
        System.out.println("Nomor antrean: " + nomorAntrian);
        System.out.println("Keluhan      : " + keluhan);

        System.out.print("Simpan kunjungan? (y/n): ");
        String konfirmasi = input.nextLine();

        if (!konfirmasi.equalsIgnoreCase("y")) {
            System.out.println("Pendaftaran dibatalkan.");
            return;
        }

        boolean berhasil = kunjunganController.tambah(kunjungan);

        System.out.println(berhasil
                ? "Pendaftaran berhasil disimpan."
                : "Pendaftaran gagal disimpan.");
    }

    private static String buatIdKunjungan(List<RiwayatKunjungan> daftar) {

        String id;

        do {
            id = buatId("KJ", 8);

            boolean sudahAda = false;

            for (RiwayatKunjungan k : daftar) {
                if (id.equals(k.getIdKunjungan())) {
                    sudahAda = true;
                    break;
                }
            }

            if (!sudahAda) {
                return id;
            }

        } while (true);
    }

    private static void riwayatKunjungan() throws SQLException {

        System.out.println("\n========== RIWAYAT KUNJUNGAN ==========");
        System.out.println("1. Semua kunjungan");
        System.out.println("2. Berdasarkan tanggal");

        int pilihan = bacaInt("Pilih: ");

        List<RiwayatKunjungan> daftar;

        if (pilihan == 2) {
            LocalDate tanggal = bacaTanggal("Tanggal (YYYY-MM-DD): ");
            daftar = kunjunganController.berdasarkanTanggal(tanggal);
        } else {
            daftar = kunjunganController.semua();
        }

        tampilKunjungan(daftar);
    }

    private static void tampilKunjungan(List<RiwayatKunjungan> daftar) {

        System.out.println("\n========== DATA KUNJUNGAN ==========");

        if (daftar.isEmpty()) {
            System.out.println("Belum ada data kunjungan.");
            return;
        }

        for (RiwayatKunjungan k : daftar) {
            cetakKunjungan(k);
        }
    }

    private static void cetakKunjungan(RiwayatKunjungan k) {

        System.out.println("--------------------------------");
        System.out.println("ID Kunjungan : " + k.getIdKunjungan());
        System.out.println("ID Pasien    : " + k.getIdPasien());
        System.out.println("ID Poliklinik: " + k.getIdPoli());
        System.out.println("Nomor antrean: " + k.getNomorAntrian());
        System.out.println("Tanggal      : " + k.getTanggalPendaftaran());
        System.out.println("Waktu daftar : " + k.getWaktuPendaftaran());
        System.out.println("Status       : " + k.getStatusKunjungan());
        System.out.println("Keluhan      : " + k.getKeluhan());
    }

    // ==================== MENU DOKTER ====================

    private static void menuDokter(Akun akun) throws SQLException {

        int pilihan;

        do {
            System.out.println("\n========== MENU DOKTER ==========");
            System.out.println("Selamat datang, " + akun.getNama());
            System.out.println("Poliklinik: " + akun.getNamaPoli());
            System.out.println("1. Lihat antrean pasien");
            System.out.println("2. Periksa pasien");
            System.out.println("3. Riwayat pemeriksaan");
            System.out.println("0. Logout");

            pilihan = bacaInt("Pilih menu: ");

            switch (pilihan) {
                case 1:
                    antreanDokter(akun);
                    break;
                case 2:
                    periksaPasien(akun);
                    break;
                case 3:
                    riwayatPemeriksaan(akun);
                    break;
                case 0:
                    System.out.println("Logout berhasil.");
                    break;
                default:
                    System.out.println("Pilihan tidak tersedia!");
            }

        } while (pilihan != 0);
    }

    private static List<RiwayatKunjungan> antreanPoli(Akun akun)
            throws SQLException {

        List<RiwayatKunjungan> semua = kunjunganController.semua();
        List<RiwayatKunjungan> hasil = new java.util.ArrayList<>();

        for (RiwayatKunjungan k : semua) {
            if (akun.getIdPoli() != null
                    && akun.getIdPoli().equals(k.getIdPoli())
                    && LocalDate.now().equals(k.getTanggalPendaftaran())
                    && ("menunggu".equalsIgnoreCase(k.getStatusKunjungan())
                    || "diperiksa".equalsIgnoreCase(k.getStatusKunjungan()))) {

                hasil.add(k);
            }
        }

        return hasil;
    }

    private static void antreanDokter(Akun akun) throws SQLException {

        System.out.println("\n========== ANTREAN DOKTER ==========");
        tampilKunjungan(antreanPoli(akun));
    }

    private static void periksaPasien(Akun akun) throws SQLException {

        System.out.println("\n========== PEMERIKSAAN PASIEN ==========");

        List<RiwayatKunjungan> daftar = antreanPoli(akun);

        if (daftar.isEmpty()) {
            System.out.println("Tidak ada pasien dalam antrean.");
            return;
        }

        tampilKunjungan(daftar);

        System.out.print("\nMasukkan ID kunjungan yang akan diperiksa: ");
        String idKunjungan = input.nextLine();

        RiwayatKunjungan terpilih = null;

        for (RiwayatKunjungan k : daftar) {
            if (k.getIdKunjungan().equalsIgnoreCase(idKunjungan)) {
                terpilih = k;
                break;
            }
        }

        if (terpilih == null) {
            System.out.println("Kunjungan tidak ditemukan dalam antrean poliklinik ini.");
            return;
        }

        HasilKunjungan hasilLama =
                pemeriksaanController.berdasarkanKunjungan(idKunjungan);

        if (hasilLama != null) {
            System.out.println("Hasil pemeriksaan sudah tersedia.");
            System.out.println("Gunakan menu riwayat pemeriksaan untuk melihat data.");
            return;
        }

        boolean statusBerhasil =
                kunjunganController.ubahStatus(idKunjungan, "diperiksa");

        if (!statusBerhasil) {
            System.out.println("Status kunjungan gagal diperbarui.");
            return;
        }

        System.out.print("Keluhan: ");
        String keluhan = input.nextLine();

        System.out.print("Hasil pemeriksaan: ");
        String pemeriksaan = input.nextLine();

        System.out.print("Diagnosa: ");
        String diagnosa = input.nextLine();

        System.out.print("Resep obat (isi - jika tidak ada): ");
        String resep = input.nextLine();

        System.out.print("Catatan tindak lanjut (boleh kosong): ");
        String catatan = input.nextLine();

        String idHasil = buatIdHasil();

        HasilKunjungan hasil = new HasilKunjungan(
                idHasil,
                idKunjungan,
                akun.getNip(),
                keluhan,
                pemeriksaan,
                diagnosa,
                resep,
                catatan
        );

        boolean tersimpan = pemeriksaanController.tambah(hasil);

        if (!tersimpan) {
            System.out.println("Hasil pemeriksaan gagal disimpan.");
            System.out.println("Status kunjungan tetap diperiksa.");
            return;
        }

        boolean selesai =
                kunjunganController.ubahStatus(idKunjungan, "selesai");

        if (selesai) {
            System.out.println("Hasil pemeriksaan berhasil disimpan.");
            System.out.println("Status kunjungan: selesai.");
        } else {
            System.out.println("Hasil tersimpan, tetapi status kunjungan gagal diperbarui.");
        }
    }

    private static String buatIdHasil() throws SQLException {

        List<HasilKunjungan> daftar = pemeriksaanController.semua();

        String id;

        do {
            id = buatId("HS", 8);

            boolean sudahAda = false;

            for (HasilKunjungan h : daftar) {
                if (id.equals(h.getIdHasil())) {
                    sudahAda = true;
                    break;
                }
            }

            if (!sudahAda) {
                return id;
            }

        } while (true);
    }

    private static void riwayatPemeriksaan(Akun akun) throws SQLException {

        System.out.println("\n========== RIWAYAT PEMERIKSAAN ==========");

        List<HasilKunjungan> daftar = pemeriksaanController.semua();
        int jumlah = 0;

        for (HasilKunjungan h : daftar) {

            if (!akun.getNip().equals(h.getNipDokter())) {
                continue;
            }

            jumlah++;

            System.out.println("--------------------------------");
            System.out.println("ID Hasil          : " + h.getIdHasil());
            System.out.println("ID Kunjungan      : " + h.getIdKunjungan());
            System.out.println("Keluhan           : " + h.getKeluhan());
            System.out.println("Hasil pemeriksaan : " + h.getHasilPemeriksaan());
            System.out.println("Diagnosa          : " + h.getDiagnosa());
            System.out.println("Resep obat        : " + h.getResepObat());
            System.out.println("Catatan           : " + h.getCatatanTindakLanjut());
        }

        if (jumlah == 0) {
            System.out.println("Belum ada riwayat pemeriksaan.");
        }
    }

    // ==================== METHOD BANTU ====================

    private static int bacaInt(String pesan) {

        while (true) {
            System.out.print(pesan);
            String nilai = input.nextLine();

            try {
                return Integer.parseInt(nilai.trim());
            } catch (NumberFormatException e) {
                System.out.println("Masukkan angka yang valid.");
            }
        }
    }

    private static LocalDate bacaTanggal(String pesan) {

        while (true) {
            System.out.print(pesan);
            String nilai = input.nextLine();

            try {
                return LocalDate.parse(nilai.trim());
            } catch (DateTimeParseException e) {
                System.out.println("Format tanggal salah. Gunakan YYYY-MM-DD.");
            }
        }
    }

    private static String buatId(String awalan, int panjang) {

        String uuid = UUID.randomUUID().toString().replace("-", "");
        return awalan + uuid.substring(0, panjang);
    }

    private static boolean poliklinikAda(
            List<Poliklinik> daftar, String id) {

        for (Poliklinik p : daftar) {
            if (p.getIdPoli().equalsIgnoreCase(id)) {
                return true;
            }
        }

        return false;
    }

    private static boolean shiftAda(List<Shift> daftar, String id) {

        for (Shift s : daftar) {
            if (s.getIdShift().equalsIgnoreCase(id)) {
                return true;
            }
        }

        return false;
    }
}