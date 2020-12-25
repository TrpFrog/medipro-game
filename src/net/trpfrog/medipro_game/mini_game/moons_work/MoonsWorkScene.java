package net.trpfrog.medipro_game.mini_game.moons_work;

import net.trpfrog.medipro_game.SceneManager;
import net.trpfrog.medipro_game.mini_game.race_game.RaceGameScene;
import net.trpfrog.medipro_game.scene.GameScene;

public class MoonsWorkScene extends GameScene {

    public MoonsWorkScene() {
        setModel(new MoonsWorkModel());
        setView(new MoonsWorkView((MoonsWorkModel)getModel()));
        setController(new MoonsWorkController((MoonsWorkModel)getModel(), (MoonsWorkView)getView()));
    }

    public static void main(String[] args) {
        SceneManager.getInstance().push(new MoonsWorkScene());
    }
}
