package net.trpfrog.medipro_game.mini_game.shonben_kozou;

import net.trpfrog.medipro_game.SceneManager;
import net.trpfrog.medipro_game.mini_game.MiniGameScene;

import java.nio.file.Paths;

public class ShonbenKozouScene extends MiniGameScene {

    public ShonbenKozouScene() {
        setModel(new ShonbenKozouModel());
        setView(new ShonbenKozouView((ShonbenKozouModel)getModel()));
        setController(new ShonbenKozouController((ShonbenKozouModel)getModel(), (ShonbenKozouView)getView()));

        setGameTitle("星屑集め");
        setCreatorName("Q");
        setGameDescription("星屑を回収しろ！");
        setHowToPlay("「単位を取りたい！」という願い事を叶えたいなら",
                     "できるだけ多くの星屑を集めてお願いしないといけません。",
                     "10秒間でより多くの星屑を集めてください。",
                     "",
                     "・・・誰が何と言おうと星屑です！");
        makeDescriptionDialog();

        setStarImage(Paths.get(".","resource","mini_game","shonben_kozou","star.png"));
    }

    public static void main(String[] args){
        SceneManager.getInstance().push(new ShonbenKozouScene());
    }
}
