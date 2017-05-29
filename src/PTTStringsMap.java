import java.util.ArrayDeque;
/**
 * An implementation of {@link StringsMap} using a ternary search tree
 * @author fc49449 - Rafael Prates && fc49457 - José Gonçalves
 * 
 * @param <V> The type of the values in the map.
 */

public class PTTStringsMap<V> implements StringsMap<V>, Cloneable {

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

	/**
	 * Returns a series of nodes 
	 * @param keyChar - The key
	 * @param value - The value associated with 
	 * @param index - The index of the String
	 * @return - The starting node
	 * @requires keyChar.length() > 0 && value != null 
	 * 			&& 0 <= index < keyChar.length()
	 */

	private Node<V> putAux(String keyChar, V value, int index) {


		if(index < keyChar.length() - 1){

			// If we haven't reached the end of the key we need to keep making nodes
			// below eachother until we reach the end

			return new Node<>(keyChar.charAt(index), null, null, putAux(keyChar, value, index+1), null);

		} else {

			// Once we reach the end of the key we associate the final character of the
			// key to the Node

			return new Node<>(keyChar.charAt(index), value, null, null, null);

		}
	}

	/**
	 * Traverses the tree with the key given to add the key and it's values
	 * @param node - The current node
	 * @param index - The index of the String
	 * @param key - The key
	 * @param value - The value associated with the given key
	 * @requires node != null && 0 <= index < key.length() && key.length() > 0
	 * 				&& value != null
	 */

	private void putAux(Node<V> node, int index, String key, V value) {

		if (Character.compare(node.caracter, key.charAt(index)) == 0) {

			if (index == key.length() - 1) {

				// If we reached the end of the key all we have to do
				// Is change the value of the key

				node.value = value;
				return;

			} else if (node.mid == null) {

				/* If the Node below the current one is null and we haven't reached the
				 * end of the key we add the rest of the key's characters and increase
				 * the size
				 */

				node.mid = putAux(key, value, index + 1);
				size++;
				return;

			}

			putAux(node.mid, index + 1, key, value);

		} else if (Character.compare(node.caracter, key.charAt(index)) > 0) {

			/*
			 * If the node on the left is null then we have to create a new node
			 * with the remaining of the key and increase the size. Otherwise
			 * we just need to switch to the node on the left and keep going
			 */

			if (node.left == null) {

				node.left = putAux(key, value, index);
				size++;
				return;

			}

			putAux(node.left, index, key, value);

		} else {

			/*
			 * Same reasoning as the above comment except we're looking at the node on
			 * the right
			 */

			if (node.right == null) {

				node.right = putAux(key, value, index);
				size++;
				return;

			}

			putAux(node.right, index, key, value);

		}

	}

	public boolean containsKey(String key) {

		if (root != null) {

			return find(root, 0, key) != null;

		} else {

			return false;

		}

	}

	/**
	 * Returns the node  
	 * @param node - The current Node
	 * @param index - The index of the String
	 * @param key - The key being searched for
	 * @return The node with the final character of the key if it's in the map and it
	 * 			contains a value otherwise returns false
	 * @requires node != null && 0 <= index < key.length() && key.length() > 0
	 */

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
			 *if the node below it is null (means that there's no other characters left to search)
			 */

			if (index == key.length() - 1 || node.mid == null) {

				return null;

			}

			/*
			 * Move to the node below it
			 */

			return find(node.mid, index + 1, key);

		} else if (Character.compare(node.caracter, key.charAt(index)) > 0) { //left node

			if (node.left == null) {

				return null;

			}

			return find(node.left, index, key);

		} else { //right node

			if (node.right == null) {

				return null;

			}

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

		if (root != null) {

			keysAux(root, "", lista);

		}


		return lista;

	}

	/**
	 * Recursively navigate through the tree and form keys and puts them on
	 * an ArrayDeque
	 * @param node - The current node
	 * @param key - The key being formed
	 * @param lista - An ArrayDeque where all the keys should be saved to
	 * @requires node != null &&  key.length() > 0 && lista != null
	 */

	private void keysAux(Node<V> node, String key, ArrayDeque<String> lista) {

		if (node.left != null) {

			keysAux(node.left, key, lista);

		}

		if (node.right != null) {

			keysAux(node.right, key, lista);

		}

		key += node.caracter;

		if(node.value != null) {

			lista.add(key);
		}

		if(node.mid != null) {

			keysAux(node.mid, key, lista);
		}

	}


	/**
	 * Returns an Iterable containing all keys that start with pref
	 * @param pref - The prefix
	 * @return An Iterable containing all keys that start with pref
	 * @requires pref.length() > 0
	 */

	public Iterable<String> keysStartingWith(String pref) {

		ArrayDeque<String> lista = new ArrayDeque<>();

		if (root != null) {

			keysStartingWithAux(pref, 0, root, lista);

		}

		return lista;

	}

