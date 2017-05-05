
public class PTTStringsMap<V> implements StringsMap<V> {
	
	private Node<V> root;
	private int size;
	
	
	public void put(String key, V value){

		char[] keyChar = key.toCharArray();

		if(root == null) {

			root = putAux(keyChar, value, 0);

		} else {
			
			find(root, 0, keyChar, value);

		}
	}

	private Node<V> putAux(char[] keyChar, V value, int i) {
		
		if(i < keyChar.length-1){
			
			return new Node<>(keyChar[i], null, null, putAux(keyChar, value, i+1), null);
			
		} else {
			
			return new Node<>(keyChar[i], value, null, null, null);
			
		}
	}

	private void find(Node<V> node, int index, char[] key, V value) {
		
		if (Character.compare(node.caracter, key[index]) == 0) {
			
			if (node.mid == null && index == key.length - 1) {
				
				node.value = value;
				return;
				
			} else if (node.mid == null) {
				
				node.mid = putAux(key, value, index);
				return;
				
			}
			
			find(node.mid, index + 1, key, value);
			
		} else if (Character.compare(node.caracter, key[index]) > 0) {
			
			if (node.left == null) {
				
				node.left = putAux(key, value, index);
				return;
				
			}
			
			find(node.left, index, key, value);
			
		} else {
			
			if (node.right == null) {
				
				node.right = putAux(key, value, index);
				return;
				
			}
			
			find(node.right, index, key, value);
			
		}
		
	}
	
	public boolean containsKey(String key) {
		
		return false;
		
	}

	public V get(String key) {
		
		return null;
	}

	public int size() {
		
		return size;
	}

	public Iterable<String> keys() {
		
		return null;
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

	}
}
