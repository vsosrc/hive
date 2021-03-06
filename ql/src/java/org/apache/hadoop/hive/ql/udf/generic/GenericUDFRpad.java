/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hadoop.hive.ql.udf.generic;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.io.Text;

/**
 * UDFRpad.
 *
 */
@Description(name = "rpad", value = "_FUNC_(str, len, pad) - " +
    "Returns str, right-padded with pad to a length of len",
    extended = "If str is longer than len, the return value is shortened to "
    + "len characters.\n"
    + "Example:\n"
    + "  > SELECT _FUNC_('hi', 5, '??') FROM src LIMIT 1;\n"
    + "  'hi???'" + "  > SELECT _FUNC_('hi', 1, '??') FROM src LIMIT 1;\n" + "  'h'")
public class GenericUDFRpad extends GenericUDFBasePad {
  public GenericUDFRpad() {
    super("rpad");
  }

  @Override
  protected void performOp(byte[] data, byte[] txt, byte[] padTxt, int len, Text str, Text pad) {
    int pos;
    // Copy the text
    for (pos = 0; pos < str.getLength() && pos < len; pos++) {
      data[pos] = txt[pos];
    }

    // Copy the padding
    while (pos < len) {
      for (int i = 0; i < pad.getLength() && i < len - pos; i++) {
        data[pos + i] = padTxt[i];
      }
      pos += pad.getLength();
    }
  }
}
