package com.test.bank.utils;

import com.github.difflib.DiffUtils;
import com.github.difflib.UnifiedDiffUtils;
import com.github.difflib.patch.Patch;
import com.github.difflib.text.DiffRow;
import com.github.difflib.text.DiffRowGenerator;
import com.test.bank.dto.TestCaseDTO;
import com.test.bank.model.Diff;
import com.test.bank.model.TestCase;
import lombok.SneakyThrows;

import java.util.List;

import static java.util.Arrays.asList;
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

    public static Diff of(TestCase originalCase, TestCase revisedCase) {
        List<String> original = asList(originalCase.getTitle(), originalCase.getReference());

        List<String> revised = asList(revisedCase.getTitle(), revisedCase.getReference());

        return of(original, revised);
    }


    public static Diff of(List<String> original, List<String> revised) {
        try {
            Patch<String> patch = DiffUtils.diff(original, revised);
            List<String> unifiedDiff = UnifiedDiffUtils.generateUnifiedDiff("old", "new", original, patch, 20);
            return new Diff(String.join(",", original),
                    String.join(",", revised),
                    String.join(",", unifiedDiff));
        } catch (Exception exception) {
            return empty();
        }
    }

    public static Diff of(String original, String revised) {
        List<String> originalData = asList(original);
        List<String> revisedData = asList(revised);
        return of(originalData, revisedData);

    }

    public static Diff empty() {
        return new Diff("", "", "");
    }
}
