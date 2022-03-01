package cn.labzen.cells.network.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static cn.labzen.cells.network.utils.Addresses.isIp;
import static cn.labzen.cells.network.utils.Addresses.isIpv4;

class AddressesTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(AddressesTest.class);

  @Test
  void testIsIp() {
    Assertions.assertTrue(isIpv4("192.168.1.255"));
    Assertions.assertFalse(isIpv4("192.168.1.300"));
    Assertions.assertFalse(isIpv4("ABCD:EF01:2345:6789:ABCD:EF01:2345:6789"));
    Assertions.assertFalse(isIpv4("ABCD:EF01:2345:6789:ABCD:EF01:"));

    Assertions.assertTrue(isIp("127.0.0.1"));
  }

}
