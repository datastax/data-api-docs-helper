package com.dtsx.docs.commands.docgen;

import com.dtsx.docs.commands.docgen.table_errors.TableErrorsCmd;
import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Spec;

@Command(
    name = "docgen",
    description = "Documentation generation commands",
    mixinStandardHelpOptions = true,
    subcommands = {
        TableErrorsCmd.class
    }
)
public class DocgenCmd implements Runnable {
    @Spec
    private CommandSpec spec;

    @Override
    public void run() {
        spec.commandLine().usage(System.out);
    }
}
