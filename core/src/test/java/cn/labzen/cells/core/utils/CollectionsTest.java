package cn.labzen.cells.core.utils;

import com.google.common.collect.Lists;
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

}
