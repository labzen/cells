package cn.labzen.cells.network.http.server.bean;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class User {

  private Long id;
  private String name;
  private int age;
  private String department;
}
