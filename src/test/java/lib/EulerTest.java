
package lib;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

public class EulerTest extends EulerLib {

    protected long startTime = System.currentTimeMillis();
    protected long ans;
    protected boolean checked;

    @Before
    public void before() {
        ans = 0;
        checked = false;
    }

    public void check(Object expected) {
        check(ans, expected);
    }

    public void check(Object ans, Object expected) {
        Assert.assertEquals(expected + "", ans + "");
        checked = true;
    }

    public List<String> read() {
        try {
            return Files.readAllLines(Paths.get("files", getClass().getSimpleName() + ".txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public long[][] readAsGrid(String delimiter) {
        return read().stream()
            .map(s -> Arrays.asList(s.split(delimiter)).stream()
                .filter(n -> !n.isEmpty())
                .mapToLong(Long::parseLong)
                .toArray())
            .toArray(long[][]::new);
    }

    /**
     * Expected format: "STRING1","STRING2","STRING3"
     */
    public String[] readAsCommaSeparatedStrings() {
        return read().get(0).substring(1).split("[\",]+");
    }

    public void time(Object... labels) {
        pr("time", System.currentTimeMillis() - startTime, labels);
    }

    public void exit() {
        System.exit(0);
    }

    @After
    public void after() {
        Assert.assertTrue("no check() statement", checked);
    }
}
