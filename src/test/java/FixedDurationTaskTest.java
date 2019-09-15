import duke.DukeException;
import duke.Parser;
import duke.TaskList;
import duke.commands.Command;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FixedDurationTaskTest {
    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private static final PrintStream originalOut = System.out;
    private static final PrintStream originalErr = System.err;

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    void restoreStreams() {
        outContent.reset();
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void test(String input) throws DukeException {
        setUpStreams();
        TaskList taskList = new TaskList();
        Command c = Parser.parse(input);
        c.execute(taskList, DukeTest.ui, DukeTest.storage);
        String exp = "Got it. I've added this task: \n   [T][✗] needing to finish my needs (needs 2 hours)\nNow you have 1 tasks in the list.";
        assertEquals(exp, outContent.toString().trim());
        restoreStreams();
    }
}
