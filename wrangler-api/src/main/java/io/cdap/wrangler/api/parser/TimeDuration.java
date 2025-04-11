package io.cdap.wrangler.api.parser;

import java.util.Locale;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class TimeDuration implements Token {

    private Double time;

    /**
     * Constructs a TimeDuration token.
     *
     * @param value The string representation of time value (e.g., "10sec").
     */
    public TimeDuration(String value) {
        this.time = parseMilliSeconds(value);
    }

    /**
     * Returns the value of this {@code TIME_DURATION} object as a
     * {@code Double} representing the time in milliseconds.
     *
     * @return the time (milliseconds) value of this object.
     */
    @Override
    public Double value() {
        return getTime();
    }

    /**
     * Returns the value in milliseconds.
     *
     * @return time value in double.
     */
    public double getTime() {
        return (double) this.time;
    }

    /**
     * Returns the type of this {@code TIME_DURATION} object as a
     * {@code TokenType} enum.
     *
     * @return the enumerated {@code TokenType} of this object.
     */
    @Override
    public TokenType type() {
        return TokenType.TIME_DURATION;
    }

    /**
     * Parses the input string and converts it to milliseconds.
     *
     * @param input The input time value string.
     * @return The time in milliseconds.
     * @throws IllegalArgumentException if input is not a valid time unit
     * string.
     */
    private Double parseMilliSeconds(String value) {
        String normalize = value.trim().toLowerCase(Locale.ENGLISH);
        double value_double = Double.parseDouble(normalize.replaceAll("[^0-9.]", ""));

        if (normalize.endsWith("ms")) {
            return (double) (value_double);
        } else if (normalize.endsWith("s") || normalize.endsWith("sec") || normalize.endsWith("secs")) {
            return (double) (value_double * 1000);
        } else if (normalize.endsWith("m") || normalize.endsWith("min") || normalize.endsWith("mins")) {
            return (double) (value_double * 60 * 1000);
        } else if (normalize.endsWith("h") || normalize.endsWith("hr") || normalize.endsWith("hour") || normalize.endsWith("hours")) {
            return (double) (value_double * 60 * 60 * 1000);
        } else {
            throw new IllegalArgumentException("Unsupported time unit in: " + value);
        }

    }

    /**
     * Returns the members of this {@code TIME_DURATION} object as a
     * {@code JsonElement}.
     *
     * @return Json representation of this {@code TIME_DURATION} object as
     * {@code JsonElement}
     */
    @Override
    public JsonElement toJson() {

        JsonObject object = new JsonObject();
        object.addProperty("type", TokenType.TIME_DURATION.name());
        object.addProperty("value", this.time);
        return object;
    }
}
