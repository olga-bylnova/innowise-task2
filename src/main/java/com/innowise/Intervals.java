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
    private static final String DOUBLE_SHARP = "##";
    private static final String FLAT = "b";
    private static final String DOUBLE_FLAT = "bb";

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

    private static final String ASCENDING_ORDER = "ASC";
    private static final String DESCENDING_ORDER = "DSC";

    private static final List<String> notes = List.of(C, D, E, F, G, A, B);
    private static final List<Integer> semitones = List.of(2, 2, 1, 2, 2, 2, 1);
    private static final List<String> accidentals = List.of(SHARP, FLAT, "",
            DOUBLE_SHARP, DOUBLE_FLAT);
    private static final List<String> building_orders = List.of(ASCENDING_ORDER,
            DESCENDING_ORDER);
    private static final List<String> intervals = List.of(m2, M2, m3, M3, P4, P5,
            m6, M6, m7, M7, P8);

    public static String intervalConstruction(String[] args) {
        int degreeCount = 0, semitoneCount = 0;
        int index = 0;
        String intervalName = args[index++];
        String firstNote = args[index++];
        String firstNoteWithoutAccidentals = firstNote.substring(0, 1);
        String firstNoteAccidentals = firstNote.substring(1);
        String intervalOrder = ASCENDING_ORDER;

        if (args.length > index) {
            intervalOrder = args[index];

            if (!building_orders.contains(intervalOrder.toUpperCase())) {
                intervalOrder = ASCENDING_ORDER;
            }
        }

        if (!notes.contains(firstNoteWithoutAccidentals)
                || !accidentals.contains(firstNoteAccidentals)) {
            throw new RuntimeException("no such note");
        }

        if (!intervals.contains(intervalName)) {
            throw new RuntimeException("no such interval");
        }




        return null;
    }

    public static String intervalIdentification(String[] args) {
        return null;
    }
}
