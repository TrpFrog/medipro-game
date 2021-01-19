package net.trpfrog.medipro_game.player;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private final List<Medal> gotMedals;

    public Player() {
        gotMedals = new ArrayList<>();
    }

    public void registerMedal(Medal medal) {
        gotMedals.add(medal);
    }

    public List<Medal> createMedalList() {
        return new ArrayList<>(gotMedals);
    }

}
