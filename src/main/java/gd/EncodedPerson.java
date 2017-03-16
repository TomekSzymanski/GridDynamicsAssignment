package gd;

public class EncodedPerson {

    public final long id;
    public final long[] attributes;

    /**
     * @param id
     * @param attributes encoded person attributes
     * @see BitwiseEncoder#encode
     */
    public EncodedPerson(long id, long[] attributes) {
        this.id = id;
        this.attributes = attributes;
    }
}
