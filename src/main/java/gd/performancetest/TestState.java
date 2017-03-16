package gd.performancetest;

import gd.Attribute;
import gd.BitwiseEncoder;
import gd.EncodedPerson;
import lombok.Value;

import java.util.List;

@Value
public class TestState {
    final List<EncodedPerson> input;
    final BitwiseEncoder encoder;
    final List<Attribute> filterAttributes;
    final List<Attribute> groupByAttributes;
}
