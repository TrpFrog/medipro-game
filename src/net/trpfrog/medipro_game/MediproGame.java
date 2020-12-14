package net.trpfrog.medipro_game;

import net.trpfrog.medipro_game.space.SpaceScene;

public class MediproGame {
    public static void main(String[] args) {
        SceneManager sceneManager = SceneManager.getInstance();
        MainView view = MainView.getInstance();
        new MainController(sceneManager, view);

        sceneManager.push(new SpaceScene());
    }
}