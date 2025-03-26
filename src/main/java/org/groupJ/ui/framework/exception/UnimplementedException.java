package org.groupJ.ui.framework.exception;

public class UnimplementedException extends Exception {
    public UnimplementedException(String message) {
        super("NOT IMPLEMENTED: " + message);
    }
}
