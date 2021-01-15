package net.trpfrog.medipro_game.mini_game.galaxy_express;

import net.trpfrog.medipro_game.SceneManager;
import net.trpfrog.medipro_game.mini_game.MiniGameScene;

import java.nio.file.Paths;

public class GalaxyExpressScene extends MiniGameScene {

    public GalaxyExpressScene(){
        setModel(new GalaxyExpressModel());
        setView(new GalaxyExpressView((GalaxyExpressModel)getModel()));
        setController(new GalaxyExpressController((GalaxyExpressModel)getModel(),(GalaxyExpressView)getView()));

        setGameTitle("Galaxy Express");
        setCreatorName("わくわくぎんがだん");
        setGameDescription(
                "電車をゴールまで導こう！"
        );
        setHowToPlay(
                "わくわくぎんがだんのボスです",
                "最近ものわすれがひどく、行き先を忘れてしまいました...",
                "お願いします！わたしたちをたすけて！",
                "道を間違えると脱線します"
        );
        makeDescriptionDialog();
        setStarImage(Paths.get(".","resource","space_game","EventStar.png"));
    }

    // テスト用
    public static void main(String[] args) {
        SceneManager.getInstance().push(new GalaxyExpressScene());
    }
}
