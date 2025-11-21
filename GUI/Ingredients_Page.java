package GUI;
import javax.swing.*;
import java.awt.*;

public class Ingredients_Page extends JFrame {
    public Ingredients_Page() {
        this.setTitle("Ingredients");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setLayout(new BorderLayout());

        JPanel ingHeader = new JPanel();
        ingHeader.setBackground(Color.black);
        ingHeader.setBounds(0, 0, 2400, 100);

        JLabel ingHL = new JLabel("Ingredients");
        ingHL.setFont(new Font("Arial", Font.BOLD,30));
        ingHL.setForeground(Color.white);
        ingHL.setHorizontalAlignment(JLabel.CENTER);
        ingHeader.add(ingHL);

        this.add(ingHeader, BorderLayout.NORTH);

        JPanel ingPanel = new JPanel();
        ingPanel.setBackground(new Color(200,200,200));
        ingPanel.setLayout(null);


        this.add(ingPanel, BorderLayout.CENTER);

    }
}