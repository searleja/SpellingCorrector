package spell;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SpellCorrector implements ISpellCorrector {
  Trie myTrie = new Trie();

  /**
   * Tells this <code>SpellCorrector</code> to use the given file as its dictionary
   * for generating suggestions.
   * @pre SpellCorrector will have had empty-param constructor called, but dictionary has nothing in it.
   * @param dictionaryFileName the file containing the words to be used
   * @throws IOException If the file cannot be read
   * @post SpellCorrector will have dictionary filled and be ready to suggestSimilarWord any number of times.
   */
  @Override
  public void useDictionary(String dictionaryFileName) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(dictionaryFileName));
    String next;

    while ((next = br.readLine()) != null) {
      next = next.toLowerCase();

      if (next.contains(" ")) { //multiple words in a line
        String[] words = next.split("\\s");
        for (String s : words) {
          myTrie.add(s);
        }
      }
      else { //only one word in the line
        myTrie.add(next);
      }
    }
    System.out.println(myTrie.toString()); //for testing purposes
    br.close();
  }

  /**
   * Suggest a word from the dictionary that most closely matches
   * <code>inputWord</code>.
   * @param inputWord the word we are trying to find or find a suggestion for
   * @return the suggestion or null if there is no similar word in the dictionary
   */
  @Override
  public String suggestSimilarWord(String inputWord) {
    return null;
  }
}
