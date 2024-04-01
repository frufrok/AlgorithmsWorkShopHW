package RedNBlack;

public class Node {

    /**
     * The value of the node.
     */
    public int value;

    /**
     * Left child Node entity.
     */
    public Node left;

    /**
     *  Right child Node entity.
     */
    public Node right;

    /**
     * Right child Node entity.
     */
    public Color color;

    /**
     * Return new Node entity with given value and color.
     * @param value the value to set.
     * @param color the color to set.
     */
    Node(int value, Color color) {
        this.value = value;
        this.color = color;
    }

    @Override
    public String toString() {
        return String.format("%d", this.value);
    }
}
