package net.trpfrog.medipro_game.mini_game.moons_work;

import net.trpfrog.medipro_game.SceneManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CompleteWindow extends JPanel {

    private MoonsWorkModel model;

    public CompleteWindow(MoonsWorkModel model) {
        this.model = model;

        setSize(300, 200);
        setOpaque(false);

        setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel  messageLabel         = new JLabel("GAME OVER");
        JLabel  countLabel           = new JLabel(model.getCounter().getValue() + "");
        JButton backToMainMenuButton = new JButton("Back to Main Menu");

        countLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 100));
        countLabel.setHorizontalAlignment(JLabel.CENTER);
        countLabel.setForeground(Color.WHITE);

        messageLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        messageLabel.setForeground(Color.WHITE);

        backToMainMenuButton.addActionListener(e -> {
            SceneManager sm = SceneManager.getInstance();
            int size = sm.size();
            for (int i = 0; i < size - 1; i++) {
                sm.pop();
            }
        });

        setLayout(new BorderLayout());
        add(messageLabel, BorderLayout.NORTH);
        add(countLabel, BorderLayout.CENTER);
        add(backToMainMenuButton, BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(0xB5762200, true));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
    }
}
