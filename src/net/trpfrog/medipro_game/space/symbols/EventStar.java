package net.trpfrog.medipro_game.space.symbols;

import net.trpfrog.medipro_game.SceneManager;
import net.trpfrog.medipro_game.mini_game.MiniGameScene;
import net.trpfrog.medipro_game.symbol.RelativeHitBox;

import java.awt.*;

public class EventStar extends Star {
    private RocketEvent event;

    public EventStar(Image starImage, int radius, RocketEvent event) {
        super(starImage, radius);
        this.event = event;
        setRelativeHitBox(RelativeHitBox.makeCircle(radius));
    }

    public EventStar(Image starImage, RocketEvent event) {
        this(starImage, 300, event);
    }

    public RocketEvent getEvent() {
        return event;
    }

    public static EventStar createSceneTransitionStar(int radius, Class<? extends MiniGameScene> sceneClass) {
        Image starImage = null;
        try {
            starImage = sceneClass.getConstructor().newInstance().getStarImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        RocketEvent event = rocket -> {
            try {
                var scene = sceneClass.getDeclaredConstructor().newInstance();
                SceneManager.getInstance().push(scene, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        return new EventStar(starImage, radius, event);
    }

    public interface RocketEvent {
        void run(Rocket rocket);
    }
}
