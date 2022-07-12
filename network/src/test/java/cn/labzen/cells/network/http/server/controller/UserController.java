package cn.labzen.cells.network.http.server.controller;

import cn.labzen.cells.core.utils.Randoms;
import cn.labzen.cells.network.http.server.bean.User;
import cn.labzen.cells.network.http.server.annotation.Get;
import cn.labzen.cells.network.http.server.annotation.Restful;
import cn.labzen.cells.network.http.server.annotation.Variable;

@Restful("user/profiles/")
public class UserController {

  @Get("details/{username}/get")
  public User getUser(@Variable() String username) {
    return new User().setId(Randoms.longNumber(Long.MAX_VALUE))
                     .setName(username)
                     .setAge(Randoms.intNumber(10, 90))
                     .setDepartment("XX部门");
  }
}
