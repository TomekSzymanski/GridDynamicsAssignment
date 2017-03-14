package gd.performancetest;

import gd.Attributes;
import gd.BitwiseEncoder;
import gd.Person;
import lombok.Value;

import java.util.List;

@Value
public class TestState {
    final List<Person> input;
    final BitwiseEncoder encoder;
    final List<Attributes> filterAttributes;
    final List<Attributes> groupByAttributes;
}
