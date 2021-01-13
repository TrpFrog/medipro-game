package net.trpfrog.medipro_game.mini_game.shooting_star;

import net.trpfrog.medipro_game.mini_game.moons_work.symbols.Background;
import net.trpfrog.medipro_game.mini_game.shooting_star.symbols.Couple;
import net.trpfrog.medipro_game.mini_game.shooting_star.symbols.ScoreCounter;
import net.trpfrog.medipro_game.mini_game.shooting_star.symbols.ShootingStar;
import net.trpfrog.medipro_game.scene.GameModel;

import java.util.LinkedList;
import java.util.List;

public class ShootingStarModel extends GameModel {

    private Background background;
    private Couple couple;
    private ScoreCounter scoreCounter;
    private int score = 0;

    private List<ShootingStar> stars = new LinkedList<>();

    public ShootingStarModel() {
        background = new Background();
        couple = new Couple(this);
        scoreCounter = new ScoreCounter(this);
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

    @Override
    public void suspend(){
    }

    @Override
    public void resume() {
    }
}
