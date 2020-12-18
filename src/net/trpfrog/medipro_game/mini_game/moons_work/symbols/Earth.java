package net.trpfrog.medipro_game.mini_game.moons_work.symbols;

import net.trpfrog.medipro_game.mini_game.moons_work.MoonsWorkModel;
import net.trpfrog.medipro_game.symbol.Symbol;

import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Earth extends Symbol {
    private Path imagePath = Paths.get(".", "resource", "mini_game", "moons_work", "earth.png");
    private Image image = Toolkit.getDefaultToolkit().getImage(String.valueOf(imagePath));

    public Image getImage() {
        return image;
    }

    public Earth(MoonsWorkModel model) {
        Point p = model.getCenterPoint();
        setLocation(p.x, p.y);
        createHitJudgementRectangle(100, 100);
        Rectangle rect = this.getHitJudgeRectangle();
        setDrawer(g -> g.drawImage(image, rect.x, rect.y, rect.width, rect.height, null));
    }
}
