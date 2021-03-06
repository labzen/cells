/*


  Jacksum 3.2.0 - a checksum utility in Java
  Copyright (c) 2001-2022 Dipl.-Inf. (FH) Johann N. Löfflmann,
  All Rights Reserved, <https://jacksum.net>.

  This program is free software: you can redistribute it and/or modify it under
  the terms of the GNU General Public License as published by the Free Software
  Foundation, either version 3 of the License, or (at your option) any later
  version.

  This program is distributed in the hope that it will be useful, but WITHOUT
  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
  FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
  details.

  You should have received a copy of the GNU General Public License along with
  this program. If not, see <https://www.gnu.org/licenses/>.


 */
package net.jacksum.selectors;

import net.jacksum.algorithms.AbstractChecksum;
import net.jacksum.algorithms.checksums.Fnv1a_32;
import net.jacksum.algorithms.checksums.Fnv1a_n;

import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author johann
 */
public class FNV1a_Selector extends Selector {

  @Override
  public Map<String, String> getAvailableAlgorithms() {
    Map<String, String> map = new LinkedHashMap<>(6);
    map.put("fnv-1a_32", "FNV-1a (32 bits)");
    map.put("fnv-1a_64", "FNV-1a (64 bits)");
    map.put("fnv-1a_128", "FNV-1a (128 bits)");
    map.put("fnv-1a_256", "FNV-1a (256 bits)");
    map.put("fnv-1a_512", "FNV-1a (512 bits)");
    map.put("fnv-1a_1024", "FNV-1a (1024 bits)");
    return map;
  }

  @Override
  public AbstractChecksum getPrimaryImplementation() throws NoSuchAlgorithmException {
    String bits = name.substring(7);
    if (bits.equals("32")) {
      // use this specific implementation if possible since it is optimized
      return new Fnv1a_32();
    } else {
      // Fnv1_n is much slower than the specific Fnv1_32
      return new Fnv1a_n(bits);
    }
  }

}
