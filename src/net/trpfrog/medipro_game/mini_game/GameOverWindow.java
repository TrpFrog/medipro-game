package net.trpfrog.medipro_game.mini_game;

import net.trpfrog.medipro_game.SceneManager;
import net.trpfrog.medipro_game.mainmenu.MainMenuScene;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class GameOverWindow extends JPanel {

    private Color backgroundColor;

    public GameOverWindow(String title, int score, Color backgroundColor) {

        this.backgroundColor = backgroundColor;

        setSize(300, 200);
        setOpaque(false);

        setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel  messageLabel         = new JLabel(title);
        JLabel  countLabel           = new JLabel(score + "");
        JButton backToMainMenuButton = new JButton("Back to Main Menu");

        countLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 100));
        countLabel.setHorizontalAlignment(JLabel.CENTER);
        countLabel.setForeground(Color.WHITE);

        messageLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        messageLabel.setForeground(Color.WHITE);

        backToMainMenuButton.addActionListener(e -> {
            SceneManager sm = SceneManager.getInstance();
            while(!(sm.top() instanceof MainMenuScene)) sm.pop();
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
        g2.setColor(backgroundColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
    }
}
