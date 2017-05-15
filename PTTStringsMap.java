import java.util.ArrayDeque;
import java.util.Iterator;

/*
 * TODO
 * 
 * - Implementar o size, o Iterable
 * - Implementar o resto dos métodos
 * - Javadoc
 * - Comentários maybe
 */

public class PTTStringsMap<V> implements StringsMap<V> {

	private Node<V> root;
	private int size;


	public void put(String key, V value){

		char[] keyChar = key.toLowerCase().toCharArray();

		if(root == null) {

			root = putAux(keyChar, value, 0);

		} else {

			putAux(root, 0, keyChar, value);

		}

	}

	private Node<V> putAux(char[] keyChar, V value, int i) {

		if(i < keyChar.length-1){

			return new Node<>(keyChar[i], null, null, putAux(keyChar, value, i+1), null);

		} else {

			return new Node<>(keyChar[i], value, null, null, null);

		}
	}

	private void putAux(Node<V> node, int index, char[] key, V value) {

		if (Character.compare(node.caracter, key[index]) == 0) {

			if (node.mid == null && index == key.length - 1) {

				node.value = value;
				return;

			} else if (node.mid == null) {

				node.mid = putAux(key, value, index);
				return;

			}

			putAux(node.mid, index + 1, key, value);

		} else if (Character.compare(node.caracter, key[index]) > 0) {

			if (node.left == null) {

				node.left = putAux(key, value, index);
				return;

			}

			putAux(node.left, index, key, value);

		} else {

			if (node.right == null) {

				node.right = putAux(key, value, index);
				return;

			}

			putAux(node.right, index, key, value);

		}

	}

	public boolean containsKey(String key) {

		if (key.equals("")) return false;

		char[] keyChar = key.toLowerCase().toCharArray();

		return find(root, 0, keyChar) != null;

	}

	private Node<V> find(Node<V> node, int index, char[] key) {

		//If the caracter is the same then we'll continue down
		if (Character.compare(node.caracter, key[index]) == 0) {

			/*If we checked all the characters and the value on node is not null
			 * (Meaning it's the end of key) then we return the node
			 */

			if (index == key.length - 1 && node.value != null) {

				return node;

			}

			/*If we reached the end of the key without finding any match or
			 *if the node below it is null (means that there's no other caracters left to search)
			 */

			if (index == key.length - 1 || node.mid == null) {

				return null;

			}

			/*
			 * Move to the node below it
			 */

			return find(node.mid, index + 1, key);

		} else if (Character.compare(node.caracter, key[index]) > 0) { //left node

			if (node.left == null) return null;

			return find(node.left, index, key);

		} else { //right node

			if (node.right == null) return null;

			return find(node.right, index, key);

		}

	}

	public V get(String key) {

		char[] keyChar = key.toLowerCase().toCharArray();

		Node<V> result = find(root, 0, keyChar);

		if (result == null) {

			return null;

		} else {

			return result.value;

		}
	}

	public int size() {

		return size;
	}

	public Iterable<String> keys() {

		ArrayDeque<String> lista = new ArrayDeque<>();

		keysAux(root, "", lista);

		return lista;

	}

	private void keysAux(Node<V> node, String key, ArrayDeque<String> lista) {

		if (node.left != null) {

			keysAux(node.left, key, lista);

		}

		if (node.right != null) {

			keysAux(node.right, key, lista);

		}
		
		if (node.value == null && node.mid != null) {
			
			key += node.caracter;
			
			keysAux(node.mid, key, lista);
			
		} else if (node.value != null && node.mid != null) {
			
			key += node.caracter;
			
			lista.add(key);
			
			keysAux(node.mid, key, lista);
			
		} else if (node.value != null && node.mid == null) {
			
			key += node.caracter;
			
			lista.add(key);
			
		}

	}

	private static class Node<V> {

		private char caracter;
		private V value;
		private Node<V> left;
		private Node<V> mid;
		private Node<V> right;

		public Node (char caracter, V value, Node<V> left, Node<V> mid, Node<V> right) {

			this.caracter = caracter;
			this.left = left;
			this.right = right;
			this.mid = mid;
			this.value = value;

		}
	}


	public static void main(String[] args) {

		PTTStringsMap<Integer> mapa = new PTTStringsMap<>();

		mapa.put("abc", 3);
		mapa.put("abf", 5);
		mapa.put("abc", 69);
		mapa.put("lul", 6);
		mapa.put("aaa", 7);

		Iterable<String> iter = mapa.keys();
	
		
		for (String string : iter) {
			
			System.out.println(string);
			
		}
	}
}
