package net.trpfrog.medipro_game.space.symbols;

import net.trpfrog.medipro_game.SceneManager;
import net.trpfrog.medipro_game.mini_game.MiniGameScene;
import net.trpfrog.medipro_game.symbol.RelativeHitBox;

import java.awt.*;

public class MiniGameStar extends EventStar {

    public MiniGameStar(int radius, Class<? extends MiniGameScene> sceneClass) {
        Image starImage;
        try {
            starImage = sceneClass.getConstructor().newInstance().getStarImage();
        } catch (Exception e) {
            e.printStackTrace();
            starImage = MiniGameScene.DEFAULT_STAR_IMAGE;
        }
        setImage(starImage, radius * 2);
        RocketEvent event = (rocket, star) -> {
            try {
                var scene = sceneClass.getDeclaredConstructor().newInstance();
                SceneManager.getInstance().push(scene, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        setEvent(event);
        setRelativeHitBox(RelativeHitBox.makeCircle(radius));
    }

}
