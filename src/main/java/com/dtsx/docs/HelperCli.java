package com.dtsx.docs;

import com.dtsx.docs.commands.completions.CompgenCmd;
import com.dtsx.docs.commands.docgen.DocgenCmd;
import com.dtsx.docs.commands.logs.LogsCmd;
import com.dtsx.docs.commands.review.ReviewCmd;
import com.dtsx.docs.commands.run.RunCmd;
import com.dtsx.docs.commands.startgate.StartgateCmd;
import com.dtsx.docs.commands.test.TestCmd;
import com.dtsx.docs.lib.CliLogger;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.val;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Help.Ansi.Style;
import picocli.CommandLine.Help.ColorScheme;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static com.dtsx.docs.lib.ColorUtils.ACCENT_COLOR;

@Command(
    name = "dh",
    mixinStandardHelpOptions = true,
    subcommands = {
        TestCmd.class,
        RunCmd.class,
        ReviewCmd.class,
        CompgenCmd.class,
        LogsCmd.class,
        StartgateCmd.class,
        DocgenCmd.class,
    }
)
public class HelperCli {
    public static Path CLI_DIR = Path.of(Optional.ofNullable(System.getenv("CLI_DIR")).orElse(".")).toAbsolutePath();

    @SuppressWarnings("UnnecessaryModifier")
    public static void main(String[] args) {
        for (val dir : List.of("./", CLI_DIR.toString())) {
            Dotenv.configure().directory(dir).systemProperties().ignoreIfMissing().load();
        }

        System.setProperty("APPROVALTESTS_PROJECT_DIRECTORY", CLI_DIR.toString());

        val cli = new CommandLine(new HelperCli())
            .setCaseInsensitiveEnumValuesAllowed(true)
            .setColorScheme(new ColorScheme.Builder()
                .commands(ACCENT_COLOR)
                .options(ACCENT_COLOR)
                .parameters(ACCENT_COLOR)
                .optionParams(Style.italic)
                .build()
            )
            .setExecutionExceptionHandler((ex, _, _) -> {
                CliLogger.exception(ex);
                throw ex;
            });

        val exitCode = cli.execute(args);
        System.exit(exitCode);
    }
}
