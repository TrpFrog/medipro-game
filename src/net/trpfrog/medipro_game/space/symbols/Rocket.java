package net.trpfrog.medipro_game.space.symbols;

import net.trpfrog.medipro_game.Drawable;
import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.Suspendable;
import net.trpfrog.medipro_game.space.SpaceModel;
import net.trpfrog.medipro_game.symbol.MovableSymbol;
import net.trpfrog.medipro_game.symbol.Symbol;
import net.trpfrog.medipro_game.util.MusicPlayer;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.stream.Stream;

/**
 * 操作キャラクターであるロケットの情報を保持するクラス。
 * @author つまみ
 */
public class Rocket extends MovableSymbol implements Suspendable{

    private int depth;
    private RocketAnimation animation;
    private SpaceModel model;
    private Timer astronautTimer;
    private Image rocketImage = getImagePath(Paths.get(".","resource","space_game","rocket.png"));
    private int imageWidth = 120;
    private int imageHeight = 80;
    private long invincibleTimeUntil = 0;

    public Rocket(SpaceModel model) {
        this.model = model;
        this.animation = new RocketAnimation(this);
        this.setDrawer(animation);
        astronautTimer = new Timer(100, e -> warpToTouchingStar());
        this.setMaxSpeed(500.0);
        this.setMinSpeed(0.0);
    }

    private Image getImagePath(Path path) {
        return Toolkit.getDefaultToolkit().getImage(path.toString());
    }
    public int getImageWidth(){ return this.imageWidth;}
    public int getImageHeight(){ return this.imageHeight; }

    private void warpToTouchingStar() {

        if(System.currentTimeMillis() < invincibleTimeUntil) {
            return;
        }

        int searchRange = 100;
        var searchRect = new Rectangle(
                (int)getX() - searchRange, (int)getY() - searchRange,
                2 * searchRange, 2 * searchRange);

        var nearbyStars = model.getRocketFloorMap().rangeSymbolStream(searchRect);

        var touchingEventStar = searchEventStar(nearbyStars);
        if(touchingEventStar != null) {
            touchingEventStar.getEvent().run(this, touchingEventStar);
        }
    }

    // 最も近い触れている星を返す
    private EventStar searchEventStar(Stream<Symbol> starStream) {
        var eventStar = starStream
                .filter(e -> e instanceof EventStar)
                .filter(this::touches)
                .min(Comparator.comparingDouble(e -> getPoint2D().distance(e.getPoint2D())));

        if(eventStar.isEmpty()) {
            return null;
        } else {
            return (EventStar) eventStar.get();
        }
    }

    /**
     * ロケットが存在するマップ上の深さを返します。
     * @return ロケットのマップ上の深さ
     */
    public int getDepth() {
        return depth;
    }

    /**
     * ロケットが存在するマップ上の深さを変更します。
     * @param depth ロケットのマップ上の深さ
     */
    public void setDepth(int depth) {
        this.depth = depth;
    }

    public RocketAnimation getAnimation(){ return animation;}

    @Override
    public void suspend() {
        astronautTimer.stop();
        MusicPlayer.ROCKET_SE.stop();
        MusicPlayer.ROCKET_SE.setFramePosition(0);
        stop();
    }

    @Override
    public void resume() {
        invincibleTimeUntil = System.currentTimeMillis() + 5000;
        astronautTimer.start();
        MusicPlayer.ROCKET_SE.loop(Clip.LOOP_CONTINUOUSLY);
        start();
    }

    public boolean isInvincible() {
        return System.currentTimeMillis() < invincibleTimeUntil;
    }

    /**
     * ロケットのアニメーションに関するメソッドを定義します。
     */
    public class RocketAnimation implements Drawable{
        private final Rocket rocket;
        private Timer damageTimer;
        private int damageCounter;
        private Image rocketNow;
        private Image rocketImage = getImagePath(Paths.get(".","resource","space_game","rocket.png"));
        private Image invincibleImage = getImagePath(Paths.get(".","resource","space_game","invincibleRocket.png"));
        private Image damagedImage = getImagePath(Paths.get(".","resource","space_game","rocket_damaged.png"));
        private Image rocketStopImage = getImagePath(Paths.get(".","resource","space_game","rocket_stop.png"));
        private Image invincibleStopImage = getImagePath(Paths.get(".","resource","space_game","invincibleRocketStop.png"));

        public RocketAnimation(Rocket rocket) {
            this.rocket = rocket;

            damageTimer = new Timer(10,e->{
                rocket.setAngleDegrees(rocket.getAngleDegrees()+8);
                damageCounter++;
                if(damageCounter >= 16){
                    damageTimer.stop();
                    damageCounter = 0;
                }
            });
        }

        public void damaged() {
            rocketNow = damagedImage;
            damageTimer.start();
            damageCounter = 0;
        }

        @Override
        public void draw(Graphics2D g) {
            MainView mainView = MainView.getInstance();
            int drawX = mainView.getWidth()/2;
            int drawY = mainView.getHeight()/2;
            double angle = getAngleRadians();
            // ロケットの画像選択
            if(rocket.getSpeedPxPerSecond() < 1 && rocket.isInvincible()){
                rocketNow = invincibleStopImage;
            }else if(rocket.getSpeedPxPerSecond() >= 1 && rocket.isInvincible()){
                rocketNow = invincibleImage;
            }else if(rocket.getSpeedPxPerSecond() < 1){
                rocketNow = rocketStopImage;
            }else{
                rocketNow = rocketImage;
            }
            if(damageCounter != 0){
                rocketNow = damagedImage;
            }

            g.rotate(angle,drawX,drawY);
            g.drawImage(rocketNow,drawX-imageWidth/2,drawY-imageHeight/2,imageWidth,imageHeight,null);
            g.rotate(-angle,drawX,drawY);
        }
    }
}
