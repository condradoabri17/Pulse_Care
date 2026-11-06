/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package view;

import model.Akun;
import javax.swing.JOptionPane;
import dao.ReferensiDAO;

/**
 *
 * @author Lenovo
 */
public class FormAkun extends javax.swing.JDialog {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FormAkun.class.getName());

    private String mode;
    /**
     * Creates new form FormAkun
     */
    public FormAkun(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tampilkanShift();
        tampilkanSpesialisasi();
        
        setSize(450, 650);
        setLocationRelativeTo(getOwner());
        
        kolomIdentitas.setText("Masukkan Username / NIP");
        kolomNama.setText("Masukkan Nama lengkap");
        kolomTanggalLahir.setText("YYYY-MM-DD");
        
        pasangPlaceholder(kolomIdentitas, "Masukkan Username / NIP");
        pasangPlaceholder(kolomNama, "Masukkan Nama lengkap");
        pasangPlaceholder(kolomTanggalLahir, "YYYY-MM-DD");
        
        kolomIdentitas.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (kolomIdentitas.getText().equals("Masukkan Username / NIP")) {
                    kolomIdentitas.setText("");
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (kolomIdentitas.getText().isEmpty()) {
                    kolomIdentitas.setText("Masukkan Username / NIP");
                }
            }
        });
        
        kolomRole.setSelectedIndex(0);
        aturFormRole();
    }

    public void modeTambah() {
        mode = "Tambah";

        judulForm.setText("Tambah Akun");
        btnSimpan.setText("Simpan");

        kolomRole.setSelectedIndex(0);

        kolomIdentitas.setText("Masukkan Username / NIP");
        kolomNama.setText("Masukkan Nama lengkap");
        kolomTanggalLahir.setText("YYYY-MM-DD");

        kolomIdentitas.setForeground(java.awt.Color.GRAY);
        kolomNama.setForeground(java.awt.Color.GRAY);
        kolomTanggalLahir.setForeground(java.awt.Color.GRAY);

        kolomIdentitas.setEditable(true);
        aturFormRole();
    }    
    public void modeEdit(Akun akun) {
        mode = "Edit";

        judulForm.setText("Edit Akun");
        btnSimpan.setText("Simpan Perubahan");

        kolomRole.setSelectedItem(akun.getRole());
        kolomIdentitas.setText(akun.getNip());
        kolomNama.setText(akun.getNama());

        kolomIdentitas.setEditable(false);
        kolomRole.setEnabled(false);
        kolomPassword.setText("");

        if ("Petugas".equals(akun.getRole())) {
            try {
                ReferensiDAO dao = new ReferensiDAO();
                String idShift = dao.shiftPetugas(akun.getNip());

                for (int i = 0; i < kolomShift.getItemCount(); i++) {
                    String pilihan = kolomShift.getItemAt(i);

                    if (pilihan.startsWith(idShift + " - ")) {
                        kolomShift.setSelectedIndex(i);
                        break;
                    }
                }

            } catch (java.sql.SQLException e) {
                JOptionPane.showMessageDialog(
                        this, "Gagal mengambil shift: " + e.getMessage());
            }
        }
    }
    private void aturFormRole() {
    String role = kolomRole.getSelectedItem().toString();

    if (role.equals("Admin")) {
        labelIdentitas.setText("Username");

        kolomTanggalLahir.setEnabled(false);
        kolomSpesialisasi.setEnabled(false);
        kolomShift.setEnabled(false);

    } else if (role.equals("Dokter")) {
        labelIdentitas.setText("NIP");

        kolomTanggalLahir.setEnabled(true);
        kolomSpesialisasi.setEnabled(true);
        kolomShift.setEnabled(false);
        kolomShift.setSelectedIndex(-1);

    } else if (role.equals("Petugas")) {
        labelIdentitas.setText("NIP");

        kolomTanggalLahir.setEnabled(true);
        kolomSpesialisasi.setEnabled(false);
        kolomShift.setEnabled(true);
        kolomSpesialisasi.setSelectedIndex(-1);

    } else {
        labelIdentitas.setText("NIP / Username");

        kolomTanggalLahir.setEnabled(false);
        kolomSpesialisasi.setEnabled(false);
        kolomShift.setEnabled(false);

        kolomSpesialisasi.setSelectedIndex(-1);
        kolomShift.setSelectedIndex(-1);
    }
}
    private void pasangPlaceholder(
        javax.swing.JTextField kolom, String teks) {

        kolom.setText(teks);
        kolom.setForeground(java.awt.Color.GRAY);

        kolom.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (kolom.getText().equals(teks)) {
                    kolom.setText("");
                    kolom.setForeground(java.awt.Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (kolom.getText().isEmpty()) {
                    kolom.setText(teks);
                    kolom.setForeground(java.awt.Color.GRAY);
                }
            }
        });
    } 
    private void tampilkanShift() {
        ReferensiDAO dao = new ReferensiDAO();

        try {
            kolomShift.removeAllItems();

            for (model.Shift shift : dao.semuaShift()) {
                kolomShift.addItem(
                    shift.getIdShift() + " - " + shift.getNamaShift()
                );
            }

            kolomShift.setSelectedIndex(-1);

        } catch (java.sql.SQLException e) {
            javax.swing.JOptionPane.showMessageDialog(
                this,
                "Gagal mengambil data shift: " + e.getMessage()
            );
        }
    }
    private void tampilkanSpesialisasi() {
    ReferensiDAO dao = new ReferensiDAO();

    try {
        kolomSpesialisasi.removeAllItems();

        for (Object[] data : dao.dokterDanSpesialisasi()) {
            String spesialisasi = data[1].toString();

            boolean sudahAda = false;

            for (int i = 0; i < kolomSpesialisasi.getItemCount(); i++) {
                if (kolomSpesialisasi.getItemAt(i).equals(spesialisasi)) {
                    sudahAda = true;
                    break;
                }
            }

            if (!sudahAda) {
                kolomSpesialisasi.addItem(spesialisasi);
            }
        }

        kolomSpesialisasi.setSelectedIndex(-1);

    } catch (java.sql.SQLException e) {
        javax.swing.JOptionPane.showMessageDialog(
            this,
            "Gagal mengambil data spesialisasi: " + e.getMessage()
        );
    }
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        judulForm = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        labelNama = new javax.swing.JLabel();
        kolomNama = new javax.swing.JTextField();
        kolomIdentitas = new javax.swing.JTextField();
        labelIdentitas = new javax.swing.JLabel();
        kolomTanggalLahir = new javax.swing.JTextField();
        labelTanggalLahir = new javax.swing.JLabel();
        labelSpesialisasi = new javax.swing.JLabel();
        labelShift = new javax.swing.JLabel();
        labelRole = new javax.swing.JLabel();
        kolomRole = new javax.swing.JComboBox<>();
        kolomShift = new javax.swing.JComboBox<>();
        kolomSpesialisasi = new javax.swing.JComboBox<>();
        labelPassword = new javax.swing.JLabel();
        kolomPassword = new javax.swing.JPasswordField();
        btnBatal = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Form Kelola Akun");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(244, 247, 248));
        jPanel1.setPreferredSize(new java.awt.Dimension(450, 650));

        jPanel2.setBackground(new java.awt.Color(11, 59, 68));

        judulForm.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        judulForm.setForeground(new java.awt.Color(255, 255, 255));
        judulForm.setText("Form Kelola Akun");

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tambah akun.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(judulForm)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(judulForm))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        labelNama.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        labelNama.setText("Nama Lengkap");

        kolomNama.setForeground(new java.awt.Color(102, 102, 102));
        kolomNama.setText("Masukkan Nama lengkap");

        kolomIdentitas.setForeground(new java.awt.Color(102, 102, 102));
        kolomIdentitas.setText("Masukkan Username / NIP");
        kolomIdentitas.addActionListener(this::kolomIdentitasActionPerformed);

        labelIdentitas.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        labelIdentitas.setText("Username / NIP");

        kolomTanggalLahir.setForeground(new java.awt.Color(102, 102, 102));
        kolomTanggalLahir.setText("YYYY-MM-DD");

        labelTanggalLahir.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        labelTanggalLahir.setText("Tanggal Lahir");

        labelSpesialisasi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        labelSpesialisasi.setText("Spesialisasi");

        labelShift.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        labelShift.setText("Shift");

        labelRole.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        labelRole.setText("Role");

        kolomRole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Role", "Admin", "Petugas", "Dokter" }));
        kolomRole.addActionListener(this::kolomRoleActionPerformed);

        kolomShift.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Shift", "Pagi", "Siang", "Malam" }));

        kolomSpesialisasi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Spesialisasi", "Ortopedia", "THT", "Bedah", "Penyakit Dalam", "Paru", "Umum", "Jantung", "Anak", "Psikiatri", "Kulit", "Mata", "Saraf", "Radiologi", "Kandungan", "Gigi" }));

        labelPassword.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        labelPassword.setText("Password");

        btnBatal.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnBatal.setForeground(new java.awt.Color(20, 96, 108));
        btnBatal.setText("Batal");
        btnBatal.addActionListener(this::btnBatalActionPerformed);

        btnSimpan.setBackground(new java.awt.Color(20, 96, 108));
        btnSimpan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSimpan.setForeground(new java.awt.Color(255, 255, 255));
        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(this::btnSimpanActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(labelPassword)
                                .addComponent(labelShift)
                                .addComponent(labelIdentitas)
                                .addComponent(labelRole)
                                .addComponent(labelTanggalLahir)
                                .addComponent(labelSpesialisasi)
                                .addComponent(labelNama))
                            .addGap(261, 261, 261))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(kolomRole, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kolomIdentitas, javax.swing.GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE)
                    .addComponent(kolomNama)
                    .addComponent(kolomTanggalLahir)
                    .addComponent(kolomSpesialisasi, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kolomShift, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kolomPassword))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelRole)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kolomRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelIdentitas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kolomIdentitas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelNama)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kolomNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelTanggalLahir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kolomTanggalLahir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelSpesialisasi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kolomSpesialisasi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelShift)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kolomShift, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelPassword)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kolomPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void kolomIdentitasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kolomIdentitasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kolomIdentitasActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        dispose();
    }//GEN-LAST:event_btnBatalActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        String role = kolomRole.getSelectedItem().toString();
        String identitas = kolomIdentitas.getText().trim();
        String nama = kolomNama.getText().trim();
        String tanggal = kolomTanggalLahir.getText().trim();
        String password = new String(kolomPassword.getPassword());

        String spesialisasi = null;
        String idShift = null;
        java.time.LocalDate tanggalLahir = null;

        // Validasi data wajib
        if (role.equals("Pilih Role")
                || identitas.isEmpty()
                || identitas.equals("Masukkan Username / NIP")
                || nama.isEmpty()
                || nama.equals("Masukkan Nama lengkap")
                || password.isEmpty()) {

            javax.swing.JOptionPane.showMessageDialog(
                    this, "Semua data wajib diisi!");
            return;
        }

        try {
            controller.AdminController controller =
                    new controller.AdminController();

            // MODE EDIT
            if ("Edit".equals(mode)) {

                if (role.equals("Petugas")) {
                    if (kolomShift.getSelectedItem() == null) {
                        javax.swing.JOptionPane.showMessageDialog(
                                this, "Silakan pilih shift!");
                        return;
                    }

                    String pilihanShift = kolomShift.getSelectedItem().toString();
                    idShift = pilihanShift.split(" - ")[0];
                }

                boolean berhasil = controller.ubah(
                        role, identitas, nama, password, idShift
                );

                if (berhasil) {
                    javax.swing.JOptionPane.showMessageDialog(
                            this, "Data akun berhasil diperbarui!");
                    dispose();
                } else {
                    javax.swing.JOptionPane.showMessageDialog(
                            this, "Data akun gagal diperbarui!");
                }

            } else {

                // MODE TAMBAH

                if (!role.equals("Admin")) {
                    if (tanggal.isEmpty() || tanggal.equals("YYYY-MM-DD")) {
                        javax.swing.JOptionPane.showMessageDialog(
                                this, "Tanggal lahir wajib diisi!");
                        return;
                    }

                    try {
                        tanggalLahir = java.time.LocalDate.parse(tanggal);
                    } catch (java.time.format.DateTimeParseException e) {
                        javax.swing.JOptionPane.showMessageDialog(
                                this, "Format tanggal harus YYYY-MM-DD!");
                        return;
                    }
                }

                if (role.equals("Dokter")) {
                    if (kolomSpesialisasi.getSelectedItem() == null) {
                        javax.swing.JOptionPane.showMessageDialog(
                                this, "Silakan pilih spesialisasi!");
                        return;
                    }

                    spesialisasi = kolomSpesialisasi.getSelectedItem().toString();

                } else if (role.equals("Petugas")) {
                    if (kolomShift.getSelectedItem() == null) {
                        javax.swing.JOptionPane.showMessageDialog(
                                this, "Silakan pilih shift!");
                        return;
                    }

                    String pilihanShift = kolomShift.getSelectedItem().toString();
                    idShift = pilihanShift.split(" - ")[0];
                }

                boolean berhasil = controller.tambah(
                        role,
                        identitas,
                        nama,
                        tanggalLahir,
                        spesialisasi,
                        idShift,
                        password
                );

                if (berhasil) {
                    javax.swing.JOptionPane.showMessageDialog(
                            this, "Akun berhasil ditambahkan!");
                    dispose();
                } else {
                    javax.swing.JOptionPane.showMessageDialog(
                            this, "Akun gagal ditambahkan. NIP mungkin sudah digunakan.");
                }
            }

        } catch (java.sql.SQLException e) {
            javax.swing.JOptionPane.showMessageDialog(
                    this, "Terjadi kesalahan database: " + e.getMessage());
        }

    }//GEN-LAST:event_btnSimpanActionPerformed

    private void kolomRoleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kolomRoleActionPerformed
        aturFormRole();
    }//GEN-LAST:event_kolomRoleActionPerformed

    /**
     * @param args the command line arguments
     */ 
        /* Create and display the dialog */
        public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                FormAkun dialog = new FormAkun(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel judulForm;
    private javax.swing.JTextField kolomIdentitas;
    private javax.swing.JTextField kolomNama;
    private javax.swing.JPasswordField kolomPassword;
    private javax.swing.JComboBox<String> kolomRole;
    private javax.swing.JComboBox<String> kolomShift;
    private javax.swing.JComboBox<String> kolomSpesialisasi;
    private javax.swing.JTextField kolomTanggalLahir;
    private javax.swing.JLabel labelIdentitas;
    private javax.swing.JLabel labelNama;
    private javax.swing.JLabel labelPassword;
    private javax.swing.JLabel labelRole;
    private javax.swing.JLabel labelShift;
    private javax.swing.JLabel labelSpesialisasi;
    private javax.swing.JLabel labelTanggalLahir;
    // End of variables declaration//GEN-END:variables
}
