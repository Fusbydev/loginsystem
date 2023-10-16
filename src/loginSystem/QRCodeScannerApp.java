package loginSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.Dimension;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

public class QRCodeScannerApp extends JFrame{
    static JLabel resultLabel;
    private WebcamPreviewPanel webcamPreviewPanel;
    JPanel panel;
    public static JPanel imagePanel;
    JLabel timeLabel;
    JLabel name, studentID, yearLevel, section;
    JLabel nameL, studentIDl, yearLevelL, sectionl;
    String url = "jdbc:mysql://localhost:3306/loginSystem";
    String user = "root";
    String pass = "password";
    String urlphp = "jdbc:mysql://localhost:3307/mydb";
    String userphp = "root";
    String passphp = "password";
    String uniqueIdentifier;
    int studentId;

    public QRCodeScannerApp() {
        this.setTitle("Login System");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        panel = new JPanel();
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(400, 400));
        panel.setBackground(Color.lightGray);

        imagePanel = new JPanel();
        imagePanel.setBounds(140, 50, 100, 100);
        imagePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4, true));

        name = new JLabel("Name          :");
        name.setBounds(10, 170, 100, 50);
        nameL = new JLabel();
        nameL.setBounds(120, 170, 200, 50);
        name.setFont(new Font("impact", Font.PLAIN, 20));
        nameL.setFont(new Font("impact", Font.PLAIN, 20));

        studentID = new JLabel("Student ID :");
        studentID.setBounds(10, 220, 100, 50);
        studentIDl = new JLabel();
        studentIDl.setBounds(120, 220, 150, 50);
        studentID.setFont(new Font("impact", Font.PLAIN, 20));
        studentIDl.setFont(new Font("impact", Font.PLAIN, 20));

        yearLevel = new JLabel("Year Level :");
        yearLevel.setBounds(10, 315, 100, 50);
        yearLevelL = new JLabel();
        yearLevelL.setBounds(120, 315, 150, 50);
        yearLevel.setFont(new Font("impact", Font.PLAIN, 20));
        yearLevelL.setFont(new Font("impact", Font.PLAIN, 20));

        section = new JLabel("Section :");
        section.setBounds(10, 270, 100, 50);
        sectionl = new JLabel();
        sectionl.setBounds(120, 270, 150, 50);
        section.setFont(new Font("impact", Font.PLAIN, 20));
        sectionl.setFont(new Font("impact", Font.PLAIN, 20));

        resultLabel = new JLabel("Scan result will appear here.");
        this.add(resultLabel, BorderLayout.NORTH);

        webcamPreviewPanel = new WebcamPreviewPanel();
        this.add(webcamPreviewPanel, BorderLayout.CENTER);

        timeLabel = new JLabel();
        timeLabel.setFont(new Font("impact", Font.BOLD, 40));
        timeLabel.setForeground(Color.black);
        timeLabel.setBounds(120, 365, 500, 50);

        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTime();
            }
        });
        timer.start();

        panel.add(section);
        panel.add(sectionl);
        panel.add(name);
        panel.add(nameL);
        panel.add(studentID);
        panel.add(studentIDl);
        panel.add(yearLevel);
        panel.add(yearLevelL);
        panel.add(imagePanel);
        panel.add(timeLabel);

        this.setResizable(false);
        this.add(panel, BorderLayout.EAST);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        // Start scanning immediately after the frame is shown
        startScanning();
    }

    private void updateTime() {
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("hh:mm:ss");
        String newTime = currentTime.format(format);
        timeLabel.setText(newTime);
    }

    private void startScanning() {
        new Thread(() -> {
            while (true) {
                BufferedImage image = webcamPreviewPanel.getImage();
                if (image != null) {
                    LuminanceSource source = new BufferedImageLuminanceSource(image);
                    BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

                    Reader reader = new MultiFormatReader();

                    try {
                        Result result = reader.decode(bitmap);
                        if (result != null) {
                            String qrCodeContent = result.getText();
                            resultLabel.setText(qrCodeContent);

                            // Query database and update labels
                            try {

                                Connection conn = DriverManager.getConnection(url, user, pass);
                                String query = "SELECT * FROM students WHERE Student_ID = ?";
                                PreparedStatement st = conn.prepareStatement(query);
                                st.setString(1, qrCodeContent);
                                ResultSet rs = st.executeQuery();

                                if (rs.next()) {
                                    nameL.setText(rs.getString("Student_Name"));
                                    studentIDl.setText(String.valueOf(rs.getInt("Student_ID")));
                                    yearLevelL.setText(rs.getString("Year_Level"));
                                    sectionl.setText(rs.getString("Section"));

                                    login.getData(rs.getInt("Student_ID"), rs.getString("Student_Name"),
                                            Integer.parseInt(rs.getString("Year_Level")), rs.getString("Section"));

                                    // Display the image
                                    int studentID = rs.getInt("Student_ID");
                                    BufferedImage studentImage = login.setImage(studentID);
                                    if (studentImage != null) {
                                        Image scaledImage = studentImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                                        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
                                        imagePanel.removeAll();
                                        imagePanel.add(imageLabel);
                                        imagePanel.revalidate();
                                    }
                                    Thread.sleep(2000);

                                    nameL.setText("");
                                    studentIDl.setText("");
                                    yearLevelL.setText("");
                                    sectionl.setText("");
                                    imagePanel.removeAll();
                                    imagePanel.revalidate();
                                    imagePanel.repaint();
                                } else {
                                    
                                    Connection connPHP = DriverManager.getConnection(urlphp, userphp, passphp);
                                    String queryPHP = "SELECT * FROM visitors WHERE unique_identifiers = ?";
                                    PreparedStatement stPHP = connPHP.prepareStatement(queryPHP);
                                    stPHP.setString(1, String.valueOf(qrCodeContent));   
                                    ResultSet rsPHP = stPHP.executeQuery();

                                    if (rsPHP.next()) {
                                        nameL.setText(rsPHP.getString("visitor_name"));
                                        studentID.setText("Status:");
                                        studentIDl.setText("Visitor");
                                        yearLevel.setVisible(false);
                                        section.setVisible(false);

                                        login.insertVisitorData(rsPHP.getString("unique_identifiers"), rsPHP.getString("visitor_name"), 
                                                                rsPHP.getString("nature_of_visit"));

                                    } else{
                                        resultLabel.setText("NO DATA FOUND");
                                    }

                                    Thread.sleep(2000);

                                    nameL.setText("");
                                    studentIDl.setText("");
                                    studentID.setText("Student ID:");
                                    yearLevel.setVisible(true);
                                    section.setVisible(true);
                                    imagePanel.removeAll();
                                    imagePanel.revalidate();
                                    imagePanel.repaint();
                                }
                            } catch (SQLException | NumberFormatException | IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            resultLabel.setText("No QR Code found.");
                        }
                    } catch (NotFoundException | ChecksumException | FormatException | InterruptedException e) {
                        resultLabel.setText("No QR Code found.");
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    resultLabel.setText("No webcam image available.");
                }
            }
        }).start();
    }
}
