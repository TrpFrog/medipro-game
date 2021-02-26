package net.trpfrog.medipro_game.util;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import javax.imageio.ImageIO;

public class ResourceLoader {

    private static final String ROOT_FILE_PATH_IDE; // "../../../.."
    private static final String ROOT_FILE_PATH_JAR = "";

    static {
        String tmp = "..";
        for(int i = 0; i < 3; i++) {
            tmp += File.separatorChar + "..";
        }
        ROOT_FILE_PATH_IDE = tmp;
    }

    private static String rootFilePath = ROOT_FILE_PATH_JAR;

    private static String toClassLoaderReadablePath(String path) {
        if(path.startsWith(".")) {
            path = rootFilePath + path.substring(1);
        }
        return path;
    }

    public static Image readImage(String path) {
        try {
            return ImageIO.read(getInputStream(path));
        } catch (IOException e) {
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
        try {
            return ResourceLoader.class.getResourceAsStream(path);
        } catch (IllegalArgumentException e) {
            // change to IDE root path
            if(rootFilePath.equals(ROOT_FILE_PATH_JAR)) {
                rootFilePath = ROOT_FILE_PATH_IDE;
                return getInputStream("." + path);
            } else {
                // file is really not found
                throw e;
            }
        }
    }

    public static InputStream getInputStream(Path path) {
        return getInputStream(path.toString());
    }

    public static InputStream getInputStream(String first, String... more) {
        return getInputStream(Path.of(first, more));
    }

    private ResourceLoader() {}
}
