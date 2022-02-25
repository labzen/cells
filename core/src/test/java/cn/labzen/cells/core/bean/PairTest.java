package cn.labzen.cells.core.bean;

import cn.labzen.cells.core.bean.MutablePair;
import cn.labzen.cells.core.bean.Pair;
import cn.labzen.cells.core.bean.StrictPair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PairTest {

  @Test
  void testPair() {
    Pair<String, Integer> numberPair = new Pair<>("number", 123);

    Pair<String, Integer> copy = numberPair.copy();
    Assertions.assertEquals(numberPair.getFirst(), copy.getFirst());

    Pair<String, Integer> copyOther = numberPair.copy("new number", 456);
    Assertions.assertNotEquals(numberPair.getFirst(), copyOther.getFirst());

    MutablePair<String, Integer> mutablePair = numberPair.toMutablePair();
    mutablePair.setFirst("changed number");
    Assertions.assertEquals(mutablePair.getFirst(), "changed number");

    Pair<String, Integer> nullPair = new Pair<>(null, null);
    Assertions.assertNull(nullPair.getFirst());
  }

  @Test
  void testStrictPair() {
    Assertions.assertThrows(NullPointerException.class, () -> new StrictPair<>(null, null));
  }
}
