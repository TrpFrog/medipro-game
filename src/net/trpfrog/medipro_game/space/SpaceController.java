package net.trpfrog.medipro_game.space;


import net.trpfrog.medipro_game.scene.GameController;
import net.trpfrog.medipro_game.space.symbols.Rocket;
import net.trpfrog.medipro_game.pause.EscapeToPause;

import javax.swing.*;
import java.awt.event.*;

public class SpaceController extends GameController implements KeyListener, MouseListener, MouseWheelListener {
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
    private Timer accelerateTimer = new Timer(20, e -> rocket.accelerate(50.0));
    private Timer decelerateTimer = new Timer(20, e -> rocket.accelerate(-25.0));

    private void faceToGradient(MouseEvent e){
        int mouseX = e.getX();
        int mouseY = e.getY();
        double rocketX = rocket.getX();
        double rocketY = rocket.getY();
        double mapX = rocketX - view.getWidth()/2 + mouseX;
        double mapY = rocketY - view.getHeight()/2 + mouseY;
        double dx = mapX - rocketX;
        double dy = mapY - rocketY;

        double toOtherAngleDegrees = Math.toDegrees(Math.atan2(dy, dx));
        double currentAngleDegrees = rocket.getAngleDegrees();
        double rocketToOtherAngleDegrees = (toOtherAngleDegrees - currentAngleDegrees + 720) % 360;

        double dAngleDegrees = 5.0;
        if(Math.abs(rocketToOtherAngleDegrees) < 20.0) dAngleDegrees = 1.0;
        if(Math.abs(rocketToOtherAngleDegrees) < 1.0) return;

        if(rocketToOtherAngleDegrees < 180) dAngleDegrees *= -1;
        rocket.turnClockwiseDegrees(dAngleDegrees);
    }
    private Timer faceToGradientTimer;

    public SpaceController(SpaceModel model, SpaceView view) {
        super(model, view);
        this.model = model;
        this.isUpperAngle = true;

        rocket = model.getRocket();
        rocket.start(); // とりあえずC側でスタート

        this.view = view;
        this.view.addKeyListener(this);
        this.view.addMouseListener(this);
        this.view.addMouseWheelListener(this);
        this.view.addKeyListener(new EscapeToPause(false));
        decelerateTimer.start();
    }

    @Override
    public void suspend() {
        rotateTimerL.stop();
        rotateTimerR.stop();
        decelerateTimer.stop();
    }

    @Override
    public void resume() {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        double dSpeedPxPerSecond = 50.0; // とりあえず

        switch (e.getKeyChar()){
            // W: 加速
            case 'w' -> rocket.accelerate(dSpeedPxPerSecond);
            // A: 左旋回
            case 'a' -> rotateTimerL.start();
            // D: 右旋回
            case 'd' -> rotateTimerR.start();
            // Z: 上昇
            case 'z' -> {
                int currentDepth = rocket.getDepth();
                int mapDepth = model.get3DMap().getDepth();
                currentDepth += 1;
                currentDepth = (currentDepth + mapDepth) % mapDepth;
                rocket.setDepth(currentDepth);
            }
            // X: 下降
            case 'x' -> {
                int currentDepth = rocket.getDepth();
                int mapDepth = model.get3DMap().getDepth();
                currentDepth -= 1;
                currentDepth = (currentDepth + mapDepth) % mapDepth;
                rocket.setDepth(currentDepth);
            }
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

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        faceToGradientTimer = new Timer(20, o -> faceToGradient(e));
        faceToGradientTimer.start();
        accelerateTimer.start();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        faceToGradientTimer.stop();
        accelerateTimer.stop();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int direction = e.getWheelRotation();
        int currentDepth = rocket.getDepth();
        int mapDepth = model.get3DMap().getDepth();
        currentDepth += direction;
        currentDepth = (currentDepth + mapDepth) % mapDepth;
        rocket.setDepth(currentDepth);
    }
}
