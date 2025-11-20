package GUI;

import javax.swing.*;
import java.awt.*;

public class Records_Page extends JFrame {
    public Records_Page() {
        this.setTitle("Records");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        JLabel recordTitle = new JLabel("Records");
        recordTitle.setFont(new Font("Arial", Font.BOLD,20));
        recordTitle.setForeground(Color.white);

        this.add(recordTitle);
    }
}