/*
 * This file is part of the PSL software.
 * Copyright 2011-2015 University of Maryland
 * Copyright 2013-2022 The Regents of the University of California
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.linqs.psl.cli;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.linqs.psl.database.ReadableDatabase;
import org.linqs.psl.evaluation.statistics.ContinuousEvaluator;
import org.linqs.psl.evaluation.statistics.DiscreteEvaluator;
import org.linqs.psl.model.term.Constant;
import org.linqs.psl.model.term.ConstantType;
import org.linqs.psl.model.term.UniqueStringID;

import org.junit.Test;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class SimpleAcquaintancesTest extends CLITest {
    @Test
    public void testOnlineBase() {
        String modelPath = Paths.get(baseModelsDir, "simple-acquaintances.psl").toString();
        String dataPath = Paths.get(baseDataDir, "simple-acquaintances", "base.data").toString();
        String actionPath = Paths.get(baseOnlineActionsDir, "simple-acquaintances", "base-actions.txt").toString();

        String clientOutput = runOnline(modelPath, dataPath, actionPath);
        assertTrue(clientOutput.contains("OnlinePSL inference stopped."));
    }

    @Test
    public void testOnlinePredicateError() {
        String modelPath = Paths.get(baseModelsDir, "simple-acquaintances.psl").toString();
        String dataPath = Paths.get(baseDataDir, "simple-acquaintances", "base.data").toString();
        String actionPath = Paths.get(baseOnlineActionsDir, "simple-acquaintances", "predicate-error-actions.txt").toString();

        String clientOutput = runOnline(modelPath, dataPath, actionPath);
        assertTrue(clientOutput.contains("Error parsing command:"));
    }
}
