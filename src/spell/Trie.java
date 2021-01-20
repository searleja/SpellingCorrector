package spell;

import java.util.*;

public class Trie implements ITrie{
  Node root = new Node();
  int wordCount;
  int nodeCount=1; //1 because root is a node
  int hashCode;

  /**
   * Adds the specified word to the trie (if necessary) and increments the word's frequency count.
   *
   * @param word the word being added to the trie
   */
  @Override
  public void add(String word) {
    addHelper(root, word, 0); //need to do recursively in order to go through children
  }
      //NOTE -- if there are issues with my trie, test the final character in words being put in with this recursion
  private void addHelper(Node current, String word, int index) {
    char c = word.charAt(index); //this cleans up a lot of code
    if (current.children[c - 'a'] == null) { //no existing words with this letter
      current.children[c - 'a']=new Node();
      nodeCount++;
    }
    if (index == word.length() - 1) {
      if (current.children[c-'a'].getValue() == 0) wordCount++; //first occurence of a word
      current.children[c - 'a'].incrementValue();
      return; //end recursion
    }
    addHelper(current.children[c - 'a'], word, index + 1);
  }

  /**
   * Searches the trie for the specified word.
   *
   * @param word the word being searched for.
   *
   * @return a reference to the trie node that represents the word,
   * 			or null if the word is not in the trie
   */
  @Override
  public INode find(String word) {
    return findHelper(root, word, 0);
  }

  private INode findHelper(Node current, String word, int index) {
    if (index >= word.length() || current.children[word.charAt(index) - 'a'] == null) return null; //word is not in the trie

    char c = word.charAt(index); //this cleans up a lot of code
    if (index == word.length() - 1 && current.children[c-'a'] != null && current.children[c-'a'].getValue() > 0) return current.children[c-'a'];
    return findHelper(current.children[c-'a'], word, index + 1);
  }

  /**
   * Returns the number of unique words in the trie.
   *
   * @return the number of unique words in the trie
   */
  @Override
  public int getWordCount() {
    return wordCount;
  }

  /**
   * Returns the number of nodes in the trie.
   *
   * @return the number of nodes in the trie
   */
  @Override
  public int getNodeCount() {
    return nodeCount;
  }


  /**
   * The toString specification is as follows:
   * For each word, in alphabetical order:
   * <word>\n
   * MUST BE RECURSIVE.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    StringBuilder words = new StringBuilder();
    words = toStringHelper(root, sb, words);
    words.setLength(words.length() - 1); //remove last new line
    return words.toString();
  }

  private StringBuilder toStringHelper(Node current, StringBuilder sb, StringBuilder words) {
    for (int i = 0; i < current.children.length; i++) {
      if (current.children[i] != null) {
        sb.append((char) (i+'a'));
        if (current.children[i].getValue() > 0) {
          words.append(sb.toString() + '\n');
        }

        words = toStringHelper(current.children[i], sb, words);
        if (sb.length() > 0) sb.setLength(sb.length() - 1); //to move onto next character in the array
      }
    }
    return words;
  }

  /**
   * Returns the hashcode of this trie.
   * MUST be constant time.
   * @return a uniform, deterministic identifier for this trie.
   */
  @Override
  public int hashCode() {
    hashCode = wordCount * nodeCount;
    for (int i = 0; i < root.children.length; i++) {
      if (root.children[i] != null) {
        if (i != 0) hashCode *= i;
      }
    }
    return hashCode;
  }

  /**
   * Checks if an object is equal to this trie.
   * MUST be recursive.
   * @param o Object to be compared against this trie
   * @return true if o is a Trie with same structure and node count for each node
   * 		   false otherwise
   */
  @Override
  public boolean equals(Object o) {
    Trie test = (Trie) o; //to make o a trie instead of Object
    return equalsHelper(root, test.root);
  }

  private boolean equalsHelper(Node current, Node test) {
    boolean intermediate = true; //if this becomes false, there were a different amount of a specific word between tries
    for (int i = 0; i < 26; i++) {
      if (current.children[i] != null && test.children[i] != null) {
        if (current.children[i].getValue() != test.children[i].getValue()) return false;
        intermediate = equalsHelper(current.children[i], test.children[i]);
      }
      else if (current.children[i] == null && test.children[i] == null) {} //do nothing
      else return false; //one has a value and the other does not
      if (!intermediate) return false;
    }
    return true;
  }

}
