package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.LayoutManager;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class Frame extends JFrame {
    Frame() {
        this.setTitle("Infom");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(3);
        this.setResizable(true);
        ImageIcon icon2 = new ImageIcon("UGANDAN.jpg");
        this.setIconImage(icon2.getImage());


        JPanel header = new JPanel();
        header.setLayout((LayoutManager)null);
        header.setBounds(0, 0, 800, 60);
        header.setBackground(Color.black);
        JLabel headerLabel = new JLabel("Cloud Kitchen Inventory - Forecasting & Demand");
        headerLabel.setFont(new Font("Arial", 1, 20));
        headerLabel.setHorizontalAlignment(0);
        headerLabel.setVerticalAlignment(1);
        headerLabel.setForeground(Color.white);
        headerLabel.setOpaque(false);
        headerLabel.setBounds(0, 10, 800, 60);
        header.add(headerLabel);


        this.add(header);
        JPanel ingredients = new JPanel();
        JButton showIng = new JButton("Show Ingredients");
        showIng.setFont(new Font("Arial", 1, 12));
        showIng.setForeground(new Color(38, 38, 38));
        showIng.setBackground(Color.black);
        showIng.setOpaque(false);
        showIng.setPreferredSize(new Dimension(150, 20));
        showIng.setHorizontalAlignment(0);
        ingredients.setLayout(new BorderLayout());
        ingredients.setBounds(0, 60, 800, 80);
        ingredients.setBackground(Color.LIGHT_GRAY);


        JPanel ingWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        ingWrapper.setOpaque(false);
        ingWrapper.add(showIng);
        ingredients.add(ingWrapper, BorderLayout.CENTER);
        this.add(ingredients);


        JPanel items = new JPanel();
        JButton showItems = new JButton("Show Items");
        showItems.setFont(new Font("Arial", 1, 12));
        showItems.setForeground(new Color(200, 200, 200));
        showItems.setBackground(Color.black);
        showItems.setOpaque(false);
        showItems.setPreferredSize(new Dimension(150, 20));
        showItems.setHorizontalAlignment(0);
        items.setLayout(new BorderLayout());
        items.setBounds(0, 140, 800, 80);
        items.setBackground(new Color(38, 38, 38));


        JPanel itemsWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        itemsWrapper.setOpaque(false);
        itemsWrapper.add(showItems);
        items.add(itemsWrapper, BorderLayout.CENTER);
        this.add(items);


        JPanel history = new JPanel();
        JButton showHistory = new JButton("Show History");
        showHistory.setFont(new Font("Arial", 1, 12));
        showHistory.setForeground(new Color(38, 38, 38));
        showHistory.setBackground(Color.black);
        showHistory.setOpaque(false);
        showHistory.setPreferredSize(new Dimension(150, 20));
        showHistory.setHorizontalAlignment(0);
        history.setLayout(new BorderLayout());
        history.setBounds(0, 220, 800, 80);
        history.setBackground(Color.LIGHT_GRAY);


        JPanel histWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        histWrapper.setOpaque(false);
        histWrapper.add(showHistory);
        history.add(histWrapper, BorderLayout.CENTER);
        this.add(history);


        JPanel branch = new JPanel();
        JButton showBranch = new JButton("Show Branch");
        showBranch.setFont(new Font("Arial", 1, 12));
        showBranch.setForeground(new Color(200, 200, 200));
        showBranch.setBackground(Color.black);
        showBranch.setOpaque(false);
        showBranch.setPreferredSize(new Dimension(150, 20));
        showBranch.setHorizontalAlignment(0);
        branch.setLayout(new BorderLayout());
        branch.setBackground(new Color(38, 38, 38));
        branch.setBounds(0, 300, 800, 80);


        JPanel branchWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        branchWrapper.setOpaque(false);
        branchWrapper.add(showBranch);
        branch.add(branchWrapper, BorderLayout.CENTER);
        this.add(branch);


        JPanel records = new JPanel();
        JButton showRecords = new JButton("Show Records");
        showRecords.setFont(new Font("Arial", 1, 12));
        showRecords.setForeground(new Color(38, 38, 38));
        showRecords.setBackground(Color.black);
        showRecords.setOpaque(false);
        showRecords.setPreferredSize(new Dimension(150, 20));
        showRecords.setHorizontalAlignment(0);
        records.setLayout(new BorderLayout());
        records.setBackground(Color.LIGHT_GRAY);
        records.setBounds(0, 380, 800, 80);


        JPanel recordsWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        recordsWrapper.setOpaque(false);
        recordsWrapper.add(showRecords);
        records.add(recordsWrapper, BorderLayout.CENTER);
        this.add(records);


        JPanel end = new JPanel();
        JButton showEnd = new JButton("Exit");
        showEnd.setFont(new Font("Arial", 1, 12));
        showEnd.setForeground(new Color(200, 200, 200));
        showEnd.setBackground(Color.black);
        showEnd.setOpaque(false);
        showEnd.setPreferredSize(new Dimension(150, 20));
        showEnd.setHorizontalAlignment(0);
        end.setLayout(new BorderLayout());
        end.setBackground(new Color(38, 38, 38));
        end.setBounds(0, 490, 800, 80);

        JPanel endWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        endWrapper.setOpaque(false);
        endWrapper.add(showEnd);
        end.add(endWrapper, BorderLayout.SOUTH);
        this.add(end);
    }
}