package net.trpfrog.medipro_game.player;

import java.awt.*;
import java.util.Date;

public class Medal {
    private final Image medalImage;
    private final Date acquisitionDate;

    public Medal(Image medalImage) {
        this.medalImage = medalImage;
        this.acquisitionDate = new Date();
    }

    public Image getMedalImage() {
        return medalImage;
    }

    public Date getAcquisitionDate() {
        return acquisitionDate;
    }
}
