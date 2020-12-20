package assignment;
/**
 *
 * CS314H Programming Assignment 1 - Java image processing
 *
 * Included is the Invert effect from the assignment.  Use this as an
 * example when writing the rest of your transformations.  For
 * convenience, you should place all of your transformations in this file.
 *
 * You can compile everything that is needed with
 * javac -d bin src/assignment/*.java
 *
 * You can run the program with
 * java -cp bin assignment.JIP
 *
 * Please note that the above commands assume that you are in the prog1
 * directory.
 */

import java.util.ArrayList;

class NoRed extends ImageEffect {
    @Override
    public int[][] apply(int[][] pixels,
                         ArrayList<ImageEffectParam> params) {
        int width = pixels[0].length;
        int height = pixels.length;

        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y++){
                int green = getGreen(pixels[y][x]);
                int blue = getBlue(pixels[y][x]);
                pixels[y][x] = makePixel(0, green, blue);
            }
        }
        return pixels;
    }
}

class NoGreen extends ImageEffect {
    @Override
    public int[][] apply(int[][] pixels,
                         ArrayList<ImageEffectParam> params) {
        int width = pixels[0].length;
        int height = pixels.length;

        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y++){
                int red = getRed(pixels[y][x]);
                int blue = getBlue(pixels[y][x]);
                pixels[y][x] = makePixel(red,0, blue);
            }
        }
        return pixels;
    }
}

class NoBlue extends ImageEffect {
    @Override
    public int[][] apply(int[][] pixels,
                         ArrayList<ImageEffectParam> params) {
        int width = pixels[0].length;
        int height = pixels.length;

        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y++){
                int red = getRed(pixels[y][x]);
                int green = getGreen(pixels[y][x]);
                pixels[y][x] = makePixel(red, green, 0);
            }
        }
        return pixels;
    }
}

class RedOnly extends ImageEffect {
    @Override
    public int[][] apply(int[][] pixels,
                         ArrayList<ImageEffectParam> params) {
        int width = pixels[0].length;
        int height = pixels.length;

        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y++){
                int red = getRed(pixels[y][x]);
                pixels[y][x] = makePixel(red, 0, 0);
            }
        }
        return pixels;
    }
}

class GreenOnly extends ImageEffect {
    @Override
    public int[][] apply(int[][] pixels,
                         ArrayList<ImageEffectParam> params) {
        int width = pixels[0].length;
        int height = pixels.length;

        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y++){
                int green = getGreen(pixels[y][x]);
                pixels[y][x] = makePixel(0, green, 0);
            }
        }
        return pixels;
    }
}

class BlueOnly extends ImageEffect {
    @Override
    public int[][] apply(int[][] pixels,
                         ArrayList<ImageEffectParam> params) {
        int width = pixels[0].length;
        int height = pixels.length;

        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y++){
                int blue = getBlue(pixels[y][x]);
                pixels[y][x] = makePixel(0, 0, blue);
            }
        }
        return pixels;
    }
}

class BlackAndWhite extends ImageEffect {
    @Override
    public int[][] apply(int[][] pixels,
                         ArrayList<ImageEffectParam> params) {
        int width = pixels[0].length;
        int height = pixels.length;

        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y++){
                int avg = (getRed(pixels[y][x]) + getGreen(pixels[y][x]) + getBlue(pixels[y][x]))/3;
                pixels[y][x] = makePixel(avg, avg, avg);
            }
        }
        return pixels;
    }
}

class VerticalReflect extends ImageEffect {
    public int[][] apply(int[][] pixels,
                         ArrayList<ImageEffectParam> params) {
        int width = pixels[0].length;
        int height = pixels.length;

        for (int x = 0; x < width/2; x++) {
            for (int y = 0; y < height; y++) {
                int temp = pixels[y][x];
                pixels[y][x] = pixels[y][width-1-x];
                pixels[y][width-x-1] = temp;
            }
        }
        return pixels;
    }
}

class HorizontalReflect extends ImageEffect {
    public int[][] apply(int[][] pixels,
                         ArrayList<ImageEffectParam> params) {
        int width = pixels[0].length;
        int height = pixels.length;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height/2; y++) {
                int temp = pixels[y][x];
                pixels[y][x] = pixels[height-1-y][x];
                pixels[height-1-y][x] = temp;
            }
        }
        return pixels;
    }
}

