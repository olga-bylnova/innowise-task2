package com.innowise;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class Intervals {
    private static final Integer OCTAVE_SEMITONE_COUNT = 12;
    private static final Integer OCTAVE_DEGREE_COUNT = 7;
    private static final String SEMITONE = "SEMITONE";
    private static final String DEGREE = "DEGREE";
    private static final Map<String, Map<String, Integer>> INTERVALS_TO_SEMITONES_DEGREES_MAP =
            Map.ofEntries(
                    Map.entry("m2", Map.of(SEMITONE, 1, DEGREE, 2)),
                    Map.entry("M2", Map.of(SEMITONE, 2, DEGREE, 2)),
                    Map.entry("m3", Map.of(SEMITONE, 3, DEGREE, 3)),
                    Map.entry("M3", Map.of(SEMITONE, 4, DEGREE, 3)),
                    Map.entry("P4", Map.of(SEMITONE, 5, DEGREE, 4)),
                    Map.entry("P5", Map.of(SEMITONE, 7, DEGREE, 5)),
                    Map.entry("m6", Map.of(SEMITONE, 8, DEGREE, 6)),
                    Map.entry("M6", Map.of(SEMITONE, 9, DEGREE, 6)),
                    Map.entry("m7", Map.of(SEMITONE, 10, DEGREE, 7)),
                    Map.entry("M7", Map.of(SEMITONE, 11, DEGREE, 7)),
                    Map.entry("P8", Map.of(SEMITONE, 12, DEGREE, 8))
            );
    private static final Map<String, Map<String, Integer>> NOTES_TO_SEMITONES_DEGREES_MAP =
            Map.ofEntries(
                    Map.entry("C", Map.of(SEMITONE, 1, DEGREE, 1)),
                    Map.entry("D", Map.of(SEMITONE, 3, DEGREE, 2)),
                    Map.entry("E", Map.of(SEMITONE, 5, DEGREE, 3)),
                    Map.entry("F", Map.of(SEMITONE, 6, DEGREE, 4)),
                    Map.entry("G", Map.of(SEMITONE, 8, DEGREE, 5)),
                    Map.entry("A", Map.of(SEMITONE, 10, DEGREE, 6)),
                    Map.entry("B", Map.of(SEMITONE, 12, DEGREE, 7))
            );
    private static final Map<String, Integer> ACCIDENTALS_TO_SEMITONES_MAP =
            Map.ofEntries(
                    Map.entry("#", 1),
                    Map.entry("##", 2),
                    Map.entry("b", -1),
                    Map.entry("bb", -2),
                    Map.entry("", 0)
            );
    private static final String ASCENDING_ORDER = "asc";
    private static final String DESCENDING_ORDER = "dsc";
    private static final List<String> BUILDING_ORDERS = List.of(ASCENDING_ORDER,
            DESCENDING_ORDER);
    private static final String INVALID_NOTE_ARGUMENT_EXCEPTION = "Invalid note argument in args array";
    private static final String INVALID_INTERVAL_ARGUMENT_EXCEPTION = "Invalid interval argument in args array";
    private static final String NO_SUCH_INTERVAL_EXCEPTION = "Cannot identify the interval";
    private static final String NO_SUCH_ACCIDENTAL_EXCEPTION = "Cannot identify the accidental";
    private static final String NO_SUCH_NOTE_EXCEPTION = "Cannot identify the note";
    private static final String INVALID_ORDER_ARGUMENT_EXCEPTION = "Invalid building order argument in args array";
    private static final String INVALID_ARGUMENT_COUNT_EXCEPTION = "Invalid number of arguments in args array";

    public static String intervalConstruction(String[] args) {
        checkArgsArray(args);
        String intervalName = args[0];

        String firstNoteName = parseNoteName(args[1]);
        String firstNoteAccidentals = parseNoteAccidentals(args[1]);

        String intervalOrder = args.length == 3 ? args[2] : ASCENDING_ORDER;

        intervalConstructionArgumentCheck(intervalName, firstNoteName, firstNoteAccidentals, intervalOrder);

        int intervalSemitoneCount = INTERVALS_TO_SEMITONES_DEGREES_MAP.get(intervalName).get(SEMITONE);
        int intervalDegreeCount = INTERVALS_TO_SEMITONES_DEGREES_MAP.get(intervalName).get(DEGREE);

        int noteSemitoneCount = getNoteSemitoneCount(firstNoteName, firstNoteAccidentals);
        int noteDegreeCount = NOTES_TO_SEMITONES_DEGREES_MAP.get(firstNoteName).get(DEGREE);

        int resultNoteSemitoneCount;
        int resultNoteDegreeCount;
        if (intervalOrder.equals(ASCENDING_ORDER)) {
            resultNoteDegreeCount = getDegreeCountAsc(noteDegreeCount, intervalDegreeCount);
            resultNoteSemitoneCount = getSemitoneCountAsc(noteSemitoneCount, intervalSemitoneCount);
        } else {
            resultNoteDegreeCount = getDegreeCountDesc(noteDegreeCount, intervalDegreeCount);
            resultNoteSemitoneCount = getSemitoneCountDesc(noteSemitoneCount, intervalSemitoneCount);
        }
        String secondNoteName = getNoteNameByDegreeCount(resultNoteDegreeCount);
        String secondNoteAccidentals = getNoteAccidentalsBySemitoneCount(secondNoteName, resultNoteSemitoneCount);

        return secondNoteName + secondNoteAccidentals;
    }

    private static int getNoteSemitoneCount(String noteName, String noteAccidentals) {
        return NOTES_TO_SEMITONES_DEGREES_MAP.get(noteName).get(SEMITONE)
                + ACCIDENTALS_TO_SEMITONES_MAP.get(noteAccidentals);
    }

    private static String getNoteAccidentalsBySemitoneCount(String noteName, int semitoneCount) {
        int naturalNoteSemitoneCount = NOTES_TO_SEMITONES_DEGREES_MAP.get(noteName).get(SEMITONE);
        return getAccidentalsBySemitoneCount(semitoneCount - naturalNoteSemitoneCount);
    }

    private static String getNoteNameByDegreeCount(int degreeCount) {
        return NOTES_TO_SEMITONES_DEGREES_MAP
                .entrySet()
                .stream()
                .filter(entry -> degreeCount == entry.getValue().get(DEGREE))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(NO_SUCH_NOTE_EXCEPTION));
    }

    private static int getDegreeCountDesc(int firstDegreeCount, int secondDegreeCount) {
        int result = firstDegreeCount - secondDegreeCount + 1;
        return result > 0 ? result : result + OCTAVE_DEGREE_COUNT;
    }

    private static int getDegreeCountAsc(int firstDegreeCount, int secondDegreeCount) {
        int result = firstDegreeCount + secondDegreeCount - 1;
        return result <= OCTAVE_DEGREE_COUNT ? result : result - OCTAVE_DEGREE_COUNT;
    }

    private static int getSemitoneCountAsc(int firstSemitoneCount, int secondSemitoneCount) {
        int result = firstSemitoneCount + secondSemitoneCount;
        return result <= OCTAVE_SEMITONE_COUNT ? result : result - OCTAVE_SEMITONE_COUNT;
    }

    private static int getSemitoneCountDesc(int firstSemitoneCount, int secondSemitoneCount) {
        int result = firstSemitoneCount - secondSemitoneCount;
        return result > 0 ? result : result + OCTAVE_SEMITONE_COUNT;
    }

    private static void checkArgsArray(String[] args) {
        if (args == null || (args.length != 2 && args.length != 3)) {
            throw new IllegalArgumentException(INVALID_ARGUMENT_COUNT_EXCEPTION);
        }
    }

    private static void intervalConstructionArgumentCheck(String intervalName,
                                                          String firstNoteName,
                                                          String firstNoteAccidentals,
                                                          String intervalOrder) {
        checkNoteWithAccidentalsExists(firstNoteName, firstNoteAccidentals);

        checkIntervalNameExists(intervalName);

        checkIntervalBuildingOrderExists(intervalOrder);
    }

    private static void intervalIdentificationArgumentCheck(String firstNoteName,
                                                            String firstNoteAccidentals,
                                                            String secondNoteName,
                                                            String secondNoteAccidentals,
                                                            String intervalOrder) {
        checkNoteWithAccidentalsExists(firstNoteName, firstNoteAccidentals);
        checkNoteWithAccidentalsExists(secondNoteName, secondNoteAccidentals);

        checkIntervalBuildingOrderExists(intervalOrder);
    }

    private static void checkNoteWithAccidentalsExists(String firstNoteName, String firstNoteAccidentals) {
        if (!NOTES_TO_SEMITONES_DEGREES_MAP.containsKey(firstNoteName)
                || !ACCIDENTALS_TO_SEMITONES_MAP.containsKey(firstNoteAccidentals)) {
            throw new IllegalArgumentException(INVALID_NOTE_ARGUMENT_EXCEPTION);
        }
    }

    private static void checkIntervalBuildingOrderExists(String intervalOrder) {
        if (!BUILDING_ORDERS.contains(intervalOrder)) {
            throw new IllegalArgumentException(INVALID_ORDER_ARGUMENT_EXCEPTION);
        }
    }

    private static void checkIntervalNameExists(String intervalName) {
        if (!INTERVALS_TO_SEMITONES_DEGREES_MAP.containsKey(intervalName)) {
            throw new IllegalArgumentException(INVALID_INTERVAL_ARGUMENT_EXCEPTION);
        }
    }

    private static String getAccidentalsBySemitoneCount(Integer semitoneCount) {
        return ACCIDENTALS_TO_SEMITONES_MAP
                .entrySet()
                .stream()
                .filter(entry -> semitoneCount.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findFirst().orElseThrow(() -> new NoSuchElementException(NO_SUCH_ACCIDENTAL_EXCEPTION));
    }

    private static String parseNoteAccidentals(String noteWithAccidentals) {
        return noteWithAccidentals.substring(1);
    }

    private static String parseNoteName(String noteWithAccidentals) {
        return noteWithAccidentals.substring(0, 1);
    }
//
//    private static String getSecondNoteNameByDegreeCount(String firstNote, int degreeCount,
//                                                         String intervalBuildingOrder) {
//        int secondIntervalNoteIndex;
//        int firstIntervalNoteIndex = NOTES.indexOf(firstNote);
//
//        if (intervalBuildingOrder.equals(DESCENDING_ORDER)) {
//            degreeCount = -degreeCount;
//        }
//        secondIntervalNoteIndex = Math.floorMod(firstIntervalNoteIndex + degreeCount, NOTES.size());
//        return NOTES.get(secondIntervalNoteIndex);
//    }
//
//    private static int getSemitoneCountBetweenNotes(String firstNote,
//                                                    String secondNote,
//                                                    String intervalBuildingOrder) {
//        int firstNoteIndex = NOTES.indexOf(firstNote);
//        int secondNoteIndex = NOTES.indexOf(secondNote);
//
//        int minIndex = Math.min(firstNoteIndex, secondNoteIndex);
//        int maxIndex = Math.max(firstNoteIndex, secondNoteIndex);
//
//        int semitoneCount = 0;
//        for (int i = minIndex; i < maxIndex; i++) {
//            semitoneCount += NATURAL_NOTES_TO_SEMITONES_MAP.get(NOTES.get(i));
//        }
//
//        if ((intervalBuildingOrder.equals(ASCENDING_ORDER) && firstNoteIndex > secondNoteIndex)
//                || (intervalBuildingOrder.equals(DESCENDING_ORDER) && firstNoteIndex < secondNoteIndex)) {
//            semitoneCount = OCTAVE_SEMITONE_COUNT - semitoneCount;
//        }
//
//        return semitoneCount;
//    }
//
//    private static int getNoteSemitoneCount(String firstNote,
//                                            String secondNote,
//                                            String firstNoteAccidentals,
//                                            int intervalSemitoneCount,
//                                            String intervalBuildingOrder) {
//        int semitoneCountBetweenNotes = getSemitoneCountBetweenNotes(firstNote, secondNote, intervalBuildingOrder);
//        int firstNoteSemitoneCount = accidentals.get(firstNoteAccidentals);
//        return firstNoteSemitoneCount + semitoneCountBetweenNotes - intervalSemitoneCount;
//    }
//
//    public static String intervalIdentification(String[] args) {
//        checkArgsArray(args);
//
//        String firstNoteName = parseNoteName(args[0]);
//        String firstNoteAccidentals = parseNoteAccidentals(args[0]);
//
//        String secondNoteName = parseNoteName(args[1]);
//        String secondNoteAccidentals = parseNoteAccidentals(args[1]);
//
//        String intervalOrder = args.length == 3 ? args[2] : ASCENDING_ORDER;
//
//        intervalIdentificationArgumentCheck(firstNoteName, firstNoteAccidentals,
//                secondNoteName, secondNoteAccidentals, intervalOrder);
//
//        accidentals = intervalOrder.equals(ASCENDING_ORDER) ? ACCIDENTALS_TO_SEMITONES_ASC_MAP
//                : ACCIDENTALS_TO_SEMITONES_DESC_MAP;
//
//        int intervalDegreeCount = getIntervalDegreeCountByNotes(firstNoteName, secondNoteName, intervalOrder);
//        int intervalSemitoneCount = getIntervalSemitoneCountByNotes(firstNoteName, firstNoteAccidentals,
//                secondNoteName, secondNoteAccidentals, intervalOrder);
//
//        Map<String, Integer> intervalSemitoneDegreeMap = Map.of(SEMITONE, intervalSemitoneCount,
//                DEGREE, intervalDegreeCount);
//
//        return INTERVALS_TO_SEMITONES_DEGREES_MAP
//                .entrySet()
//                .stream()
//                .filter(entry -> intervalSemitoneDegreeMap.equals(entry.getValue()))
//                .map(Map.Entry::getKey)
//                .findFirst()
//                .orElseThrow(() -> new NoSuchElementException(NO_SUCH_INTERVAL_EXCEPTION));
//    }
//
//    private static int getIntervalSemitoneCountByNotes(String firstNoteName,
//                                                       String firstNoteAccidentals,
//                                                       String secondNoteName,
//                                                       String secondNoteAccidentals,
//                                                       String intervalOrder) {
//        int naturalNoteSemitoneCount = getSemitoneCountBetweenNotes(firstNoteName, secondNoteName, intervalOrder);
//        int firstNoteSemitoneCount = accidentals.get(firstNoteAccidentals);
//        int secondNoteSemitoneCount = accidentals.get(secondNoteAccidentals);
//        return naturalNoteSemitoneCount + firstNoteSemitoneCount - secondNoteSemitoneCount;
//    }
//
//    private static int getIntervalDegreeCountByNotes(String firstNote,
//                                                     String secondNote,
//                                                     String intervalBuildingOrder) {
//        int firstNoteIndex = NOTES.indexOf(firstNote);
//        int secondNoteIndex = NOTES.indexOf(secondNote);
//
//        int intervalDegreeCount = Math.abs(firstNoteIndex - secondNoteIndex);
//        if ((intervalBuildingOrder.equals(ASCENDING_ORDER) && firstNoteIndex > secondNoteIndex)
//                || (intervalBuildingOrder.equals(DESCENDING_ORDER) && firstNoteIndex < secondNoteIndex)) {
//            intervalDegreeCount = NOTES.size() - intervalDegreeCount;
//        }
//        return intervalDegreeCount + 1;
//    }
}
