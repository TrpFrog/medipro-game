package net.trpfrog.medipro_game.mini_game.shooting_star;

import net.trpfrog.medipro_game.SceneManager;
import net.trpfrog.medipro_game.mini_game.MiniGameScene;

public class ShootingStarScene extends MiniGameScene {
    public ShootingStarScene() {
        setModel(new ShootingStarModel());
        setView(new ShootingStarView((ShootingStarModel)getModel()));
        setController(new ShootingStarController((
                ShootingStarModel)getModel(),
                (ShootingStarView)getView())
        );

        setGameTitle("Don't Show Shooting Stars!");
        setCreatorName("つまみ流星群 inspired by ラブ一世");

        setGameDescription(
                "今日は流れ星がよく見えそうです！"
        );

        setHowToPlay(
                "あなたはカップルが嫌いな神です。",
                "流れ星を待ち望むカップルのために、カップルが見ていない隙に流れ星を流しましょう。",
                "スペースキーを押すと流れ星を流すことができます。",
                "60秒間でできるだけ多くの流れ星を流しましょう。"
        );

        makeDescriptionDialog();
    }

    // テスト用にこういうのがあると便利
    public static void main(String[] args) {
        SceneManager.getInstance().push(new ShootingStarScene());
    }
}
