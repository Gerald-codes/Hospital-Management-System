package org.groupJ.util;

import static org.groupJ.Globals.gson;
import static org.groupJ.Globals.gsonPrettyPrint;
/**
 * Provides a base interface for all objects in the system requiring standardized
 * methods for equality checks, JSON serialization, and content printing.
 * This interface ensures consistency across different objects, especially for
 * operations involving comparisons and debug output.
 */

public interface ObjectBase {
    /**
     * Determines whether some other object is "equal to" this one.
     * Implementing classes must override this method to define equality based on their specific state.
     *
     * @param obj the reference object with which to compare.
     * @return true if this object is the same as the obj argument; false otherwise.
     */
    public boolean equals(Object obj);
    /**
     * Returns an efficiently formatted JSON string, relies on static gson object defined in Globals.java
     * @return Efficiently formatted JSON string
     */
    default String toJsonString(){
        return gson.toJson(this);
    }

    /**
     * Returns a PRETTY formatted JSON string, relies on static gson object defined in Globals.java
     * Useful for debugging and inspecting object data
     * @return Pretty formatted JSON string
     */
    default String toPrettyJsonString(){
        return gsonPrettyPrint.toJson(this);
    }

    /**
     * Prints the contents of the object in a pretty JSON format to the console.
     * This is primarily used for debugging and inspecting object states during development.
     */
    default void printContents(){System.out.println(toPrettyJsonString());}
}
