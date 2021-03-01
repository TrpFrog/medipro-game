package net.trpfrog.medipro_game.settings;

import net.trpfrog.medipro_game.util.CustomFont;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class SettingWindow extends JPanel {

    public SettingWindow() {
        setSize(500, 400);
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        add(createSettingsTextLabel());
        add(new VolumeSettingsPanel());
    }

    private static JLabel createSettingsTextLabel() {
        JLabel label = new JLabel("Settings");
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

        g2.setColor(new Color(0, 43, 108));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

        g2.setPaint(new LinearGradientPaint(
                0, 0, 0, getHeight(),
                new float[] {0, 1},
                new Color[] {new Color(234, 255, 255), new Color(168, 225, 255)}
        ));
        int border = 5;
        g2.fillRoundRect(border, border,
                getWidth() - 2 * border, getHeight() - 2 * border,
                30 - border, 30 - border);
    }
}
