import java.util.ArrayDeque;

/*
 * TODO
 * - Javadoc
 * - Comments
 */

public class PTTStringsMap<V> implements StringsMap<V> {

	private Node<V> root;
	private int size;



	public void put(String key, V value){

		if(root == null) {

			root = putAux(key, value, 0);
			size++;

		} else {

			putAux(root, 0, key, value);

		}

	}

	private Node<V> putAux(String keyChar, V value, int i) {

		if(i < keyChar.length() - 1){

			return new Node<>(keyChar.charAt(i), null, null, putAux(keyChar, value, i+1), null);

		} else {

			return new Node<>(keyChar.charAt(i), value, null, null, null);

		}
	}

	private void putAux(Node<V> node, int index, String key, V value) {

		if (Character.compare(node.caracter, key.charAt(index)) == 0) {

			if (index == key.length() - 1) {

				node.value = value;
				return;

			} else if (node.mid == null) {

				node.mid = putAux(key, value, index);
				size++;
				return;

			}

			putAux(node.mid, index + 1, key, value);

		} else if (Character.compare(node.caracter, key.charAt(index)) > 0) {

			if (node.left == null) {

				node.left = putAux(key, value, index);
				size++;
				return;

			}

			putAux(node.left, index, key, value);

		} else {

			if (node.right == null) {

				node.right = putAux(key, value, index);
				size++;
				return;

			}

			putAux(node.right, index, key, value);

		}

	}

	public boolean containsKey(String key) {

		return !key.equals("") && find(root, 0, key) != null;

	}

	private Node<V> find(Node<V> node, int index, String key) {

		//If the caracter is the same then we'll continue down
		if (Character.compare(node.caracter, key.charAt(index)) == 0) {

			/*If we checked all the characters and the value on node is not null
			 * (Meaning it's the end of key) then we return the node
			 */

			if (index == key.length() - 1 && node.value != null) {

				return node;

			}

			/*If we reached the end of the key without finding any match or
			 *if the node below it is null (means that there's no other caracters left to search)
			 */

			if (index == key.length() - 1 || node.mid == null) {

				return null;

			}

			/*
			 * Move to the node below it
			 */

			return find(node.mid, index + 1, key);

		} else if (Character.compare(node.caracter, key.charAt(index)) > 0) { //left node

			if (node.left == null) return null;

			return find(node.left, index, key);

		} else { //right node

			if (node.right == null) return null;

			return find(node.right, index, key);

		}

	}

	public V get(String key) {

		Node<V> result = find(root, 0, key);

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


	public Iterable<String> keysStartingWith(String pref) {

		ArrayDeque<String> lista = new ArrayDeque<>();

		Node<V> node = keysStartingWithAux(pref, 0, root);

		if (node != null && node.mid != null)

			keysAux(node.mid, pref, lista);

		return lista;

	}

	private Node<V> keysStartingWithAux(String pref, int index, Node<V> node) {

		if (Character.compare(node.caracter, pref.charAt(index)) == 0) {

			/*If we checked all the characters and the value on node is not null
			 * (Meaning it's the end of key) then we return the node
			 */

			if (index == pref.length() - 1) {

				return node;

			}

			/*If we reached the end of the key without finding any match or
			 *if the node below it is null (means that there's no other caracters left to search)
			 */

			if (node.mid == null) {

				return null;

			}

			/*
			 * Move to the node below it
			 */

			return keysStartingWithAux(pref, index + 1, node.mid);

		} else if (Character.compare(node.caracter, pref.charAt(index)) > 0) { //left node

			if (node.left == null) return null;

			return keysStartingWithAux(pref, index, node.left);

		} else { //right node

			if (node.right == null) return null;

			return keysStartingWithAux(pref, index, node.right);

		}

	}

	@SuppressWarnings("unchecked")
	public boolean equals (Object object) {

		return this == object || (object instanceof PTTStringsMap && (((PTTStringsMap<V>) object).size() == this.size)
				&& equalsAux((PTTStringsMap<V>)object));

	}

	private boolean equalsAux (PTTStringsMap<V> object) {

		Iterable<String> keys = this.keys();

		V firstValue;
		V secondValue;

		for (String string : keys) {

			firstValue = this.get(string);
			secondValue = object.get(string);

			if (secondValue == null || !firstValue.equals(secondValue)) {

				return false;
			}
		}

		return true;
	}


	public String toString() {

		StringBuilder result = new StringBuilder();
		
		toStringAux(root, 0, result);
		
		return result.toString();
		
	}

	private void toStringAux(Node<V> node, int index, StringBuilder sb) {

		sb.append("Char: " + node.caracter + "; Value: " + node.value + "; Depth: " + index + ";\n");

		if (node.left != null) {

			toStringAux(node.left, index + 1, sb);

		}

		if (node.mid != null) {

			toStringAux(node.mid, index + 1, sb);

		}

		if (node.right != null) {

			toStringAux(node.right, index + 1, sb);

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
		mapa.put("Larry is gay for Swood", 69);
		mapa.put("Kol is kool", 420);
	
		
		System.out.println(mapa.toString());
	}
}