class Grow extends ImageEffect {
    public int[][] apply(int[][] pixels,
                         ArrayList<ImageEffectParam> params) {
        int width = pixels[0].length;
        int height = pixels.length;
        int[][] newPixels = new int[height*2][width*2];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                newPixels[y*2][x*2] = pixels[y][x];
                newPixels[y*2+1][x*2] = pixels[y][x];
                newPixels[y*2+1][x*2+1] = pixels[y][x];
                newPixels[y*2][x*2+1] = pixels[y][x];
            }
        }
        return newPixels;
    }
}

class Shrink extends ImageEffect {
    public int[][] apply(int[][] pixels,
                         ArrayList<ImageEffectParam> params) {
        int width = pixels[0].length;
        int height = pixels.length;
        int[][] newPixels = new int[height/2][width/2];

        for (int x = 0; x < width-1; x+=2) {
            for (int y = 0; y < height-1; y+=2) {
                int avgRed = (getRed(pixels[y][x]) + getRed(pixels[y+1][x]) + getRed(pixels[y+1][x+1]) + getRed(pixels[y][x+1]))/4;
                int avgGreen = (getGreen(pixels[y][x])+getGreen(pixels[y+1][x]) + getGreen(pixels[y+1][x+1]) + getGreen(pixels[y][x+1]))/4;
                int avgBlue = (getBlue(pixels[y][x]) + getBlue(pixels[y+1][x]) + getBlue(pixels[y+1][x+1]) + getBlue(pixels[y][x+1]))/4;
                newPixels[y/2][x/2] = makePixel(avgRed,avgGreen,avgBlue);
            }
        }
        return newPixels;
    }
}

class Threshold extends ImageEffect{
    public Threshold() {
        super();
        params = new ArrayList<ImageEffectParam>();
        params.add(new ImageEffectParam("Threshold",
                "Description of param.",
                127, 0, 1000));
    }

    @Override
    public int[][] apply(int[][] pixels,
                         ArrayList<ImageEffectParam> params) {
        int width = pixels[0].length;
        int height = pixels.length;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int threshold = params.get(0).getValue();
                int red = getRed(pixels[y][x]) >= threshold ? 255:0;
                int green = getGreen(pixels[y][x]) >= threshold ? 255:0;
                int blue = getBlue(pixels[y][x]) >= threshold ? 255:0;
                pixels[y][x] = makePixel(red,green,blue);
            }
        }
        return pixels;
    }
}

class Smooth extends ImageEffect {
    public int[][] apply(int[][] pixels,
                         ArrayList<ImageEffectParam> params) {
        int width = pixels[0].length;
        int height = pixels.length;
        int[][] newPixels = new int[height/2][width/2];

        for (int x = 0; x < width-3; x++) {
            for (int y = 0; y < height-3; y++) {
                int avgRed = (getRed(pixels[y][x])+getRed(pixels[y+1][x]) + getRed(pixels[y+1][x+1]) + getRed(pixels[y][x+1]) + getRed(pixels[y+2][x]) + getRed(pixels[y+2][x+1]) + getRed(pixels[y+2][x+2]) + getRed(pixels[y][x+2]) + getRed(pixels[y+1][x+2]))/9;
                int avgGreen = (getGreen(pixels[y][x]) + getGreen(pixels[y+1][x]) + getGreen(pixels[y+1][x+1]) + getGreen(pixels[y][x+1]) + getGreen(pixels[y+2][x]) + getGreen(pixels[y+2][x+1]) + getGreen(pixels[y+2][x+2]) + getGreen(pixels[y][x+2]) + getGreen(pixels[y+1][x+2]))/9;
                int avgBlue = (getBlue(pixels[y][x]) + getBlue(pixels[y+1][x]) + getBlue(pixels[y+1][x+1]) + getBlue(pixels[y][x+1])+getBlue(pixels[y+2][x]) + getBlue(pixels[y+2][x+1]) + getBlue(pixels[y+2][x+2]) + getBlue(pixels[y][x+2]) + getBlue(pixels[y+1][x+2]))/9;
                pixels[y][x] = makePixel(avgRed,avgGreen,avgBlue);
            }
        }
        return pixels;
    }
}

class Invert extends ImageEffect {
    public int[][] apply(int[][] pixels,
                         ArrayList<ImageEffectParam> params) {
        int width = pixels[0].length;
        int height = pixels.length;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixels[y][x] = ~pixels[y][x];
            }
        }
        return pixels;
    }
}

class Dummy extends ImageEffect {

    public Dummy() {
        super();
        params = new ArrayList<ImageEffectParam>();
        params.add(new ImageEffectParam("ParamName",
                                           "Description of param.",
                                           10, 0, 1000));
    }

    public int[][] apply(int[][] pixels,
                         ArrayList<ImageEffectParam> params) {
        // Use params here.
        return pixels;
    }
}
