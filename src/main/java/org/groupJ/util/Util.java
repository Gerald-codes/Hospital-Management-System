package org.groupJ.util;

import java.lang.reflect.Type;

import static org.groupJ.Globals.gson;

public class Util {

    /**
     * Creates the specified object from the given JSON string. Relies on static gson object defined in Globals.java
     * You can also do this with gson, but I made this to keep it simple.
     * @param jsonString JSON string
     * @param clazz class type
     * @return Object of type T
     * @param <T> Type T of the object
     */
    public static<T> T fromJsonString(String jsonString, Class<T> clazz){
        return gson.fromJson(jsonString, clazz);
    }

    public static <T> T fromJsonString(String jsonString, Type type) {
        return gson.fromJson(jsonString, type); // Gson's fromJson method has an overload that takes Type
    }
}
