package net.trpfrog.medipro_game.pause;

import net.trpfrog.medipro_game.SceneManager;
import net.trpfrog.medipro_game.mainmenu.MainMenuScene;
import net.trpfrog.medipro_game.space.SpaceScene;

import javax.swing.*;
import java.awt.*;

public class PauseWindow extends JPanel {
    public PauseWindow(boolean hasBackToSpaceButton) {
        setSize(300, 200);
        setOpaque(false);
        setLayout(new BorderLayout());

        add(createResumeButton(), BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BorderLayout());

        buttonPanel.add(createBackToMenuButton(), BorderLayout.NORTH);
        if(hasBackToSpaceButton) {
            buttonPanel.add(createBackToSpaceButton(), BorderLayout.SOUTH);
        }
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private static JButton createBackToMenuButton() {
        JButton button = new JButton("Back to Main Menu");
        button.addActionListener(e -> {
            SceneManager sm = SceneManager.getInstance();
            while(!(sm.top() instanceof MainMenuScene)) sm.pop();
        });
        return button;
    }

    private static JButton createBackToSpaceButton() {
        JButton button = new JButton("Back to Space");
        button.addActionListener(e -> {
            SceneManager sm = SceneManager.getInstance();
            while(!(sm.top() instanceof SpaceScene)) sm.pop();
        });
        return button;
    }

    private static JButton createResumeButton() {
        JButton button = new JButton("Resume");
        button.addActionListener(e -> SceneManager.getInstance().pop());
        return button;
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
