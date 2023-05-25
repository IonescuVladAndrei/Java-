# Image Editor in Java for jpg files with a JavaFX GUI

## Which are the main aspects of my project?

- it uses 16 threads to apply a filter in order to cut down on time
- you can choose which filter you want to apply to the image from the gui
- you can apply multiple filters without having to move the file from the output folder to the input folder
- you can choose to copy the metadata from the input file, keeping details from the camera
- except for the gui, it uses only java native libraries, this includes the calculations done to each pixel (hence why I've decided to go with 16 threads)
- for the 20 filters, I've used the Factory design pattern, which means there is no endless "if else" and adding a new filter doesn't intervene with the rest of them
- lossy compression is set to 0 so no quality is lost
- I've coded various methods for checking paths so the program doesn't break if the input files aren't found (this includes the paths for the src folders)

` If you want to see a quick demonstration, you can check out: `

` If you want to see some of the images, you can check out `

## Installation
### If you want to try it out, you'll need the following:
- Visual Studio Code
- Extension Pack for Java (VSC extension)
- I work with jdk 17, haven't tested it on older versions so this is what I recommended
- javafx-sdk-20 (https://gluonhq.com/products/javafx/) 
- Run the code once from src/Gui/Main.java for VSC to create its own configuration in the launch.json file
- Next you'll need to go the top configuration which has "projectName": "ImageEditorV3WithGUI_ + a new hash" and add the following line
"vmArgs": "--module-path path_to_where_javafx-sdk-20_was_downloaded --add-modules javafx.controls,javafx.fxml"

