package net.trpfrog.medipro_game.mini_game.galaxy_express;


import com.sun.tools.javac.Main;
import net.trpfrog.medipro_game.MainView;
import net.trpfrog.medipro_game.SceneManager;
import net.trpfrog.medipro_game.dialog_background.DialogBackgroundScene;
import net.trpfrog.medipro_game.mini_game.GameOverWindow;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GalaxyExpressAnswerPanel extends JPanel {
    private Path path = Paths.get(".","resource","mini_game","galaxy_express","bkg1.jpg");
    private Image bgImage = Toolkit.getDefaultToolkit().getImage(path.toString());
    private MainView mainView = MainView.getInstance();
    private int correctAnswer;
    private GalaxyExpressModel model;
    private int score;

    public GalaxyExpressAnswerPanel(){
        correctAnswer = GalaxyExpressModel.trainNums;
        setSize(mainView.getWidth()-70,mainView.getHeight()-80);
        setLayout(new BorderLayout(50,50));
        JTextField tf = new JTextField(3);
        tf.setSize(50,50);
        tf.setMargin(new Insets(10,10,10,10));
        tf.addActionListener(e ->{
            score = 100 - 5*Math.abs(Integer.parseInt(tf.getText()) - correctAnswer);
            var window = new GameOverWindow("Game Over", getScore(),Color.BLUE);
            var scene = new DialogBackgroundScene(window, false);
            SceneManager.getInstance().push(scene);
        });
        add(tf,BorderLayout.SOUTH);
    }

    public int getScore(){return Math.max(score,0);}

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImage,0,0,null);
        g.setColor(Color.WHITE);
        g.drawString("電車はいくつあった？",100,100);
        g.drawString("正解との差がすくないほど点が高いよ",100,120);
    }
}
