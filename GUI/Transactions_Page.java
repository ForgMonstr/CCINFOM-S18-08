package GUI;

import javax.swing.*;
import java.awt.*;

public class Transactions_Page extends JFrame {
    public Transactions_Page() {
        this.setTitle("Transactions");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        JLabel transactionTitle = new JLabel("Transactions");
        transactionTitle.setFont(new Font("Arial", Font.BOLD,20));
        transactionTitle.setForeground(Color.white);

        this.add(transactionTitle);
    }
}
