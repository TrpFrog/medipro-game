package net.trpfrog.medipro_game.space.field_mini_game.zodiac;

import net.trpfrog.medipro_game.player.Medal;
import net.trpfrog.medipro_game.player.MedalWindow;
import net.trpfrog.medipro_game.space.symbols.EventStar;
import net.trpfrog.medipro_game.space.symbols.Rocket;
import net.trpfrog.medipro_game.space.symbols.RocketEvent;
import net.trpfrog.medipro_game.space.symbols.Star;
import net.trpfrog.medipro_game.symbol.RelativeHitBox;
import net.trpfrog.medipro_game.util.ResourceLoader;

import java.awt.*;

public class ZodiacSignStar extends EventStar implements RocketEvent {
    private final ZodiacSign zodiacSign;
    private boolean touchedByRocket = false;

    public ZodiacSignStar(ZodiacSign zodiacSign, double x, double y) {
        this.zodiacSign = zodiacSign;
        setEvent(this);
        setImage(Star.STAR_IMAGE_DARKENED, 35);
        setLocation(x, y);
        setRelativeHitBox(RelativeHitBox.makeCircle(35/2.0));
    }

    public boolean isTouchedByRocket() {
        return touchedByRocket;
    }

    public void setTouchedByRocket(boolean touchedByRocket) {
        this.touchedByRocket = touchedByRocket;
    }

    @Override
    public void run(Rocket r, Star s) {
        assert (s instanceof ZodiacSignStar);

        boolean allTouched = true;
        for(var star : zodiacSign.zodiacSignStars) {
            if(!star.isTouchedByRocket()) {
                allTouched = false;
                break;
            }
        }

        int clearedStars = 0;
        for(var star : zodiacSign.zodiacSignStars) {
            clearedStars++;
            if(star.equals(this)) break;
            if(!star.isTouchedByRocket()) {
                clearedStars = 0;
                break;
            }
        }

        if (allTouched) {
            zodiacSign.lineColor = Color.WHITE;
            MedalWindow.pushMedalWindow(new Medal("星座巡り", ResourceLoader.readImage(
                    ".", "resource", "space_game", "medal.png"
            )));
        } else {
            zodiacSign.lineColor = new Color(0.7f, 0.7f, 0.7f,
                    0.25f + 0.5f * clearedStars / zodiacSign.stars);
        }

        if(isTouchedByRocket()) return;

        boolean previousAllTouched = true;
        for(var star : zodiacSign.zodiacSignStars) {
            if(star.equals(this)) break;
            if(!star.isTouchedByRocket()) {
                previousAllTouched = false;
                break;
            }
        }

        if (previousAllTouched) {
            s.setImage(Star.STAR_IMAGE_LIGHTED, 30);
            touchedByRocket = true;
        } else {
            for (var star : zodiacSign.zodiacSignStars) {
                star.setTouchedByRocket(false);
                star.setImage(Star.STAR_IMAGE_DARKENED, 30);
            }
        }
    }
}
