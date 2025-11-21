package GUI;
import javax.swing.*;
import java.awt.*;

public class Items_Page extends JFrame {
    public Items_Page() {
        this.setTitle("Items");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setLayout(new BorderLayout());

        JPanel itemsHeader = new JPanel();
        itemsHeader.setBackground(Color.black);
        itemsHeader.setBounds(0, 0, 800, 600);

        JLabel itemsHL = new JLabel("Items");
        itemsHL.setFont(new Font("Arial", Font.BOLD,30));
        itemsHL.setForeground(Color.white);
        itemsHeader.add(itemsHL);

        this.add(itemsHeader, BorderLayout.NORTH);

        JPanel itemsPanel = new JPanel();
        itemsPanel.setBackground(new Color(200,200,200));
        itemsPanel.setLayout(null);

        this.add(itemsPanel, BorderLayout.CENTER);
    }
}