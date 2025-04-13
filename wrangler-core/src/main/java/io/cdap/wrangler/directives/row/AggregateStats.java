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

package io.cdap.wrangler.directives.row;

import java.util.Collections;
import java.util.List;

import io.cdap.wrangler.api.DirectiveExecutionException;
import io.cdap.wrangler.api.DirectiveParseException;
import io.cdap.wrangler.api.ExecutorContext;
import io.cdap.wrangler.api.parser.ByteSize;
import io.cdap.wrangler.api.parser.TimeDuration;
import io.cdap.wrangler.api.parser.UsageDefinition;
import io.cdap.wrangler.api.Arguments;
import io.cdap.wrangler.api.Row;
import io.cdap.wrangler.api.parser.TokenType;
import io.cdap.wrangler.api.Executor;

public class AggregateStats implements Executor<List<Row>, List<Row>> {
    private String sourceSizeCol;
    private String sourceTimeCol;
    private String targetSizeCol;
    private String targetTimeCol;
    private String sizeUnit;
    private String timeUnit;
    private String aggregationType;

    public UsageDefinition define() {
        UsageDefinition.Builder builder = UsageDefinition.builder("aggregate-stats");
        builder.define("sourceSizeCol", TokenType.IDENTIFIER);
        builder.define("sourceTimeCol", TokenType.IDENTIFIER);
        builder.define("targetSizeCol", TokenType.IDENTIFIER);
        builder.define("targetTimeCol", TokenType.IDENTIFIER);
        builder.define("sizeUnit", TokenType.TEXT);
        builder.define("timeUnit", TokenType.TEXT);
        builder.define("aggregationType", TokenType.TEXT);
        return builder.build();
    
    }

    @Override
    public void initialize(Arguments arguments) throws DirectiveParseException {
        this.sourceSizeCol = arguments.value("sourceSizeCol");
        this.sourceTimeCol = arguments.value("sourceTimeCol");
        this.targetSizeCol = arguments.value("targetSizeCol");
        this.targetTimeCol = arguments.value("targetTimeCol");
        this.sizeUnit = arguments.value("sizeUnit") != null ? arguments.value("sizeUnit") : "MB";
        this.timeUnit = arguments.value("timeUnit") != null ? arguments.value("timeUnit") : "seconds";
        this.aggregationType = arguments.value("aggregationType") != null ? arguments.value("aggregationType") : "total";
    }

    @Override
    public List<Row> execute(List<Row> rows, ExecutorContext context) throws DirectiveExecutionException {
        long totalBytes = 0L;
        long totalTimeNanos = 0L;

        for (Row row : rows) {
            ByteSize byteSize = parseByteSize(row.getValue(sourceSizeCol));
            TimeDuration timeDuration = parseTimeDuration(row.getValue(sourceTimeCol));
            totalBytes += byteSize.getBytes();
            totalTimeNanos += timeDuration.toNanos(); // Adjusted to use the correct method
        }

        double finalSize = convertBytes(totalBytes, sizeUnit);
        double finalTime = convertTime(totalTimeNanos, timeUnit);

        if ("average".equalsIgnoreCase(aggregationType) && !rows.isEmpty()) {
            finalSize /= rows.size();
            finalTime /= rows.size();
        }

        Row result = new Row();
        result.add(targetSizeCol, finalSize);
        result.add(targetTimeCol, finalTime);

        return Collections.singletonList(result);
    }

    private ByteSize parseByteSize(Object value) throws DirectiveExecutionException {
        if (value instanceof String) {
            return new ByteSize((String) value);
        }
        throw new DirectiveExecutionException("Invalid byte size value: " + value);
    }

    private TimeDuration parseTimeDuration(Object value) throws DirectiveExecutionException {
        if (value instanceof String) {
            return new TimeDuration((String) value);
        }
        throw new DirectiveExecutionException("Invalid time duration value: " + value);
    }

    private double convertBytes(long bytes, String unit) {
        switch (unit.toUpperCase()) {
            case "KB": return bytes / 1024.0;
            case "MB": return bytes / (1024.0 * 1024);
            case "GB": return bytes / (1024.0 * 1024 * 1024);
            default: return bytes;
        }
    }

    private double convertTime(long nanos, String unit) {
        switch (unit.toLowerCase()) {
            case "milliseconds": return nanos / 1_000_000.0;
            case "seconds": return nanos / 1_000_000_000.0;
            case "minutes": return nanos / (1_000_000_000.0 * 60);
            case "hours": return nanos / (1_000_000_000.0 * 3600);
            default: return nanos;
        }
    }

    @Override
    public void destroy() {
        // No-op
    }
}
