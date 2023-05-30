# Image processing V2
This project is an upgrade brought to the previous project by adding new feauters and improving the code.

## Improvements
- Better communication between ImageWriter threads which removes the 2 seconds delay
- Class methods now follow encapsulation 
- Dividing the image is now achieved using a Factory method patern(including the new filters)

## Concepts used:
- Multithreading
	 - Producer-Consumer
	 - Multithread Communication(notify)
- Pipe communication
- OOP
- Factory method patern
- Interfaces
- Constructors (including super)
- Reading/writing to files(png)
- Reading input from keyboard

## Project Description
This project aims to showcase OOP as well as multithreading in Java. It is the standalone version(without GUI) written in Java 17.
In the source folder there are 4 packs, each with their own uses.
  - packImageSizeFactory contains 6 classes which are part of a Factory method design pattern. Their purpose is to return the Y and X values for the corners from the input picture which are later on used throughout the program all while avoiding the usual "if else" statements. 
  - packImageConsumer contains 14 classes, which are also part of a Factory method design pattern. Currently there are 12 different filters, however thanks to the design pattern more filters can be added later on without having to modify the main class. I've chosen the filters based on popularity as well as work done for uni. We have the usual greyscale, sepia, negative and mirroring filters as well as brightness, saturation, gama, contrast, red, green and blue filters which take an aditional input value depending on much we want to alterate the output picture.
  - packWork has 4 classes. ImageBuffer allocates memory for a matrix of Color objects and has 2 synchronized methods which are used by the 12 threads on read and write in the matrix. ImagePath is used for the paths of the input and output images. ImageProducer reads the input image and depending on its position, fills a quarter of the matrix from ImageBuffer. Finally, ImageWriter communicates with either of the 13 Filters (ImageConsumers) and writes the image.
  - packTest contains a single class which is the testing one. Here, we get to choose which filter and value we want to apply. It's worth mentioning that multiple filters can be applied to the same image, one after the other. Also, here we instantiate an ImageBuffer object with the image size as well as the 12 threads.


