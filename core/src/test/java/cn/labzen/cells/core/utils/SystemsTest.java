package cn.labzen.cells.core.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SystemsTest {

  @Test
  void testInfo() {
    Assertions.assertNotNull(Systems.os());
    Assertions.assertEquals(64, Systems.osArch());
  }

}
