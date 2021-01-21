package spell;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

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
    inputWord = inputWord.toLowerCase();
    String finalSuggestion = null;

    if (myTrie.find(inputWord) != null) { //word exists in the trie
      return inputWord;
    }
    ArrayList<String> candidates = new ArrayList<>();
    candidates = deletion(candidates, inputWord);
    candidates = insertion(candidates, inputWord);
    candidates = transposition(candidates, inputWord);
    candidates = alteration(candidates, inputWord);

    ArrayList<String> validWords = new ArrayList<>();
    for (int i = 0; i < candidates.size(); i++) {
      if (myTrie.find(candidates.get(i)) != null) {
        validWords.add(candidates.get(i));
      }
    }

    int max = 0;
    for (int i = 0; i < validWords.size(); i++) {
      if (myTrie.find(validWords.get(i)).getValue() > max) {
        max = myTrie.find(validWords.get(i)).getValue();
        finalSuggestion = validWords.get(i);
      }
    }
    if (finalSuggestion != null) return finalSuggestion;

    //second time
    ArrayList<String> secondCandidates = new ArrayList<>();
    for (String word : candidates) {
      secondCandidates=deletion(secondCandidates, word);
      secondCandidates=insertion(secondCandidates, word);
      secondCandidates=transposition(secondCandidates, word);
      secondCandidates=alteration(secondCandidates, word);
    }

    validWords = new ArrayList<>();
    for (int i = 0; i < secondCandidates.size(); i++) {
      if (myTrie.find(secondCandidates.get(i)) != null) {
        validWords.add(secondCandidates.get(i));
      }
    }

    max = 0;
    for (int i = 0; i < validWords.size(); i++) {
      if (myTrie.find(validWords.get(i)).getValue() > max) {
        max = myTrie.find(validWords.get(i)).getValue();
        finalSuggestion = validWords.get(i);
      }
    }

    return finalSuggestion;
  }

  private ArrayList<String> deletion(ArrayList<String> candidates, String word) {
    for (int i = 0; i < word.length(); i++) {
      StringBuilder sb = new StringBuilder(word);
      sb.deleteCharAt(i);
      candidates.add(sb.toString());
    }
    return candidates;
  }

  private ArrayList<String> transposition(ArrayList<String> candidates, String word) {
    for (int i = 0; i < word.length() - 1; i++) {
      StringBuilder sb = new StringBuilder(word);
      sb.setCharAt(i, word.charAt(i+1));
      sb.setCharAt(i+1, word.charAt(i));
      candidates.add(sb.toString());
    }
    return candidates;
  }

  private ArrayList<String> alteration(ArrayList<String> candidates, String word) {
    for (int i = 0; i < word.length(); i++) {
      for (int j = 0; j < 26; j++) {
        StringBuilder sb=new StringBuilder(word);
        sb.setCharAt(i, (char)(j+'a'));
        candidates.add(sb.toString());
      }
    }
    return candidates;
  }

  private ArrayList<String> insertion(ArrayList<String> candidates, String word) {
    for (int i = 0; i <= word.length(); i++) {
      for (int j = 0; j < 26; j++) { //each letter of the alphabet
        StringBuilder sb = new StringBuilder(word);
        sb.insert(i, (char)(j+'a'));
        candidates.add(sb.toString());
      }
    }
    return candidates;
  }



}
