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
        setCreatorName("つまみ流星群 impacted by ねぎー彗星");

        setGameDescription("今日は流れ星がよく見えそうです！");

        setHowToPlay(
                "あなたはカップルが嫌いな神です。",
                "カップルが見ていない隙に流れ星を流しましょう。","",
                "スペースキーを押す、または画面をクリックすることで",
                "流れ星を流すことができます。",
                "30秒間でできるだけ多くの星を流しましょう。",
                "ただし、流れ星を見られてしまうと10点減点です。"
        );

        makeDescriptionDialog();
    }

    // テスト用にこういうのがあると便利
    public static void main(String[] args) {
        SceneManager.getInstance().push(new ShootingStarScene());
    }
}
