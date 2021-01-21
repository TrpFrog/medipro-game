package net.trpfrog.medipro_game.player;

import java.awt.*;
import java.util.Date;
import java.util.Objects;

public class Medal {
    private final String title;
    private final Image medalImage;
    private final Date acquisitionDate;

    public Medal(String title, Image medalImage) {
        this.title = title;
        this.medalImage = medalImage;
        this.acquisitionDate = new Date();
    }

    public Image getMedalImage() {
        return medalImage;
    }

    public Date getAcquisitionDate() {
        return acquisitionDate;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medal medal = (Medal) o;
        return Objects.equals(title, medal.title) && Objects.equals(medalImage, medal.medalImage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, medalImage);
    }
}
