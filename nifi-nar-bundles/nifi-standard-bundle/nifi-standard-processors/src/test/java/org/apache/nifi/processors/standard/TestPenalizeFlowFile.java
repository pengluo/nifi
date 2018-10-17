/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.nifi.processors.standard;

import org.apache.nifi.util.MockFlowFile;
import org.apache.nifi.util.TestRunner;
import org.apache.nifi.util.TestRunners;
import org.junit.Assert;
import org.junit.Test;


public class TestPenalizeFlowFile {

    @Test
    public void testFlowFilePenalization(){
        final TestRunner runner = TestRunners.newTestRunner(new PenalizeFlowFile());
        runner.enqueue("");
        runner.run();
        runner.assertPenalizeCount(1);
        runner.assertAllFlowFilesTransferred(PenalizeFlowFile.REL_SUCCESS);

        MockFlowFile ff = runner.getFlowFilesForRelationship(PenalizeFlowFile.REL_SUCCESS).get(0);
        Assert.assertTrue(ff.isPenalized());

        runner.clearTransferState();

        runner.enqueue(ff);
        runner.run();
        runner.assertTransferCount(PenalizeFlowFile.REL_SUCCESS, 1);

        ff = runner.getFlowFilesForRelationship(PenalizeFlowFile.REL_SUCCESS).get(0);
        Assert.assertTrue(ff.isPenalized());
    }
}
