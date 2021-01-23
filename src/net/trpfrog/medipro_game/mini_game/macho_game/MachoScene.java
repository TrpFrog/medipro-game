package net.trpfrog.medipro_game.mini_game.macho_game;

import net.trpfrog.medipro_game.SceneManager;
import net.trpfrog.medipro_game.mini_game.MiniGameScene;

import java.nio.file.Paths;

public class MachoScene extends MiniGameScene {
    public MachoScene() {
        setModel(new MachoModel());
        setView(new MachoView((MachoModel)getModel()));
        setController(new MachoController((MachoModel)getModel(), (MachoView)getView()));

        setGameTitle("マッチョ星雲");
        setCreatorName("Created by キン肉一世");

        setGameDescription(
                "マッチョの世界へようこそ！"
        );

        setHowToPlay(
            "　　_＿",
            "　 / )))　　　＿",
            "`／ イ~　　　(((ヽ",
            "(　 ﾉ　　　　 ￣Ｙ＼",
            "|　(＼　∧＿∧　｜　)",
            "ヽ　ヽ`(´･ω･)／ノ/",
            "　＼ |　⌒Ｙ⌒　/ /",
            "　 ｜ヽ　 ｜　 ﾉ／",
            "　　＼トー仝ーイ",
            "　　 ｜ ミ土彡/",
            "　　　)　　　｜",
            "　　 /　　_　 ＼",
            "　　/　 ／ ＼　 ヽ",
            "　 /　 /　　 ヽ　|"
        );

        setStarImage(Paths.get(".","resource","mini_game","macho_game","staricon.png"));

        makeDescriptionDialog();
    }

    // テスト用にこういうのがあると便利
    public static void main(String[] args) {
        SceneManager.getInstance().push(new MachoScene());
    }
}
