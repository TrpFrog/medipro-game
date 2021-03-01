package net.trpfrog.medipro_game.settings;

import net.trpfrog.medipro_game.util.MusicPlayer;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;

class VolumeSettingsPanel extends JPanel {

    private final JSlider bgmGainSlider, seGainSlider;

    public VolumeSettingsPanel() {

        setBorder(new TitledBorder("Volume"));
        setLayout(new GridLayout(4, 1));
        setOpaque(false);

        add(new JLabel("BGM"));
        bgmGainSlider = new JSlider();
        bgmGainSlider.setValue((int)(MusicPlayer.getBgmGain() * 100));
        bgmGainSlider.addChangeListener(e -> {
            MusicPlayer.setBgmGain(bgmGainSlider.getValue() / 100f);
        });
        bgmGainSlider.setMajorTickSpacing(20);
        bgmGainSlider.setMinorTickSpacing(5);
        bgmGainSlider.setPaintTicks(true);
        add(bgmGainSlider);

        add(new JLabel("SE"));
        seGainSlider = new JSlider();
        seGainSlider.setValue((int)(MusicPlayer.getSeGain() * 100));
        seGainSlider.addChangeListener(e -> {
            MusicPlayer.setSeGain(seGainSlider.getValue() / 100f);
        });
        seGainSlider.setMajorTickSpacing(20);
        seGainSlider.setMinorTickSpacing(5);
        seGainSlider.setPaintTicks(true);
        add(seGainSlider);
    }
}
