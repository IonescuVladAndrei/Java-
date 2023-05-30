package packWork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImagePath {

	private String path;

	public ImagePath() {
		this.path = null;
	}

	public ImagePath(String path) {
		this.path = path;
	}

	public void setPath(int act) throws IOException {
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		if (act == 0) {
			System.out.println("Introduceti numele fisierului de intrare(inclusiv extensia): ");
			
			this.path = reader.readLine();
			Path newPath = Paths.get("Images/input/" + this.path);
			while(!Files.exists(newPath)){
				System.out.println("Numele fisierului introdus anterior nu a fost gasit. Reintroduceti: ");
				this.path = reader.readLine();
				newPath = Paths.get("input/" + this.path);
			}
			this.path = newPath.toAbsolutePath().toString();
			System.out.println("Am gasit fisierul de intrare in: " + this.path);
		} else {
			System.out.println("Alegeti o denumire pentru numele fisierului de iesire(inclusiv .png): ");
			Path newPath = Paths.get("Images/output/" + reader.readLine());
			if(Files.exists(newPath))
				System.out.println("(cel putin merge)");
			this.path = newPath.toAbsolutePath().toString();
			System.out.println("Am setat fisierul de iesire: " + this.path);	
		}
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String newPath) {
		this.path = newPath;
	}
}
