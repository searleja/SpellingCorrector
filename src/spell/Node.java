package spell;

public class Node implements INode {
  Node[] children = new Node[26];
  int value;
  /**
   * Returns the frequency count for the word represented by the node.
   *
   * @return the frequency count for the word represented by the node.
   */
  @Override
  public int getValue() {
    return value;
  }

  /**
   * Increments the frequency count for the word represented by the node.
   */
  @Override
  public void incrementValue() {
    value++;
  }

  /**
   * Returns the child nodes of this node.
   *
   * @return the child nodes.
   */
  @Override
  public INode[] getChildren() {
    return children;
  }
}
