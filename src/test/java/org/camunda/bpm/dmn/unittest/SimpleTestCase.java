/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH
 * under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright
 * ownership. Camunda licenses this file to you under the Apache License,
 * Version 2.0; you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.bpm.dmn.unittest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

import java.io.InputStream;
import org.camunda.bpm.dmn.engine.DmnDecision;
import org.camunda.bpm.dmn.engine.DmnDecisionRuleResult;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.dmn.engine.test.junit5.DmnEngineExtension;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(DmnEngineExtension.class)
public class SimpleTestCase {

  @Test
  public void shouldEvaluateDecision(DmnEngine dmnEngine) {
    // Parse decision
    InputStream inputStream = getClass().getResourceAsStream("Example.dmn");
    DmnDecision decision = dmnEngine.parseDecision("orderDecision", inputStream);

    // Set input variables
    VariableMap variables = Variables.createVariables()
      .putValue("status", "silver")
      .putValue("sum", 9000);

    // Evaluate decision with id 'orderDecision' from file 'Example.dmn'
    DmnDecisionTableResult results = dmnEngine.evaluateDecisionTable(decision, variables);

    // Check that one rule has matched
    assertThat(results).hasSize(1);

    DmnDecisionRuleResult result = results.getSingleResult();
    assertThat(result)
      .containsOnly(
        entry("result", "notok"),
        entry("reason", "you took too much man, you took too much!")
      );

    // Change input variables
    variables.putValue("status", "gold");

    // Evaluate decision again
    results = dmnEngine.evaluateDecisionTable(decision, variables);

    // Check new result
    assertThat(results).hasSize(1);

    result = results.getSingleResult();
    assertThat(result)
      .containsOnly(
        entry("result", "ok"),
        entry("reason", "you get anything you want")
      );
  }

}
