package net.trpfrog.medipro_game.space.symbols;

import net.trpfrog.medipro_game.scene.GameScene;
import net.trpfrog.medipro_game.symbol.RelativeHitBox;

import java.awt.*;

public class EventStar extends Star {
    private GameScene eventScene;

    public EventStar(Image starImage, int radius, GameScene event) {
        super(starImage, radius);
        this.eventScene = event;
        setRelativeHitBox(RelativeHitBox.makeCircle(radius));
    }

    public EventStar(Image starImage, GameScene event) {
        this(starImage, 300, event);
    }

    public GameScene getEvent() {
        return eventScene;
    }
}
