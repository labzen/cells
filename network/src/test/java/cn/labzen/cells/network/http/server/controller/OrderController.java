package cn.labzen.cells.network.http.server.controller;

import cn.labzen.cells.network.http.server.annotation.*;
import com.google.common.collect.Maps;

import java.util.HashMap;

@Path("order/bill")
public class OrderController {

  @Restful("get1")
  public Object getBill() {
    HashMap<String, Object> result = Maps.newHashMap();
    result.put("name", "账单");
    result.put("count", "123.1");
    return result;
  }

  @Get("see")
  @Template("order/look")
  public Object lookLook() {
    HashMap<String, Object> result = Maps.newHashMap();
    result.put("name", "账单123");
    result.put("count", "i9823");
    //result.put("param", con + "<<<");
    return result;
  }
}
