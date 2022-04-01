package shop.flower;

import org.junit.jupiter.api.Test;

import java.io.StringWriter;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class AppTest {

  App target = new App().setAdapters();

  @Test
  void exampleText() {

    var stringWriter = new StringWriter();
    var input = """
        10 R12
        15 L09
        13 T58
        """;

    target.start(new Scanner(input), stringWriter);

    var tulipsResult = """
        13 T58 $25.85
        	2 x 5 $9.95
        	1 x 3 $5.95
        """;
    var liliesResult = """
        15 L09 $41.90
        	1 x 9 $24.95
        	1 x 6 $16.95
        """;
    var rosesResult = """
        10 R12 $12.99
        	1 x 10 $12.99
        """;

    var resultString = stringWriter.toString();
    assertThat(resultString).contains(tulipsResult, liliesResult, rosesResult);

    var restString = resultString
        .replace(tulipsResult, "")
        .replace(liliesResult, "")
        .replace(rosesResult, "");
    assertThat(restString).isEmpty();
  }

}
