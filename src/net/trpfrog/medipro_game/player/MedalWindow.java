package net.trpfrog.medipro_game.player;

import net.trpfrog.medipro_game.MediproGame;
import net.trpfrog.medipro_game.SceneManager;
import net.trpfrog.medipro_game.Suspendable;
import net.trpfrog.medipro_game.dialog_background.DialogBackgroundScene;
import net.trpfrog.medipro_game.util.MusicPlayer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MedalWindow extends JPanel implements Suspendable {

    private Medal medal;
    private Timer timer;

    public static boolean pushMedalWindow(Medal medal) {
        if(MediproGame.getPlayer().hasMedal(medal)) {
            return false;
        } else {
            var scene = new DialogBackgroundScene(new MedalWindow(medal), true);
            SceneManager.getInstance().push(scene);
            return true;
        }
    }

    public MedalWindow(Medal medal) {
        this.medal = medal;
        timer = new Timer(100, e -> repaint());

        setSize(300, 300);
        setOpaque(false);

        setBorder(new EmptyBorder(20, 20, 20, 20));

        JButton okButton     = new JButton("I got it");
        okButton.addActionListener(e -> SceneManager.getInstance().pop());

        setLayout(new BorderLayout());
        add(createTitleLabel(), BorderLayout.NORTH);
        add(createMedalNameLabel(), BorderLayout.SOUTH);
    }

    private JLabel createTitleLabel() {
        JLabel messageLabel = new JLabel("You got a medal!");
        messageLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        messageLabel.setForeground(Color.WHITE);
        return messageLabel;
    }

    private JLabel createMedalNameLabel() {
        JLabel medalNameLabel = new JLabel(medal.getTitle());
        medalNameLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD | Font.ITALIC, 18));
        medalNameLabel.setHorizontalAlignment(JLabel.CENTER);
        medalNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        medalNameLabel.setForeground(Color.WHITE);
        return medalNameLabel;
    }

    private void drawRotationLight(Graphics2D g2, int medalR) {
        int r = medalR + 30;
        RadialGradientPaint rgp = new RadialGradientPaint(
                (int)(getWidth()/2.0), (int)(getHeight()/2.0), r,
                new float[] {0.0f, 1.0f},
                new Color[] {Color.WHITE, new Color(0x2FFFFFF, true)}
        );

        var defaultPaint = g2.getPaint();
        g2.setPaint(rgp);

        int degreePerSec = 15;
        int angle = (int) ((System.currentTimeMillis() / 1000.0 * degreePerSec) % 360);
        int numberOfRadical = 7;
        for(int i = 0; i < numberOfRadical; i++) {
            g2.fillArc((int)(getWidth()/2.0) - r, (int)(getHeight()/2.0) - r,
                    2 * r, 2 * r,
                    360/numberOfRadical * i + angle, 360 / (numberOfRadical * 2));
        }
        g2.setPaint(defaultPaint);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(0x88AA8F00, true));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 100, 100);

        int r = 100;

        drawRotationLight(g2, r);

        g2.drawImage(medal.getMedalImage(), getWidth()/2 - r, getHeight()/2 - r,
                2 * r, 2 * r, null);
    }

    @Override
    public void suspend() {
        timer.stop();
    }

    @Override
    public void resume() {
        timer.start();
        MusicPlayer.ACHIEVEMENT_SE.start();
        MediproGame.getPlayer().registerMedal(medal);
    }
}
