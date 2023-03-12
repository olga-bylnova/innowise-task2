package com.innowise;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class IntervalIdentificationTest {
    @Test
    public void testIntervalIdentificationNullArgsArray() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> Intervals.intervalIdentification(null));
    }
    @Test
    public void testIntervalIdentificationIncorrectNumberOfArguments() {
        String[] args = {"С", "D", "M2", "dsc"};

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> Intervals.intervalIdentification(args));
    }

    @Test
    public void testIntervalIdentificationIncorrectNoteName() {
        String[] args = {"N", "A", "dsc"};

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> Intervals.intervalIdentification(args));
    }

    @Test
    public void testIntervalIdentificationIncorrectIntervalBuildingOrder() {
        String[] args = {"C", "D", "desc"};

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> Intervals.intervalIdentification(args));
    }

    @Test
    public void testIntervalIdentificationTwoArgumentsAsc() {
        List<String[]> argsList = List.of(
                new String[]{"C", "D"},
                new String[]{"B", "F#"},
                new String[]{"Fb", "Gbb"},
                new String[]{"G", "F#"}
        );
        List<String> expectedResults = List.of("M2", "P5", "m2", "M7");

        for (int i = 0; i < argsList.size(); i++) {
            Assertions.assertEquals(expectedResults.get(i),
                    Intervals.intervalIdentification(argsList.get(i)));
        }
    }
    @Test
    public void testIntervalIdentificationThreeArgumentsAsc() {
        List<String[]> argsList = List.of(
                new String[]{"C", "D", "asc"},
                new String[]{"B", "F#", "asc"},
                new String[]{"Fb", "Gbb", "asc"},
                new String[]{"G", "F#", "asc"}
        );
        List<String> expectedResults = List.of("M2", "P5", "m2", "M7");

        for (int i = 0; i < argsList.size(); i++) {
            Assertions.assertEquals(expectedResults.get(i),
                    Intervals.intervalIdentification(argsList.get(i)));
        }
    }
    @Test
    public void testIntervalIdentificationThreeArgumentsDesc() {
        List<String[]> argsList = List.of(
                new String[]{"Bb", "A", "dsc"},
                new String[]{"Cb", "Abb", "dsc"},
                new String[]{"G#", "D#", "dsc"},
                new String[]{"E", "B", "dsc"},
                new String[]{"E#", "D#", "dsc"},
                new String[]{"B", "G#", "dsc"}
        );
        List<String> expectedResults = List.of("m2", "M3", "P4", "P4", "M2", "m3");

        for (int i = 0; i < argsList.size(); i++) {
            Assertions.assertEquals(expectedResults.get(i),
                    Intervals.intervalIdentification(argsList.get(i)));
        }
    }
}
