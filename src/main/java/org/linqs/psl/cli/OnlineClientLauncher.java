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

import org.linqs.psl.application.inference.online.messages.responses.OnlineResponse;
import org.linqs.psl.util.FileUtils;
import org.linqs.psl.util.Logger;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * A client interface to online PSL.
 */
public class OnlineClientLauncher {
    public static final String OPTION_HELP = "h";
    public static final String OPTION_HELP_LONG = "help";
    public static final String OPTION_ONLINE_SERVER_RESPONSE_OUTPUT = "onlineServerOutput";

    private static final Logger log = Logger.getLogger(OnlineClientLauncher.class);
    private CommandLine parsedOptions;

    private OnlineClientLauncher(CommandLine parsedOptions) {
        this.parsedOptions = parsedOptions;
    }

    private void outputServerResponses(List<OnlineResponse> serverResponses) {
        for (OnlineResponse response : serverResponses) {
            System.out.println(response.toString());
        }
    }

    private void outputServerResponses(List<OnlineResponse> serverResponses, String outputFilePath) {
        Path outputDirectory = Paths.get(outputFilePath).getParent();
        if (outputDirectory != null) {
            FileUtils.mkdir(outputDirectory.toString());
        }

        try (BufferedWriter bufferedWriter = FileUtils.getBufferedWriter(outputFilePath)) {
            for (OnlineResponse response : serverResponses) {
                bufferedWriter.write(response.toString() + "\n");
            }
        } catch (IOException ex) {
            throw new RuntimeException(String.format("Error writing online server responses to file: %s", outputFilePath), ex);
        }
    }

    private void run() {
        log.info("Starting OnlinePSL client.");
        List<OnlineResponse> serverResponses = OnlineActionInterface.run();
        log.info("OnlinePSL client closed.");

        // Output the results.
        if (!(parsedOptions.hasOption(OPTION_ONLINE_SERVER_RESPONSE_OUTPUT))) {
            log.trace("Writing server responses to stdout.");
            outputServerResponses(serverResponses);
        } else {
            String outputFilePath = parsedOptions.getOptionValue(OPTION_ONLINE_SERVER_RESPONSE_OUTPUT);
            log.trace("Writing inferred predicates to file: " + outputFilePath);
            outputServerResponses(serverResponses, outputFilePath);
        }
    }

    private static Options getOptions() {
        Options options = new Options();

        options.addOption(Option.builder(OPTION_HELP)
                .longOpt(OPTION_HELP_LONG)
                .desc("Print this help message and exit")
                .build());


        options.addOption(Option.builder(OPTION_ONLINE_SERVER_RESPONSE_OUTPUT)
                .desc("Optional file path for writing online server responses to filesystem (default is STDOUT)")
                .hasArg()
                .argName("path")
                .build());

        return options;
    }

    private static CommandLine getCommandLineOptions(String[] args) {
        CommandLineParser parser = new DefaultParser();
        Options options = getOptions();

        CommandLine parsedOptions = null;
        boolean printHelp = false;

        try {
            parsedOptions = parser.parse(options, args);
        } catch (ParseException ex) {
            System.err.println("Command line error: " + ex.getMessage());
            printHelp = true;
        }

        if (printHelp || parsedOptions.hasOption(OPTION_HELP)) {
            HelpFormatter helpFormatter = new HelpFormatter();
            helpFormatter.printHelp("online-client", options, true);
        }

        return parsedOptions;
    }

    public static void main(String[] args) {
        main(args, false);
    }

    public static void main(String[] args, boolean rethrow) {
        try {
            CommandLine parsedOptions = getCommandLineOptions(args);
            if (parsedOptions == null) {
                return;
            }

            OnlineClientLauncher client = new OnlineClientLauncher(parsedOptions);
            client.run();
        } catch (Exception ex) {
            if (rethrow) {
                throw new RuntimeException("Failed to run Online PSL client: " + ex.getMessage(), ex);
            } else {
                System.err.println("Unexpected exception!");
                ex.printStackTrace(System.err);
                System.exit(1);
            }
        }
    }
}
