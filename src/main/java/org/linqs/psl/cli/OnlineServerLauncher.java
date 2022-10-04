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

/**
 * A server interface to online PSL than thinly wraps the CLI launcher.
 */
public class OnlineServerLauncher extends Launcher {
    private OnlineServerLauncher() {
        super(null);
    }

    public static void main(String[] args) {
        main(args, false);
    }

    public static void main(String[] args, boolean rethrow) {
        Launcher.main(args, rethrow);
    }
}
