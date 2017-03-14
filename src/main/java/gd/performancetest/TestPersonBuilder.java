package gd.performancetest;

import gd.BitwiseEncoder;
import gd.Person;

import java.util.Arrays;
import java.util.Random;

class TestPersonBuilder {

    private final BitwiseEncoder encoder;

    private final Random rnd = new Random();

    TestPersonBuilder(BitwiseEncoder encoder) {
        this.encoder = encoder;
    }

    Person buildRandomPerson(int personId) {
        // this takes time, in the loop. For 1M records 8.5 second
        int numAttrsToSet = 1 + rnd.nextInt(encoder.maxAttributes);
        int [] attributeIndexes = new int[numAttrsToSet];
        Arrays.fill(attributeIndexes, rnd.nextInt(encoder.maxAttributes));
        long[] attribs = encoder.encode(attributeIndexes);
        return new Person(personId, attribs);
    }

    Person buildSamplePerson(int personId) {
        int randomAttr1 = rnd.nextInt(10);
        int randomAttr2 = rnd.nextInt(10);
        return new Person(personId, encoder.encode(new int[]{randomAttr1, randomAttr2, 100, 200, 400}));
    }
}
