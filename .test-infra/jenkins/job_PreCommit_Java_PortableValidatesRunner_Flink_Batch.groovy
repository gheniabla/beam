/*
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

import CommonTestProperties
import PrecommitJobBuilder

// This job runs the suite of ValidatesRunner tests against the Flink runner.
PrecommitJobBuilder builder = new PrecommitJobBuilder(
    scope: this,
    nameBase: 'Java_PVR_Flink_Batch',
    gradleTask: ":runners:flink:${CommonTestProperties.getFlinkVersion()}:job-server:validatesPortableRunnerBatch",
    timeoutMins: 240,
    triggerPathPatterns: [
      '^sdks/java/core/src/test/java/org/apache/beam/sdk/transforms/.*$',
      '^runners/flink/.*$',
      '^runners/java-fn-execution/.*$',
    ],
    )
builder.build {
  previousNames('beam_PostCommit_Java_PVR_Flink_Batch')
  // Publish all test results to Jenkins.
  publishers {
    archiveJunit('**/build/test-results/**/*.xml')
  }
}
