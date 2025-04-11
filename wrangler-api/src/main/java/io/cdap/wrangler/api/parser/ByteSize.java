package io.cdap.wrangler.api.parser;

import java.util.Locale;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


public class ByteSize implements Token {

    private Double bytes;

   /**
   * Constructs a ByteSize token.
   *
   * @param value The string representation of byte size (e.g., "10KB").
   */
    public ByteSize(String value){
        this.bytes = parseBytes(value);
    }
   
   /**
   * Returns the value of this {@code ByteSize} object as a {@code Double}
   * representing the size in bytes.
   *
   * @return the byte size value of this object.
   */
    @Override  
    public Double value() {
        return getBytes();
    }


    /**
   * Returns the value in bytes.
   *
   * @return Byte size in long.
   */
    public double getBytes() {
        return (double) this.bytes;
    }

   /**
   * Returns the type of this {@code BYTE_SIZE} object as a {@code TokenType}
   * enum.
   *
   * @return the enumerated {@code TokenType} of this object.
   */
    @Override
    public TokenType type() {
        return TokenType.BYTE_SIZE;
    }

    /**
   * Parses the input string and converts it to bytes.
   *
   * @param input The input byte size string.
   * @return The size in bytes.
   * @throws IllegalArgumentException if input is not a valid byte size string.
   */

    private Double parseBytes(String value) {
        String normalize = value.trim().toLowerCase(Locale.ENGLISH);
        double value_double = Double.parseDouble(normalize.replaceAll("[^0-9.]", ""));

        if (normalize.endsWith("kb")) {
            return (double) (value_double * 1024);
        }else if (normalize.endsWith("mb")) {
            return (double) (value_double * 1024 * 1024);
        } else if (normalize.endsWith("gb")) {
            return (double) (value_double * 1024 * 1024 * 1024);
        }  else if (normalize.endsWith("tb")) {
            return (double) (value_double  * 1024 * 1024 * 1024 * 1024);
        } else {
            throw new IllegalArgumentException("Unsupported byte unit in: " + value);
        }

    }


  /**
   * Returns the members of this {@code BYTE_SIZE} object as a {@code JsonElement}.
   *
   * @return Json representation of this {@code BYTE_SIZE} object as {@code JsonElement}
   */
    @Override
    public JsonElement toJson() {

        JsonObject object = new JsonObject();
        object.addProperty("type", TokenType.BYTE_SIZE.name());
        object.addProperty("value", this.bytes);
        return object;
    }
} 