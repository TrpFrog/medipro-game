package net.trpfrog.medipro_game.mini_game.moons_work;

import net.trpfrog.medipro_game.SceneManager;
import net.trpfrog.medipro_game.mini_game.MiniGameScene;

import java.nio.file.Paths;

public class MoonsWorkScene extends MiniGameScene {

    public MoonsWorkScene() {
        setModel(new MoonsWorkModel());
        setView(new MoonsWorkView((MoonsWorkModel)getModel()));
        setController(new MoonsWorkController((MoonsWorkModel)getModel(), (MoonsWorkView)getView()));

        setGameTitle("Moon's Work");
        setCreatorName("Attacked by つまムーン");
        setGameDescription(
                "地球を隕石から守れ！"
        );
        setHowToPlay(
                "マウスカーソルで月を誘導して隕石からの攻撃を防ぎましょう。",
                "科学の発展のためにロケットはできるだけ壊さないようにしましょう。",
                "ロケットは3回までしか壊すことができません。",
                "隕石が地球に落ちたらゲームオーバーです。"
        );

        setStarImage(Paths.get(".","resource","mini_game","moons_work","staricon.png"));

        makeDescriptionDialog();
    }

    public static void main(String[] args) {
        SceneManager.getInstance().push(new MoonsWorkScene());
    }
}
