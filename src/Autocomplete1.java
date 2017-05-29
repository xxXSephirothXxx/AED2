import java.io.BufferedReader;
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
				
				BufferedReader leitorFile = new BufferedReader(new FileReader(args[0]));
				String palavra = leitorFile.readLine();

				while (palavra != null) {
					
					mapa.put(palavra, 0);
					palavra = leitorFile.readLine();
					
				}

				leitorFile.close();
				
				Scanner leitor = new Scanner(System.in);

				while (leitor.hasNext()) {

					StopWatch contador = new StopWatch();
					Iterable<String> iter = mapa.keysStartingWith(leitor.nextLine());
					
					for (String string : iter) {
						
						System.out.println(string);
						
					}
					
					System.out.println(contador.elapsedTime());
					
				}
				
			} catch (FileNotFoundException e) {
				
				System.out.println("O ficheiro não foi encontrado");
				
			} catch (IOException e) {

				System.out.println("Erro de I/O");
				
			}

	}

}
