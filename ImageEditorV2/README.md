# Image processing V2
This project is an improvement brought to the first one by adding new feauters and improving the code.

## Improvements
- In etapa de scriere a imaginii a fost scos delay-ul de doua secunde, existand o comunicare mai buna intre statusul threadurilor
- During 
- Metodele claselor respecta conceptul de incapsulare
- Impartirea imaginii in sferturi se realizeaza folosind Factory method patern(inclusiv alegerea filtrului)

## Project Description
This project aims to showcase OOP as well as multithreading in Java. It is the standalone version(without GUI) written in Java 17.
In the source folder there are 4 packs, each with their own uses.
  - packImageSizeFactory contains 6 classes which are part of a Factory method design pattern. Their purpose is to return the Y and X values for the corners from the input picture which are later on used throughout the program all while avoiding the usual "if else" statements. 
  - packImageConsumer contains 15 classes, which are also part of a Factory method design pattern. Currently there are 13 different filters, however thanks to the design pattern more filters can be added later on without having to modify the main class. I've chosen the filters based on popularity as well as work done for uni. We have the usual greyscale, sepia, negative and mirroring filters as well as brightness, saturation, gama, contrast, red, green and blue filters which take an aditional input value depending on much we want to alterate the output picture.There is also a filter called paint, which as the name insinuates, attempts to replicate a painted version of the input picture. This is achieved using an algorithm adapted from a book sourced below. Because of the demanding nature of the algorithm(for each pixel, it searches the most frequent neighbour value in a 10 by 10 radius), the program becomes unstable and it is best suited for a max 1300x900 image. If you want to check out some output images feel free to do so https://drive.google.com/drive/folders/104LUUHGwxGB0ZVEsYvZouFreGlIyVrbr
  - packWork has 4 classes. ImageBuffer allocates memory for a matrix of Color objects and has 2 synchronized methods which are used by the 12 threads on read and write in the matrix. ImagePath is used for the paths of the input and output images. ImageProducer reads the input image and depending on its position, fills a quarter of the matrix from ImageBuffer. Finally, ImageWriter communicates with either of the 13 Filters (ImageConsumers) and writes the image.

## Bibliography
- https://dyclassroom.com/image-processing-project/how-to-convert-a-color-image-into-sepia-image
- https://towardsdatascience.com/image-processing-and-pixel-manipulation-photo-filters-5d37a2f992fa
- https://docs.oracle.com/javase/7/docs/api/java/awt/image/BufferedImage.html#getColorModel()
- https://docs.oracle.com/javase/7/docs/api/java/awt/Color.html
- https://stackoverflow.com/questions/3514158/how-do-you-clone-a-bufferedimage
- https://www.geeksforgeeks.org/ways-to-read-input-from-console-in-java/
- https://cloudinary.com/guides/bulk-image-resize/3-ways-to-resize-images-in-java
- https://www.geeksforgeeks.org/factory-method-design-pattern-in-java/
- Computer Science: An Interdisciplinary Approach by Robert Sedgewick, Kevin Wayne  (for the algorithm used in ImageConsumerPaint)
