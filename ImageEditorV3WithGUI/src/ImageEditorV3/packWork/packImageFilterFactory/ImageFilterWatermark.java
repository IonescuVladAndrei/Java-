package ImageEditorV3.packWork.packImageFilterFactory;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.nio.file.Paths;
import java.awt.Color;
import javafx.scene.image.ImageView;

import ImageEditorV3.packWork.ImageWriterClass;
import ImageEditorV3.packWork.ImagePaths;
import ImageEditorV3.packWork.packImageSizeFactory.ImageSize;
import ImageEditorV3.packWork.packImageSizeFactory.ImageFactory;

public class ImageFilterWatermark extends Thread implements ImageFilter {
    private BufferedImage buffImage;
    private ImagePaths imagePaths;
    private int segmentVlaue;
    private int execTime;
    private static int nrOfThreadsDone = 0;
    private ImageView imageView;

    public ImageFilterWatermark(BufferedImage buffImage, ImagePaths imagePaths, int segmentVlaue, ImageView imageView) {
        this.buffImage = buffImage;
        this.imagePaths = imagePaths;
        this.segmentVlaue = segmentVlaue;
        this.imageView = imageView;
    }

    private void setExecTime(int execTime) {
        this.execTime = execTime;
        System.out.println("Thread nr " + this.segmentVlaue + " has taken " + this.execTime + " ms");
    }

    @Override
    public int getExecTime() {
        return this.execTime;
    }

