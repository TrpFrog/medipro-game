package net.trpfrog.medipro_game.mini_game.race_game;

import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.SceneManager;
import net.trpfrog.medipro_game.mainmenu.MainMenuScene;
import net.trpfrog.medipro_game.mini_game.moons_work.MoonsWorkController;
import net.trpfrog.medipro_game.mini_game.moons_work.MoonsWorkModel;
import net.trpfrog.medipro_game.mini_game.moons_work.MoonsWorkView;
import net.trpfrog.medipro_game.scene.GameScene;

public class RaceGameScene extends GameScene {
    public RaceGameScene() {
        setModel(new RaceGameModel());
        setView(new RaceGameView((RaceGameModel)getModel()));
        setController(new RaceGameController((RaceGameModel)getModel(), (RaceGameView)getView()));
    }

    // テスト用にこういうのがあると便利
    public static void main(String[] args) {
        SceneManager.getInstance().push(new RaceGameScene());
    }
}
