package gd.performancetest;

import gd.BitwiseEncoder;
import gd.EncodedPerson;

import java.util.Arrays;
import java.util.Random;

class TestPersonFactory {

    // assume that person typically has no more than 25% attributes set.
    // when more dense it still does not change significantly the benchmark results
    public static final float ATTRIBUTES_USED_RATIO = 0.25f;

    private final BitwiseEncoder encoder;

    private final int maxAttributes;

    private final Random rnd = new Random();

    TestPersonFactory(BitwiseEncoder encoder, int maxAttributes) {
        this.encoder = encoder;
        this.maxAttributes = maxAttributes;
    }

    /**
     * Generates test person with random number of active attributes and random actual attributes active
     */
    EncodedPerson buildRandomPerson(int personId) {
        int numAttrsToSet = 1 + rnd.nextInt(maxAttributes);
        int [] attributeIndexes = new int[numAttrsToSet];
        Arrays.fill(attributeIndexes, rnd.nextInt(maxAttributes));
        long[] attribs = encoder.encode(attributeIndexes);
        return new EncodedPerson(personId, attribs);
    }

    /**
     * Generates test person which has their parameters randomly set.
     * Test person will have two kinds of attributes set (they may overlap):
     *  - focus attributes: attributes that we want to make sure are represented with much higher probability than then the other. Useful to hit exactly that attributes later in filtering or grouping, to target more negative use case from performance perspective.
     * - other attributes
     * @param personId
     * @param focusAttributesMaxIndex maximum index of the 4 generated focus attributes. If you specify for example 10, then there will be 4 focus attributes with indexes up to 10 (they may not be distinct)
     */
    EncodedPerson buildSamplePerson(int personId, int focusAttributesMaxIndex) {
        if (focusAttributesMaxIndex >= maxAttributes) {
            throw new IllegalArgumentException("focusAttributesMaxIndex: " + focusAttributesMaxIndex + " must be smaller than maxAttributes: " + maxAttributes);
        }
        int[] focusAttributesIndexes = {rnd.nextInt(focusAttributesMaxIndex), rnd.nextInt(focusAttributesMaxIndex), rnd.nextInt(focusAttributesMaxIndex), rnd.nextInt(focusAttributesMaxIndex)};
        int[] otherIndexes = new int[(int)(maxAttributes * ATTRIBUTES_USED_RATIO)];
        Arrays.fill(otherIndexes, focusAttributesMaxIndex + rnd.nextInt(maxAttributes - focusAttributesMaxIndex));
        int[] allIndexes = concatArrays(focusAttributesIndexes, otherIndexes);
        return new EncodedPerson(personId, encoder.encode(allIndexes));
    }

    private int[] concatArrays(int[] a, int[] b) {
        int aLen = a.length;
        int bLen = b.length;
        int[] c= new int[aLen+bLen];
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);
        return c;
    }
}
