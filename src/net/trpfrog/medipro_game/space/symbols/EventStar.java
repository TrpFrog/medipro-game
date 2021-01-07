package net.trpfrog.medipro_game.space.symbols;

import net.trpfrog.medipro_game.scene.GameScene;

import java.awt.*;

public class EventStar extends Star {
    private GameScene eventScene;

    public EventStar(Image starImage, GameScene event) {
        super(starImage, 300);
    }

    public GameScene getEvent() {
        return eventScene;
    }
}
