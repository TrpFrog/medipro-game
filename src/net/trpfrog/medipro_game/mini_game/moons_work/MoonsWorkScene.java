package net.trpfrog.medipro_game.mini_game.moons_work;

import net.trpfrog.medipro_game.scene.GameScene;

public class MoonsWorkScene extends GameScene {

    public MoonsWorkScene() {
        setModel(new MoonsWorkModel());
        setView(new MoonsWorkView((MoonsWorkModel)getModel()));
        setController(new MoonsWorkController((MoonsWorkModel)getModel(), (MoonsWorkView)getView()));
    }
}
