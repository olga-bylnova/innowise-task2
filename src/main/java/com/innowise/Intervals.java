package com.innowise;

import java.util.List;

public class Intervals {
    private static final String A = "A";
    private static final String B = "B";
    private static final String C = "C";
    private static final String D = "D";
    private static final String E = "E";
    private static final String F = "F";
    private static final String G = "G";
    private static final String SHARP = "#";
    private static final String FLAT = "b";

    private static final String m2 = "m2";
    private static final String M2 = "M2";
    private static final String m3 = "m3";
    private static final String M3 = "M3";
    private static final String P4 = "P4";
    private static final String P5 = "P5";
    private static final String m6 = "m6";
    private static final String M6 = "M6";
    private static final String m7 = "m7";
    private static final String M7 = "M7";
    private static final String P8 = "P8";

    private static final Integer[] m2_SEMITONE_DEGREE = {1, 2};
    private static final Integer[] M2_SEMITONE_DEGREE = {2, 2};
    private static final Integer[] m3_SEMITONE_DEGREE = {3, 3};
    private static final Integer[] M3_SEMITONE_DEGREE = {4, 3};
    private static final Integer[] P4_SEMITONE_DEGREE = {5, 4};
    private static final Integer[] P5_SEMITONE_DEGREE = {7, 5};
    private static final Integer[] m6_SEMITONE_DEGREE = {8, 6};
    private static final Integer[] M6_SEMITONE_DEGREE = {9, 6};
    private static final Integer[] m7_SEMITONE_DEGREE = {10, 7};
    private static final Integer[] M7_SEMITONE_DEGREE = {11, 7};
    private static final Integer[] P8_SEMITONE_DEGREE = {12, 8};

    private static final List<String> notes = List.of(A, B, C, D, E, F, G);
    private static final List<String> accidentals = List.of(SHARP, FLAT);
    private static final List<String> intervals = List.of(m2, M2, m3, M3, P4, P5,
            m6, M6, m7, M7, P8);

    public static String intervalConstruction(String[] args) {
        String intervalName = args[0];
        String firstNote = args[1];

        if (!notes.contains(firstNote.substring(0, 1))
        || !accidentals.contains(firstNote.substring(1, 1))) {
            throw new RuntimeException("no such note");
        }

        return null;
    }

    public static String intervalIdentification(String[] args) {
        return null;
    }
}
