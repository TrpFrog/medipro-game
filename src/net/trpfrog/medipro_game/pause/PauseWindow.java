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

        JButton backToMainMenuButton = new JButton("Back to Main Menu");
        JButton resumeButton         = new JButton("Resume");
        JButton spaceButton          = new JButton("Back to Space");

        backToMainMenuButton.addActionListener(e -> {
            SceneManager sm = SceneManager.getInstance();
            while(!(sm.top() instanceof MainMenuScene)) sm.pop();
        });

        spaceButton.addActionListener(e -> {
            SceneManager sm = SceneManager.getInstance();
            while(!(sm.top() instanceof SpaceScene)) sm.pop();
        });

        resumeButton.addActionListener(e -> SceneManager.getInstance().pop());

        setLayout(new BorderLayout());
        add(resumeButton, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.add(backToMainMenuButton, BorderLayout.NORTH);
        if(hasBackToSpaceButton) buttonPanel.add(spaceButton, BorderLayout.SOUTH);
        buttonPanel.setOpaque(false);
        add(buttonPanel, BorderLayout.SOUTH);
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
