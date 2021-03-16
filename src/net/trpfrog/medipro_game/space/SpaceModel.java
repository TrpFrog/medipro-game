package net.trpfrog.medipro_game.space;

import net.trpfrog.medipro_game.SceneManager;
import net.trpfrog.medipro_game.mini_game.MiniGameScene;
import net.trpfrog.medipro_game.pause.PauseScene;
import net.trpfrog.medipro_game.scene.GameModel;
import net.trpfrog.medipro_game.space.map.SpaceMap2D;
import net.trpfrog.medipro_game.space.map.SpaceMap3D;
import net.trpfrog.medipro_game.space.symbols.EventStar;
import net.trpfrog.medipro_game.space.symbols.MiniGameStar;
import net.trpfrog.medipro_game.space.symbols.Rocket;
import net.trpfrog.medipro_game.util.ResourceLoader;
import net.trpfrog.medipro_game.util.SparsePointsBuilder;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class SpaceModel extends GameModel {

    private SpaceMap3D map = new SpaceMap3D(24, 24, 128, 10);
    private Rocket rocket = new Rocket(this);
    private List<EventStar> eventStarsList = new ArrayList<>();
    private CometManager cometManager;

    public SpaceModel(){
        cometManager = new CometManager(this);

        loadMiniGameStars();
        redistributeMiniGameStars();
    }

    private void loadMiniGameStars() {
        var miniGameStarsTextFileScanner = new Scanner(
                ResourceLoader.getInputStream(".","resource", "space_game", "MiniGameStarFQCNs.txt")
        );
        miniGameStarsTextFileScanner.nextLine(); // ignore first line

        while(miniGameStarsTextFileScanner.hasNextLine()) {
            int depth   = Integer.parseInt(miniGameStarsTextFileScanner.nextLine());
            String fqcn = miniGameStarsTextFileScanner.nextLine();
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            try {
                var scene = loader.loadClass(fqcn);
                var star = new MiniGameStar(50, (Class<? extends MiniGameScene>) scene);
                eventStarsList.add(star);
                get3DMap().get2DMap(depth).addSymbol(0, 0, star);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void redistributeMiniGameStars() {
        var rocketFloorMap = getRocketFloorMap();
        int margin = 100;
        var points = SparsePointsBuilder.build(
                new Rectangle(
                        margin, margin,
                        rocketFloorMap.getWidth() - 2 * margin,
                        rocketFloorMap.getHeight() - 2 * margin
                ),
                500,
                eventStarsList.size()
        );
        Collections.shuffle(points);

        for(int i = 0; i < eventStarsList.size(); i++) {
            var star = eventStarsList.get(i);

            int starDepth = -1;
            for(int depth = 0; depth < get3DMap().getDepth(); depth++) {
                if(get3DMap().get2DMap(depth).contains(star)) {
                    starDepth = depth;
                    break;
                }
            }

            final var map = get3DMap().get2DMap(starDepth);
            map.removeSymbol((int)star.getX(), (int)star.getY(), star);
            map.addSymbol(points.get(i).x, points.get(i).y, star);
        }
    }

    public CometManager getCometManager(){ return cometManager;}

    /**
     * 宇宙全体のSpaceMap3Dを返します。
     * @return 宇宙全体のSpaceMap3D
     */
    public SpaceMap3D get3DMap() {
        return map;
    }

    /**
     * ロケットが今いる階層の地図を返します。
     * @return ロケットが存在する階層のSpaceMap2D
     */
    public SpaceMap2D getRocketFloorMap() {
        return map.get2DMap(rocket.getDepth());
    }

    /**
     * このゲームで唯一のロケットオブジェクトを返します。
     * @return 操作するロケット
     */
    public Rocket getRocket() {
        return rocket;
    }

    @Override
    public void suspend() {
        rocket.suspend();
        cometManager.suspend();
    }

    @Override
    public void resume() {
        rocket.resume();
        cometManager.resume();
    }
}
