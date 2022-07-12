package cn.labzen.cells.network.http.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class UndertowServerTest {

  public static void main(String[] args) {
    SpringApplication.run(UndertowServerTest.class, args);
  }

  private boolean started = false;

  @EventListener
  public void start(ApplicationEvent event) {
    if (!started) {
      started = true;

      ServerConfiguration configuration = ServerConfiguration.create(8111,
          "cn.labzen.cells.network.http.server.controller").addResourcePath("C:\\Dean\\temp\\map").build();
      Server server = HttpServers.createUndertowServer(configuration);
      server.start();
    }
  }
}
