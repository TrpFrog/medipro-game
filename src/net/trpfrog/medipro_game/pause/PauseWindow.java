package net.trpfrog.medipro_game.pause;

import net.trpfrog.medipro_game.SceneManager;

import javax.swing.*;
import java.awt.*;

public class PauseWindow extends JPanel {
    public PauseWindow() {
        setSize(300, 200);
        setOpaque(false);

        JButton backToMainMenuButton = new JButton("Back to Main Menu");
        JButton resumeButton         = new JButton("Resume");

        backToMainMenuButton.addActionListener(e -> {
            SceneManager sm = SceneManager.getInstance();
            int size = sm.size();
            for (int i = 0; i < size - 1; i++) {
                sm.pop();
            }
        });
        resumeButton.addActionListener(e -> SceneManager.getInstance().pop());

        setLayout(new BorderLayout());
        add(backToMainMenuButton, BorderLayout.NORTH);
        add(resumeButton, BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.CYAN);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
    }
}
