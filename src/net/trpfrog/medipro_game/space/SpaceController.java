package net.trpfrog.medipro_game.space;


import net.trpfrog.medipro_game.scene.GameController;
import net.trpfrog.medipro_game.space.symbols.Rocket;
import net.trpfrog.medipro_game.pause.EscapeToPause;

import javax.swing.*;
import java.awt.event.*;

public class SpaceController extends GameController {
    private SpaceModel model;
    private SpaceView view;
    private Rocket rocket;
    private Timer stepTimer;
    private int spf;
    private MouseState mouseState;
    private KeyState keyState;

    // ===(一連の流れ)===
    // キー入力があるか判定
    // ある => キー操作して終わり
    // ない => マウス入力があるか判定
    //        ある => マウス操作して終わり
    //        ない => 終わり

    private void step(){
        // W: 加速, S: 減速, クリック: 加減速
        double acceleration = 2.0; // 250f(5s)で最高速度到達
        boolean isPressedW = keyState.isPressed(KeyEvent.VK_W);
        boolean isPressedS = keyState.isPressed(KeyEvent.VK_S);
        if(isPressedW || isPressedS){
            int accelerationVec = 0;
            if(isPressedW) accelerationVec += 1;
            if(isPressedS) accelerationVec -= 1;
            rocket.accelerate(acceleration * (double) accelerationVec);
        }else if(mouseState.isClicked()){
            rocket.accelerate(acceleration * mouseEventToScale());
        }

        // A: 左旋回, D: 右旋回, 長押し: 向かって旋回
        double dAngleDegrees = 3.6; // 100f(2s)で1周
        boolean isPressedA = keyState.isPressed(KeyEvent.VK_A);
        boolean isPressedD = keyState.isPressed(KeyEvent.VK_D);
        if(isPressedA || isPressedD){
            int rotateVec = 0;
            if(isPressedA) rotateVec -= 1;
            if(isPressedD) rotateVec += 1;
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
        boolean isPressedZ = keyState.isPressed(KeyEvent.VK_Z);
        boolean isPressedX = keyState.isPressed(KeyEvent.VK_X);
        if(isPressedZ || isPressedX){
            if(isPressedZ){
                depthVec += 1;
                keyState.remove(KeyEvent.VK_Z);
            }
            if(isPressedX){
                depthVec -= 1;
                keyState.remove(KeyEvent.VK_X);
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
        keyState = new KeyState(view);
        stepTimer = new Timer(spf, e -> step());
        stepTimer.start();

        rocket = model.getRocket();

        this.view = view;
        this.view.addKeyListener(new EscapeToPause(false));
    }

    @Override
    public void suspend() {
        keyState.clear();
        mouseState.clear();
        stepTimer.stop();
    }

    @Override
    public void resume() {
        stepTimer.start();
    }
}