	/**
	 * Recursively navigate through the tree adding all keys starting with pref to
	 * an ArrayDeque
	 * @param pref - The prefix
	 * @param index - The current index
	 * @param node - The current node
	 * @param lista - The ArrayDeque to add the keys
	 * @requires 0 <= index < pref.length() && pref.length() > 0 &&
	 * 			node != null && lista != null
	 */

	private void keysStartingWithAux(String pref, int index, Node<V> node, ArrayDeque<String> lista) {

		if (Character.compare(node.caracter, pref.charAt(index)) == 0){

			if(pref.length() - 1 == index){

				if(node.value != null){

					lista.add(pref);

				}

				if(node.mid != null){

					keysAux(node.mid, pref, lista);
				}

			} else if(node.mid != null){

				keysStartingWithAux(pref, index + 1, node.mid, lista);

			}

		} else if (Character.compare(node.caracter, pref.charAt(index)) > 0) {

			if (node.left != null) {

				keysStartingWithAux(pref, index, node.left, lista);

			}

		} else {

			if (node.right != null) {

				keysStartingWithAux(pref, index, node.right, lista);

			}
		}

	}

	@SuppressWarnings("unchecked")
	public boolean equals (Object object) {

		return this == object || (object instanceof PTTStringsMap && (((PTTStringsMap<V>) object).size() == this.size)
				&& equalsAux((PTTStringsMap<V>)object));

	}

	/**
	 * Are these two PTTStringsMap equal?
	 * @param object - The other PTTStringsMap
	 * @return True if they contain the same keys with the same values. False otherwise.
	 */

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

	public PTTStringsMap<V> clone() {

		try {

			@SuppressWarnings("unchecked")
			PTTStringsMap<V> result = (PTTStringsMap<V>) super.clone();

			if (root != null) {

				result.root = cloneAux(root);
			}

			return result;


		} catch (CloneNotSupportedException e) {

			System.out.println("Clone not supported.\n");

		}

		return null;

	}

	/**
	 * Returns a Node deep copied
	 * @param node - The starting node
	 * @return - A Node deep copied
	 */

	private Node<V> cloneAux(Node<V> node) {


		if (node != null) {

			/*
			 * Create a new Node that copies the original node's character and value
			 */

			Node<V> newNode = new Node<>(node.caracter, node.value, null, null, null);

			/*
			 * And create the Nodes below it
			 */

			if (node.mid != null) {

				newNode.mid = cloneAux(node.mid);

			}

			if (node.left != null) {

				newNode.left = cloneAux(node.left);

			}

			if(node.right != null) {

				newNode.right = cloneAux(node.right);

			}

			return newNode;
		}

		return null;
	}


	public String toString() {

		StringBuilder result = new StringBuilder();

		if (root != null) {

			toStringAux(root, result);

		}


		return result.toString();

	}

	/**
	 * Recursively navigate through the tree to give a textual representation of the tree
	 * @param node - The current node
	 * @param sb - The StringBuilder which will hold the key and the value in their textual
	 * 				representation
	 * @requires node != null && sb != null
	 */

	private void toStringAux(Node<V> node, StringBuilder sb) {

		if (node.left != null) {

			toStringAux(node.left, sb);

		}

		if (node.right != null) {


			toStringAux(node.right, sb);
		}

		sb.append(node.caracter);

		if(node.value != null) {


			sb.append("; Value: " + node.value.toString() + "\n");


		}

		if(node.mid != null) {

			toStringAux(node.mid, sb);

		}



	}



	/**
	 * Returns a String that represents a tree in a format useful for debugging
	 * @return A String that that represents the tree
	 */

	public String toStringForDebugging() {

		StringBuilder result = new StringBuilder();

		if (root != null) {

			toStringForDebuggingAux(root, 0, result);

		}

		return result.toString();

	}

	/**
	 * Recursively navigate the tree to create a String representation and
	 * store it on a StringBuilder
	 * @param node - The current node
	 * @param index - The depth index
	 * @param sb - The StringBuilder
	 * @requires node != null && index >= 0 && sb != null
	 */

	private void toStringForDebuggingAux(Node<V> node, int index, StringBuilder sb) {

		sb.append("Char: " + node.caracter + "; Value: " + node.value + "; Depth: " + index + ";\n");

		if (node.left != null) {

			toStringForDebuggingAux(node.left, index + 1, sb);

		}

		if (node.mid != null) {

			toStringForDebuggingAux(node.mid, index + 1, sb);

		}

		if (node.right != null) {

			toStringForDebuggingAux(node.right, index + 1, sb);

		}
	}

	/**
	 * 
	 * @author fc49449 && fc49457
	 *
	 * @param <V> The type of the values.
	 */

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

}
