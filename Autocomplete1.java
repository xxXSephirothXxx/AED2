import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Autocomplete1 {

	public static void main(String[] args) {

		/*
		 * Uses the PTTStringsMap
		 */
		
		PTTStringsMap<Integer> mapa = new PTTStringsMap<>();

			try {
				
				BufferedReader leitorFile = new BufferedReader(new FileReader(new File(args[0])));
				String palavra = leitorFile.readLine();

				while (palavra != null) {
					
					mapa.put(palavra, 0);
					palavra = leitorFile.readLine();
					
				}

				leitorFile.close();
				
				Scanner leitor = new Scanner(System.in);

				while (leitor.hasNext()) {

					for (String string : mapa.keysStartingWith(leitor.nextLine())) {
						
						System.out.println(string);
						
					}
					
				}
				
			} catch (FileNotFoundException e) {
				
				System.out.println("O ficheiro não foi encontrado");
				
			} catch (IOException e) {

				System.out.println("Erro de I/O");
				
			}

	}

}
