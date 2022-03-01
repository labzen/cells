package cn.labzen.cells.network.onvif;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

class OnvifTest {

  private Logger logger = LoggerFactory.getLogger(OnvifTest.class);

  @Test
  void testDiscovery() {
    long timeout = 16500;

    logger.info("broadcast addresses: {}", Onvif.INSTANCE.broadcastAddresses());
    logger.info("interface addresses: {}", Onvif.INSTANCE.interfaceAddresses());

    logger.info("========================================================");

    Onvif.create((int) timeout)
         .mode(DiscoveryMode.HIK_VISION)
         .listen(() -> logger.info("started at {}", LocalDateTime.now().toString()))
         .listen((hostname, devices) -> {
           logger.info("===> found single host <<{}>> devices....", hostname);
           devices.forEach(this::print);
         })
         .listen((DiscoveredAllDevicesListener) (devices) -> {
           logger.info("discovered all devices at {}", LocalDateTime.now().toString());
           devices.forEach(this::print);
         })
         .listen((DiscoveryFinishedListener) (count) -> logger.info("finished with {} device discovered at {}",
             count,
             LocalDateTime.now().toString()))
         .discovery();

    try {
      Thread.sleep(timeout + 1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private void print(Device device) {
    HikVisionDevice hk = (HikVisionDevice) device;
    logger.info("''{}'' at |{}|, <{}>", hk.getDescription(), hk.getIpv4(), hk.getMac());
  }
}
