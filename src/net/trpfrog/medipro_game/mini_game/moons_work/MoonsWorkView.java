package net.trpfrog.medipro_game.mini_game.moons_work;

import net.trpfrog.medipro_game.mini_game.moons_work.symbols.ExplosionAnimation;
import net.trpfrog.medipro_game.scene.GameView;

import javax.swing.*;
import java.awt.*;

public class MoonsWorkView extends GameView {

    private MoonsWorkModel model;
    private final Timer drawTimer = new Timer(10, e -> {
        if(model.isPlaying()) repaint();
    });

    public MoonsWorkView(MoonsWorkModel model) {
        super(model);
        this.model = model;
        setBackground(new Color(0, 34, 82));

        PointerInfo pi = MouseInfo.getPointerInfo();
        Point p = pi.getLocation();
        SwingUtilities.convertPointFromScreen(p, this);
        model.getMoon().setLocation(p.x, p.y);

        // 初回の処理が重いので先に static initializer を動かしておく必要がある
        new ExplosionAnimation();
    }

    // 月の軌道を描画
    private void drawCircleOrbital(Graphics g) {
        var rect = model.getCircleDrawArea();
        g.setColor(Color.WHITE);
        g.drawOval(rect.x, rect.y, rect.width, rect.height);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // アンチエイリアス
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        model.getBackground().getDrawer().draw(g2);

        drawCircleOrbital(g2);

        // ロケットを描画
        model.getRocketManager().forEach(e -> e.getDrawer().draw(g2));

        // 隕石を描画
        model.getMeteoriteManager().forEach(e -> e.getDrawer().draw(g2));

        // シンボルリストにあるシンボルを全て描画
        model.getSymbolsList().forEach(e -> e.getDrawer().draw(g2));

        // アラートを描画
        model.getAlert().setAlertLevel(model.getEarth().dangerousLevel());
        model.getAlert().getDrawer().draw(g2);

        // 爆発アニメーションを描画
        model.getMeteoriteManager().getExplosionAnimations().forEach(e -> e.getDrawer().draw(g2));
        model.getRocketManager().getExplosionAnimations().forEach(e -> e.getDrawer().draw(g2));

        model.getEarth().getExplosionAnimation().draw(g2);
    }

    @Override
    public void suspend() {
        drawTimer.stop();
    }

    @Override
    public void resume() {
        drawTimer.start();
    }
}
