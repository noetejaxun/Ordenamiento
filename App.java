import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class App {
	public static String orderBy;
	public static String fromFile;
	public static String toFile;
	public static Tree<Course> avlTree = new AVLTree<>();
	
	public static void main(String[] args) throws IOException {
		Scanner inputText = new Scanner(System.in);
		ManipulateFile files = new ManipulateFile();
		
		System.out.print("Ingrese la ubicación del archivo de entrada: ");
		fromFile = inputText.nextLine() + "/";
		toFile = fromFile;
		
		System.out.print("Nombre del archivo de entrada: ");
		fromFile = fromFile + inputText.nextLine();
		
		switch (App.chooseOption()) {
		case 1:
			App.orderBy = "Code";
			break;
		case 2:
			App.orderBy = "Name";
			break;
		default:
			App.orderBy = "Code";
			break;
		}
		
		toFile = toFile + "Output_Ordered_By_" + App.orderBy +".txt";
		
		System.out.println("Processing...");
		App.addDataToTree(App.fromFile, App.toFile, App.orderBy);
		App.avlTree.traverse();
		
		files.showContent(toFile);
		System.out.println("Process complete.");
	}
	
	public static int chooseOption() {
		Scanner inputInt = new Scanner(System.in);
		int option;
		
		System.out.println("\nTIPOS DE ORDENAMIENTO:");
		System.out.println("1. Código");
		System.out.println("2. Nombre");
		// System.out.println("3. Carrera");
		System.out.print  ("   Elija una opción... ");
		
		option = inputInt.nextInt();
		if ( !(option > 0 && option < 4) ) {
			return chooseOption();
		} else {
			return option;
		}
	}
	
	public static void addDataToTree(String fromFile, String toFile, String orderBy) throws FileNotFoundException, IOException {

		try {
			String content;
			
			File newFile = new File(toFile);
			
			if (newFile.exists()) {
				System.out.println("This file already exist");
				System.exit(0);
				
			} else {
				FileReader fileRead = new FileReader(fromFile);
				BufferedReader buffer = new BufferedReader(fileRead);

				while ((content = buffer.readLine()) != null) {
					// PIPE(|), semmicolumn(;) and script(-) delimitations to single row
					String[] course = content.split(",");
					
					// Data to insert to the Tree
					String code = course[0].trim();
					code = String.format("%010d", Integer.parseInt(code));
					String name = course[1].trim();
					String section = course[2].trim();
					
					App.orderBy = orderBy;
					avlTree.insert(new Course(code.trim(), name.trim(), section.trim()));
					
				}
				buffer.close();
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}

	}
}