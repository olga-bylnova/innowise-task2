package com.innowise;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class IntervalConstructionTest {
    @Test
    public void testIntervalConstructionNullArgsArray() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> Intervals.intervalConstruction(null));
    }
    @Test
    public void testIntervalConstructionIncorrectNumberOfArguments() {
        String[] args = {"m2", "A", "B", "dsc"};

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> Intervals.intervalConstruction(args));
    }

    @Test
    public void testIntervalConstructionIncorrectIntervalName() {
        String[] args = {"N2", "A", "dsc"};

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> Intervals.intervalConstruction(args));
    }

    @Test
    public void testIntervalConstructionIncorrectNoteName() {
        String[] args = {"m2", "I", "dsc"};

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> Intervals.intervalConstruction(args));
    }

    @Test
    public void testIntervalConstructionIncorrectIntervalBuildingOrder() {
        String[] args = {"m2", "I", "desc"};

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> Intervals.intervalConstruction(args));
    }

    @Test
    public void testIntervalConstructionTwoArgumentsAsc() {
        List<String[]> argsList = List.of(
                new String[]{"M2", "C"},
                new String[]{"P5", "B"},
                new String[]{"m2", "Fb"},
                new String[]{"m2", "D#"},
                new String[]{"M7", "G"}
        );
        List<String> expectedResults = List.of("D", "F#", "Gbb", "E", "F#");

        for (int i = 0; i < argsList.size(); i++) {
            Assertions.assertEquals(expectedResults.get(i),
                    Intervals.intervalConstruction(argsList.get(i)));
        }
    }
    @Test
    public void testIntervalConstructionThreeArgumentsAsc() {
        List<String[]> argsList = List.of(
                new String[]{"M2", "C", "asc"},
                new String[]{"P5", "B", "asc"},
                new String[]{"m2", "Fb", "asc"},
                new String[]{"m2", "D#", "asc"},
                new String[]{"M7", "G", "asc"}
        );
        List<String> expectedResults = List.of("D", "F#", "Gbb", "E", "F#");

        for (int i = 0; i < argsList.size(); i++) {
            Assertions.assertEquals(expectedResults.get(i),
                    Intervals.intervalConstruction(argsList.get(i)));
        }
    }
    @Test
    public void testIntervalConstructionThreeArgumentsDesc() {
        List<String[]> argsList = List.of(
                new String[]{"m2", "Bb", "dsc"},
                new String[]{"M3", "Cb", "dsc"},
                new String[]{"P4", "G#", "dsc"},
                new String[]{"m3", "B", "dsc"},
                new String[]{"M2", "E#", "dsc"},
                new String[]{"P4", "E", "dsc"}
        );
        List<String> expectedResults = List.of("A", "Abb", "D#", "G#", "D#", "B");

        for (int i = 0; i < argsList.size(); i++) {
            Assertions.assertEquals(expectedResults.get(i),
                    Intervals.intervalConstruction(argsList.get(i)));
        }
    }
}
