/*
 * Copyright © 2017-2019 Cask Data, Inc.
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

 import io.cdap.wrangler.api.annotations.PublicEvolving;
 
 import java.io.Serializable;
 
 /**
  * The TokenType class provides the enumerated types for different types of
  * tokens that are supported by the grammar.
  *
  * Each of the enumerated types specified in this class also has an associated
  * object representing it. For example, {@code DIRECTIVE_NAME} is represented by
  * the object {@code DirectiveName}.
  *
  * @see Bool
  * @see BoolList
  * @see ColumnName
  * @see ColumnNameList
  * @see DirectiveName
  * @see Numeric
  * @see NumericList
  * @see Properties
  * @see Ranges
  * @see Expression
  * @see Text
  * @see TextList
  */
 @PublicEvolving
 public enum TokenType implements Serializable {
 
   /**
    * Represents the directive name token.
    */
   DIRECTIVE_NAME,
 
   /**
    * Represents a column name token (e.g., :<column-name>).
    */
   COLUMN_NAME,
 
   /**
    * Represents a string token (quoted with single or double quotes).
    */
   TEXT,
 
   /**
    * Represents a numeric token (integer or real number).
    */
   NUMERIC,
 
   /**
    * Represents a boolean token ('true' or 'false').
    */
   BOOLEAN,
 
   /**
    * Represents a comma-separated list of column names.
    * Example: ColumnName[,ColumnName]*
    */
   COLUMN_NAME_LIST,
 
   /**
    * Represents a comma-separated list of quoted text values.
    * Example: Text[,Text]*
    */
   TEXT_LIST,
 
   /**
    * Represents a comma-separated list of numeric values.
    * Example: Numeric[,Numeric]*
    */
   NUMERIC_LIST,
 
   /**
    * Represents a comma-separated list of boolean values.
    * Example: Boolean[,Boolean]*
    */
   BOOLEAN_LIST,
 
   /**
    * Represents an expression block.
    * Example: exp:{ <expression || condition> }
    */
   EXPRESSION,
 
   /**
    * Represents a collection of key-value properties.
    * Example: prop:{ key=value[,key=value]* }
    */
   PROPERTIES,
 
   /**
    * Represents a collection of range mappings.
    * Example: <start>:<end>=value[,<start>:<end>=value]*
    */
   RANGES,
 
   /**
    * Represents a string identifier with character restrictions.
    */
   IDENTIFIER,
 
   /**
    * Represents a byte size token (e.g., 10MB, 256KB).
    */
   BYTE_SIZE,
 
   /**
    * Represents a time duration token (e.g., 10s, 5m).
    */
   TIME_DURATION
 }
 