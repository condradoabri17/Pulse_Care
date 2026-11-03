package model;

public class Admin {
    private String nip;
    private String namaLengkap;
    private String username;
    private String password;

    public Admin() {
    }

    public Admin(String nip, String namaLengkap, String username, String password, String statusAkun) {
        this.nip = nip;
        this.namaLengkap = namaLengkap;
        this.username = username;
        this.password = password;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
