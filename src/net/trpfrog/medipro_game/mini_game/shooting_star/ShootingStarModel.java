package net.trpfrog.medipro_game.mini_game.shooting_star;

import net.trpfrog.medipro_game.SceneManager;
import net.trpfrog.medipro_game.dialog_background.DialogBackgroundScene;
import net.trpfrog.medipro_game.mini_game.GameOverWindow;
import net.trpfrog.medipro_game.mini_game.moons_work.symbols.Background;
import net.trpfrog.medipro_game.mini_game.shooting_star.symbols.Couple;
import net.trpfrog.medipro_game.mini_game.shooting_star.symbols.GameTimer;
import net.trpfrog.medipro_game.mini_game.shooting_star.symbols.ScoreCounter;
import net.trpfrog.medipro_game.mini_game.shooting_star.symbols.ShootingStar;
import net.trpfrog.medipro_game.player.Medal;
import net.trpfrog.medipro_game.player.MedalWindow;
import net.trpfrog.medipro_game.scene.GameModel;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class ShootingStarModel extends GameModel {

    private Background background;
    private Couple couple;
    private ScoreCounter scoreCounter;
    private GameTimer gameTimer;
    private Timer timer;
    private int score = 0;
    private boolean gameEnded = false;

    private List<ShootingStar> stars = new LinkedList<>();

    public ShootingStarModel() {
        background = new Background();
        couple = new Couple(this);
        scoreCounter = new ScoreCounter(this);
        gameTimer = new GameTimer(30 * 1000, this);

        timer = new Timer(10, e -> stars.forEach(ShootingStar::turn));
    }

    public List<ShootingStar> getStars() {
        return stars;
    }

    public void addStar() {
        score++;
        stars.add(new ShootingStar(this));
    }

    public Background getBackground() {
        return background;
    }

    public Couple getCouple() {
        return couple;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public ScoreCounter getScoreCounter() {
        return scoreCounter;
    }

    public GameTimer getGameTimer() {
        return gameTimer;
    }

    public void endGame() {
        if(gameEnded) return;
        gameEnded = true;
        var window = new GameOverWindow(
                "Game Over",
                getScore(),
                new Color(200, 200, 0, 50)
        );
        var scene = new DialogBackgroundScene(window, false);
        SceneManager.getInstance().push(scene);
        if(score >= 100) {
            MedalWindow.pushMedalWindow(new Medal("Don't show shooting stars!",
                    new ShootingStarScene().getStarImage()));
        }
    }

    @Override
    public void suspend(){
        timer.stop();
    }

    @Override
    public void resume() {
        timer.start();
    }
}
