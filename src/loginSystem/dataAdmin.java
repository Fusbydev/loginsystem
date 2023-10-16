package loginSystem;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.*;

public class dataAdmin extends JFrame implements ActionListener{

    static JTable infoTable;
    JScrollPane scrollPane;
    static String user = "root";
    static String pass = "password";
    static String url = "jdbc:mysql://localhost:3306/loginSystem";
    JButton deleteData;
    JPanel container;
    JButton printCSV, searcButton;
    JLabel date1, searchLabel, yearl, secl, printl;
    JTextField searchField;
    JRadioButton sec;
    static JRadioButton year;
    ButtonGroup group, group2;
    static JCheckBox student, visitor;

    public dataAdmin() {
        
        DefaultTableModel model = new DefaultTableModel();
        infoTable = new JTable(model);
        infoTable.setBackground(Color.lightGray);
        infoTable.setGridColor(Color.gray);
        infoTable.setSelectionBackground(Color.white);
        infoTable.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.black));
        // Customize table cell renderer for center-aligned text
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        infoTable.setDefaultRenderer(Object.class, cellRenderer);

        // Customize table header cell renderer for centered and bold text
        JTableHeader header = infoTable.getTableHeader();
        header.setFont(header.getFont().deriveFont(Font.BOLD));
        header.setForeground(Color.white);
        header.setBackground(Color.darkGray);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.black));
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) infoTable.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        scrollPane = new JScrollPane(infoTable);
        scrollPane.setBounds(220, 60, 790, 250);
        scrollPane.setBackground(Color.white);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.black, 2, true));
        scrollPane.setVisible(true);
        
        deleteData = new JButton("DELETE DATA");
        deleteData.setBounds(450, 350, 150, 20);
        deleteData.addActionListener(this);
        deleteData.setBackground(Color.decode("#00FF7F"));
        deleteData.setBorder(BorderFactory.createLineBorder(Color.darkGray, 2, false));
        deleteData.setFocusable(false);
        deleteData.setVisible(true);

        printCSV = new JButton("PRINT DATA");
        printCSV.setBounds(640, 350, 150, 20);
        printCSV.addActionListener(this);
        printCSV.setVisible(true);
        printCSV.setMnemonic(KeyEvent.VK_P);
        printCSV.setBackground(Color.decode("#00FF7F"));
        printCSV.setFocusable(false);
        printCSV.setBorder(BorderFactory.createLineBorder(Color.darkGray, 2, false));
        
        sec = new JRadioButton("SEARCH SECTION");
        year = new JRadioButton("SEARCH YEAR LEVEL");
    
        sec.setBounds(45, 150, 150, 20);
        sec.addActionListener(this);
        sec.setMnemonic(KeyEvent.VK_C);

        year.setBounds(45, 120, 150, 20);
        year.addActionListener(this);
        year.setMnemonic(KeyEvent.VK_E);
       
        sec.setFont(new Font("Arial", Font.BOLD, 12));
        year.setFont(new Font("Arial", Font.BOLD, 12));
        year.setForeground(Color.black);
        sec.setForeground(Color.black);
        
        sec.setBackground(null);
        year.setBackground(null);

        group = new ButtonGroup();
        group.add(sec);
        group.add(year);
       
        student = new JCheckBox("SHOW ALL STUDENTS");
        student.setBounds(430, 10, 180, 50);
        student.setBackground(null);
        student.setFocusable(false);
        student.addActionListener(this);
        student.setMnemonic(KeyEvent.VK_S);
        student.setBorder(new LineBorder(Color.black, 2));
        student.setFont(new Font("Arial", Font.BOLD, 14));

        visitor = new JCheckBox("SHOW ALL VISITORS");
        visitor.setBounds(610, 10, 180, 50);
        visitor.setBackground(null);
        visitor.setFocusable(false);
        visitor.addActionListener(this);
        visitor.setMnemonic(KeyEvent.VK_V);
        visitor.setBorder(new LineBorder(Color.black, 2));
        visitor.setFont(new Font("Arial", Font.BOLD, 14));

        group2 = new ButtonGroup();
        group2.add(student);
        group2.add(visitor);

        searchField = new JTextField();
        searchField.setBounds(60, 210, 100, 20);
        searchLabel = new JLabel("SEARCH/PRINT");
        searchLabel.setBounds(66, 180, 200, 20);
        searchField.setEditable(false);
        searchField.setBackground(Color.lightGray);
        searchField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        
        searcButton = new JButton("SEARCH");
        searcButton.setBounds(60, 250, 100, 20);
        searcButton.addActionListener(this);
        searcButton.setBackground(Color.decode("#00FF7F"));
        searcButton.setBorder(BorderFactory.createLineBorder(Color.darkGray, 2, false));
        searcButton.setVisible(true);
        searcButton.setEnabled(false);

        searcButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        deleteData.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        printCSV.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        LocalDate date = LocalDate.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String title = date.format(format);
        
        date1 = new JLabel(title);
        date1.setBounds(220, 10, 300, 50);
        date1.setFont(new Font("sans", Font.PLAIN,20));
        date1.setForeground(Color.black);

        deleteData.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Set hover effect when mouse enters the button
                deleteData.setForeground(Color.white);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Reset to default when mouse exits the button
                deleteData.setForeground(Color.darkGray);
            }
        });

        printCSV.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Set hover effect when mouse enters the button
                printCSV.setForeground(Color.white);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Reset to default when mouse exits the button
                printCSV.setForeground(Color.darkGray);
            }
        });

        searcButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Set hover effect when mouse enters the button
                searcButton.setForeground(Color.white);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Reset to default when mouse exits the button
                searcButton.setForeground(Color.darkGray);
            }
        });

        this.add(date1);
        this.add(student);
        this.add(visitor);
        this.add(searchLabel);
        this.add(year);
        this.add(sec);
        this.add(searchField);
        this.add(printCSV);
        this.add(searcButton);
        this.add(deleteData);
        this.add(scrollPane);
        this.setLayout(null);
        this.setSize(1100, 450);
        this.setTitle("Data Admin");
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.getContentPane().setBackground(Color.lightGray);
        this.setResizable(false);

        inputToAdmin();
    }


    static ResultSet resultSet;
    static Connection conn;
    static PreparedStatement st;
    static String title;
    static String query = null;
    String filePath = null;
    LocalDate date = LocalDate.now();
    DateTimeFormatter format = DateTimeFormatter.ofPattern("YYYY-MM-dd");

    static void inputIntoAdminForVisitor() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, pass);

            String getVisitor = "SELECT * FROM visitors";
            st = conn.prepareStatement(getVisitor);

            ResultSet rs = st.executeQuery();

            DefaultTableModel model = new DefaultTableModel();
            infoTable.setModel(model);

            ResultSetMetaData rsmd = rs.getMetaData();
            int cols = rsmd.getColumnCount();
            String[] colName = new String[cols];

            for (int i = 0; i < cols; i++) {
                colName[i] = rsmd.getColumnName(i + 1);
            }

            model.setColumnIdentifiers(colName);

            while (rs.next()) {
                String unique = rs.getString(1);
                String name = rs.getString(2);
                String status = rs.getString(3);
                String reason = rs.getString(4);
                String login = rs.getString(5);
                String logout = rs.getString(6);

                String[] row = {unique, name, status, reason, login, logout};
                model.addRow(row);
            }
            st.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    void searchedInput() throws SQLException {
        String query = null;
            if (sec.isSelected()) {
                query = "SELECT * FROM lstatus WHERE section = ?";
            }
            else if (year.isSelected()) {
                query = "SELECT * FROM lstatus WHERE Year_Level = ?";
            }

            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, searchField.getText());
            ResultSet rs = st.executeQuery();
            DefaultTableModel model = new DefaultTableModel();
            infoTable.setModel(model);

            ResultSetMetaData rsmd = rs.getMetaData();
            int cols = rsmd.getColumnCount();
            String[] colName = new String[cols];

            for (int i = 0; i < cols; i++) {
                colName[i] = rsmd.getColumnName(i + 1);
            }

            model.setColumnIdentifiers(colName);

            while (rs.next()) {
                String studentId = rs.getString(1);
                String name = rs.getString(2);
                String year = rs.getString(3);
                String section = rs.getString(4);
                String logindate = rs.getString(5);
                String login = rs.getString(6);
                String logout = rs.getString(7);

                String[] row = {studentId, name, year, section, logindate, login, logout};
                model.addRow(row);
            }

            st.close();
            conn.close();
    }

    void delete(){
        try {
            if(student.isSelected()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DriverManager.getConnection(url, user, pass);
                String query = "DELETE FROM lstatus";
                PreparedStatement st = conn.prepareStatement(query);
                st.executeUpdate();
                inputToAdmin();
            } else if (visitor.isSelected()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DriverManager.getConnection(url, user, pass);
                String query = "DELETE FROM visitors";
                PreparedStatement st = conn.prepareStatement(query);
                st.executeUpdate();
                inputToAdmin();
            }

        } catch (ClassNotFoundException | SQLException e1) {
            e1.printStackTrace();
        }
        searchField.setText("");
    }

    void printCSV() throws IOException, SQLException {
        title = date.format(format);
            if (student.isSelected()) {
                filePath = "C:/Users/kirkf/OneDrive/Documents/loginsystem/" + title + ".csv";
                query = "SELECT * FROM lstatus";
                st = conn.prepareStatement(query);
                printCSVE();
                JOptionPane.showMessageDialog(null, "print successfuly for the date " + title);
            }else if (year.isSelected()) {
                filePath = "C:/Users/kirkf/OneDrive/Documents/loginsystem/" + title +"-"+searchField.getText()+ "-year.csv";
                query = "SELECT * FROM lstatus WHERE Year_Level = ?";
                st = conn.prepareStatement(query);
                st.setInt(1, Integer.parseInt(searchField.getText()));
                printCSVE();
                JOptionPane.showMessageDialog(null, "print successfuly for the date " + title + " year "+searchField.getText());
            }else if (sec.isSelected()) {
                filePath = "C:/Users/kirkf/OneDrive/Documents/loginsystem/" + title +"-section-"+searchField.getText()+ ".csv";
                query = "SELECT * FROM lstatus WHERE Section = ?";
                st = conn.prepareStatement(query);
                st.setString(1, (searchField.getText()));
                printCSVE();
                JOptionPane.showMessageDialog(null, "print successfuly for the date " + title + " section "+ searchField.getText());
            }else if (visitor.isSelected()) {
                filePath = "C:/Users/kirkf/OneDrive/Documents/loginsystem/" + title +"-visitors.csv";
                query = "SELECT * FROM visitors";
                st = conn.prepareStatement(query);
                printCSVE();
                JOptionPane.showMessageDialog(null, "print successfuly for the date " + title + " for visitors ");
            }        
    }

    void printCSVE() throws SQLException, IOException {
        resultSet = st.executeQuery();
        FileWriter writer = new FileWriter(filePath);
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        
        for (int i = 1; i <= columnCount; i++) {
            String columnName = metaData.getColumnName(i);
            writer.write(columnName);
            if (i < columnCount) {
                writer.write(",");
            }
        }
        writer.write("\n");
    
        while (resultSet.next()) {
            for (int i = 1; i <= columnCount; i++) {
                String columnValue = resultSet.getString(i);
                writer.write(columnValue);
                if (i < columnCount) {
                    writer.write(",");
                }
            }
            writer.write("\n");
        }
    
        writer.close();
        resultSet.close();
        st.close();
        conn.close();
        searchField.setText("");
    }

    static void inputToAdmin() {
        try {
            // Register the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Open a connection
            conn = DriverManager.getConnection(url, user, pass);

            String query = "SELECT * FROM lstatus";
            st = conn.prepareStatement(query);
            ResultSet rs = st.executeQuery();

            DefaultTableModel model = new DefaultTableModel();
            infoTable.setModel(model);

            ResultSetMetaData rsmd = rs.getMetaData();
            int cols = rsmd.getColumnCount();
            String[] colName = new String[cols];

            for (int i = 0; i < cols; i++) {
                colName[i] = rsmd.getColumnName(i + 1);
            }

            model.setColumnIdentifiers(colName);

            while (rs.next()) {
                String studentId = rs.getString(1);
                String name = rs.getString(2);
                String year = rs.getString(3);
                String section = rs.getString(4);
                String logindate = rs.getString(5);
                String login = rs.getString(6);
                String logout = rs.getString(7);

                String[] row = {studentId, name, year, section, logindate, login, logout};
                model.addRow(row);
            }

            st.close();
            conn.close();

        } catch (SQLException | ClassNotFoundException a) {
            a.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == visitor) {
            inputIntoAdminForVisitor();
            group.clearSelection();
            printCSV.setText("PRINT VISITORS");
            searchField.setEditable(false);
            searchLabel.setBounds(66, 180, 200, 20);
            searchLabel.setText("SEARCH/PRINT");
        }
        if (e.getSource() == student) {
            inputToAdmin();
            group.clearSelection();
            printCSV.setText("PRINT STUDENTS");
            searchField.setEditable(false);
            searchLabel.setBounds(66, 180, 200, 20);
            searchLabel.setText("SEARCH/PRINT");
        }
        if (e.getSource() == year || e.getSource() == sec) {
            searchLabel.setText("SEARCH/PRINT YEAR LEVEL");
            searchLabel.setBounds(60, 180, 200, 20);
            searchField.setEditable(true);
            searcButton.setEnabled(true);
            group2.clearSelection();
        }
        if (e.getSource() == sec) {
            searchLabel.setText("SEARCH/PRINT SECTION");
        }
        if (e.getSource() == deleteData) {
            delete();
        }
        if (e.getSource() == printCSV) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    conn = DriverManager.getConnection(url, user, pass);
                    printCSV();

                } catch (ClassNotFoundException | SQLException | IOException e1) {
                    JOptionPane.showMessageDialog(null, "Select a data to print", "error", getDefaultCloseOperation(), null);
                }
            
        }

        if (e.getSource() == searcButton) {
                try {
                    // Register the JDBC driver
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    // Open a connection
                    conn = DriverManager.getConnection(url, user, pass);
                    searchedInput(); 
                } catch (SQLException | ClassNotFoundException a) {
                    JOptionPane.showMessageDialog(null, "error input");
                }
                group.clearSelection();
                searchField.setEditable(false);
                searcButton.setEnabled(false);
    }
    }
}
