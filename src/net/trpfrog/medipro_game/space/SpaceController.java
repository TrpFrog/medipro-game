package net.trpfrog.medipro_game.space;


import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.scene.GameController;
import net.trpfrog.medipro_game.space.symbols.Rocket;
import net.trpfrog.medipro_game.pause.EscapeToPause;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;

public class SpaceController extends GameController implements KeyListener, MouseMotionListener, MouseListener, MouseWheelListener {
    private SpaceModel model;
    private SpaceView view;
    private Rocket rocket;
    private Map<Integer, Boolean> keyStateMap;
    private Timer stepTimer;
    private Timer accelerateByMouseTimer, faceToGradientTimer;
    private double acceleration;
    private int spf;
    private boolean isUpperAngle;
    private Point pointerLocation;

    private void rotateTimerFunc(boolean isLeft){
        double dAngleDegrees = 5.0; // とりあえず
        if(!isUpperAngle) dAngleDegrees *= -1;
        if(isLeft) dAngleDegrees *= -1;
        rocket.turnAnticlockwiseDegrees(dAngleDegrees);
    }
    private void faceToGradient(){
        double mouseX = pointerLocation.getX();
        double mouseY = pointerLocation.getY();
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
        if(keyStateMap.getOrDefault(KeyEvent.VK_W, false)) rocket.accelerate(50.0);
        // A: 左旋回
        if(keyStateMap.getOrDefault(KeyEvent.VK_A, false)) rotateTimerFunc(true);
        // D: 右旋回
        if(keyStateMap.getOrDefault(KeyEvent.VK_D, false)) rotateTimerFunc(false);
        // Z: 上昇
        if(keyStateMap.getOrDefault(KeyEvent.VK_Z, false)){
            moveDepth(1);
            keyStateMap.put(KeyEvent.VK_Z, false);
        };
        // X: 下降
        if(keyStateMap.getOrDefault(KeyEvent.VK_X, false)){
            moveDepth(-1);
            keyStateMap.put(KeyEvent.VK_X, false);
        };
    }
    private double mouseEventToScale(MouseEvent e){
        int mouseX = e.getX();
        int mouseY = e.getY();
        int halfHeight = view.getHeight()/2;
        double distance = Point2D.distance(
                mouseX, mouseY,
                view.getWidth()/2, halfHeight
        );

        double scale = distance / halfHeight;
        return scale;
    }

    public SpaceController(SpaceModel model, SpaceView view) {
        super(model, view);
        this.model = model;
        isUpperAngle = true;
        spf = 1000 / 50;

        acceleration = 0.0;
        accelerateByMouseTimer = new Timer(spf, e -> rocket.accelerate(25.0 + acceleration));
        faceToGradientTimer = new Timer(spf, o -> faceToGradient());

        keyStateMap = new HashMap<Integer, Boolean>();
        stepTimer = new Timer(spf, e -> step());
        stepTimer.start();

        rocket = model.getRocket();

        this.view = view;
        this.view.addKeyListener(this);
        this.view.addMouseListener(this);
        this.view.addMouseMotionListener(this);
        this.view.addMouseWheelListener(this);
        this.view.addKeyListener(new EscapeToPause(false));
    }

    @Override
    public void suspend() {
        keyStateMap.clear();
        stepTimer.stop();
        accelerateByMouseTimer.stop();
        faceToGradientTimer.stop();
    }

    @Override
    public void resume() {
        stepTimer.start();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        keyStateMap.put(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyStateMap.put(e.getKeyCode(), false);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        acceleration = 25 * mouseEventToScale(e);

        PointerInfo pi = MouseInfo.getPointerInfo();
        pointerLocation = pi.getLocation();
        SwingUtilities.convertPointFromScreen(pointerLocation, MainView.getInstance().getContentPane());

        faceToGradientTimer.start();
        accelerateByMouseTimer.start();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        faceToGradientTimer.stop();
        accelerateByMouseTimer.stop();
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
        acceleration = 25 * mouseEventToScale(e);

        PointerInfo pi = MouseInfo.getPointerInfo();
        pointerLocation = pi.getLocation();
        SwingUtilities.convertPointFromScreen(pointerLocation, MainView.getInstance().getContentPane());
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
