package net.trpfrog.medipro_game.util;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import javax.imageio.ImageIO;

public class ResourceLoader {

    private static String toClassLoaderReadablePath(String path) {
        if(path.startsWith(".")) {
            path = path.substring(1);
        }
        return path.replace(File.separatorChar, '/');
    }

    public static Image readImage(String path) {
        try {
            return ImageIO.read(getInputStream(path));
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Image readImage(Path path) {
        return readImage(path.toString());
    }

    public static Image readImage(String first, String... more) {
        return readImage(Path.of(first, more));
    }

    public static InputStream getInputStream(String path) {
        path = toClassLoaderReadablePath(path);
        return ResourceLoader.class.getResourceAsStream(path);
    }

    public static InputStream getInputStream(Path path) {
        return getInputStream(path.toString());
    }

    public static InputStream getInputStream(String first, String... more) {
        return getInputStream(Path.of(first, more));
    }

    private ResourceLoader() {}
}
