package net.trpfrog.medipro_game.space.ui;

import net.trpfrog.medipro_game.Drawable;
import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.MediproGame;
import net.trpfrog.medipro_game.space.SpaceModel;
import net.trpfrog.medipro_game.space.symbols.Rocket;
import net.trpfrog.medipro_game.util.CustomFont;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class IndicatorUI implements Drawable {
    private MainView mainView;
    private SpaceModel model;
    private Rocket rocket;
    private final float alpha = 0.8f;
    private SimpleDateFormat sdf;

    private int fontSize = 30;
    private int margin = 5;
    private int barHeight = fontSize + margin * 4;
    private Font digitalNumericFont =
            new Font(CustomFont.DSEG7_CLASSIC, Font.BOLD, (int)(fontSize * 0.7));



    public IndicatorUI(SpaceModel model){
        this.model = model;
        this.rocket = model.getRocket();
        this.mainView = MainView.getInstance();
        this.sdf = new SimpleDateFormat("HH:mm");
    }

    private void drawBar(Graphics2D g) {
        var lgp = new LinearGradientPaint(
                0, 0, 0, barHeight,
                new float[] {0, 0.95f, 1},
                new Color[] {new Color(76, 76, 76), new Color(26, 26, 26), Color.BLACK}
        );
        g.setPaint(lgp);
        g.fillRect(0, 0, MainView.getInstance().getWidth(), barHeight);
    }

    private void drawValue(Graphics2D g, int x, int width, String title, String value) {
        var lgp = new LinearGradientPaint(
                0, 0, 0, barHeight,
                new float[] {0, 1},
                new Color[] {new Color(0, 0, 0), new Color(17, 17, 17)}
        );
        g.setPaint(lgp);
        g.fillRoundRect(x, margin * 3, width, fontSize, 10, 10);

        g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 2 * margin));
        int strWidth = g.getFontMetrics().stringWidth(title);
        g.setColor(Color.GRAY);
        g.drawString(title, x + (width - strWidth) / 2, (int)(margin * 2.5));

        g.setFont(digitalNumericFont);
        g.setColor(Color.GREEN);
        strWidth = g.getFontMetrics().stringWidth(value);
        g.drawString(value, x + width - strWidth - margin, 3 * margin + fontSize - 5);
    }


    private void drawValue(Graphics2D g, int x, String title, int value, int digits) {
        g.setFont(digitalNumericFont);
        String test = (int)(Math.pow(10, digits + 1) - 10) + "";
        String valueStr = value + "";
        if(digits < valueStr.length()) {
            valueStr = valueStr.substring(valueStr.length() - digits);
        }
        drawValue(g, x, g.getFontMetrics().stringWidth(test) - margin, title, valueStr);
    }

    private void drawGameTitle(Graphics2D g) {
        String title = "Space Wandering";
        g.setFont(new Font(CustomFont.BUNGEE_SHADE, Font.PLAIN, fontSize));
        int x = (MainView.getInstance().getWidth() - g.getFontMetrics().stringWidth(title)) / 2;
        int y = (barHeight + fontSize) / 2 - margin;

        g.setColor(new Color(0x75CDCDCD, true));
        g.drawString(title, x, y);
    }

    private int drawClock(Graphics2D g) {
        String time = sdf.format(Calendar.getInstance().getTime()).trim();
        g.setFont(digitalNumericFont);
        int width = g.getFontMetrics().stringWidth(time) + margin + 5;
        int x = MainView.getInstance().getWidth() - width - 20;
        drawValue(g, x, width, "CLOCK", time);
        return x;
    }

    private int drawCleared(Graphics2D g, int x) {
        String cleared = MediproGame.getPlayer().numberOfMedals() + "";
        int width = g.getFontMetrics().stringWidth(cleared) + margin + 5;
        x -= width;
        drawValue(g, x, width, "CLEARED", cleared);
        return x;
    }

    @Override
    public void draw(Graphics2D g) {
        drawBar(g);

        drawValue(g, 20, "X-AXIS", (int)(rocket.getX()), 5);
        drawValue(g, 125,"Y-AXIS", (int)(rocket.getY()), 5);
        drawValue(g, 230, "DEPTH", rocket.getDepth(), 1);

        drawGameTitle(g);
        int x = drawClock(g);
        drawCleared(g, x - 10);
    }
}
