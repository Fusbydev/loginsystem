package loginSystem;

import javax.swing.JFrame;
import javax.swing.*;

public class register {
    JFrame frame = new JFrame();
    JTextField name, studentID, yearLevel;
    JLabel nameL, studentIDl, yearLevelL;

    public register() {

        nameL = new JLabel("Name: ");
        nameL.setBounds(150, 0, 50, 100);
        nameL.setVisible(true);

        studentIDl = new JLabel("Student ID: ");
        studentIDl.setBounds(150, 60, 100, 100);
        studentIDl.setVisible(true);
        
        yearLevelL = new JLabel("Year Level: ");
        yearLevelL.setBounds(150, 120, 100, 100);
        yearLevelL.setVisible(true);

        frame.add(nameL);
        frame.add(studentIDl);
        frame.add(yearLevelL);
        frame.setSize(500, 400);
        frame.setLayout(null);
        frame.setTitle("Student Registration");
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
