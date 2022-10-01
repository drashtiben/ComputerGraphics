package finalproject.raytracing.rendering;

import finalproject.raytracing.pixeldata.Color;
import finalproject.raytracing.math.Vector3;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
//https://en.wikipedia.org/wiki/Skybox_(video_games)#:~:text=A%20skybox%20is%20a%20method,is%20enclosed%20in%20a%20cuboid.
public class Skybox {
    private BufferedImage sphereImage;
    private boolean loaded;

    public Skybox(String resourceName) {
        sphereImage = new BufferedImage(2,2, BufferedImage.TYPE_INT_RGB);
        loaded = false;

        new Thread("Skybox loader") {
            @Override
            public void run() {
                try {
                    System.out.println("Loading skybox "+resourceName+"...");
                    sphereImage = ImageIO.read(getClass().getResourceAsStream("/res/"+resourceName));
                    System.out.println("Skybox ready.");
                    loaded = true;
                } catch (IOException | IllegalArgumentException ex) {
                    try {
                        sphereImage = ImageIO.read(getClass().getResourceAsStream("/res/error_skybox.jpg"));
                        loaded = true;
                    } catch (IOException | IllegalArgumentException ex2) {
                        ex2.printStackTrace();
                        System.exit(-1);
                    }
                    ex.printStackTrace();
                }
            }
        }.start();
    }

    public Color getColor(Vector3 d) {
        // (Wikipedia UV Unwrapping page) Convert Unit vector to texture coordinates
        float u = (float) (0.5+Math.atan2(d.getZ(), d.getX())/(2*Math.PI));
        float v = (float) (0.5 - Math.asin(d.getY())/Math.PI);

        try {
            return Color.fromInt(sphereImage.getRGB((int)(u*(sphereImage.getWidth()-1)), (int)(v*(sphereImage.getHeight()-1))));
        } catch (Exception e) {
            System.out.println("U: "+u+" V: "+v);
            e.printStackTrace();

            return Color.MAGENTA;
        }
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void reload(String resourceName) {
        sphereImage = new BufferedImage(2,2, BufferedImage.TYPE_INT_RGB);
        loaded = false;

        new Thread("Skybox loader") {
            @Override
            public void run() {
                try {
                    System.out.println("Loading skybox "+resourceName+"...");
                    sphereImage = ImageIO.read(getClass().getResourceAsStream("/res/"+resourceName));
                    System.out.println("Skybox ready.");
                    loaded = true;
                } catch (IOException | IllegalArgumentException ex) {
                    try {
                        sphereImage = ImageIO.read(getClass().getResourceAsStream("/res/error_skybox.jpg"));
                        loaded = true;
                    } catch (IOException | IllegalArgumentException ex2) {
                        ex2.printStackTrace();
                        System.exit(-1);
                    }
                    ex.printStackTrace();
                }
            }
        }.start();
    }

    public void reloadFromFile(File file) {
        sphereImage = new BufferedImage(2,2, BufferedImage.TYPE_INT_RGB);
        loaded = false;

        new Thread("Skybox loader") {
            @Override
            public void run() {
                try {
                    System.out.println("Loading skybox "+file.getName()+"...");
                    sphereImage = ImageIO.read(file);
                    System.out.println("Skybox ready.");
                    loaded = true;
                } catch (IOException | IllegalArgumentException ex) {
                    try {
                        sphereImage = ImageIO.read(getClass().getResourceAsStream("/res/error_skybox.jpg"));
                        loaded = true;
                    } catch (IOException | IllegalArgumentException ex2) {
                        ex2.printStackTrace();
                        System.exit(-1);
                    }
                    ex.printStackTrace();
                }
            }
        }.start();
    }
}
