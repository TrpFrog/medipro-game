package net.trpfrog.medipro_game.space;


import net.trpfrog.medipro_game.scene.GameController;
import net.trpfrog.medipro_game.space.symbols.Rocket;
import net.trpfrog.medipro_game.pause.EscapeToPause;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SpaceController extends GameController implements KeyListener {
    private SpaceModel model;
    private SpaceView view;
    private boolean isUpperAngle;
    private Rocket rocket;

    private void rotateTimerFunc(boolean isLeft){
        double dAngleDegrees = 5.0; // とりあえず
        if(!this.isUpperAngle) dAngleDegrees *= -1;
        if(isLeft) dAngleDegrees *= -1;
        rocket.turnAnticlockwiseDegrees(dAngleDegrees);
    }
    private Timer rotateTimerL = new Timer(20, e -> rotateTimerFunc(true));
    private Timer rotateTimerR = new Timer(20, e -> rotateTimerFunc(false));

    public SpaceController(SpaceModel model, SpaceView view) {
        super(model, view);
        this.model = model;
        this.isUpperAngle = true;

        rocket = model.getRocket();
        rocket.start(); // とりあえずC側でスタート

        this.view = view;
        this.view.addKeyListener(this);
        this.view.addKeyListener(new EscapeToPause(false));
    }

    @Override
    public void suspend() {
        rotateTimerL.stop();
        rotateTimerR.stop();
    }

    @Override
    public void resume() {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        double currentSpeedPxPerSecond = rocket.getSpeedPxPerSecond();
        double dSpeedPxPerSecond = 20.0; // とりあえず
        double dAngleDegrees = 5.0; // とりあえず
        if(!isUpperAngle) dAngleDegrees *= -1;

        switch (e.getKeyChar()){
            // W: 加速
            case 'w' -> {
                if(500.0 <= currentSpeedPxPerSecond) return; // 上限速度の場合に離脱
                currentSpeedPxPerSecond = Math.min(currentSpeedPxPerSecond + dSpeedPxPerSecond, 500.0);
                rocket.setSpeedPxPerSecond(currentSpeedPxPerSecond);
            }
            // A: 左旋回
            case 'a' -> rotateTimerL.start();
            // S: 減速
            case 's' -> {
                if(currentSpeedPxPerSecond <= 0) return; // 下限速度の場合に離脱
                currentSpeedPxPerSecond = Math.max(currentSpeedPxPerSecond - dSpeedPxPerSecond, 0);
                rocket.setSpeedPxPerSecond(currentSpeedPxPerSecond);
            }
            // D: 右旋回
            case 'd' -> rotateTimerR.start();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        double currentAngleDegrees = rocket.getAngleDegrees();
        // 旋回方向の決定に少し猶予を持たせる
        if(30<=currentAngleDegrees && currentAngleDegrees<=150){
            this.isUpperAngle = false;
        }else if(210<=currentAngleDegrees && currentAngleDegrees<=330){
            this.isUpperAngle = true;
        }

        rotateTimerL.stop();
        rotateTimerR.stop();
    }
}
