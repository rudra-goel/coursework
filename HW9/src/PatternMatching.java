import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;


/**
 * Your implementations of various string searching algorithms.
 *
 * @author Rudra Goel
 * @version 1.0
 * @userid rgoel68
 * @GTID 903897740
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class PatternMatching {

    /**
     * Knuth-Morris-Pratt (KMP) algorithm relies on the failure table (also
     * called failure function). Works better with small alphabets.
     *
     * Make sure to implement the buildFailureTable() method before implementing
     * this method.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> kmp(CharSequence pattern, CharSequence text,
                                    CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Pattern cannot be null or of zero length");
        }
        if (comparator == null || text == null) {
            throw new IllegalArgumentException("Comparator and/or text cannot be null");
        }
        List<Integer> matches = new LinkedList<Integer>();
        if (pattern.length() > text.length()) {
            return matches;
        }
        int[] failTable = buildFailureTable(pattern, comparator);
        int j = 0;
        int k = 0;

        while (k <= text.length() - pattern.length()) {
            while (j < pattern.length() && comparator.compare(text.charAt(k + j), pattern.charAt(j)) == 0) {
                j++; //increment pattern check 
            }
            if (j == 0) {
                k++; //incr text check
            } else {
                if (j == pattern.length()) {

                    matches.add(k);
                }
                int offset = failTable[j - 1]; // Get the shift value from the failTable
                k += j - offset; // Move k forward by j - shift
                j = offset; // Set j to shift
            }
        }
        return matches;
    }

    /**
     * Builds failure table that will be used to run the Knuth-Morris-Pratt
     * (KMP) algorithm.
     *
     * The table built should be the length of the input pattern.
     *
     * Note that a given index i will contain the length of the largest prefix
     * of the pattern indices [0..i] that is also a suffix of the pattern
     * indices [1..i]. This means that index 0 of the returned table will always
     * be equal to 0
     *
     * Ex. pattern = ababac
     *
     * table[0] = 0
     * table[1] = 0
     * table[2] = 1
     * table[3] = 2
     * table[4] = 3
     * table[5] = 0
     *
     * If the pattern is empty, return an empty array.
     *
     * @param pattern    a pattern you're building a failure table for
     * @param comparator you MUST use this to check if characters are equal
     * @return integer array holding your failure table
     * @throws java.lang.IllegalArgumentException if the pattern or comparator
     *                                            is null
     */
    public static int[] buildFailureTable(CharSequence pattern,
                                          CharacterComparator comparator) {

        if (pattern == null) {
            throw new IllegalArgumentException("Pattern cannot be null or empty");
        }

        if (comparator == null) {
            throw new IllegalArgumentException("Comparator cannot be null");
        }

        if (pattern.length() == 0) {
            return new int[0];
        }
        
        int[] table = new int[pattern.length()];
        table[0] = 0;

        int i = 0;
        int j = 1;

        while (j < pattern.length()) {
            //if the two chars are equal, then add the num of chars from beginning to table (i+1)
            if (comparator.compare(pattern.charAt(i), pattern.charAt(j)) == 0) {
                //attempt to extend prefix and suffix
                table[j] = i + 1;
                i++;
                j++;
            } else if (i == 0) {
                //if the char is at the beginning then only increment j
                table[j] = 0;
                j++;
            } else {
                //back track the character front pointer
                i = table[i - 1];
            }
        }
        // System.out.println("FT for Pattern " + pattern + " is " + Arrays.toString(table));
        // System.out.println("Comparisons " + comparator.getComparisonCount());

        return table;
    }

    /**
     * Boyer Moore algorithm that relies on last occurrence table. Works better
     * with large alphabets.
     *
     * Make sure to implement the buildLastTable() method before implementing
     * this method. Do NOT implement the Galil Rule in this method.
     *
     * Note: You may find the getOrDefault() method from Java's Map class
     * useful.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for the pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> boyerMoore(CharSequence pattern,
                                           CharSequence text,
                                           CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Pattern cannot be null or of zero length");
        }
        if (comparator == null || text == null) {
            throw new IllegalArgumentException("Comparator and/or text cannot be null");
        }

        List<Integer> matches = new LinkedList<Integer>();
        Map<Character, Integer> lot = buildLastTable(pattern);

        int i = 0;

        while (i <= text.length() - pattern.length()) {
            int j = pattern.length() - 1;

            while (j >= 0 && comparator.compare(text.charAt(i + j), pattern.charAt(j)) == 0) {
                j = j - 1;
            }

            if (j == -1) {
                matches.add(i);
                //look for next occurrence
                i++;
                j = pattern.length() - 2;
            } else {
                int shiftAmt = lot.getOrDefault(text.charAt(i + j), -1);

                if (shiftAmt < j) {
                    i = i + j - shiftAmt;
                } else {
                    i = i + 1;
                }
            }
        }

        return matches;
    }

    /**
     * Builds last occurrence table that will be used to run the Boyer Moore
     * algorithm.
     *
     * Note that each char x will have an entry at table.get(x).
     * Each entry should be the last index of x where x is a particular
     * character in your pattern.
     * If x is not in the pattern, then the table will not contain the key x,
     * and you will have to check for that in your Boyer Moore implementation.
     *
     * Ex. pattern = octocat
     *
     * table.get(o) = 3
     * table.get(c) = 4
     * table.get(t) = 6
     * table.get(a) = 5
     * table.get(everything else) = null, which you will interpret in
     * Boyer-Moore as -1
     *
     * If the pattern is empty, return an empty map.
     *
     * @param pattern a pattern you are building last table for
     * @return a Map with keys of all of the characters in the pattern mapping
     * to their last occurrence in the pattern
     * @throws java.lang.IllegalArgumentException if the pattern is null
     */
    public static Map<Character, Integer> buildLastTable(CharSequence pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("Pattern cant be null");
        }
        Map<Character, Integer> lot = new HashMap<Character, Integer>();

        for (int i = 0; i < pattern.length(); i++) {
            lot.put(pattern.charAt(i), i); // will update LOT of char with the actual last ocurrence sine it is a map
        }

        return lot;
    }

    /**
     * Prime base used for Rabin-Karp hashing.
     * DO NOT EDIT!
     */
    private static final int BASE = 113;

    /**
     * Runs the Rabin-Karp algorithm. This algorithms generates hashes for the
     * pattern and compares this hash to substrings of the text before doing
     * character by character comparisons.
     *
     * When the hashes are equal and you do character comparisons, compare
     * starting from the beginning of the pattern to the end, not from the end
     * to the beginning.
     *
     * You must use the Rabin-Karp Rolling Hash for this implementation. The
     * formula for it is:
     *
     * sum of: c * BASE ^ (pattern.length - 1 - i)
     *   c is the integer value of the current character, and
     *   i is the index of the character
     *
     * We recommend building the hash for the pattern and the first m characters
     * of the text by starting at index (m - 1) to efficiently exponentiate the
     * BASE. This allows you to avoid using Math.pow().
     *
     * Note that if you were dealing with very large numbers here, your hash
     * will likely overflow; you will not need to handle this case.
     * You may assume that all powers and calculations CAN be done without
     * overflow. However, be careful with how you carry out your calculations.
     * For example, if BASE^(m - 1) is a number that fits into an int, it's
     * possible for BASE^m will overflow. So, you would not want to do
     * BASE^m / BASE to calculate BASE^(m - 1).
     *
     * Ex. Hashing "bunn" as a substring of "bunny" with base 113
     * = (b * 113 ^ 3) + (u * 113 ^ 2) + (n * 113 ^ 1) + (n * 113 ^ 0)
     * = (98 * 113 ^ 3) + (117 * 113 ^ 2) + (110 * 113 ^ 1) + (110 * 113 ^ 0)
     * = 142910419
     *
     * Another key point of this algorithm is that updating the hash from
     * one substring to the next substring must be O(1). To update the hash,
     * subtract the oldChar times BASE raised to the length - 1, multiply by
     * BASE, and add the newChar as shown by this formula:
     * (oldHash - oldChar * BASE ^ (pattern.length - 1)) * BASE + newChar
     *
     * Ex. Shifting from "bunn" to "unny" in "bunny" with base 113
     * hash("unny") = (hash("bunn") - b * 113 ^ 3) * 113 + y
     *              = (142910419 - 98 * 113 ^ 3) * 113 + 121
     *              = 170236090
     *
     * Keep in mind that calculating exponents is not O(1) in general, so you'll
     * need to keep track of what BASE^(m - 1) is for updating the hash.
     *
     * Do NOT use Math.pow() in this method.
     *
     * @param pattern    a string you're searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> rabinKarp(CharSequence pattern,
                                          CharSequence text,
                                          CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Pattern cannot be null or of zero length");
        }
        if (comparator == null || text == null) {
            throw new IllegalArgumentException("Comparator and/or text cannot be null");
        }   
        
        List<Integer> matches = new LinkedList<Integer>();
        if (pattern.length() > text.length()) {
            return matches;
        }

        int i = 0;
        // for(int x = 0; x < pattern.length(); x++) {
        //     System.out.println("Pattern Char: " +
        // pattern.charAt(x) + "\tHash: " + ((Character)pattern.charAt(x)).hashCode());
        // }
        // for(int x = 0; x < pattern.length(); x++) {
        //     System.out.println("Text Char: " + text.charAt(x) + "\tHash: " + ((Character)text.charAt(x)).hashCode());
        // }

        //value will stay constant throughout the matching algorithm
        long patternHash = ((Character) pattern.charAt(pattern.length() - 1)).hashCode();
        //val will change depending on each check
        long textHash = ((Character) text.charAt(pattern.length() - 1)).hashCode();
        
        long baseMultiplier = 1;
        
        for (int p = pattern.length() - 2; p >= 0; p--) {
            
            baseMultiplier *= BASE;
            
            long patternCharCode = ((Character) pattern.charAt(p)).hashCode();
            
            long  textCharCode = ((Character) text.charAt(p)).hashCode();
            
            patternHash += patternCharCode * baseMultiplier;
            
            textHash += textCharCode * baseMultiplier;
            
        }


        while (i <= text.length() - pattern.length()) {
            // System.out.println("Pattern Hash: "+ patternHash);
            // System.out.println("Text Hash: "+ textHash + "\n");

            if (patternHash == textHash) {
                //check char at a time to see if the pattern matches that 
                // System.out.println("Matching Hash");
                int p = 0;
                while (p < pattern.length()) {
                    
                    // System.out.println("Text Char: " + text.charAt(i + p) + "\tPattern Char: " + pattern.charAt(p));

                    if (comparator.compare(text.charAt(i + p), pattern.charAt(p)) != 0) {
                        break; // break out of the for loop
                    }
                    p++;
                }

                if (p == pattern.length()) {
                    matches.add(i);
                }
            }
            
            //if the two hashes do not equate then roll the hash if current counter is not on last possible check
            if (i == text.length() - pattern.length()) {
                //when  reached the end so cannot roll any farther
                return matches;
            }
            
            Character newChar = text.charAt(i + pattern.length());
            Character oldChar = text.charAt(i);
            
            textHash = rollHash(textHash, newChar, oldChar, baseMultiplier);
            
            i++;
        }

        return matches;
    }

    /**
     * helper method to roll the hash
     * @param previousHash pre hash
     * @param newChar new char
     * @param oldChar old char
     * @param baseMultiplier bm
     * @return new hash 
     */
    private static long rollHash(long previousHash, Character newChar, Character oldChar, long baseMultiplier) {
        // System.out.println("Old Char: " + oldChar + "\tHash: " + oldChar.hashCode());
        // System.out.println("New Char: " + newChar + "\tHash: " + newChar.hashCode());
        // System.out.println("Base Multiplier: " + baseMultiplier);
        // System.out.println("Prev Hash: " + previousHash);
        
        
        long newHash = (previousHash - (oldChar.hashCode() * baseMultiplier)) * BASE + newChar.hashCode();
        // System.out.println("New Hash: " + newHash + "\n");


        return newHash;
    }

    /**
     * This method is OPTIONAL and for extra credit only.
     *
     * The Galil Rule is an addition to Boyer Moore that optimizes how we shift the pattern
     * after a full match. Please read Extra Credit: Galil Rule section in the homework pdf for details.
     *
     * Make sure to implement the buildLastTable() method and buildFailureTable() method
     * before implementing this method.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for the pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> boyerMooreGalilRule(CharSequence pattern,
                                          CharSequence text,
                                          CharacterComparator comparator) {
        return null; // if you are not implementing this method, do NOT modify this line
    }
}
