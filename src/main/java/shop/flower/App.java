package shop.flower;

import shop.flower.application.ports.input.OrderInputPort;
import shop.flower.framework.adapters.input.OrderCLIAdapter;
import shop.flower.framework.adapters.output.ItemsOutputStaticAdapter;
import shop.flower.framework.adapters.output.OrdersOutputInMemoryAdapter;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.Scanner;

public class App {

  OrderCLIAdapter inputAdapter;

  public static void main(String[] args) {
    new App().setAdapters().start(new Scanner(System.in), new PrintWriter(System.out));
  }

  App setAdapters() {
    var inputPort =
        new OrderInputPort(
            OrdersOutputInMemoryAdapter.getInstance(), ItemsOutputStaticAdapter.getInstance());

    inputAdapter = new OrderCLIAdapter(inputPort, inputPort);
    return this;
  }

  void start(Scanner scanner, Writer writer) {
    inputAdapter.processRequest(scanner, writer);
  }
}
