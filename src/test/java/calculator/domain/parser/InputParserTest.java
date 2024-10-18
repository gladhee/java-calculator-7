package calculator.domain.parser;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import calculator.domain.parser.InputParser.ParsedInput;
import org.junit.jupiter.api.Test;

public class InputParserTest {

    private final InputParser inputParser = new InputParser();

    @Test
    void 기본구분자_테스트() {
        String input = "1,2:3";

        ParsedInput parsedInput = inputParser.parse(input);

        assertThat(parsedInput.numbers()).containsExactly("1", "2", "3");
    }

    @Test
    void 커스텀_구분자_테스트() {
        String input = "//;\\n13;21";

        ParsedInput parsedInput = inputParser.parse(input);

        assertThat(parsedInput.numbers()).containsExactly("13","21");
    }

    @Test
    void 중복된_커스텀_구분자_테스트() {
        String input = "//;;;;;;;;;;;\\n13;21:3";

        ParsedInput parsedInput = inputParser.parse(input);

        assertThat(parsedInput.numbers()).containsExactly("13","21","3");
    }

    @Test
    void 많은_커스텀_구분자_테스트() {
        String input = "//*&^%$#@!\\n13*21&3^4%5$6@7!8";

        ParsedInput parsedInput = inputParser.parse(input);

        assertThat(parsedInput.numbers()).containsExactly("13","21","3","4","5","6","7","8");
    }

    @Test
    void 빈문자열_테스트() {
        final String input = "";

        final ParsedInput parsedInput = inputParser.parse(input);

        assertThat(parsedInput.numbers()).containsExactly("0");
    }

    @Test
    void 커스텀_구분자에_개행_및_중복_종결_테스트() {
        final String input = "//\n\\n2\n\\n2\\n3";

        final ParsedInput parsedInput = inputParser.parse(input);

        assertThat(parsedInput.numbers()).containsExactly("3");
    }

    @Test
    void 커스텀_구분자에_공백_테스트() {
        String input = "// \\n13 21 3";

        ParsedInput parsedInput = inputParser.parse(input);

        assertThat(parsedInput.numbers()).containsExactly("13","21","3");
    }

}