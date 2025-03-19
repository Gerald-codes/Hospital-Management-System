package org.lucas.ui.framework.views;

/**
 * defines an interface for the lambda method for user input. Should not be accessed by user (as in programmers).
 */
public interface UserInputResult {
    void onInput(String userInput);
}
