/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package transaksi;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import login.appLogin;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import koneksiDatabase.koneksi;
import static koneksiDatabase.koneksi.getConnection;
import javax.swing.JTextField;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 *
 * @author maeepp
 */
public class menuTransaksi extends javax.swing.JFrame {

    private int columnIndexForTotalHarga;
    

    /**
     * Creates new form daftar_produk
     */
    public menuTransaksi() {
        initComponents();
        getConnection();
        displayCurrentDateAndTime();

        btnMasukan.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMasukanActionPerformed(evt);
            }
        });
        btnBayar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBayarActionPerformed(evt);
            }
        });

        txtBayar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBayarActionPerformed(evt);
            }
        });

    }
     
     

    private int calculateTotal() {
        DefaultTableModel model = (DefaultTableModel) tblTransaksi.getModel();
        int rowCount = model.getRowCount();
        int total = 0;

        for (int i = 0; i < rowCount; i++) {
            int subtotal = (int) model.getValueAt(i, 4); // Ambil nilai total harga (kolom ke-4)
            total += subtotal;
        }

        return total;
    }

    public String getLastCustomerIdFromDatabase() {
        String lastCustomerId = "";
        try (Connection connection = (Connection) koneksi.getConnection()) {
            String query = "SELECT MAX(id_customer) AS last_id FROM customer_id";
            try (java.sql.PreparedStatement preparedStatement = connection.prepareStatement(query); ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    lastCustomerId = resultSet.getString("last_id");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lastCustomerId;
    }

    public String getLastTransactionIdFromDatabase() {
        String lastTransactionId = "";
        try (Connection connection = (Connection) koneksi.getConnection()) {
            String query = "SELECT MAX(id_transaksi) AS last_id FROM transaksi_id";
            try (PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query); ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    lastTransactionId = resultSet.getString("last_id");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lastTransactionId;
    }

    public void insertNewCustomerIdToDatabase(String newCustomerId) {
        try (Connection connection = (Connection) koneksi.getConnection()) {
            String query = "INSERT INTO customer_id (id_customer) VALUES (?)";
            try (PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query)) {
                preparedStatement.setString(1, newCustomerId);
                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertNewTransactionIdToDatabase(String newTransactionId) {
        try (Connection connection = (Connection) koneksi.getConnection()) {
            String query = "INSERT INTO transaksi_id (id_transaksi) VALUES (?)";
            try (PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query)) {
                preparedStatement.setString(1, newTransactionId);
                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Metode untuk menghasilkan ID pelanggan baru
    private String generateNewCustomerId() {

        String lastCustomerId = getLastCustomerIdFromDatabase();
        if (lastCustomerId == null || lastCustomerId.isEmpty()) {
            return "CUST00001"; 
        }

        int lastIdNumber = Integer.parseInt(lastCustomerId.substring(4));
        int newIdNumber = lastIdNumber + 1;

        String newCustomerId = String.format("CUST%05d", newIdNumber);

        return newCustomerId;
    }

    private String generateNewTransactionId() {

        String lastTransactionId = getLastTransactionIdFromDatabase();

        if (lastTransactionId == null || lastTransactionId.isEmpty()) {
            return "TRAN00001"; 
        }

        int lastIdNumber = Integer.parseInt(lastTransactionId.substring(4));
        int newIdNumber = lastIdNumber + 1;

        String newTransactionId = String.format("TRAN%05d", newIdNumber);

        return newTransactionId;
    }

    private void displayCurrentDateAndTime() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String formattedDateTime = dateFormat.format(currentDate);
        txtTanggal.setText(formattedDateTime);
    }

    private void getProdukInfo(String idProduk) {

        try (Connection connection = (Connection) koneksi.getConnection()) {
            String query = "SELECT nama_produk, harga_satuan FROM input_produk WHERE kd_barang = ?";
            try (PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query)) {
                preparedStatement.setString(1, idProduk);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String namaProduk = resultSet.getString("nama_produk");
                        int hargaSatuan = resultSet.getInt("harga_satuan");
                        txtHargasatuan.setText(String.valueOf(hargaSatuan));
                        txtNamaproduk.setText(namaProduk);
                    } else {
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        jLabel1 = new javax.swing.JLabel();
        btnLogout = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtIdTransaksi = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtJumlahbeli = new javax.swing.JTextField();
        txtTotalharga = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtKdBarang = new javax.swing.JTextField();
        btnMasukan = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        txtHargasatuan = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtTotalbyr = new javax.swing.JTextField();
        txtBayar = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtKembalian = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        btnBayar = new javax.swing.JButton();
        btnTransaksiBaru = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtIdcustomer = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtNamacustomer = new javax.swing.JTextField();
        txtTanggal = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblTransaksi = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        txtNamaproduk = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(0, 153, 204));

        jLabel1.setFont(new java.awt.Font("JetBrains Mono SemiBold", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("MENU TRANSAKSI");

        btnLogout.setText("LOGOUT");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLogout)
                .addGap(16, 16, 16))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1454, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(btnLogout)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addContainerGap(44, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("JetBrains Mono SemiBold", 0, 18)); // NOI18N
        jLabel2.setText("ID Transaksi");

        txtIdTransaksi.setEnabled(false);

        jLabel7.setFont(new java.awt.Font("JetBrains Mono SemiBold", 0, 18)); // NOI18N
        jLabel7.setText("Kode Barang");

        jLabel8.setFont(new java.awt.Font("JetBrains Mono SemiBold", 0, 18)); // NOI18N
        jLabel8.setText("Jumlah Beli");

        txtJumlahbeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJumlahbeliActionPerformed(evt);
            }
        });

        txtTotalharga.setEnabled(false);

        jLabel9.setFont(new java.awt.Font("JetBrains Mono SemiBold", 0, 18)); // NOI18N
        jLabel9.setText("Total Harga");

        txtKdBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKdBarangActionPerformed(evt);
            }
        });

        btnMasukan.setText("MASUKAN");
        btnMasukan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMasukanActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("JetBrains Mono SemiBold", 0, 18)); // NOI18N
        jLabel10.setText("Harga Satuan");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Total Bayar");

        txtTotalbyr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalbyrActionPerformed(evt);
            }
        });

        txtBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBayarActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Bayar");

        txtKembalian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKembalianActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setText("Kembalian");

        btnBayar.setText("Bayar");
        btnBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBayarActionPerformed(evt);
            }
        });

        btnTransaksiBaru.setText("TRANSAKSI BARU");
        btnTransaksiBaru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTransaksiBaruActionPerformed(evt);
            }
        });

        jLabel3.setText("ID Customer");

        txtIdcustomer.setEnabled(false);
        txtIdcustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdcustomerActionPerformed(evt);
            }
        });

        jLabel13.setText("Nama Customer");

        txtNamacustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamacustomerActionPerformed(evt);
            }
        });

        txtTanggal.setEnabled(false);
        txtTanggal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTanggalActionPerformed(evt);
            }
        });

        jLabel16.setText("Tanggal ");

        tblTransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kode Barang", "Nama Produk", "Harga Satuan", "Jumlah Beli", "Total Harga"
            }
        ));
        tblTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTransaksiMouseClicked(evt);
            }
        });
        tblTransaksi.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                tblTransaksiComponentShown(evt);
            }
        });
        jScrollPane2.setViewportView(tblTransaksi);

        jLabel12.setFont(new java.awt.Font("JetBrains Mono SemiBold", 0, 18)); // NOI18N
        jLabel12.setText("Nama Produk");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addGap(95, 95, 95)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtJumlahbeli)
                            .addComponent(txtTotalharga)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel7))
                        .addGap(84, 84, 84)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtIdTransaksi, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                            .addComponent(txtKdBarang, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel12))
                        .addGap(84, 84, 84)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtHargasatuan)
                            .addComponent(txtNamaproduk)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnTransaksiBaru, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(26, 26, 26)
                        .addComponent(btnMasukan, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnBayar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(txtIdcustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(29, 29, 29)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNamacustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(jLabel16))))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 886, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(txtTotalbyr, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel11)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel6)
                                    .addGap(52, 52, 52)
                                    .addComponent(txtBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addGap(1305, 1305, 1305))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1462, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtIdcustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNamacustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(8, 8, 8)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtIdTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(txtKdBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtNamaproduk, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(txtHargasatuan, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8)
                                    .addComponent(txtJumlahbeli, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9)
                                    .addComponent(txtTotalharga, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnTransaksiBaru, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnMasukan, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel15)
                        .addGap(335, 335, 335)
                        .addComponent(btnBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtTotalbyr, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(txtKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(125, 125, 125))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(1479, 856));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtNamacustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamacustomerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamacustomerActionPerformed

    private void txtIdcustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdcustomerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdcustomerActionPerformed

    private void txtKembalianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKembalianActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKembalianActionPerformed

    private void txtBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBayarActionPerformed
        // TODO add your handling code here:
        try {

            int jumlahBayar = Integer.parseInt(txtBayar.getText());

            int totalBayar = Integer.parseInt(txtTotalbyr.getText());
            int kembalian = jumlahBayar - totalBayar;

            txtKembalian.setText(String.valueOf(kembalian));
            String kdBarang = txtKdBarang.getText();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Format angka tidak valid.");
        }

    }//GEN-LAST:event_txtBayarActionPerformed

    private void txtTotalbyrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalbyrActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalbyrActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        // TODO add your handling code here:
        appLogin l = new appLogin();
        l.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void txtTanggalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTanggalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTanggalActionPerformed

    private void txtKdBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKdBarangActionPerformed
        // TODO add your handling code here:
        String idProduk = txtKdBarang.getText();
        getProdukInfo(idProduk);

    }//GEN-LAST:event_txtKdBarangActionPerformed

    private void txtJumlahbeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJumlahbeliActionPerformed
        // TODO add your handling code here:
        String jumlahBeliStr = txtJumlahbeli.getText().trim();

        if (!jumlahBeliStr.isEmpty()) {
            try {
                int jumlahBeli = Integer.parseInt(jumlahBeliStr);
                int hargaSatuan = Integer.parseInt(txtHargasatuan.getText().trim());
                int totalBayar = hargaSatuan * jumlahBeli;
                txtTotalharga.setText(String.valueOf(totalBayar));
            } catch (NumberFormatException e) {

                e.printStackTrace();
            }
        } else {

        }

    }//GEN-LAST:event_txtJumlahbeliActionPerformed

    private void btnTransaksiBaruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTransaksiBaruActionPerformed
        // TODO add your handling code here:

        String newCustomerId = generateNewCustomerId();
        String newTransactionId = generateNewTransactionId();

        insertNewCustomerIdToDatabase(newCustomerId);

        insertNewTransactionIdToDatabase(newTransactionId);

        DefaultTableModel model = (DefaultTableModel) tblTransaksi.getModel();
        model.setRowCount(0); 

        txtIdcustomer.setText(newCustomerId);
        txtIdTransaksi.setText(newTransactionId);

        txtTotalbyr.setText("");
        txtBayar.setText("");
        txtKembalian.setText("");


    }//GEN-LAST:event_btnTransaksiBaruActionPerformed

    private void btnMasukanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMasukanActionPerformed
        // TODO add your handling code here:
        try {

            String idProduk = txtKdBarang.getText();
            String namaProduk = txtNamaproduk.getText();
            int hargaSatuan = Integer.parseInt(txtHargasatuan.getText());

            if (!txtJumlahbeli.getText().isEmpty() && txtJumlahbeli.getText().matches("\\d+")) {
                int jumlahBeli = Integer.parseInt(txtJumlahbeli.getText());
                int totalHarga = Integer.parseInt(txtTotalharga.getText());

                DefaultTableModel model = (DefaultTableModel) tblTransaksi.getModel();
                Object[] row = {idProduk, namaProduk, hargaSatuan, jumlahBeli, totalHarga};
                model.addRow(row);

                txtKdBarang.setText("");
                txtNamaproduk.setText("");
                txtHargasatuan.setText("");
                txtJumlahbeli.setText("");
                txtTotalharga.setText("");
            } else {

            }
        } catch (NumberFormatException e) {
        }

    }//GEN-LAST:event_btnMasukanActionPerformed

    private void tblTransaksiComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_tblTransaksiComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_tblTransaksiComponentShown

    private void btnBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBayarActionPerformed
        // TODO add your handling code here :
        int totalBayar = calculateTotal();
        txtTotalbyr.setText(String.valueOf(totalBayar));

    }//GEN-LAST:event_btnBayarActionPerformed

    private void tblTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTransaksiMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblTransaksiMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(menuTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(menuTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(menuTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(menuTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new menuTransaksi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBayar;
    public javax.swing.JButton btnLogout;
    private javax.swing.JButton btnMasukan;
    private javax.swing.JButton btnTransaksiBaru;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblTransaksi;
    private javax.swing.JTextField txtBayar;
    private javax.swing.JTextField txtHargasatuan;
    private javax.swing.JTextField txtIdTransaksi;
    private javax.swing.JTextField txtIdcustomer;
    private javax.swing.JTextField txtJumlahbeli;
    private javax.swing.JTextField txtKdBarang;
    private javax.swing.JTextField txtKembalian;
    private javax.swing.JTextField txtNamacustomer;
    private javax.swing.JTextField txtNamaproduk;
    private javax.swing.JTextField txtTanggal;
    private javax.swing.JTextField txtTotalbyr;
    private javax.swing.JTextField txtTotalharga;
    // End of variables declaration//GEN-END:variables

}
