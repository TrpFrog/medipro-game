package net.trpfrog.medipro_game.space.ui;

import net.trpfrog.medipro_game.Drawable;
import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.space.SpaceModel;
import net.trpfrog.medipro_game.space.symbols.Rocket;

import java.awt.*;

public class SpeedIndicatorUI implements Drawable {

    /**
     * 速度計を画面左上に描画するための定数です。
     */
    public static final int UPPER_LEFT  = 0b01;

    /**
     * 速度計を画面右上に描画するための定数です。
     */
    public static final int UPPER_RIGHT = 0b00;

    /**
     * 速度計を画面左下に描画するための定数です。
     */
    public static final int LOWER_LEFT  = 0b10;

    /**
     * 速度計を画面右下に描画するための定数です。
     */
    public static final int LOWER_RIGHT = 0b11;

    private final SpaceModel model;
    private final Rectangle drawRange;
    private final Rocket rocket;
    private int position;
    private int radius = 150;

    private final int margin = 10;

    public SpeedIndicatorUI(SpaceModel model, int position) {
        this.model = model;
        this.rocket = model.getRocket();
        this.position = position;

        drawRange = new Rectangle(margin - radius, margin - radius,
                2 * radius, 2 * radius);

        var contentPane = MainView.getInstance().getContentPane();
        if((position & 1) == 1) {
            drawRange.translate(contentPane.getWidth() - margin * 2, 0);
        }
        if((position >> 1 & 1) == 1){
            drawRange.translate(0, contentPane.getHeight() - margin * 2);
        }
    }

    private int speedToAngle() {
        double ratio = (rocket.getSpeedPxPerSecond() - rocket.getMinSpeed())
                / (rocket.getMaxSpeed() - rocket.getMinSpeed());
        return (int)(ratio * 90) + (90 * position % 4);
    }

    private void drawScale(Graphics2D g) {
        g.setColor(new Color(0x00A2BF));
        g.setStroke(new BasicStroke(2));
        drawRange.grow(-margin, -margin);
        int startAngle = 90 * position % 4;
        g.drawArc(drawRange.x, drawRange.y, drawRange.width, drawRange.height,
                startAngle, startAngle + 90);

        for(int i = startAngle; i <= startAngle + 90; i += 10) {
            int x1 = (int)drawRange.getCenterX();
            int y1 = (int)drawRange.getCenterY();
            int x2 = x1 + (int)((radius - margin) *  Math.cos(Math.toRadians(i)));
            int y2 = y1 + (int)((radius - margin) * -Math.sin(Math.toRadians(i)));

            double ratio = 10;
            x1 = (int)((x1 + ratio * x2) / (ratio + 1));
            y1 = (int)((y1 + ratio * y2) / (ratio + 1));
            g.drawLine(x1, y1, x2, y2);
        }

        drawRange.grow(margin, margin);
    }

    private void drawNeedle(Graphics2D g) {
        drawRange.grow(-margin, -margin);
        g.setColor(Color.RED);
        int x1 = (int)drawRange.getCenterX();
        int y1 = (int)drawRange.getCenterY();
        int t = speedToAngle();
        int x2 = x1 + (int)((radius - margin) *  Math.cos(Math.toRadians(t)));
        int y2 = y1 + (int)((radius - margin) * -Math.sin(Math.toRadians(t)));
        g.drawLine(x1, y1, x2, y2);
        g.setColor(Color.BLACK);

        int coverR = 20;
        g.fillOval(x1 - coverR, y1 - coverR, coverR * 2, coverR * 2);
        drawRange.grow(margin, margin);
    }

    private void drawCoordinate(Graphics2D g) {
        long x = (long) rocket.getX();
        long y = (long) rocket.getY();
        int  z = rocket.getDepth();
        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        g.setColor(Color.WHITE);
        String indicator = "<html> X = " + x + "<br>";
              indicator += "Y = " + y + "<br>";
              indicator += "Z = " + z + "</html>";
        g.drawString(indicator, (int)drawRange.getCenterX(), (int)drawRange.getCenterY());
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(new Color(0x111010));
        g.fillOval(drawRange.x, drawRange.y, drawRange.width, drawRange.height);
        drawScale(g);
//        drawCoordinate(g);
        drawNeedle(g);
    }
}
