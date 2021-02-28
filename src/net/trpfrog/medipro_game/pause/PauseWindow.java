package net.trpfrog.medipro_game.pause;

import net.trpfrog.medipro_game.SceneManager;
import net.trpfrog.medipro_game.mainmenu.MainMenuScene;
import net.trpfrog.medipro_game.space.SpaceScene;
import net.trpfrog.medipro_game.util.CustomFont;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

public class PauseWindow extends JPanel {

    public PauseWindow(boolean hasBackToSpaceButton) {
        setSize(300, 200);
        setOpaque(false);
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20, 20, 20, 20));

        add(createPauseTextLabel(), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BorderLayout());

        buttonPanel.add(createResumeButton(), BorderLayout.NORTH);
        buttonPanel.add(createBackToMenuButton(), BorderLayout.CENTER);
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

    private static JLabel createPauseTextLabel() {
        JLabel label = new JLabel("Pause");
        label.setFont(new Font(CustomFont.BUNGEE_SHADE, Font.PLAIN, 50));
        label.setForeground(new Color(143, 221, 255));
        label.setHorizontalAlignment(JLabel.HORIZONTAL);
        return label;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(143, 221, 255));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

        g2.setPaint(new LinearGradientPaint(
                0, 0, 0, getHeight(),
                new float[] {0, 1},
                new Color[] {new Color(0, 15, 62), new Color(2, 31, 106)}
        ));
        int border = 5;
        g2.fillRoundRect(border, border,
                getWidth() - 2 * border, getHeight() - 2 * border,
                30 - border, 30 - border);
    }
}
