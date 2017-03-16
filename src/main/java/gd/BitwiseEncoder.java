package gd;

import com.google.common.base.Preconditions;

/**
 * Encodes arrays of boolean variables as machine words. Also contains utility functions operating on such encoded representation.
 * The encoded machine word representation has bits
 */
public class BitwiseEncoder {

    private static final int MACHINE_WORD_LENGTH = 64;

    private final int maxSupportedAttributes;

    private final int wordsNeeded;

    public BitwiseEncoder(int maxSupportedAttributes) {
        Preconditions.checkArgument(maxSupportedAttributes > 0, "maxSupportedAttributes must be positive");
        this.maxSupportedAttributes = maxSupportedAttributes;
        this.wordsNeeded = (int)Math.ceil((double) maxSupportedAttributes / MACHINE_WORD_LENGTH);
    }

    /**
     * Accepts one index of boolean array that point to true value (all others are assumed false) and encodes that as machine words.
     * So for example for the boolean array [0, 0, 1, 0, 0] you should pass int array with value [2]. Such representation is effective for sparse boolean matrices. See unit tests.
     * @param paramIdx single index of boolean array that point to true value (all others are assumed false)
     * @return array of long machine words representing the boolean set to true
     */
    public long[] encode(int paramIdx) {
        return encode(new int[] {paramIdx});
    }

    /**
     * Accepts indexes of boolean array that point to true values (all others are assumed false) and encodes them as machine words.
     * So for example for the boolean array [1, 0, 0, 1, 0] you should pass int array with values [0, 3]. Such representation is effective for sparse boolean matrices. See unit tests.
     * @param paramIndexes indexes of boolean array that point to true values (all others are assumed false)
     * @return array of long machine words representing the booleans set to true
     */
    public long[] encode(int[] paramIndexes) {
        // checking preconditions disabled for performance
        // ensureAllIndexesValid(paramIndexes);
        long[] codingWords = new long[wordsNeeded];
        int idx;
        int wordIndex, indexInWord;
        for (int i = 0; i < paramIndexes.length; i++) {
            idx = paramIndexes[i];
            wordIndex = idx / MACHINE_WORD_LENGTH;
            indexInWord = idx % MACHINE_WORD_LENGTH;
            codingWords[wordIndex] = codingWords[wordIndex] | (1L << indexInWord);
        }
        return codingWords;
    }

    /**
     * Checks if given encoded input (array of booleans) have all provided indexes active (their cells set to true).
     * Useful for AND filtering of inputs (index_1==true AND index_2==true AND index_n==true)
     * @param encodedInput encoded input (array of booleans)
     * @param paramIdx array with indexes which values must be all true
     * @return true if all indexes were true. False if at least one of the specified indexes was not true.
     */
    public boolean forAllAttributes(long[] encodedInput, int[] paramIdx) {
        long[] encodedParamIndexes = encode(paramIdx);
        return forAllAttributes(encodedInput, encodedParamIndexes);
    }

    /**
     * Checks if given encoded input (array of booleans) have all provided indexes active (their cells set to true).
     * Useful for AND filtering of inputs (index_1==true AND index_2==true AND index_n==true)
     * @param encodedInput encoded input (array of booleans)
     * @param encodedParamIndexes array with encoded indexes which values must be all true
     * @return true if all indexes were true. False if at least one of the specified indexes was not true.
     */
    public boolean forAllAttributes(long[] encodedInput, long[] encodedParamIndexes) {
        for (int i = 0; i < encodedInput.length; i++) {
            if ((encodedInput[i] & encodedParamIndexes[i]) != encodedParamIndexes[i]) {
                return false;
            }
        }
        return true;
    }

    private void ensureAllIndexesValid(int[] paramIdx) {
        Preconditions.checkArgument(paramIdx.length > 0, "parameters indexes must be non empty");
        for (int idx : paramIdx) {
            Preconditions.checkArgument(idx >= 0, "index cannot be negative number");
            Preconditions.checkArgument(idx < maxSupportedAttributes, "index cannot be greater than encoder max capacity");
        }
    }
}
