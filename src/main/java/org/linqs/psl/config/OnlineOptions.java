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
package org.linqs.psl.config;

/**
 * Additional options for the psl-onine module.
 */
public class OnlineOptions {
    public static final Option ONLINE_HOST = new Option(
        "inference.onlinehostname",
        "127.0.0.1",
        "The hostname for the online server."
    );

    public static final Option ONLINE_PORT_NUMBER = new Option(
        "inference.onlineportnumber",
        56734,
        "The port number for the online server."
    );

    public static final Option ONLINE_READ_PARTITION = new Option(
        "onlineatommanager.read",
        -1,
        "The partition to add new observations to."
        + " If negative, the first read partition in the database will be used."
    );

    public static final Option PARTIAL_GROUNDING_POWERSET = new Option(
        "partialgrounding.powerset",
        false,
        "Whether or not to iterate over the powerset of partial targets during a partial grounding."
        + " If true the partial grounding will result in no regret in the inference. "
        + " If false an approximation will be made such that only one atom in a ground rule can come from a special partition."
    );

    static {
        Options.addClassOptions(OnlineOptions.class);
    }

    // Static only.
    private OnlineOptions() {}
}
