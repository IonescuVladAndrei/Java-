package packWork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ImagePath{

	private String path;

    public ImagePath() {
        this.path = null;
    }

    public ImagePath(String path) {
        this.path = path;
    }
    
    public void setPath(int act) throws IOException {
    	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        if(act == 0)
        	{
        		System.out.println("Introduceti numele imaginii input (cu tot cu extensie)");
        		this.path = "C:\\Users\\Vlad\\Desktop\\Java dev\\Images\\input\\";
        	}
        else if (act == 1)
        	{
        		System.out.println("Introduceti numele imaginii output (cu tot cu extensie)");
        		this.path = "C:\\Users\\Vlad\\Desktop\\Java dev\\Images\\output\\";
        	}
        String tempPath =  reader.readLine();
        this.path = this.path.concat(tempPath);     //concatenare destinatie fisier de intrare/iesire
        if (act == 1)
        	reader.close();
        System.out.println("Am setat path-ul: " + this.path);
    }
    
    public String getPath()
    {
    	return this.path;
    }
}