package net.trpfrog.medipro_game.mini_game;

import net.trpfrog.medipro_game.SceneManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MiniGameStartDialogPanel extends JPanel {

    private MiniGameScene scene;

    public MiniGameStartDialogPanel(MiniGameScene scene) {

        this.scene = scene;

        setSize(600, 400);
        setOpaque(false);

        setBorder(new EmptyBorder(20, 20, 20, 20));

        JButton startButton     = new JButton("Start");
        startButton.addActionListener(e -> SceneManager.getInstance().pop());

        setLayout(new BorderLayout());
        add(createTitlePanel(), BorderLayout.NORTH);
        add(createDescriptionPanel(), BorderLayout.CENTER);
        add(startButton, BorderLayout.SOUTH);
    }

    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new BorderLayout());

        JLabel  messageLabel    = new JLabel(scene.getGameTitle());
        messageLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        messageLabel.setForeground(Color.WHITE);
        titlePanel.add(messageLabel, BorderLayout.NORTH);

        titlePanel.add(Box.createRigidArea(new Dimension(10,10)), BorderLayout.CENTER);

        JLabel  creatorLabel    = new JLabel(scene.getCreatorName());
        creatorLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        creatorLabel.setHorizontalAlignment(JLabel.CENTER);
        creatorLabel.setForeground(Color.WHITE);
        titlePanel.add(creatorLabel, BorderLayout.SOUTH);

        return titlePanel;
    }

    private JPanel createDescriptionPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(Box.createRigidArea(new Dimension(10,30)));

        JLabel  descriptionLabel = new JLabel(scene.getGameDescription());
        descriptionLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD | Font.ITALIC, 18));
        descriptionLabel.setHorizontalAlignment(JLabel.CENTER);
        descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        descriptionLabel.setForeground(Color.WHITE);
        panel.add(descriptionLabel);

        panel.add(Box.createRigidArea(new Dimension(10,30)));

        JLabel  howToTitleLabel = new JLabel("ルール");
        howToTitleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        howToTitleLabel.setHorizontalAlignment(JLabel.CENTER);
        howToTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        howToTitleLabel.setForeground(Color.WHITE);
        panel.add(howToTitleLabel);

        panel.add(Box.createRigidArea(new Dimension(10,10)));

        JLabel  howToPlayLabel = new JLabel(scene.getHowToPlay());
        howToPlayLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        howToPlayLabel.setHorizontalAlignment(JLabel.CENTER);
        howToPlayLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        howToPlayLabel.setForeground(Color.WHITE);
        panel.add(howToPlayLabel);

        return panel;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(0xE6171717, true));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
    }

}
