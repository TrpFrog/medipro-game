package net.trpfrog.medipro_game.util;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import javax.imageio.ImageIO;

public class ResourceLoader {

    private static boolean useOuterResourceFolder = false;

    private static String toClassLoaderReadablePath(String path) {
        if(path.startsWith(".")) {
            path = path.substring(1);
        }
        System.err.println(path);
        return path;
    }

    public static Image readImage(String path) {
        try {
            return ImageIO.read(getInputStream(path));
        } catch (IOException | IllegalArgumentException e) {
            if(useOuterResourceFolder) {
                e.printStackTrace();
                return null;
            } else {
                useOuterResourceFolder = true;
                return readImage(path);
            }
        }
    }

    public static Image readImage(Path path) {
        return readImage(path.toString());
    }

    public static Image readImage(String first, String... more) {
        return readImage(Path.of(first, more));
    }

    public static InputStream getInputStream(String path) {
        if(useOuterResourceFolder) {
            try {
                return new FileInputStream(path);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            path = toClassLoaderReadablePath(path);
            return ResourceLoader.class.getResourceAsStream(path);
        }
        return null;
    }

    public static InputStream getInputStream(Path path) {
        return getInputStream(path.toString());
    }

    public static InputStream getInputStream(String first, String... more) {
        return getInputStream(Path.of(first, more));
    }

    private ResourceLoader() {}
}
