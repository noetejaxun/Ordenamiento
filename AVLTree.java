import java.io.IOException;

public class AVLTree<T extends Comparable<T>> implements Tree<T> {

	private Node root;

	@Override
	public void insert(T data) {
		root = insert(root, data);
	}

	@Override
	public void delete(T data) {
		root = delete(root, data);
	}

	private Node<T> insert(Node<T> node, T data) {

		if (node == null) {
			return new Node<T>(data);
		}

		if (data.compareTo(node.getData()) < 0) {
			node.setLeftNode(insert(node.getLeftNode(), data));
		} else {
			node.setRightNode(insert(node.getRightNode(), data));
		}

		node.setHeight(Math.max(height(node.getLeftNode()), height(node.getRightNode())) + 1);

		return settleViolation(data, node);
	}

	private Node<T> delete(Node<T> node, T data) {

		if (node == null)
			return node;

		if (data.compareTo(node.getData()) < 0) { 
			node.setLeftNode(delete(node.getLeftNode(), data));
		} else if (data.compareTo(node.getData()) > 0) { 
			node.setRightNode(delete(node.getRightNode(), data));
		} else {

			if (node.getLeftNode() == null && node.getRightNode() == null) {
				System.out.println("Removing a leaf node...");
				return null;
			}

			if (node.getLeftNode() == null) {
				System.out.println("Removing the right child...");
				Node<T> tempNode = node.getRightNode();
				node = null;
				return tempNode;
			} else if (node.getRightNode() == null) {
				System.out.println("Removing the left child...");
				Node<T> tempNode = node.getLeftNode();
				node = null;
				return tempNode;
			}

			System.out.println("Removing item with two children...");
			Node<T> tempNode = getPredecessor(node.getLeftNode());

			node.setData(tempNode.getData());
			node.setLeftNode(delete(node.getLeftNode(), tempNode.getData()));
		}

		node.setHeight(Math.max(height(node.getLeftNode()), height(node.getRightNode())) + 1);

		return settleDeletion(node);
	}

	private Node<T> settleDeletion(Node<T> node) {

		int balance = getBalance(node);

		if (balance > 1) {

			if (getBalance(node.getLeftNode()) < 0) {
				node.setLeftNode(leftRotation(node.getLeftNode()));
			}

			return rightRotation(node);
		}

		if (balance < -1) {
			if (getBalance(node.getRightNode()) > 0) {
				node.setRightNode(rightRotation(node.getRightNode()));
			}

			return leftRotation(node);
		}

		return node;
	}

	private Node<T> getPredecessor(Node<T> node) {

		Node<T> predecessor = node;

		while (predecessor.getRightNode() != null)
			predecessor = predecessor.getRightNode();

		return predecessor;
	}

	private Node<T> settleViolation(T data, Node<T> node) {

		int balance = getBalance(node);

		// Case 1 (left - left)
		if (balance > 1 && data.compareTo((T) node.getLeftNode().getData()) < 0) {
			System.out.println("Rotación II...");
			return rightRotation(node);
		}

		// Case 2 (right - right)
		if (balance < -1 && data.compareTo((T) node.getRightNode().getData()) > 0) {
			System.out.println("Rotación DD...");
			return leftRotation(node);
		}

		// Case 3 (left right)
		if (balance > 1 && data.compareTo((T) node.getLeftNode().getData()) > 0) {
			System.out.println("Rotación ID...");
			node.setLeftNode(leftRotation(node.getLeftNode()));
			return rightRotation(node);
		}

		// Case 4 (right - left)
		if (balance < -1 && data.compareTo((T) node.getRightNode().getData()) < 0) {
			System.out.println("Rotación DI...");
			node.setRightNode(rightRotation(node.getRightNode()));
			return leftRotation(node);
		}

		return node;
	}

	private Node<T> rightRotation(Node<T> node) {

		System.out.println("Rotando hacia la derecha el nodo: " + node);

		Node<T> tempLeftNode = node.getLeftNode();
		Node<T> t = tempLeftNode.getRightNode();

		tempLeftNode.setRightNode(node);
		node.setLeftNode(t);

		node.setHeight(Math.max(height(node.getLeftNode()), height(node.getRightNode())) + 1);
		tempLeftNode.setHeight(Math.max(height(tempLeftNode.getLeftNode()), height(tempLeftNode.getRightNode())) + 1);

		return tempLeftNode;
	}

	private Node<T> leftRotation(Node<T> node) {

		System.out.println("Rotando a la izquierda el nodo: " + node);

		Node<T> tempRightNode = node.getRightNode();
		Node<T> t = tempRightNode.getLeftNode();

		tempRightNode.setLeftNode(node);
		node.setRightNode(t);

		node.setHeight(Math.max(height(node.getLeftNode()), height(node.getRightNode())) + 1);
		tempRightNode
				.setHeight(Math.max(height(tempRightNode.getLeftNode()), height(tempRightNode.getRightNode())) + 1);

		return tempRightNode;
	}

	private int height(Node<T> node) {

		if (node == null) {
			return -1;
		}

		return node.getHeight();
	}

	private int getBalance(Node<T> node) {

		if (node == null) {
			return 0;
		}

		return height(node.getLeftNode()) - height(node.getRightNode());
	}

	@Override
	public void traverse() {

		if (root == null)
			return;

		inOrderTraversal(root);
	}

	private void inOrderTraversal(Node<T> node) {

		if (node.getLeftNode() != null)
			inOrderTraversal(node.getLeftNode());

		// System.out.println(node);
		try {
			ManipulateFile.writeContent(App.toFile, node.toString());
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}

		if (node.getRightNode() != null)
			inOrderTraversal(node.getRightNode());
	}
}
