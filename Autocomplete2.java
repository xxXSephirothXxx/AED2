import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Autocomplete2 {

	public static void main(String[] args) {

		/*
		 * Uses HashMap
		 */

		Map<String, Integer> mapa = new HashMap<>();

		try {
			
			BufferedReader leitorFile = new BufferedReader(new FileReader(new File(args[0])));
			String palavra = leitorFile.readLine();

			while (palavra != null) {

				mapa.put(palavra, 0);
				palavra = leitorFile.readLine();

			}

			leitorFile.close();

			Scanner leitor = new Scanner(System.in);
			String prefix = null;

			Set<String> set = mapa.keySet();

			while (leitor.hasNext()) {

				prefix = leitor.nextLine();

				for (String string : set) {

					if (string.startsWith(prefix)) {

						System.out.println(string);

					}

				}

			}

		} catch (FileNotFoundException e) {

			System.out.println("O ficheiro não foi encontrado");

		} catch (IOException e) {

			System.out.println("Erro de I/O");

		}

	}

}
