package net.trpfrog.medipro_game.space;


import net.trpfrog.medipro_game.scene.GameController;
import net.trpfrog.medipro_game.space.symbols.Rocket;
import net.trpfrog.medipro_game.pause.EscapeToPause;

import javax.swing.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class SpaceController extends GameController implements KeyListener, MouseMotionListener, MouseListener, MouseWheelListener {
    private SpaceModel model;
    private SpaceView view;
    private Rocket rocket;
    private Map<Integer, Boolean> keyMap;
    private Timer keyTimer;
    private Timer accelerateTimer, faceToGradientTimer;
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
    private void step(){
        rocket.accelerate(-25.0);

        // W: 加速
        if(keyMap.getOrDefault(KeyEvent.VK_W, false)) rocket.accelerate(50.0);
        // A: 左旋回
        if(keyMap.getOrDefault(KeyEvent.VK_A, false)) rotateTimerFunc(true);
        // D: 右旋回
        if(keyMap.getOrDefault(KeyEvent.VK_D, false)) rotateTimerFunc(false);
        // Z: 上昇
        if(keyMap.getOrDefault(KeyEvent.VK_Z, false)){
            moveDepth(1);
            keyMap.put(KeyEvent.VK_Z, false);
        };
        // X: 下降
        if(keyMap.getOrDefault(KeyEvent.VK_X, false)){
            moveDepth(-1);
            keyMap.put(KeyEvent.VK_X, false);
        };
    }

    public SpaceController(SpaceModel model, SpaceView view) {
        super(model, view);
        this.model = model;
        isUpperAngle = true;
        fps = 50;
        spf = 1000 / fps;

        accelerateTimer = new Timer(spf, e -> rocket.accelerate(50.0));

        keyMap = new HashMap<Integer, Boolean>();
        keyTimer = new Timer(spf, e -> step());
        keyTimer.start();

        rocket = model.getRocket();
        rocket.start(); // とりあえずC側でスタート

        this.view = view;
        this.view.addKeyListener(this);
        this.view.addMouseListener(this);
        this.view.addMouseMotionListener(this);
        this.view.addMouseWheelListener(this);
        this.view.addKeyListener(new EscapeToPause(false));
    }

    @Override
    public void suspend() {
        keyTimer.stop();
    }

    @Override
    public void resume() {
        keyTimer.start();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        keyMap.put(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyMap.put(e.getKeyCode(), false);
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
