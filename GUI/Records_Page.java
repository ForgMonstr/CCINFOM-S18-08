package GUI;

import javax.swing.*;
import java.awt.*;

public class Records_Page extends JFrame {
    public Records_Page() {
        this.setTitle("Records");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setLayout(new BorderLayout());

        JPanel recordsHeader = new JPanel();
        recordsHeader.setBackground(Color.black);
        recordsHeader.setBounds(0, 0, 800, 600);

        JLabel recordsHL = new JLabel("Records");
        recordsHL.setFont(new Font("Arial", Font.BOLD,30));
        recordsHL.setForeground(Color.white);
        recordsHeader.add(recordsHL);

        this.add(recordsHeader,  BorderLayout.NORTH);

        JPanel recordsPanel = new JPanel();
        recordsPanel.setBackground(new Color(200,200,200));
        recordsPanel.setLayout(null);
        this.add(recordsPanel, BorderLayout.CENTER);
    }
}