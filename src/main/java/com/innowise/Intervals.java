package com.innowise;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Intervals {
    private static final String A = "A";
    private static final String B = "B";
    private static final String C = "C";
    private static final String D = "D";
    private static final String E = "E";
    private static final String F = "F";
    private static final String G = "G";
    private static final Map<String, Integer> NATURAL_NOTES_TO_SEMITONES_MAP =
            Map.ofEntries(
                    Map.entry(C, 2),
                    Map.entry(D, 2),
                    Map.entry(E, 1),
                    Map.entry(F, 2),
                    Map.entry(G, 2),
                    Map.entry(A, 2),
                    Map.entry(B, 1)
            );
    private static final Integer OCTAVE_SEMITONE_COUNT = 12;
    private static final Map<String, Integer> ACCIDENTALS_TO_SEMITONES_ASC_MAP =
            Map.ofEntries(
                    Map.entry("#", -1),
                    Map.entry("##", -2),
                    Map.entry("b", 1),
                    Map.entry("bb", 2),
                    Map.entry("", 0)
            );
    private static final Map<String, Integer> ACCIDENTALS_TO_SEMITONES_DESC_MAP =
            Map.ofEntries(
                    Map.entry("#", 1),
                    Map.entry("##", 2),
                    Map.entry("b", -1),
                    Map.entry("bb", -2),
                    Map.entry("", 0)
            );
    private static final Map<String, Integer[]> INTERVALS_TO_SEMITONES_DEGREES_MAP =
            Map.ofEntries(
                    Map.entry("m2", new Integer[]{1, 2}),
                    Map.entry("M2", new Integer[]{2, 2}),
                    Map.entry("m3", new Integer[]{3, 3}),
                    Map.entry("M3", new Integer[]{4, 3}),
                    Map.entry("P4", new Integer[]{5, 4}),
                    Map.entry("P5", new Integer[]{7, 5}),
                    Map.entry("m6", new Integer[]{8, 6}),
                    Map.entry("M6", new Integer[]{9, 6}),
                    Map.entry("m7", new Integer[]{10, 7}),
                    Map.entry("M7", new Integer[]{11, 7}),
                    Map.entry("P8", new Integer[]{12, 8})
            );
    private static final String ASCENDING_ORDER = "asc";
    private static final String DESCENDING_ORDER = "dsc";
    private static final List<String> BUILDING_ORDERS = List.of(ASCENDING_ORDER,
            DESCENDING_ORDER);
    private static final List<String> NOTES = List.of(C, D, E, F, G, A, B);
    private static final String INVALID_NOTE_ARGUMENT_EXCEPTION = "Invalid note argument in args array";
    private static final String INVALID_INTERVAL_ARGUMENT_EXCEPTION = "Invalid interval argument in args array";
    private static final String INVALID_ORDER_ARGUMENT_EXCEPTION = "Invalid building order argument in args array";
    private static final String INVALID_ARGUMENT_COUNT_EXCEPTION = "Invalid number of arguments in args array";
    private static Map<String, Integer> accidentals;

    public static String intervalConstruction(String[] args) {
        if (args.length != 2 && args.length != 3) {
            throw new IllegalArgumentException(INVALID_ARGUMENT_COUNT_EXCEPTION);
        }
        String intervalName = args[0];

        String firstNoteName = parseNoteName(args[1]);
        String firstNoteAccidentals = parseNoteAccidentals(args[1]);

        String intervalOrder = args.length == 3 ? args[2] : ASCENDING_ORDER;

        argumentCheck(intervalName, firstNoteName, firstNoteAccidentals, intervalOrder);

        accidentals = intervalOrder.equals(ASCENDING_ORDER) ? ACCIDENTALS_TO_SEMITONES_ASC_MAP
                : ACCIDENTALS_TO_SEMITONES_DESC_MAP;

        int intervalSemitoneCount = INTERVALS_TO_SEMITONES_DEGREES_MAP.get(intervalName)[0];
        int intervalDegreeCount = INTERVALS_TO_SEMITONES_DEGREES_MAP.get(intervalName)[1] - 1;

        String secondNoteName = getSecondNoteNameByDegreeCount(firstNoteName,
                intervalDegreeCount, intervalOrder);

        int secondNoteSemitoneCount = getNoteSemitoneCount(firstNoteName,
                secondNoteName,
                firstNoteAccidentals,
                intervalSemitoneCount,
                intervalOrder);

        String secondNoteAccidentals = getSecondNoteAccidentals(secondNoteSemitoneCount);

        return secondNoteName + secondNoteAccidentals;
    }

    private static void argumentCheck(String intervalName,
                                      String firstNoteName,
                                      String firstNoteAccidentals,
                                      String intervalOrder) {
        if (!NATURAL_NOTES_TO_SEMITONES_MAP.containsKey(firstNoteName)
                || !ACCIDENTALS_TO_SEMITONES_ASC_MAP.containsKey(firstNoteAccidentals)) {
            throw new IllegalArgumentException(INVALID_NOTE_ARGUMENT_EXCEPTION);
        }

        if (!INTERVALS_TO_SEMITONES_DEGREES_MAP.containsKey(intervalName)) {
            throw new IllegalArgumentException(INVALID_INTERVAL_ARGUMENT_EXCEPTION);
        }

        if (!BUILDING_ORDERS.contains(intervalOrder)) {
            throw new IllegalArgumentException(INVALID_ORDER_ARGUMENT_EXCEPTION);
        }
    }

    private static String getSecondNoteAccidentals(Integer secondNoteSemitoneCount) {
        return accidentals
                .entrySet()
                .stream()
                .filter(entry -> secondNoteSemitoneCount.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findFirst().get();
    }

    private static String parseNoteAccidentals(String noteWithAccidentals) {
        return noteWithAccidentals.substring(1);
    }

    private static String parseNoteName(String noteWithAccidentals) {
        return noteWithAccidentals.substring(0, 1);
    }

    private static String getSecondNoteNameByDegreeCount(String firstNote, int degreeCount,
                                                         String intervalBuildingOrder) {
        int secondIntervalNoteIndex;
        int firstIntervalNoteIndex = NOTES.indexOf(firstNote);

        if (intervalBuildingOrder.equals(DESCENDING_ORDER)) {
            degreeCount = -degreeCount;
        }
        secondIntervalNoteIndex = Math.floorMod(firstIntervalNoteIndex + degreeCount, NOTES.size());
        return NOTES.get(secondIntervalNoteIndex);
    }

    private static int getSemitoneCountBetweenNotes(String firstNote,
                                                    String secondNote,
                                                    String intervalBuildingOrder) {
        int firstNoteIndex = NOTES.indexOf(firstNote);
        int secondNoteIndex = NOTES.indexOf(secondNote);

        int minIndex = Math.min(firstNoteIndex, secondNoteIndex);
        int maxIndex = Math.max(firstNoteIndex, secondNoteIndex);

        int semitoneCount = 0;
        for (int i = minIndex; i < maxIndex; i++) {
            semitoneCount += NATURAL_NOTES_TO_SEMITONES_MAP.get(NOTES.get(i));
        }

        if ((intervalBuildingOrder.equals(ASCENDING_ORDER) && firstNoteIndex > secondNoteIndex)
                || (intervalBuildingOrder.equals(DESCENDING_ORDER) && firstNoteIndex < secondNoteIndex)) {
            semitoneCount = OCTAVE_SEMITONE_COUNT - semitoneCount;
        }

        return semitoneCount;
    }

    private static int getNoteSemitoneCount(String firstNote,
                                            String secondNote,
                                            String firstNoteAccidentals,
                                            int intervalSemitoneCount,
                                            String intervalBuildingOrder) {
        int semitoneCountBetweenNotes = getSemitoneCountBetweenNotes(firstNote, secondNote, intervalBuildingOrder);
        int firstNoteSemitoneCount = accidentals.get(firstNoteAccidentals);
        return firstNoteSemitoneCount + semitoneCountBetweenNotes - intervalSemitoneCount;
    }

    public static String intervalIdentification(String[] args) {
        if (args.length != 2 && args.length != 3) {
            throw new IllegalArgumentException(INVALID_ARGUMENT_COUNT_EXCEPTION);
        }
        String firstNoteName = parseNoteName(args[0]);
        String firstNoteAccidentals = parseNoteAccidentals(args[0]);

        String secondNoteName = parseNoteName(args[1]);
        String secondNoteAccidentals = parseNoteAccidentals(args[1]);

        String intervalOrder = args.length == 3 ? args[2] : ASCENDING_ORDER;

        accidentals = intervalOrder.equals(ASCENDING_ORDER) ? ACCIDENTALS_TO_SEMITONES_ASC_MAP
                : ACCIDENTALS_TO_SEMITONES_DESC_MAP;

        int intervalDegreeCount = getIntervalDegreeCountByNotes(firstNoteName, secondNoteName, intervalOrder);
        int intervalSemitoneCount = getIntervalSemitoneCountByNotes(firstNoteName, firstNoteAccidentals,
                secondNoteName, secondNoteAccidentals, intervalOrder);

        Integer[] intervalSemitoneDegreeArray = {intervalSemitoneCount, intervalDegreeCount};

        return INTERVALS_TO_SEMITONES_DEGREES_MAP
                .entrySet()
                .stream()
                .filter(entry -> Arrays.equals(intervalSemitoneDegreeArray, entry.getValue()))
                .map(Map.Entry::getKey)
                .findFirst().get();
    }

    private static int getIntervalSemitoneCountByNotes(String firstNoteName,
                                                       String firstNoteAccidentals,
                                                       String secondNoteName,
                                                       String secondNoteAccidentals,
                                                       String intervalOrder) {
        int naturalNoteSemitoneCount = getSemitoneCountBetweenNotes(firstNoteName, secondNoteName, intervalOrder);
        int firstNoteSemitoneCount = accidentals.get(firstNoteAccidentals);
        int secondNoteSemitoneCount = accidentals.get(secondNoteAccidentals);
        return naturalNoteSemitoneCount + firstNoteSemitoneCount - secondNoteSemitoneCount;
    }

    private static int getIntervalDegreeCountByNotes(String firstNote,
                                                     String secondNote,
                                                     String intervalBuildingOrder) {
        int firstNoteIndex = NOTES.indexOf(firstNote);
        int secondNoteIndex = NOTES.indexOf(secondNote);

        int intervalDegreeCount = Math.abs(firstNoteIndex - secondNoteIndex);
        if ((intervalBuildingOrder.equals(ASCENDING_ORDER) && firstNoteIndex > secondNoteIndex)
                || (intervalBuildingOrder.equals(DESCENDING_ORDER) && firstNoteIndex < secondNoteIndex)) {
            intervalDegreeCount = NOTES.size() - intervalDegreeCount;
        }
        return intervalDegreeCount + 1;
    }
}
