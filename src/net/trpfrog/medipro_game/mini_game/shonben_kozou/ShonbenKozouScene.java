package net.trpfrog.medipro_game.mini_game.shonben_kozou;

import net.trpfrog.medipro_game.SceneManager;
import net.trpfrog.medipro_game.mini_game.MiniGameScene;

public class ShonbenKozouScene extends MiniGameScene {

    public ShonbenKozouScene() {
        setModel(new ShonbenKozouModel());
        setView(new ShonbenKozouView((ShonbenKozouModel)getModel()));
        setController(new ShonbenKozouController((ShonbenKozouModel)getModel(), (ShonbenKozouView)getView()));

        setGameTitle("星屑集め");
        setCreatorName("");
        setGameDescription("");
        setHowToPlay("");
        makeDescriptionDialog();
    }

    public static void main(String[] args){
        SceneManager.getInstance().push(new ShonbenKozouScene());
    }
}
