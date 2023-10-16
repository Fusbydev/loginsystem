package loginSystem;

import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.sound.sampled.AudioInputStream;

public class login {
    static String user = "root";
    static String pass = "password";
    static String url = "jdbc:mysql://localhost:3306/loginSystem";
    static Connection connection;

    public static void getData(int studentId, String name, int yearLevel, String section) {
        try {
            connection = DriverManager.getConnection(url, user, pass);
            String check = "SELECT * FROM lstatus WHERE Student_ID = ?";
            PreparedStatement st1 = connection.prepareStatement(check);
            st1.setInt(1, studentId);
            ResultSet rs = st1.executeQuery();

            if (rs.next()) {
                String logout = rs.getString("Logout_Time");

                if (logout == null) {
                    String update = "UPDATE lstatus SET Logout_Time = ? WHERE Student_ID = ?";
                    PreparedStatement upd = connection.prepareStatement(update);
                    upd.setString(1, formattedTime);
                    upd.setInt(2, studentId);
                    upd.executeUpdate();

                    try {
                        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/logout.wav"));
                        Clip clip = AudioSystem.getClip();
                        clip.open(audioInputStream);
                        clip.start();
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                        e.printStackTrace();
                    }
                } else {
                    String delete = "DELETE FROM lstatus WHERE Student_ID = ?";
                    PreparedStatement st4 = connection.prepareStatement(delete);
                    st4.setInt(1, studentId);
                    st4.executeUpdate();

                    insertData(studentId, name, yearLevel,section);
                }
            } else {
                insertData(studentId, name, yearLevel,section);
            }
            dataAdmin.inputToAdmin();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    static LocalTime currentTime = LocalTime.now();
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
    static String formattedTime = currentTime.format(formatter);
    private static AudioInputStream audioInputStream;

    static void insertData(int studentId, String name, int yearLevel, String section) {
        String query = "INSERT INTO lstatus(Student_ID, Student_Name, Year_Level, Section, Login_Date, Login_Time, Logout_Time) "
                            + "VALUES (?, ?, ?, ?, CURRENT_DATE(), ?, ?)";
                try (PreparedStatement st = connection.prepareStatement(query)) {
                    st.setInt(1, studentId);
                    st.setString(2, name);
                    st.setInt(3, yearLevel);
                    st.setString(4, section);
                    st.setString(5, formattedTime);
                    st.setString(6, null);
                    st.executeUpdate();
                

                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/notif.wav"));
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.start();
                    
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | SQLException e) {
                    e.printStackTrace();
                }
    }
    
    public static BufferedImage setImage(int studentIDforImage) throws IOException {
        BufferedImage image = ImageIO.read(new File("src/" + studentIDforImage + ".jpg"));
        return image;
    }

    static void insertVisitorData(String uniqueIdentifiers, String visitorName, String reason) {
        Connection connectionPHP;

        try {
            connectionPHP = DriverManager.getConnection(url, user, pass);
            String check = "SELECT * FROM visitors WHERE unique_identifiers = ?";
            PreparedStatement stPHP = connectionPHP.prepareStatement(check);
            stPHP.setString(1, uniqueIdentifiers);
            ResultSet rsPHP = stPHP.executeQuery();

            if (rsPHP.next()) {
                String logout = rsPHP.getString("logout_time");

                if (logout == null) {
                    String update = "UPDATE visitors SET logout_time = ? WHERE unique_identifiers = ?";
                    PreparedStatement upd = connectionPHP.prepareStatement(update);
                    upd.setString(1, formattedTime);
                    upd.setString(2, uniqueIdentifiers);
                    upd.executeUpdate();

                    try {
                        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/logout.wav"));
                        Clip clip = AudioSystem.getClip();
                        clip.open(audioInputStream);
                        clip.start();
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                        e.printStackTrace();
                    }
                } else {
                    String delete = "DELETE FROM visitors WHERE unique_identifiers = ?";
                    stPHP = connectionPHP.prepareStatement(delete);
                    stPHP.setString(1, uniqueIdentifiers);
                    stPHP.executeUpdate();

                    //insert into database
                    insertDataIntoVisitor(uniqueIdentifiers, visitorName, reason);
                }
            } else {
                //insert into database
                insertDataIntoVisitor(uniqueIdentifiers, visitorName, reason);
            }
        dataAdmin.inputIntoAdminForVisitor();
           
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        
    }

    static void insertDataIntoVisitor(String uniqueIdentifiers, String visitorName, String reason) {
                String status = "visitor";
                try {
                connection = DriverManager.getConnection(url, user, pass);
                String query = "INSERT INTO visitors(unique_identifiers, visitor_name, status, nature_of_visit, login_time, logout_time)"
                            + "VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement st = connection.prepareStatement(query);
                
                    st.setString(1, uniqueIdentifiers);
                    st.setString(2, visitorName);
                    st.setString(3, status);
                    st.setString(4, reason);
                    st.setString(5, formattedTime);
                    st.setString(6, null);
                    st.executeUpdate();
                
                    audioInputStream = AudioSystem.getAudioInputStream(new File("src/notif.wav"));
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.start();
                    
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | SQLException e) {
                    e.printStackTrace();
                }
    }
}
