package gd.performancetest;

import gd.BitwiseEncoder;
import gd.Person;

import java.util.Arrays;
import java.util.Random;

class TestPersonFactory {

    public static final float ATTRIBUTES_USED_RATIO = 0.25f;
    private final BitwiseEncoder encoder;

    private final int maxAttributes;

    private final Random rnd = new Random();

    TestPersonFactory(BitwiseEncoder encoder, int maxAttributes) {
        this.encoder = encoder;
        this.maxAttributes = maxAttributes;
    }

    Person buildRandomPerson(int personId) {
        int numAttrsToSet = 1 + rnd.nextInt(maxAttributes);
        int [] attributeIndexes = new int[numAttrsToSet];
        Arrays.fill(attributeIndexes, rnd.nextInt(maxAttributes));
        long[] attribs = encoder.encode(attributeIndexes);
        return new Person(personId, attribs);
    }

    Person buildSamplePerson(int personId, int focusAttributesMaxIndex) {
        if (focusAttributesMaxIndex >= maxAttributes) {
            throw new IllegalArgumentException("focusAttributesMaxIndex: " + focusAttributesMaxIndex + " must be smaller than maxAttributes: " + maxAttributes);
        }
        int[] focusAttributesIndexes = {rnd.nextInt(focusAttributesMaxIndex), rnd.nextInt(focusAttributesMaxIndex), rnd.nextInt(focusAttributesMaxIndex), rnd.nextInt(focusAttributesMaxIndex)};
        int[] otherIndexes = new int[(int)(maxAttributes * ATTRIBUTES_USED_RATIO)];
        Arrays.fill(otherIndexes, focusAttributesMaxIndex + rnd.nextInt(maxAttributes - focusAttributesMaxIndex));
        int[] allIndexes = concatArrays(focusAttributesIndexes, otherIndexes);
        return new Person(personId, encoder.encode(allIndexes));
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
