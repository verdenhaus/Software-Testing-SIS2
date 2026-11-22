package guard;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

@EnabledIfEnvironmentVariable(named = "CI", matches = ".+")
class NoSkippedTestsTest {

    private static final Pattern FORBIDDEN = Pattern.compile("@Disabled|@Tag\\(\\s*\"only\"\\s*\\)");
    private static final Path SELF = Paths.get("src", "test", "java", "guard", "NoSkippedTestsTest.java");

    @Test
    void noDisabledOrFocusedTestsAreCommitted() throws IOException {
        List<String> offenders;
        try (Stream<Path> files = Files.walk(Paths.get("src", "test", "java"))) {
            offenders = files
                .filter(p -> p.toString().endsWith(".java"))
                .filter(p -> !p.normalize().endsWith(SELF))
                .flatMap(NoSkippedTestsTest::forbiddenLines)
                .toList();
        }
        assertTrue(offenders.isEmpty(),
            "Disabled or focused tests are not allowed in CI (forbidOnly):\n" + String.join("\n", offenders));
    }

    private static Stream<String> forbiddenLines(Path file) {
        try {
            List<String> lines = Files.readAllLines(file);
            return IntStream.range(0, lines.size())
                .filter(i -> FORBIDDEN.matcher(lines.get(i)).find())
                .mapToObj(i -> file + ":" + (i + 1) + "  " + lines.get(i).trim());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}