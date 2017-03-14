package gd;

public class BitwiseEncoder {

    public static final int DEFAULT_MAX_ATTRIBUTES = 500;

    private static final int WORD_LENGTH = 64;

    public final int maxAttributes;

    private final int wordsNeeded;

    public BitwiseEncoder() {
        this(DEFAULT_MAX_ATTRIBUTES);
    }

    public BitwiseEncoder(int maxAttributes) {
        if (maxAttributes <= 0) {
            throw new IllegalArgumentException("maxAttributes must be positive");
        }
        this.maxAttributes = maxAttributes;
        this.wordsNeeded = (int)Math.ceil((double)maxAttributes/WORD_LENGTH);
    }


    public long[] encode(int paramIdx) {
        return encode(new int[] {paramIdx});
    }

    public long[] encode(int[] paramIndexes) {
        //ensureAllIndexesValid(paramIndexes)
        long[] codingWords = new long[wordsNeeded];
        int idx;
        int wordIndex, indexInWord;
        for (int i = 0; i < paramIndexes.length; i++) {
            idx = paramIndexes[i];
            wordIndex = idx / WORD_LENGTH;
            indexInWord = idx % WORD_LENGTH;
            codingWords[wordIndex] = codingWords[wordIndex] | (1L << indexInWord);
        }
        return codingWords;
    }

    public boolean forAllAttributes(long[] input, int[] paramIdx) {
        long[] encodedParamIndexes = encode(paramIdx);
        return forAllAttributes(input, encodedParamIndexes);
    }

    public boolean forAllAttributes(long[] input, long[] encodedParamIndexes) {
        for (int i = 0; i < input.length; i++) {
            if ((input[i] & encodedParamIndexes[i]) != encodedParamIndexes[i]) {
                return false;
            }
        }
        return true;
    }

    private void ensureAllIndexesValid(int[] paramIdx) {
//        require(paramIdx.nonEmpty)
//        require(paramIdx.forall(_ >= 0))
//        require(allIndexesWithinCapacity(paramIdx))
    }

    private boolean allIndexesWithinCapacity(int[] paramIdx) {
        for (int i = 0; i < paramIdx.length; i++) {
            if (paramIdx[i] >= maxAttributes) {
                return false;
            }
        }
        return true;
    }
}
