package net.trpfrog.medipro_game.mini_game.race_game;

import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.SceneManager;
import net.trpfrog.medipro_game.mainmenu.MainMenuScene;
import net.trpfrog.medipro_game.mini_game.MiniGameScene;
import net.trpfrog.medipro_game.mini_game.moons_work.MoonsWorkController;
import net.trpfrog.medipro_game.mini_game.moons_work.MoonsWorkModel;
import net.trpfrog.medipro_game.mini_game.moons_work.MoonsWorkView;
import net.trpfrog.medipro_game.scene.GameScene;

public class RaceGameScene extends MiniGameScene {
    public RaceGameScene() {
        setModel(new RaceGameModel());
        setView(new RaceGameView((RaceGameModel)getModel()));
        setController(new RaceGameController((RaceGameModel)getModel(), (RaceGameView)getView()));

        setGameTitle("Sample Racing");
        setCreatorName("Created by つまみ自動車学校");

        setGameDescription(
                "教習所へようこそ！"
        );

        setHowToPlay(
                "WASDで車を操作しましょう。",
                "移動中のみ曲がることができます。",
                "ブレーキはありません。勝手に止まるので待ってください。"
        );

        makeDescriptionDialog();
    }

    // テスト用にこういうのがあると便利
    public static void main(String[] args) {
        SceneManager.getInstance().push(new RaceGameScene());
    }
}
