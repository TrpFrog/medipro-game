package net.trpfrog.medipro_game;

import net.trpfrog.medipro_game.mainmenu.MainMenuScene;

public class MediproGame {
    public static void main(String[] args) {
        SceneManager sceneManager = SceneManager.getInstance();
        MainView view = MainView.getInstance();
        sceneManager.push(MainMenuScene.getInstance());
    }
}