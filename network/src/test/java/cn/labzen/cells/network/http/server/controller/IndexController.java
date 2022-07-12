package cn.labzen.cells.network.http.server.controller;

import cn.labzen.cells.core.utils.Randoms;
import cn.labzen.cells.network.http.server.annotation.*;
import com.google.common.collect.Maps;

import java.util.Map;

@Path
public class IndexController {

  @Get
  @Template("index")
  public Map<String, String> page(String color) {
    Map<String, String> attrs = Maps.newHashMap();
    attrs.put("color", color);
    attrs.put("number", Randoms.string(4, Randoms.NUMBERS));

    return attrs;
  }

  @Restful
  @Post("xyz")
  public void xyz() {
    System.out.println("normal xyz");
  }

  @Restful("abc")
  public void abc() {
    System.out.println("normal abc");
  }
}
