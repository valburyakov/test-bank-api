package com.test.bank.utils;

import com.github.difflib.text.DiffRow;
import com.github.difflib.text.DiffRowGenerator;
import lombok.SneakyThrows;

import java.util.List;

import static java.util.Collections.singletonList;

public class DiffExtractor {

    private static final DiffRowGenerator generator = DiffRowGenerator.create()
            .showInlineDiffs(true)
            .mergeOriginalRevised(true)
            .inlineDiffByWord(true)
            .oldTag(f -> "~")
            .newTag(f -> "**")
            .build();

    @SneakyThrows
    public static List<DiffRow> generateDiff(List<String> original, List<String> revisited) {
        return generator.generateDiffRows(original, revisited);
    }

    @SneakyThrows
    public static String generateDiff(String original, String revisited) {
        return generateDiff(singletonList(original), singletonList(revisited)).get(0).getOldLine();
    }
}
