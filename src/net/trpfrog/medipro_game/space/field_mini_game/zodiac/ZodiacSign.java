package net.trpfrog.medipro_game.space.field_mini_game.zodiac;

import net.trpfrog.medipro_game.fieldmap.FieldMap;
import net.trpfrog.medipro_game.symbol.Symbol;
import net.trpfrog.medipro_game.util.SparsePointsBuilder;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class ZodiacSign extends Symbol {

    protected List<ZodiacSignStar> zodiacSignStars = new LinkedList<>();
    protected List<ZodiacLineSymbol> zodiacSignLines = new LinkedList<>();
    protected float lineAlpha = 0.25f;
    protected Color lineColor = new Color(0.7f, 0.7f, 0.7f, lineAlpha);
    public final int stars;

    public ZodiacSign(int width, int height, int stars) {
        this.stars = stars;

        final double starMinDistance = 200;

        Rectangle range = new Rectangle(0, 0, width, height);
        SparsePointsBuilder.build(range, (int)starMinDistance, stars)
                .stream()
                .map(e -> new ZodiacSignStar(this, e.x, e.y))
                .forEach(e -> zodiacSignStars.add(e));

        for(int i = 1; i < zodiacSignStars.size(); i++) {
            zodiacSignLines.add(new ZodiacLineSymbol(this,
                    (int) Math.round(zodiacSignStars.get(i - 1).getX()),
                    (int) Math.round(zodiacSignStars.get(i - 1).getY()),
                    (int) Math.round(zodiacSignStars.get(i).getX()),
                    (int) Math.round(zodiacSignStars.get(i).getY())
            ));
        }
    }

    public ZodiacSign(Rectangle range, int stars) {
        this(range.width, range.height, stars);
        setLocation(range.x, range.y);
    }

    private boolean registered = false;
    public void registerOnFieldMap(FieldMap map) {
        assert(!registered);
        var mergedStream = Stream.concat(zodiacSignStars.stream(), zodiacSignLines.stream());
        mergedStream.forEach(e -> map.addSymbol((int)e.getX(), (int)e.getY(), e));
        registered = true;
    }

    @Override
    public void setLocation(double x, double y) {
        assert(!registered);
        var mergedStream = Stream.concat(zodiacSignStars.stream(), zodiacSignLines.stream());
        mergedStream.forEach(e -> e.translate(-getX(), -getY()));
        super.setLocation(x, y);
        mergedStream = Stream.concat(zodiacSignStars.stream(), zodiacSignLines.stream());
        mergedStream.forEach(e -> e.translate(getX(), getY()));
    }

    /**
     * 設定した範囲に星座を生成します。
     * この処理は非常に重いのでスレッドを生成してそちらで処理を行います。
     * @param range 星座の範囲
     * @param stars 星の数
     * @param map 登録するFieldMap
     */
    public static void buildAndRegister(Rectangle range, int stars, FieldMap map) {
        Timer timer = new Timer(10, e -> {
            var zodiac = new ZodiacSign(range, stars);
            zodiac.registerOnFieldMap(map);
        });
        timer.setRepeats(false);
        timer.start();
    }
}
