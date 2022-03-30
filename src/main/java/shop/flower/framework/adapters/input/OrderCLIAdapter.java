package shop.flower.framework.adapters.input;

import shop.flower.application.ports.input.OrderInputPort;
import shop.flower.application.usecases.OrderArrangeBundlesUseCase;
import shop.flower.application.usecases.OrderCreateUseCase;
import shop.flower.domain.Bundle;
import shop.flower.domain.BundlesArrangement;
import shop.flower.framework.adapters.output.ItemsOutputStaticAdapter;
import shop.flower.framework.adapters.output.OrdersOutputInMemoryAdapter;

import java.io.IOException;
import java.io.StringWriter;
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

  public void processRequest(Scanner scanner, Writer writer) throws IOException {

    var pattern = Pattern.compile("(\\d+) (\\w+)");

    List<OrderCreateUseCase.OrderLine> lines = new ArrayList<>();
    while (scanner.hasNextLine()) {
      var line = scanner.nextLine();
      if (line.isEmpty()) {
        break;
      }

      var result = pattern.matcher(line);

      result.find();

      lines.add(
          new OrderCreateUseCase.OrderLine(result.group(2), Integer.parseInt(result.group(1))));

    }

    var id = orderCreateUseCase.createOrder(lines);
    var bundles = orderArrangeBundlesUseCase.arrangeOrderBundles(id);

    for (BundlesArrangement arrangement: bundles) {
      var stream = arrangement.arrangement().entrySet().stream()
          .sorted(Map.Entry.comparingByKey(Comparator.comparingInt(Bundle::size).reversed()));
      var bundle = """
            %s %s %s
            """.formatted(arrangement.getTotalQuantity(), arrangement.item().code(), arrangement.getTotalCost());

      writer.append(bundle);

      stream.map(entry -> "\t\t%s x %s %s%n".formatted(entry.getValue(), entry.getKey().size(), entry.getKey().cost())).forEach(l -> {
        try {
          writer.append(l);
        } catch (IOException e) {
          e.printStackTrace();
        }
      });
    }

    writer.flush();
  }

  public static void main(String[] args) throws IOException {

    var inputPort = new OrderInputPort(OrdersOutputInMemoryAdapter.getInstance(), ItemsOutputStaticAdapter.getInstance());

    var inputAdapter = new OrderCLIAdapter(inputPort, inputPort);

    //inputAdapter.processRequest(new Scanner(System.in), new PrintWriter(System.out));

    var stringWriter = new StringWriter();
    var input = """
        10 R12
        15 L09
        13 T58
        """;
    inputAdapter.processRequest(new Scanner(input), stringWriter);

    System.out.println(stringWriter);
  }
}
