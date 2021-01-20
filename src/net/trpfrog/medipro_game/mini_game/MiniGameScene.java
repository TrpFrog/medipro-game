package net.trpfrog.medipro_game.mini_game;

import net.trpfrog.medipro_game.dialog_background.DialogBackgroundScene;
import net.trpfrog.medipro_game.scene.GameScene;
import net.trpfrog.medipro_game.scene.GameView;
import net.trpfrog.medipro_game.util.MusicPlayer;

import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MiniGameScene extends GameScene {

    private String gameTitle = "SampleGame";
    private String gameDescription = "";
    private String howToPlay = "";
    private String creatorName = "";

    private Image starImage = null;
    private final static Image defaultStarImage = Toolkit.getDefaultToolkit().getImage(
            Paths.get(".","resource","mini_game","defaultEventStar.png").toString());

    @Override
    protected void setView(GameView view) {
        super.setView(view);
        view.setBGM(MusicPlayer.MINI_GAME_THEME);
    }

    /**
     * ミニゲームのタイトルを取得します。
     * @return ミニゲームのタイトル
     */
    public String getGameTitle() {
        return gameTitle;
    }

    /**
     * ミニゲームのタイトルを設定します。
     * @param gameTitle ミニゲームのタイトル
     */
    protected void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    /**
     * ミニゲームの説明を取得します。
     * @return ミニゲームの説明
     */
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

    /**
     * ミニゲームの説明を設定します。
     * 可長変引数になっていて、引数ごとに改行されます。
     * @param gameDescription ミニゲームの説明
     */
    protected void setGameDescription(String... gameDescription) {
        this.gameDescription = generateLabelString(gameDescription);
    }

    /**
     * 作者の名前を取得します。
     * @return 作者の名前
     */
    public String getCreatorName() {
        return creatorName;
    }

    /**
     * 作者の名前を設定します。
     * @param creatorName 作者の名前
     */
    protected void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    /**
     * 遊び方を返します。
     * @return 遊び方
     */
    public String getHowToPlay() {
        return howToPlay;
    }

    /**
     * 遊び方の説明文を設定します。
     * 可長変引数になっていて、引数ごとに改行されます。
     * @param howToPlay 遊び方の説明文
     */
    public void setHowToPlay(String... howToPlay) {
        this.howToPlay = generateLabelString(howToPlay);
    }

    public Image getStarImage() {
        if(this.starImage != null){
            return starImage;
        }else{
            this.starImage = defaultStarImage;
            return starImage;
        }

    }

    protected void setStarImage(Path imagePath) {
        starImage = Toolkit.getDefaultToolkit().getImage(String.valueOf(imagePath));
    }

    /**
     * 設定されたミニゲーム情報を元に説明用パネルを生成します。
     */
    protected void makeDescriptionDialog() {
        var dialog = new DialogBackgroundScene(new MiniGameStartDialogPanel(this), true);
        addSubScene(dialog);
    }
}
