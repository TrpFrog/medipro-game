package net.trpfrog.medipro_game.space;


import net.trpfrog.medipro_game.scene.GameController;
import net.trpfrog.medipro_game.space.symbols.Rocket;
import net.trpfrog.medipro_game.pause.EscapeToPause;

import javax.swing.*;
import java.awt.event.*;

public class SpaceController extends GameController implements KeyListener, MouseMotionListener, MouseListener, MouseWheelListener {
    private SpaceModel model;
    private SpaceView view;
    private Rocket rocket;
    private Timer rotateTimerL, rotateTimerR, accelerateTimer, decelerateTimer, faceToGradientTimer;
    private int fps, spf;
    private boolean isUpperAngle;

    private void rotateTimerFunc(boolean isLeft){
        double dAngleDegrees = 5.0; // とりあえず
        if(!isUpperAngle) dAngleDegrees *= -1;
        if(isLeft) dAngleDegrees *= -1;
        rocket.turnAnticlockwiseDegrees(dAngleDegrees);
    }
    private void faceToGradient(MouseEvent e){
        int mouseX = e.getX();
        int mouseY = e.getY();
        double dx = mouseX - view.getWidth()/2;
        double dy = mouseY - view.getHeight()/2;

        double toOtherAngleDegrees = Math.toDegrees(Math.atan2(dy, dx));
        double currentAngleDegrees = rocket.getAngleDegrees();
        double rocketToOtherAngleDegrees = ((toOtherAngleDegrees - currentAngleDegrees) % 360 + 360) % 360;

        double dAngleDegrees = 5.0;
        double fastDAngleDegrees = 20.0;
        double slowDAngleDegrees = 1.0;
        if(Math.abs(rocketToOtherAngleDegrees) < fastDAngleDegrees) dAngleDegrees = slowDAngleDegrees;
        if(Math.abs(rocketToOtherAngleDegrees) < slowDAngleDegrees) return;

        if(rocketToOtherAngleDegrees < 180) dAngleDegrees *= -1;
        rocket.turnClockwiseDegrees(dAngleDegrees);
    }
    private void moveDepth(int dz){
        int currentDepth = rocket.getDepth();
        int mapDepth = model.get3DMap().getDepth();
        currentDepth += dz;
        currentDepth = (currentDepth % mapDepth + mapDepth) % mapDepth;
        rocket.setDepth(currentDepth);
    }

    public SpaceController(SpaceModel model, SpaceView view) {
        super(model, view);
        this.model = model;
        isUpperAngle = true;
        fps = 50;
        spf = 1000 / fps;

        rotateTimerL = new Timer(spf, e -> rotateTimerFunc(true));
        rotateTimerR = new Timer(spf, e -> rotateTimerFunc(false));
        accelerateTimer = new Timer(spf, e -> rocket.accelerate(50.0));
        decelerateTimer = new Timer(spf, e -> rocket.accelerate(-25.0));

        rocket = model.getRocket();
        rocket.start(); // とりあえずC側でスタート

        this.view = view;
        this.view.addKeyListener(this);
        this.view.addMouseListener(this);
        this.view.addMouseMotionListener(this);
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
        switch (e.getKeyChar()){
            // W: 加速
            case 'w' -> accelerateTimer.start();
            // A: 左旋回
            case 'a' -> rotateTimerL.start();
            // D: 右旋回
            case 'd' -> rotateTimerR.start();
            // Z: 上昇
            case 'z' -> moveDepth(1);
            // X: 下降
            case 'x' -> moveDepth(-1);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        double currentAngleDegrees = rocket.getAngleDegrees();
        double graceDegrees = 30.0;

        // 旋回方向の決定に少し猶予を持たせる
        if(graceDegrees<=currentAngleDegrees && currentAngleDegrees<=180-graceDegrees){
            this.isUpperAngle = false;
        }else if(180+graceDegrees<=currentAngleDegrees && currentAngleDegrees<=360-graceDegrees){
            this.isUpperAngle = true;
        }

        accelerateTimer.stop();
        rotateTimerL.stop();
        rotateTimerR.stop();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        faceToGradientTimer = new Timer(spf, o -> faceToGradient(e));
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
        int dz = e.getWheelRotation();
        moveDepth(dz);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        faceToGradientTimer.stop();
        faceToGradientTimer = new Timer(spf, o -> faceToGradient(e));
        faceToGradientTimer.start();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
