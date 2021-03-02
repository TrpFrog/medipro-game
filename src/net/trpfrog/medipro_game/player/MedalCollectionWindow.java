package net.trpfrog.medipro_game.player;

import net.trpfrog.medipro_game.MediproGame;
import net.trpfrog.medipro_game.SceneManager;
import net.trpfrog.medipro_game.util.CustomFont;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class MedalCollectionWindow extends JPanel {

    private final SimpleDateFormat dateFormatter;

    public MedalCollectionWindow() {
        setSize(500, 400);
        setOpaque(false);
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20, 20, 20, 20));

        dateFormatter = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

        add(createTitleTextLabel(), BorderLayout.NORTH);

        if(MediproGame.getPlayer().numberOfMedals() > 0) {

            JPanel medalListPanel = new JPanel();
            medalListPanel.setOpaque(false);
            for(Medal medal : MediproGame.getPlayer().createMedalList()) {
                medalListPanel.add(createMedalLine(medal));
            }
            medalListPanel.setLayout(new GridLayout(medalListPanel.getComponentCount(), 1));
            add(medalListPanel, BorderLayout.CENTER);

        } else {

            JLabel label = new JLabel("You have no medals yet.");
            label.setForeground(Color.GRAY);
            label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
            label.setHorizontalAlignment(JLabel.CENTER);
            add(label, BorderLayout.CENTER);

        }

        add(createCloseButton(), BorderLayout.SOUTH);
    }

    private static JLabel createTitleTextLabel() {
        JLabel label = new JLabel("Medal Collections");
        label.setFont(new Font(CustomFont.BUNGEE_SHADE, Font.PLAIN, 30));
        label.setForeground(new Color(0, 53, 80));
        label.setHorizontalAlignment(JLabel.LEFT);
        return label;
    }

    private static JButton createCloseButton() {
        var btn = new JButton("Close");
        btn.addActionListener(e -> SceneManager.getInstance().pop());
        return btn;
    }

    private String formatDate(Date date) {
        String ret = dateFormatter.format(date);
        ret = ret.replaceAll("[ ]", "<br>");
        return "<html>" + ret + "</html>";
    }

    private JPanel createMedalLine(Medal medal) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BorderLayout());

        int size = 20;

        JLabel medalImage = new JLabel(
                new ImageIcon(medal.getMedalImage().getScaledInstance(size*2, size*2, 0))
        );
        medalImage.setHorizontalAlignment(JLabel.LEFT);

        JLabel medalTitle = new JLabel(medal.getTitle());
        medalTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, size));
        medalTitle.setHorizontalAlignment(JLabel.CENTER);

        JLabel medalDate  = new JLabel(formatDate(medal.getAcquisitionDate()));
        medalDate.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, size*2/3));
        medalDate.setHorizontalAlignment(JLabel.RIGHT);

        panel.add(medalImage, BorderLayout.WEST);
        panel.add(medalTitle, BorderLayout.CENTER);
        panel.add(medalDate,  BorderLayout.EAST);
        return panel;
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
