package RedNBlack;
import java.util.ArrayList;

public class Tree {
    /**
     * Root node of the tree.
     */
    public Node root;

    /**
     * Returns tree node with the maximal value in tree.
     * @return tree node with maximal value
     */
    public Node max() {
        return max(root);
    }

    private Node max(Node node) {
        if (node.right != null) {
            return max(node.right);
        }
        else return node;
    }

    /**
     * Returns tree node with the minimal value in tree.
     * @return tree node with minimal value
     */
    public Node min() {
        return  min(root);
    }

    private Node min(Node node) {
        if (node.left!=null) {
            return  min(node.left);
        }
        else return  node;
    }

    /**
     * Returns node with the given value.
     * @param value value to find in tree.
     * @return node with the given value.
     */
    public Node find(int value) {
        return find(root, value);
    }

    private Node find(Node node, int value) {
        if (node == null) {
            return  null;
        }
        else if (node.value == value) {
            return node;
        }
        else if (node.value < value) {
            return  find(node.right, value);
        }
        else {
            return find(node.left, value);
        }
    }

    /**
     * Inserts the given value into the tree and balances tree.
     * @param value value to insert into the tree.
     * @param print to output insertion and balancing steps to console.
     */
    public void insert(int value, boolean print){
        if (root!=null){
            insert(root, value, print);
            root = balance(root);
            root.color = Color.BLACK;
            if (print) {
                System.out.println("Balancing:");
                System.out.print(this.getTreeString());
            }
        }else{
            root = new Node(value, Color.BLACK);
        }
    }

    private void insert(Node node, int value, boolean print){
        if (node.value<value) {
            if (node.right == null) {
                node.right = new Node(value, Color.RED);
                if (print) {
                    System.out.printf("Inserting value %d:\n", value);
                    System.out.print(this.getTreeString());
                }
            }else{
                insert(node.right, value, print);
                node.right = balance(node.right);
            }
        }
        else if (node.value > value) {
            if (node.left == null){
                node.left = new Node(value, Color.RED);
                System.out.printf("Inserting value %d:\n", value);
                System.out.print(this.getTreeString());
            }
            else {
                insert(node.left, value, print);
                node.left = balance(node.left);
            }
        }
    }

    private Node balance(Node node) {
        boolean flag;
        Node cur = node;
        do {
            flag = false;
            if (cur.right != null && cur.right.color == Color.RED
                    && (cur.left == null || cur.left.color == Color.BLACK)) {
                cur = leftRotate(cur);
                flag = true;
            }

            if (cur.left != null && cur.left.color == Color.RED
                    && cur.left.left != null && cur.left.left.color == Color.RED){
                cur = rightRotate(cur);
                flag = true;
            }

            if (cur.left != null && cur.left.color == Color.RED
                    && cur.right != null && cur.right.color == Color.RED) {
                swapColors(cur);
                flag = true;
            }
        }while (flag);
        return cur;
    }

    private Node leftRotate(Node node) {
        Node cur = node.right;
        node.right = cur.left;
        cur.left = node;
        cur.color = node.color;
        node.color = Color.RED;
        return cur;
    }

    private Node rightRotate(Node node) {
        Node cur = node.left;
        node.left = cur.right;
        cur.right = node;
        cur.color = node.color;
        node.color = Color.RED;
        return cur;
    }

    private void swapColors(Node node) {
        node.color = (node.color == Color.RED ? Color.BLACK : Color.RED);
        node.left.color = Color.BLACK;
        node.right.color = Color.BLACK;
    }

    /**
     * Returns Array representation of the tree.
     * @return Array representation of the tree.
     */
    public ArrayList<Node> getList() {
        ArrayList<Node> nodes = new ArrayList<>();
        addToList(nodes, 0, root);
        return  nodes;
    }

    private void addToList(ArrayList<Node> nodes, int index, Node node) {
        if (nodes.size() < index + 1) {
            for (int i = nodes.size(); i <= index + 1; i++) {
                nodes.add(null);
            }
        }
        nodes.set(index, node);
        if (node.left != null) {
            addToList(nodes, 2 * index + 1, node.left);
        }
        if (node.right != null) {
            addToList(nodes, 2 * index + 2, node.right);
        }
    }

    /**
     * Returns String representation of the tree.
     * @return String representation of the tree.
     */
    public String getTreeString() {
        ArrayList<Node> treeList = this.getList();
        int width = this.getMaxWidth();
        int nodesCount = this.getList().size();
        int treeHeight = (int)Math.ceil(Math.log(nodesCount)/Math.log(2) + 0.001);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < treeHeight; i++) {
            int restHeight = treeHeight - i;
            for (int j = 0; j < Math.pow(2, i); j++){
                int index = ((int)Math.pow(2, i)) - 1 + j;
                if (index < treeList.size()) {
                    Node node = treeList.get(index);
                    int levelWidth = (width + 1) * ((int) Math.pow(2, restHeight - 1));
                    if (index < nodesCount) {
                        sb.append(node == null? " ".repeat(levelWidth)
                                : nodeString(node, levelWidth));
                    }
                }
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    private int getMaxWidth() {
        return Math.max(String.format("%d", this.min().value).length(), String.format("%d", this.max().value).length());
    }

    private static String nodeString(Node node, int length) {
        String value = String.format("%d", node.value);
        int space = length - value.length();
        int strips = length / 2 - value.length() - 1;
        StringBuilder sb = new StringBuilder();
        sb.append(node.color == Color.RED?
                String.format("\033[31m%s\033[0m", value) : value);
        if (space > 0) {
            sb.append(node.left == null? (node.right == null? " ": "_") : "↓");
            sb.append(node.right == null? " ".repeat(space - 1) : "_".repeat(strips) + "↓" + " ".repeat(space - 2 - strips));
        }
        else {
            sb.append(" ".repeat(length - value.length()));
        }
        return sb.toString();
    }
}