    @Override
    public void run() {
        int start = (int) System.currentTimeMillis(), end, i, j, R, G, B;
        Color color = null;
        int newColor;
        int keepColor = this.imagePaths.getKeepColor();
        int watermarkPlace = this.imagePaths.getWatermarkPlace();
        int X1, X2, Y1, Y2, min;
        X1 = X2 = Y1 = Y2 = 0;
        BufferedImage logoBuff = null;
        Color logoColor = null;
        try {
            if (watermarkPlace == 2) {
                X1 = this.buffImage.getWidth() / 2 - ImageIO.read(Paths.get(imagePaths.getWatermarkPath()).toAbsolutePath().toFile()).getWidth() / 2;
                X2 = this.buffImage.getWidth() / 2 + ImageIO.read(Paths.get(imagePaths.getWatermarkPath()).toAbsolutePath().toFile()).getWidth() / 2;
                Y1 = this.buffImage.getHeight() / 2 - ImageIO.read(Paths.get(imagePaths.getWatermarkPath()).toAbsolutePath().toFile()).getHeight() / 2;
                Y2 = this.buffImage.getHeight() / 2 + ImageIO.read(Paths.get(imagePaths.getWatermarkPath()).toAbsolutePath().toFile()).getHeight() / 2;
                if (X1 < 0 || X2 < 0 || Y1 < 0 || Y2 < 0) {
                    watermarkPlace = 1;
                    System.out.println("Watermark depaseste marimea imaginii originale");
                }
            } else if (watermarkPlace == 3) {
                X1 = this.buffImage.getWidth() - ImageIO.read(Paths.get(imagePaths.getWatermarkPath()).toAbsolutePath().toFile()).getWidth();
                Y1 = this.buffImage.getHeight() - ImageIO.read(Paths.get(imagePaths.getWatermarkPath()).toAbsolutePath().toFile()).getHeight();
                if (X1 < 0 || Y1 < 0) {
                    watermarkPlace = 1;
                    System.out.println("Watermark depaseste marimea imaginii originale");
                }
            }
            logoBuff = ImageIO.read(Paths.get(imagePaths.getWatermarkPath()).toAbsolutePath().toFile());
            ImageFactory imageFactory = new ImageFactory();
            ImageSize imageSize = imageFactory.createSize(this.segmentVlaue, buffImage.getWidth(), buffImage.getHeight());
            for (i = imageSize.widthStart(); i <= imageSize.widthEnd(); i++)
                for (j = imageSize.heightStart(); j <= imageSize.heightEnd(); j++) {
                    color = new Color(buffImage.getRGB(i, j));

                    if (watermarkPlace == 1) {
                        if (keepColor == 1 && i <= logoBuff.getWidth() - 1 && j <= logoBuff.getHeight() - 1) {
                            logoColor = new Color(logoBuff.getRGB(i, j), true);
                            if (logoColor.getAlpha() != 0) {
                                if (logoColor.getAlpha() > 40)
                                    newColor = logoColor.getRGB();
                                else
                                    newColor = (color.getRed() * color.getAlpha() + logoColor.getRed() * (255 - color.getAlpha())) / 510 * 65536 + (color.getGreen() * color.getAlpha() + logoColor.getGreen() * (255 - color.getAlpha())) / 510 * 256 + (color.getBlue() * color.getAlpha() + logoColor.getBlue() * (255 - color.getAlpha())) / 510;
                                buffImage.setRGB(i, j, newColor);
                            }
                        } else if (keepColor == 2 && i <= logoBuff.getWidth() - 1 && j <= logoBuff.getHeight() - 1) {
                            logoColor = new Color(logoBuff.getRGB(i, j), true);
                            if (logoColor.getAlpha() != 0) {
                                if (color.getRed() + color.getBlue() + color.getGreen() > 400)
                                    min = 255 - Math.min(color.getRed(), Math.min(color.getBlue(), color.getGreen()));
                                else
                                    min = -100 + Math.min(color.getRed(), Math.min(color.getBlue(), color.getGreen()));
                                buffImage.setRGB(i, j, min * 65793);
                            }
                        } else if (keepColor == 3 && i <= logoBuff.getWidth() - 1 && j <= logoBuff.getHeight() - 1) {
                            logoColor = new Color(logoBuff.getRGB(i, j), true);
                            if (logoColor.getAlpha() != 0) {
                                min = -10 + Math.min(color.getRed(), Math.min(color.getBlue(), color.getGreen()));
                                buffImage.setRGB(i, j, min * 65793);
                            }
                        } else if (i <= logoBuff.getWidth() - 1 && j <= logoBuff.getHeight() - 1) {
                            logoColor = new Color(logoBuff.getRGB(i, j), true);
                            if (logoColor.getAlpha() != 0) {
                                if (color.getRed() > 105 && color.getRed() < 160)
                                    R = 0;
                                else
                                    R = 255 - color.getRed();
                                if (color.getGreen() > 105 && color.getGreen() < 160)
                                    G = 0;
                                else
                                    G = 255 - color.getGreen();
                                if (color.getBlue() > 105 && color.getBlue() < 160)
                                    B = 0;
                                else
                                    B = 255 - color.getBlue();

                                buffImage.setRGB(i, j, R * 65536 + G * 256 + B);
                            }
                        }
                    } else if (watermarkPlace == 2) {
                        if (keepColor == 1 && i >= X1 && i < X2 && j >= Y1 && j < Y2) {
                            logoColor = new Color(logoBuff.getRGB(i - X1, j - Y1), true);
                            if (logoColor.getAlpha() != 0) {
                                if (logoColor.getAlpha() > 40)
                                    newColor = logoColor.getRGB();
                                else
                                    newColor = (color.getRed() * color.getAlpha() + logoColor.getRed() * (255 - color.getAlpha())) / 510 * 65536 + (color.getGreen() * color.getAlpha() + logoColor.getGreen() * (255 - color.getAlpha())) / 510 * 256 + (color.getBlue() * color.getAlpha() + logoColor.getBlue() * (255 - color.getAlpha())) / 510;
                                buffImage.setRGB(i, j, newColor);
                            }
                        } else if (keepColor == 2 && i >= X1 && i < X2 && j >= Y1 && j < Y2) {
                            logoColor = new Color(logoBuff.getRGB(i - X1, j - Y1), true);
                            if (logoColor.getAlpha() != 0) {
                                min = -10 + Math.min(color.getRed(), Math.min(color.getBlue(), color.getGreen()));
                                buffImage.setRGB(i, j, min * 65793);
                            }
                        } else if (keepColor == 3 && i >= X1 && i < X2 && j >= Y1 && j < Y2) {
                            logoColor = new Color(logoBuff.getRGB(i - X1, j - Y1), true);
                            if (logoColor.getAlpha() != 0) {
                                if (color.getRed() + color.getBlue() + color.getGreen() > 600)
                                    min = 255 - Math.min(color.getRed(), Math.min(color.getBlue(), color.getGreen()));
                                else
                                    min = -100 + Math.min(color.getRed(), Math.min(color.getBlue(), color.getGreen()));
                                buffImage.setRGB(i, j, min * 65793);
                            }
                        } else if (i >= X1 && i < X2 && j >= Y1 && j < Y2) {
                            logoColor = new Color(logoBuff.getRGB(i - X1, j - Y1), true);
                            if (logoColor.getAlpha() != 0) {
                                if (color.getRed() > 105 && color.getRed() < 160)
                                    R = 0;
                                else
                                    R = 255 - color.getRed();
                                if (color.getGreen() > 105 && color.getGreen() < 160)
                                    G = 0;
                                else
                                    G = 255 - color.getGreen();
                                if (color.getBlue() > 105 && color.getBlue() < 160)
                                    B = 0;
                                else
                                    B = 255 - color.getBlue();

                                buffImage.setRGB(i, j, R * 65536 + G * 256 + B);
                            }
                        }
                    } else if (watermarkPlace == 3) {
                        if (keepColor == 1 && i >= X1 && j > Y1) {
                            logoColor = new Color(logoBuff.getRGB(i - X1, j - Y1), true);
                            if (logoColor.getAlpha() != 0) {
                                if (logoColor.getAlpha() > 40)
                                    newColor = logoColor.getRGB();
                                else
                                    newColor = (color.getRed() * color.getAlpha() + logoColor.getRed() * (255 - color.getAlpha())) / 510 * 65536 + (color.getGreen() * color.getAlpha() + logoColor.getGreen() * (255 - color.getAlpha())) / 510 * 256 + (color.getBlue() * color.getAlpha() + logoColor.getBlue() * (255 - color.getAlpha())) / 510;
                                buffImage.setRGB(i, j, newColor);
                            }
                        } else if (keepColor == 2 && i >= X1 && j > Y1) {
                            logoColor = new Color(logoBuff.getRGB(i - X1, j - Y1), true);
                            if (logoColor.getAlpha() != 0) {
                                min = -10 + Math.min(color.getRed(), Math.min(color.getBlue(), color.getGreen()));
                                buffImage.setRGB(i, j, min * 65793);
                            }
                        } else if (keepColor == 3 && i >= X1 && j > Y1) {
                            logoColor = new Color(logoBuff.getRGB(i - X1, j - Y1), true);
                            if (logoColor.getAlpha() != 0) {
                                if (color.getRed() + color.getBlue() + color.getGreen() > 600)
                                    min = 255 - Math.min(color.getRed(), Math.min(color.getBlue(), color.getGreen()));
                                else
                                    min = -100 + Math.min(color.getRed(), Math.min(color.getBlue(), color.getGreen()));
                                buffImage.setRGB(i, j, min * 65793);
                            }
                        } else if (i >= X1 && j > Y1) {
                            logoColor = new Color(logoBuff.getRGB(i - X1, j - Y1), true);
                            if (logoColor.getAlpha() != 0) {
                                if (color.getRed() > 105 && color.getRed() < 160)
                                    R = 0;
                                else
                                    R = 255 - color.getRed();
                                if (color.getGreen() > 105 && color.getGreen() < 160)
                                    G = 0;
                                else
                                    G = 255 - color.getGreen();
                                if (color.getBlue() > 105 && color.getBlue() < 160)
                                    B = 0;
                                else
                                    B = 255 - color.getBlue();

                                buffImage.setRGB(i, j, R * 65536 + G * 256 + B);
                            }
                        }
                    }
                }
            if (this.segmentVlaue == 16) {
                while (ImageFilterWatermark.nrOfThreadsDone < 15)
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        System.out.println("Error: Thread.sleep(10) (ImageFilterWatermark)");
                        e.printStackTrace();
                    }
                    end = (int) System.currentTimeMillis();
                    ImageWriterClass imageWriterClass = new ImageWriterClass(imagePaths, buffImage, end - start, "ImageFilterWatermark", "position: " + String.valueOf(this.imagePaths.getWatermarkPlace()) + " type: " + String.valueOf(this.imagePaths.getKeepColor()), imageView);
                    imageWriterClass.writeImage();
            }
            end = (int) System.currentTimeMillis();
            this.setExecTime(end - start);
            ImageFilterWatermark.nrOfThreadsDone++;
        } catch (IOException e) {
            System.out.println("Error in thread: " + this.segmentVlaue);
            e.printStackTrace();
        }
    }
}
