package interview2026.mockprep.parsing;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CsvParserTest {

    private final CsvParser parser = new CsvParser();

    @Test
    void testParseAndSum_normalCase() {
        String csv =
            "T001,NGX_GAS,100,45.5\n" +
            "T002,NGX_PWR,200,30.0\n" +
            "T003,INVALID_LINE\n" +
            "T004,NGX_GAS,150,46.0\n" +
            "T005,NGX_PWR,50,31.5\n";

        Map<String, Integer> result = parser.parseAndSum(csv);

        assertEquals(250, result.get("NGX_GAS"));
        assertEquals(250, result.get("NGX_PWR"));
        assertEquals(2, result.size());
    }

    @Test
    void testParseAndSum_allInvalidLines() {
        String csv = "BAD_LINE\nANOTHER_BAD\n";

        Map<String, Integer> result = parser.parseAndSum(csv);

        assertTrue(result.isEmpty());
    }

    @Test
    void testParseAndSum_emptyString() {
        Map<String, Integer> result = parser.parseAndSum("");

        assertTrue(result.isEmpty());
    }

    @Test
    void testParseAndSum_singleValidLine() {
        String csv = "T001,NGX_GAS,500,45.5\n";

        Map<String, Integer> result = parser.parseAndSum(csv);

        assertEquals(500, result.get("NGX_GAS"));
        assertEquals(1, result.size());
    }
}
