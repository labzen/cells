package cn.labzen.cells.core.utils;

import cn.labzen.cells.core.utils.assist.SimpleBean;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static cn.labzen.cells.core.utils.Collections.*;
import static org.junit.jupiter.api.Assertions.*;

class CollectionsTest {

  @Test
  void testIsNullOrEmpty() {
    assertTrue(isNullOrEmpty(null));
    assertTrue(isNullOrEmpty(Lists.newArrayList()));
    assertFalse(isNullOrEmpty(Lists.newArrayList("")));
  }

  @Test
  void testRemoveNull() {
    List<String> source = Lists.newArrayList("1", "", "2", null, "3");
    List<String> expected = Lists.newArrayList("1", "", "2", "3");
    List<String> target = removeNull(source);
    assertNotNull(target);
    assertIterableEquals(expected, target);
  }

  @Test
  void testRemoveBlank() {
    List<String> source = Lists.newArrayList("1", "  ", "2", null, "3");
    List<String> expected = Lists.newArrayList("1", "2", "3");
    List<String> target = removeBlank(source);
    assertNotNull(target);
    assertIterableEquals(expected, target);
  }

  @Test
  void testAllEquals() {
    List<SimpleBean> collection = Lists.newArrayList();
    collection.add(new SimpleBean("a", 1, true));
    collection.add(new SimpleBean("b", 2, true));
    collection.add(new SimpleBean("c", 3, true));

    Assertions.assertTrue(allEquals(collection, SimpleBean::getBooleanValue));
    Assertions.assertFalse(allEquals(collection, SimpleBean::getStringValue));
  }

}
