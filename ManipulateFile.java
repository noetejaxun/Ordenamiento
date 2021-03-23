import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ManipulateFile {
	
	public void showContent(String file) throws FileNotFoundException, IOException {

		try {
			String content;
			FileReader fileRead = new FileReader(file);
			BufferedReader buffer = new BufferedReader(fileRead);

			while ((content = buffer.readLine()) != null) {
				System.out.println(content);
			}
			buffer.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}

	}

	public static void writeContent(String path, String content) throws FileNotFoundException, IOException {

		try {
			File file = new File(path);
			FileWriter write = new FileWriter(file, true);
			
			write.write(content + "\r\n");
			write.close();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}

	}
		
}