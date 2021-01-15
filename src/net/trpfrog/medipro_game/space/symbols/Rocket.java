package net.trpfrog.medipro_game.space.symbols;

import net.trpfrog.medipro_game.Drawable;
import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.SceneManager;
import net.trpfrog.medipro_game.Suspendable;
import net.trpfrog.medipro_game.space.SpaceModel;
import net.trpfrog.medipro_game.symbol.MovableSymbol;
import net.trpfrog.medipro_game.symbol.Symbol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private EventStar touchingEventStar = null;
    private Timer astronautTimer;
    private Image rocketImage = getImagePath(Paths.get(".","resource","space_game","rocket.png"));
    private int imageWidth = 120;
    private int imageHeight = 80;

    public Rocket(SpaceModel model) {
        this.model = model;
        this.animation = new RocketAnimation(this);
        this.setDrawer(animation);
        astronautTimer = new Timer(100, e -> warpToTouchingStar());
        //animation.damaged();
    }

    private Image getImagePath(Path path) {
        return Toolkit.getDefaultToolkit().getImage(path.toString());
    }
    public int getImageWidth(){ return this.imageWidth;}
    public int getImageHeight(){ return this.imageHeight; }

    private void warpToTouchingStar() {
        // 最近触れた星から一定以上離れるまで探索を中止する
        if(touchingEventStar != null) {
            boolean touchedRecently =
                    touches(touchingEventStar) ||
                    touchingEventStar.getPoint2D().distance(getPoint2D()) < 100;
            if(touchedRecently) return;
        }

        int searchRange = 100;
        var searchRect = new Rectangle(
                (int)getX() - searchRange, (int)getY() - searchRange,
                2 * searchRange, 2 * searchRange);

        var nearbyStars = model.getRocketFloorMap().rangeSymbolStream(searchRect);

        touchingEventStar = searchEventStar(nearbyStars);
        if(touchingEventStar != null) {
            SceneManager.getInstance().push(touchingEventStar.getEvent(), true);
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

    @Override
    public void suspend() {
        astronautTimer.stop();
        stop();
    }

    @Override
    public void resume() {
        astronautTimer.start();
        start();
    }


    /**
     * ロケットのアニメーションに関するメソッドを定義します。
     */
    public class RocketAnimation implements Drawable,ActionListener {
        private final Rocket rocket;
        private Timer damageTimer;
        private int damageCounter;

        public RocketAnimation(Rocket rocket) {
            this.rocket = rocket;
            damageTimer = new Timer(10,this);
        }

        public void damaged() {
            rocketImage = getImagePath(Paths.get(".","resource","space_game","rocket_damaged.png"));
            damageTimer.start();
            damageCounter = 0;
        }

        @Override
        public void draw(Graphics2D g) {
            MainView mainView = MainView.getInstance();
            int drawX = mainView.getWidth()/2;
            int drawY = mainView.getHeight()/2;
            double angle = getAngleRadians();
            g.rotate(angle,drawX,drawY);
            g.drawImage(rocketImage,drawX-imageWidth/2,drawY-imageHeight/2,imageWidth,imageHeight,null);
            g.rotate(-angle,drawX,drawY);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            rocket.setAngleDegrees(rocket.getAngleDegrees()+8);
            damageCounter++;
            if(damageCounter >= 45*2){
                damageTimer.stop();
                rocketImage = getImagePath(Paths.get(".","resource","space_game","rocket.png"));
            }
        }
    }
}
