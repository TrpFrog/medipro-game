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
                "電車の数をおしえてあげよう！"
        );
        setHowToPlay(
                "わくわくぎんがだんのボスです",
                "最近ものわすれがひどく、出発させた電車の数を忘れてしまいました...",
                "お願いします！わたしたちをたすけて！",
                "数を間違えると電車が脱線するかもしれません"
        );
        makeDescriptionDialog();
        setStarImage(Paths.get(".","resource","mini_game","galaxy_express","starImage.png"));
    }

    // テスト用
    public static void main(String[] args) {
        SceneManager.getInstance().push(new GalaxyExpressScene());
    }
}
