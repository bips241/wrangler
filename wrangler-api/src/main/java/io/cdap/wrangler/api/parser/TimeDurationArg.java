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

/**
 * Represents a time duration token (e.g., "2h", "30m", "100s").
 */
public class TimeDurationArg implements Token {
    private final String duration;

    public TimeDurationArg(String duration) {
        this.duration = duration;
    }

    public String getDuration() {
        return duration;
    }

    @Override
    public Object value() {
        return duration;
    }

    @Override
    public TokenType type() {
        return TokenType.TIME_DURATION;  // Replace with the correct enum for time duration
    }

    @Override
    public JsonElement toJson() {
        return new JsonPrimitive(duration);
    }

    @Override
    public String toString() {
        return duration;
    }
}
