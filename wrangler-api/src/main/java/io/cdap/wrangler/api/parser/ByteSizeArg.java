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
 * Represents a byte size token (e.g., "10MB", "500KB", "2GB").
 */
public class ByteSizeArg implements Token {
    private final String byteSize;

    public ByteSizeArg(String byteSize) {
        this.byteSize = byteSize;
    }

    public String getByteSize() {
        return byteSize;
    }

    @Override
    public Object value() {
        return byteSize;
    }

    @Override
    public TokenType type() {
        return TokenType.BYTE_SIZE;  // Replace with the correct enum for byte size
    }

    @Override
    public JsonElement toJson() {
        return new JsonPrimitive(byteSize);
    }

    @Override
    public String toString() {
        return byteSize;
    }
}
