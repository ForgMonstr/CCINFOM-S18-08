package GUI;

import javax.swing.*;
import java.awt.*;

public class History_Page extends JFrame {
    public History_Page() {
        this.setTitle("History");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setLayout(new BorderLayout());

        JPanel historyHeader = new JPanel();
        historyHeader.setBackground(Color.black);
        historyHeader.setBounds(0, 0, 800, 600);

        JLabel historyHL = new JLabel("History");
        historyHL.setFont(new Font("Arial", Font.BOLD,30));
        historyHL.setForeground(Color.white);
        historyHeader.add(historyHL);

        this.add(historyHeader,  BorderLayout.NORTH);

        JPanel historyPanel = new JPanel();
        historyPanel.setBackground(new Color(200,200,200));
        historyPanel.setLayout(null);

        this.add(historyPanel, BorderLayout.CENTER);

    }
}
