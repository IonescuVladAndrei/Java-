package packWork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.File;

public class ImagePath {

	private String path;

	public ImagePath() {
		this.path = null;
	}

	public ImagePath(String path) {
		this.path = path;
	}

	public void setPath(int act) throws IOException {
		File directory = new File("src/Images/input/house.png");
        String s = directory.getAbsolutePath();
        System.out.println(s);
		if (act == 0) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Introduceti numele imaginii input (cu tot cu extensie)");
			this.path = reader.readLine();
			this.path = "C:\\Users\\Vlad\\Desktop\\Java dev\\JavaImageEditorV2\\Images\\input\\".concat(this.path);
			System.out.println("Am setat path-ul: " + this.path);
			reader.close();
		
		}else{
			this.path = "C:\\Users\\Vlad\\Desktop\\Java dev\\JavaImageEditorV2\\Images\\output\\output.png";
			System.out.println("Path output: " + this.path);
		} 	
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String newPath) {
		this.path = newPath;
	}
}