package net.trpfrog.medipro_game.mini_game;

import net.trpfrog.medipro_game.dialog_background.DialogBackgroundScene;
import net.trpfrog.medipro_game.scene.GameScene;

public class MiniGameScene extends GameScene {

    private String gameTitle = "SampleGame";
    private String gameDescription = "";
    private String howToPlay = "";
    private String creatorName = "";

    public String getGameTitle() {
        return gameTitle;
    }

    protected void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public String getGameDescription() {
        return gameDescription;
    }

    private String generateLabelString(String... strings) {
        String ret;
        if(strings.length == 1) {
            ret = strings[0];
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("<html>");
            sb.append(strings[0]);
            for(int i = 1; i < strings.length; i++) {
                sb.append("<br>");
                sb.append(strings[i]);
            }
            sb.append("</html>");
            ret = sb.toString();
        }
        return ret;
    }

    protected void setGameDescription(String... gameDescription) {
        this.gameDescription = generateLabelString(gameDescription);
    }

    public String getCreatorName() {
        return creatorName;
    }

    protected void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getHowToPlay() {
        return howToPlay;
    }

    public void setHowToPlay(String... howToPlay) {
        this.howToPlay = generateLabelString(howToPlay);
    }

    protected void makeDescriptionDialog() {
        var dialog = new DialogBackgroundScene(new MiniGameStartDialogPanel(this), true);
        addSubScene(dialog);
    }
}
