package net.trpfrog.medipro_game.space;


import net.trpfrog.medipro_game.scene.GameController;
import net.trpfrog.medipro_game.space.symbols.Rocket;
import net.trpfrog.medipro_game.pause.EscapeToPause;

import javax.swing.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class SpaceController extends GameController implements KeyListener {
    private SpaceModel model;
    private SpaceView view;
    private Rocket rocket;
    private Map<Integer, Boolean> keyStateMap;
    private Timer stepTimer;
    private int spf;
    private MouseState mouseState;

    private void step(){
        // W: 加速, S: 減速, クリック: 加減速
        double acceleration = 2.0; // 250f(5s)で最高速度到達
        if(keyStateMap.getOrDefault(KeyEvent.VK_W, false) || keyStateMap.getOrDefault(KeyEvent.VK_S, false)){
            int accelerationVec = 0;
            if(keyStateMap.getOrDefault(KeyEvent.VK_W, false)) accelerationVec += 1;
            if(keyStateMap.getOrDefault(KeyEvent.VK_S, false)) accelerationVec -= 1;
            rocket.accelerate(acceleration * (double) accelerationVec);
        }else if(mouseState.isClicked()){
            rocket.accelerate(acceleration * mouseEventToScale());
        }

        // A: 左旋回, D: 右旋回, 長押し: 向かって旋回
        double dAngleDegrees = 3.6; // 100f(2s)で1周
        if(keyStateMap.getOrDefault(KeyEvent.VK_A, false) || keyStateMap.getOrDefault(KeyEvent.VK_D, false)){
            int rotateVec = 0;
            if(keyStateMap.getOrDefault(KeyEvent.VK_A, false)) rotateVec -= 1;
            if(keyStateMap.getOrDefault(KeyEvent.VK_D, false)) rotateVec += 1;
            rocket.turnAnticlockwiseDegrees(dAngleDegrees * (double) rotateVec);
        }else if(mouseState.isClicked()){
            double mouseX = mouseState.getPointerX();
            double mouseY = mouseState.getPointerY();
            double dx = mouseX - view.getWidth()/2;
            double dy = mouseY - view.getHeight()/2;

            double toOtherAngleDegrees = Math.toDegrees(Math.atan2(dy, dx));
            double currentAngleDegrees = rocket.getAngleDegrees();
            double rocketToOtherAngleDegrees = ((toOtherAngleDegrees - currentAngleDegrees) % 360 + 360) % 360;

            double fastDAngleDegrees = 14.4; // 4フレーム分
            double slowDAngleDegrees = 1.0;
            if(Math.abs(rocketToOtherAngleDegrees) < fastDAngleDegrees) dAngleDegrees = slowDAngleDegrees;
            if(Math.abs(rocketToOtherAngleDegrees) < slowDAngleDegrees) dAngleDegrees = 0.0;

            int vec = 1;
            if(rocketToOtherAngleDegrees < 180) vec *= -1;
            rocket.turnClockwiseDegrees(dAngleDegrees * vec);
        }

        // Z: 上昇, X: 下降, ホイール(ピンチイン/アウト): 上下移動
        int dz = 1;
        int depthVec = 0;
        if(keyStateMap.getOrDefault(KeyEvent.VK_Z, false) || keyStateMap.getOrDefault(KeyEvent.VK_X, false)){
            if(keyStateMap.getOrDefault(KeyEvent.VK_Z, false)){
                depthVec += 1;
                keyStateMap.put(KeyEvent.VK_Z, false);
            }
            if(keyStateMap.getOrDefault(KeyEvent.VK_X, false)){
                depthVec -= 1;
                keyStateMap.put(KeyEvent.VK_X, false);
            }
        }else if(mouseState.isWheeled()){
            depthVec = mouseState.getWheelVec();
            mouseState.offWheel();
        }
        int currentDepth = rocket.getDepth();
        int mapDepthLength = model.get3DMap().getDepth();
        currentDepth += dz * depthVec;
        currentDepth = (currentDepth % mapDepthLength + mapDepthLength) % mapDepthLength;
        rocket.setDepth(currentDepth);
    }
    private double mouseEventToScale(){
        double distance = mouseState.getPointerDistance();
        int halfHeight = view.getHeight()/2;
        double scale = distance / halfHeight;
        return (scale - 0.5) * 2;
    }

    public SpaceController(SpaceModel model, SpaceView view) {
        super(model, view);
        this.model = model;

        spf = 1000 / 50;
        mouseState = new MouseState(view);
        keyStateMap = new HashMap<Integer, Boolean>();
        stepTimer = new Timer(spf, e -> step());
        stepTimer.start();

        rocket = model.getRocket();

        this.view = view;
        this.view.addKeyListener(this);
        this.view.addKeyListener(new EscapeToPause(false));
    }

    @Override
    public void suspend() {
        keyStateMap.clear();
        mouseState.clear();
        stepTimer.stop();
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
}
