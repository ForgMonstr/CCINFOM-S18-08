package GUI;
import javax.swing.*;
import java.awt.*;

public class Branch_Page extends JFrame {
    public Branch_Page() {
        this.setTitle("Branch");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setLayout(new BorderLayout());

        JPanel branchHeader = new JPanel();
        branchHeader.setBackground(Color.black);
        branchHeader.setBounds(0, 0, 800, 600);

        JLabel branchHL = new JLabel("Branch");
        branchHL.setFont(new Font("Arial", Font.BOLD,30));
        branchHL.setForeground(Color.white);
        branchHeader.add(branchHL);

        this.add(branchHeader, BorderLayout.NORTH);

        JPanel branchPanel = new JPanel();
        branchPanel.setBackground(new Color(200,200,200));
        branchPanel.setLayout(null);

        this.add(branchPanel, BorderLayout.CENTER);
    }
}