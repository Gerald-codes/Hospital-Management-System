package org.lucas.util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * TypeAdapter for Gson that serializes LocalDateTime into an ISO 8601 String.
 * Standard industry practice.
 */
public class LocalDateTimeTypeAdapter extends TypeAdapter<LocalDateTime> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    @Override
    public void write(JsonWriter out, LocalDateTime value) throws IOException {
        if (value == null) {
            // Handle null
            out.nullValue();
            return;
        }
        out.value(formatter.format(value));
    }

    @Override
    public LocalDateTime read(JsonReader in) throws IOException {
        if (in.peek() == com.google.gson.stream.JsonToken.NULL) {
            // handle null values
            in.nextNull();
            return null;
        }
        // read the JSON value as a string
        String dateTimeString = in.nextString();
        try {
            // Parse string back to LocalDateTime
            return LocalDateTime.parse(dateTimeString, formatter);
        } catch (DateTimeParseException e) {
            throw new IOException("Failed to parse LocalDateTime: " + dateTimeString, e);
        }
    }
}