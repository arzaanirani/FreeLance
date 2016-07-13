/**
 * Stores info about a word
 */
public class Word {

    String word;
    int wordCount; //Number of times this word has occurred

    /**
     * Construct a new word object
     * @param word  word this object represents
     */
    public Word(String word) {
        this.word = word;
        this.wordCount = 1;
    }

    /**
     * Get the word in this object
     * @return  word
     */
    public String getWord() { return word; }

    /**
     * Get the number of times this word has appeared
     * @return  count of times this word has appeared
     */
    public int getCount() { return wordCount; }

    /**
     * Increment the word count
     */
    public void increment() { wordCount++; }

    /**
     * Convert to string format "word:wordCount"
     * @return  string representation
     */
    public String toString() { return word + ":" + wordCount; }

    /**
     * Determine if this word object is equal to another object
     * @param obj   object to compare
     * @return      true if equal, false otherwise
     */
    public boolean equals(Object obj) {
        if (obj.getClass() == Word.class) {
            if (((Word) obj).word.equals(this.word)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Compare the strings and wordcount
     * @param w Word to compare to
     * @return  Comparison int
     */
    public int compareTo(Word w) {
        if (this.word.compareTo(w.word) == 0) {
            return this.wordCount - w.wordCount;
        } else {
            return this.word.compareTo(w.word);
        }
    }
}
