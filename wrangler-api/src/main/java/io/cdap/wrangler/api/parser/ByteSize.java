/*
 * Copyright © 2025 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy
 * of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

 package io.cdap.wrangler.api.parser;

 import com.google.gson.JsonElement;
 import com.google.gson.JsonPrimitive;
 
 import java.util.regex.Matcher;
 import java.util.regex.Pattern;
 
 /**
  * Token that represents a byte size (e.g., 10KB, 5MB).
  */
 public class ByteSize implements Token {
 
   private static final Pattern PATTERN =
     Pattern.compile("(\\d+)([KMGTP]?B)", Pattern.CASE_INSENSITIVE);
 
   private final long bytes;
   private final String original;
 
   /**
    * Constructs a ByteSize token by parsing the given string.
    *
    * @param value the string representing the byte size
    */
   public ByteSize(String value) {
     this.original = value.trim().toUpperCase();
     Matcher matcher = PATTERN.matcher(this.original);
 
     if (!matcher.matches()) {
       throw new IllegalArgumentException("Invalid byte size format: " + value);
     }
 
     long number = Long.parseLong(matcher.group(1));
     String unit = matcher.group(2);
 
     switch (unit) {
       case "KB":
         this.bytes = number * 1024L;
         break;
       case "MB":
         this.bytes = number * 1024L * 1024L;
         break;
       case "GB":
         this.bytes = number * 1024L * 1024L * 1024L;
         break;
       case "TB":
         this.bytes = number * 1024L * 1024L * 1024L * 1024L;
         break;
       case "PB":
         this.bytes = number * 1024L * 1024L * 1024L * 1024L * 1024L;
         break;
       case "B":
         this.bytes = number;
         break;
       default:
         throw new IllegalArgumentException("Unknown byte unit: " + unit);
     }
   }
 
   /**
    * Returns the byte count.
    *
    * @return the byte count
    */
   public long getBytes() {
     return bytes;
   }
 
   @Override
   public Object value() {
     return bytes;
   }
 
   @Override
   public TokenType type() {
     return TokenType.BYTE_SIZE;
   }
 
   @Override
   public JsonElement toJson() {
     return new JsonPrimitive(bytes);
   }
 
   @Override
   public String toString() {
     return original;
   }
 }
 