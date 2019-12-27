package ro.anud.anud;

import java.time.temporal.ValueRange;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RandomString {
    private Map<String, List<Map.Entry<String, String>>> chunkData;
    private List<String> startChunk;
    private Random random;
    private ValueRange valueRange;
    private Integer chunkSize;

    public static List<Map.Entry<String, String>> tokenize(String string, int chunkSize) {
        return Stream.iterate(0,
                              i -> i <= string.length() - chunkSize,
                              i -> i + 1)
                .map(i -> {
                    var next = "";
                    if (!((i + chunkSize) >= string.length())) {
                        next = string.charAt(i + chunkSize) + "";
                    }

                    var chunk = string.substring(i, i + chunkSize);
                    return new AbstractMap.SimpleEntry<>(chunk.toLowerCase(), next.toLowerCase());
                })
                .collect(Collectors.toList());
    }

    public RandomString(List<String> inputList, int chunkSize, ValueRange valueRange, Random random) {
        chunkData = inputList.stream()
                .flatMap(s -> tokenize(s, chunkSize).stream())
                .distinct()
                .collect(Collectors.groupingBy(Map.Entry::getKey));
        startChunk = inputList.stream()
                .map(s -> {
                    if (s.length() < chunkSize) {
                        return s;
                    }
                    return s.substring(0, chunkSize).toLowerCase();
                })
                .collect(Collectors.toList());
        this.valueRange = valueRange;
        this.chunkSize = chunkSize;
        this.random = random;
    }

    private String choseToken(String initialToken) {
        var followingList = chunkData.get(initialToken);
        if (followingList.size() > 1) {

            return followingList
                    .get(random.nextInt(followingList.size()))
                    .getValue();
        }
        return followingList.get(0)
                .getValue();
    }

    private String getStartToken() {
        return startChunk.get(random.nextInt(startChunk.size()));
    }


    public String generate() {
        while (true) {
            var generate = generateString();
            if (generate.length() >= valueRange.getMinimum()
                    && generate.length() <= valueRange.getMaximum()) {
                return generate;
            }
        }
    }

    private String generateString() {

        var token = new AtomicReference<>(getStartToken());
        Stream.generate(() -> "")
                .takeWhile(s -> {
                    var nextLetter = choseToken(token.get().substring(token.get().length() - chunkSize));
                    if (nextLetter.equals("")) {
                        return false;
                    }
                    token.accumulateAndGet(nextLetter,
                                           String::concat);
                    return true;
                })
                .forEach(s -> {});
        return token.get();
    }
}
