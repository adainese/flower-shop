package shop.flower.framework.adapters.input;

import shop.flower.application.usecases.OrderArrangeBundlesUseCase;
import shop.flower.application.usecases.OrderCreateUseCase;
import shop.flower.domain.Bundle;
import shop.flower.domain.BundlesArrangement;

import java.io.IOException;
import java.io.Writer;
import java.util.*;
import java.util.regex.Pattern;

public class OrderCLIAdapter {

  private final OrderCreateUseCase orderCreateUseCase;
  private final OrderArrangeBundlesUseCase orderArrangeBundlesUseCase;

  public OrderCLIAdapter(
      OrderCreateUseCase orderCreateUseCase,
      OrderArrangeBundlesUseCase orderArrangeBundlesUseCase) {
    this.orderCreateUseCase = orderCreateUseCase;
    this.orderArrangeBundlesUseCase = orderArrangeBundlesUseCase;
  }

  public void processRequest(Scanner scanner, Writer writer) {

    List<OrderCreateUseCase.OrderLine> orderLines = readInput(scanner);

    var id = orderCreateUseCase.createOrder(orderLines);
    var arrangements = orderArrangeBundlesUseCase.arrangeOrderBundles(id);

    writeOutput(writer, arrangements);
  }

  private List<OrderCreateUseCase.OrderLine> readInput(Scanner scanner) {

    var pattern = Pattern.compile("(\\d+) (\\w+)");

    List<OrderCreateUseCase.OrderLine> lines = new ArrayList<>();

    while (scanner.hasNextLine()) {
      var line = scanner.nextLine();
      if (line.isEmpty()) {
        break;
      }

      var result = pattern.matcher(line);

      if (result.find()) {
        lines.add(
            new OrderCreateUseCase.OrderLine(result.group(2), Integer.parseInt(result.group(1))));
      }
    }
    return lines;
  }

  private void writeOutput(Writer writer, List<BundlesArrangement> arrangements) {

    RuntimeExceptionWriter actualWriter = new RuntimeExceptionWriter(writer);

    for (BundlesArrangement arrangement : arrangements) {
      var bundle =
          "%s %s %s%n"
              .formatted(
                  arrangement.getTotalQuantity(),
                  arrangement.item().code(),
                  arrangement.getTotalCost());

      actualWriter.append(bundle);

      arrangement.arrangement().entrySet().stream()
          .sorted(Map.Entry.comparingByKey(Comparator.comparingInt(Bundle::size).reversed()))
          .map(
              entry ->
                  "\t%s x %s %s%n"
                      .formatted(entry.getValue(), entry.getKey().size(), entry.getKey().cost()))
          .forEach(actualWriter::append);
    }
    actualWriter.flush();
  }

  class RuntimeExceptionWriter {
    private final Writer writer;

    public RuntimeExceptionWriter(Writer writer) {
      this.writer = writer;
    }

    Writer append(String s) {
      try {
        return writer.append(s);
      } catch (IOException e) {
        throw new RuntimeException("Error trying to append: " + s, e);
      }
    }

    void flush() {
      try {
        writer.flush();
      } catch (IOException e) {
        throw new RuntimeException("Error trying to flush writer", e);
      }
    }
  }
}
