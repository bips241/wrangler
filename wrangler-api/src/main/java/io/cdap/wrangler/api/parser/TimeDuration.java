/*
 * Copyright © 2025 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
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
  * Token that represents a time duration (e.g., 10s, 5m, 1h).
  */
 public class TimeDuration implements Token {
 
   private static final Pattern PATTERN =
     Pattern.compile("(\\d+)(ms|s|m|h)", Pattern.CASE_INSENSITIVE);
 
   private final long milliseconds;
   private final String original;
 
   /**
    * Constructs a TimeDuration token by parsing the given string.
    *
    * @param value the string representing the time duration
    */
   public TimeDuration(String value) {
     this.original = value.trim().toLowerCase();
     Matcher matcher = PATTERN.matcher(this.original);
 
     if (!matcher.matches()) {
       throw new IllegalArgumentException("Invalid time duration format: " + value);
     }
 
     long number = Long.parseLong(matcher.group(1));
     String unit = matcher.group(2);
 
     switch (unit) {
       case "ms":
         this.milliseconds = number;
         break;
       case "s":
         this.milliseconds = number * 1000L;
         break;
       case "m":
         this.milliseconds = number * 60L * 1000L;
         break;
       case "h":
         this.milliseconds = number * 60L * 60L * 1000L;
         break;
       default:
         throw new IllegalArgumentException("Unknown time unit: " + unit);
     }
   }
 
   /**
    * Returns the duration in milliseconds.
    *
    * @return duration in milliseconds
    */
   public long getMilliseconds() {
     return milliseconds;
   }
 
   @Override
   public Object value() {
     return milliseconds;
   }
 
   @Override
   public TokenType type() {
     return TokenType.TIME_DURATION;
   }
 
   @Override
   public JsonElement toJson() {
     return new JsonPrimitive(milliseconds);
   }
 
   @Override
   public String toString() {
     return original;
   }
 }
 