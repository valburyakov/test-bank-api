package com.test.bank.utils;

import com.github.difflib.DiffUtils;
import com.github.difflib.UnifiedDiffUtils;
import com.github.difflib.patch.Patch;
import com.test.bank.model.Diff;
import com.test.bank.model.TestCase;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class DiffExtractor {

    public static Diff of(TestCase originalCase, TestCase revisedCase) {
        List<String> original = testCaseToList(originalCase);

        List<String> revised = testCaseToList(revisedCase);

        return of(originalCase.getTitle(), revisedCase.getTitle(), original, revised);
    }

    private static List<String> testCaseToList(TestCase testCase) {
        List<String> list = new ArrayList<>();

        list.add("Author: " + testCase.getChangedBy());
        list.add("Reference: " + testCase.getReference());
        list.add("Title: " + testCase.getTitle());

        return list;
    }


    public static Diff of(String oldTitle, String newTitle, List<String> original, List<String> revised) {
        try {
            Patch<String> patch = DiffUtils.diff(original, revised);
            List<String> unifiedDiff = UnifiedDiffUtils.generateUnifiedDiff(oldTitle, newTitle, original, patch, 20);
            return new Diff(String.join("\n", original),
                    String.join("\n", revised),
                    String.join("\n", unifiedDiff));
        } catch (Exception exception) {
            return empty();
        }
    }

    public static Diff of(String original, String revised) {
        List<String> originalData = asList(original);
        List<String> revisedData = asList(revised);
        return of(original, revised, originalData, revisedData);

    }

    public static Diff empty() {
        return new Diff("", "", "");
    }
}
