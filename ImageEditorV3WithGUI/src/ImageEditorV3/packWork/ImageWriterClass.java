package ImageEditorV3.packWork;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.io.File;
import javax.imageio.stream.FileImageOutputStream;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

public class ImageWriterClass {
    private ImagePaths imagePaths;
    private int timeTaken;
    private String filterName;
    private ImageView imageView;
    private BufferedImage bufferedImage;
    private String filterValue;

    public ImageWriterClass(ImagePaths imagePaths, BufferedImage bufferedImage, int timeTaken, String filterName, String filterValue, ImageView imageView) {
        this.imagePaths = imagePaths;
        this.bufferedImage = bufferedImage;
        this.timeTaken = timeTaken;
        this.filterName = filterName;
        this.imageView = imageView;
        this.filterValue = filterValue;
    }

    public void writeImage() throws IOException {
        int start = (int) System.currentTimeMillis(), end;
        Path path = Paths.get(this.imagePaths.getPathInitialImage());
        ImageReader reader = ImageIO.getImageReaders(ImageIO.createImageInputStream(path.toFile())).next();
        reader.setInput(ImageIO.createImageInputStream(path.toFile()), true);
        IIOMetadata metadata = reader.getImageMetadata(0);
        JPEGImageWriteParam jpegParams = new JPEGImageWriteParam(null);
        jpegParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        jpegParams.setCompressionQuality(1f);// max
        ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();
        path = Paths.get(imagePaths.getPathFinalImage());
        writer.setOutput(new FileImageOutputStream(new File("src/ImageEditorV3/Images/Temp/" + path.getFileName())));
        jpegParams.setOptimizeHuffmanTables(true);
        if (this.imagePaths.getKeepMetadata())
            writer.write(null, new IIOImage(bufferedImage, null, metadata), jpegParams);
        else
            writer.write(null, new IIOImage(bufferedImage, null, null), jpegParams);
        writer.dispose();
        reader.dispose();
        System.out.println("New image has successfully been saved!");
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream("src/ImageEditorV3/Images/Temp/" + path.getFileName());
            os = new FileOutputStream(path.toFile());
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
        // 2 seconds delay because although the file has been written in memory,
        // either java or javafx needs a bit of a delay before being able to read it
        // on slower computers/laptops, the delay will need to be higher
        try {
            System.out.println("Gotta wait a little");
            TimeUnit.MILLISECONDS.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        path = Paths.get("src/ImageEditorV3/Images/Temp/log.txt");
        if (!Files.exists(path)) {
            System.out.println("Creating log file.");
            Path newFilePath = Paths.get(path.toAbsolutePath().toString());
            try {
                Files.createFile(newFilePath);
            } catch (Exception e) {
                System.out.println("Error: couldn't create the log file!");
                System.out.println(e);
            }
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime timeNow = LocalDateTime.now();
        end = (int) System.currentTimeMillis();
        String logText = dtf.format(timeNow).toString() + " | Filter used: " + this.filterName + " | Filter value: " + this.filterValue + " | Time taken: " + String.valueOf((double) (this.timeTaken + end - start) / 1000) + " s | Nr. of pixels iterated through: " + String.valueOf(this.bufferedImage.getWidth() * this.bufferedImage.getHeight()) + "\n";
        Files.write(path, logText.getBytes(), StandardOpenOption.APPEND);
        path = Paths.get(this.imagePaths.getPathFinalImage());
        imageView.setImage(new Image("ImageEditorV3/Images/Output/" + path.getFileName()));
    }
}
