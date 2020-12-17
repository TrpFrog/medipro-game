package net.trpfrog.medipro_game.mainmenu;

import net.trpfrog.medipro_game.scene.GameScene;

public class MainMenuScene extends GameScene {
    private static final MainMenuScene instance = new MainMenuScene();

    public static MainMenuScene getInstance() {
        return instance;
    }

    private MainMenuScene() {
        setModel(new MainMenuModel());
        setView(new MainMenuView(getModel()));
        setController(new MainMenuController(getModel(), getView()));
    }
}
