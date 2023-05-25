# Image Editor in Java for jpg files with a JavaFX GUI

## Which are the main aspects of my project?

- it uses 16 threads to apply a filter in order to cut down on time
- you can choose which filter you want to apply to the image from the gui (it's important to note that only one filter can be applied at a time)
- you can apply multiple filters consecutively without having to move the file from the output folder to the input folder
- you can choose to copy the metadata from the input file, keeping details from the camera
- except for the gui, it uses only java native libraries, this includes the calculations done to each pixel (hence why I've decided to go with 16 threads)
- for the 20 filters, I've used the Factory design pattern, which means there is no endless "if else" and adding a new filter doesn't intervene with the rest of them
- lossy compression is set to 0 so no quality is lost
- I've coded various methods for checking paths so the program doesn't break if the input files aren't found (this includes the paths for the src folders)

` If you want to see a quick demonstration, you can check out: `

` If you want to see some of the images, you can check out: `

## Installation
### If you want to try it out, you'll need the following:
- Visual Studio Code
- Extension Pack for Java (VSC extension)
- I work with jdk 17, haven't tested it on older versions so this is what I recommended
- javafx-sdk-20 (https://gluonhq.com/products/javafx/) 
- Run the code once from src/Gui/Main.java for VSC to create its own configuration in the launch.json file
- Next you'll need to go the top configuration which has "projectName": "ImageEditorV3WithGUI_ + a new hash" and add the following line
"vmArgs": "--module-path path_to_where_javafx-sdk-20_was_downloaded --add-modules javafx.controls,javafx.fxml"

## GUI
As previously mentioned, the gui was made using javafx. The control elements are: buttons, check-boxes, radio-buttons, text-fields, one slider and one combobox.
The "Input image name", "Output image name" and "Watermark" labels change color as well as text after pressing the Start button. So if the input image isn't found or 
the incorrect extension is typed, the labels will change color from black to red and the text will become "Error: Image not found/not jpg!". Nonetheless, if the file is found 
and it's a jpg, the text will change to blue and "Image found.". For the output text-field as well as the watermark we have similar behaviour. This is achieved using the Controller java file. The slider is another element with it's own unique behaviour. You can drag it up and down (the red 0 will get swapped for the new value) changing the parameter for 8 filters. 
The styling is accomplished using a css file. Buttons have padding, are rounded with colors swapping when hovering over or pressing them. Both the text-boxes and buttons have shadows for a better look. When pressing the exit button or closing the window you'll be greeted with a smaller window (an allert) that also uses css styling for different text, buttons and image. Another element which is not visibile at first is an image-view. When pressing the Start button, the input image will be shown for a short time untill the process is completed and gets replaced by the new image.

## Applying filters

EditorMain is the java file which receives the paths for the input, output and watermark images, their presence being confirmed in the Controller class.
Each thread receives 
In packImageSizeFactory we have another Factory with classes returning the width and height (start and end) for each segment relative to the image.
