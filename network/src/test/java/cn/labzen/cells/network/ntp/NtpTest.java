package cn.labzen.cells.network.ntp;

import org.junit.jupiter.api.Test;

class NtpTest {

  @Test
  void request() {
    double time = NtpClient.request("ntp1.aliyun.com", 123);
    System.out.println(time);
  }
}
