package org.lucas.util;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code TextSplitter} class provides utility methods for processing and formatting text.
 * Specifically, it offers functionality to split long strings into multiple lines without breaking words,
 * making it useful for display purposes in text-based UIs or reports.
 *
 * <p>This class is designed to handle text wrapping by identifying the last whitespace character
 * within a specified maximum line length. If no whitespace is found, it splits the text at the maximum length.</p>
 *
 * <p><strong>Example Usage:</strong></p>
 * <pre>{@code
 * String text = "This is a long string that needs to be wrapped into smaller chunks.";
 * List<String> wrappedLines = TextSplitter.splitText(text, 20);
 * wrappedLines.forEach(System.out::println);
 * }</pre>
 *
 * <p><strong>Output:</strong></p>
 * <pre>
 * This is a long string
 * that needs to be
 * wrapped into smaller
 * chunks.
 * </pre>
 *
 */
public class TextSplitter {

    /**
     * Splits the provided text into multiple lines without breaking words. Each line will have
     * a maximum length specified by {@code maxLineLength}. If a word exceeds the maximum line length,
     * it will be split at the maximum length.
     *
     * @param text The text to be split into lines.
     * @param maxLineLength The maximum length of each line.
     * @return A {@link List} of strings, where each string represents a line of text.
     *
     * @throws IllegalArgumentException if {@code maxLineLength} is less than 1.
     */
    public static List<String> splitText(String text, int maxLineLength) {
        if (maxLineLength < 1) {
            throw new IllegalArgumentException("Maximum line length must be greater than 0.");
        }

        List<String> lines = new ArrayList<>();
        while (text.length() > maxLineLength) {
            int splitIndex = text.lastIndexOf(" ", maxLineLength);
            if (splitIndex == -1) splitIndex = maxLineLength;  // If no space is found, split at max length
            lines.add(text.substring(0, splitIndex).trim());
            text = text.substring(splitIndex).trim();
        }
        lines.add(text);  // Add any remaining text
        return lines;
    }

}