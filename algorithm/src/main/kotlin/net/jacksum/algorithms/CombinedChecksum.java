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
/*

  Author: Johann N. Loefflmann, Germany
  Contributors: Federico Tello Gentile, Argentina (multi core support)


 */
package net.jacksum.algorithms;

import net.jacksum.HashFunctionFactory;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * This design follows the Composite Pattern.
 * <p>
 * AbstractChecksum is the component that acts as the superclass for the
 * composite class and the concrete leaf classes. CombinedChecksum acts as a
 * composite class, because it supports the AbstractChecksum class interface.
 */
public class CombinedChecksum extends AbstractChecksum {

  private List<AbstractChecksum> algorithms;

  /**
   * Creates a new instance of CombinedChecksum
   */
  public CombinedChecksum() {
    algorithms = new ArrayList<>();
    length = 0;
    filename = null;
    //formatPreferences.setSeparator(" ");
    //formatPreferences.setHashEncoding(Encoding.HEX);
    //formatPreferences.setSizeWanted(true);
  }

  public CombinedChecksum(String[] algos, boolean alternate) throws NoSuchAlgorithmException {
    this();
    setAlgorithms(algos, alternate);
  }

  /**
   * @return the algorithms
   */
  public List<AbstractChecksum> getAlgorithms() {
    return algorithms;
  }

  /**
   * @param algorithms the algorithms to set
   */
  public void setAlgorithms(List<AbstractChecksum> algorithms) {
    this.algorithms = algorithms;
  }

  public void addAlgorithm(String algorithm, boolean alternate) throws NoSuchAlgorithmException {
    AbstractChecksum checksum = HashFunctionFactory.getHashFunction(algorithm, alternate);
    checksum.setName(algorithm);
    addAlgorithm(checksum);
  }

  public void addAlgorithm(AbstractChecksum checksum) {
    bitWidth += checksum.getSize();
    algorithms.add(checksum);
  }

  public final void setAlgorithms(String[] algos, boolean alternate) throws NoSuchAlgorithmException {
    for (String algo : algos) {
      addAlgorithm(algo, alternate);
    }
  }

  /**
   * Removes one algorithm specified by its name
   *
   * @param algorithm the name of the algorithm
   */
  public void removeAlgorithm(String algorithm) {
    for (int i = 0; i < algorithms.size(); i++) {
      AbstractChecksum checksum = algorithms.get(i);
      if (checksum.getName().equals(algorithm)) {
        bitWidth -= checksum.getSize();
        algorithms.remove(i);
        return;
      }
    }
  }

  /**
   * Removes one algorithm specified by its name.
   *
   * @param checksum a AbstractChecksum object.
   */
  public void removeAlgorithm(AbstractChecksum checksum) {
    removeAlgorithm(checksum.getName());
  }

  @Override
  public void reset() {
    for (AbstractChecksum algorithm : algorithms) {
      algorithm.reset();
    }
    length = 0;
  }

  /**
   * Updates all checksums with the specified byte.
   *
   * @param integer the value for the update
   */
  @Override
  public void update(int integer) {
    for (AbstractChecksum algorithm : algorithms) {
      algorithm.update(integer);
    }
    length++;
  }

  /**
   * Updates all checksums with the specified byte.
   *
   * @param b a single byte.
   */
  @Override
  public void update(byte b) {
    for (AbstractChecksum algorithm : algorithms) {
      algorithm.update(b);
    }
    length++;
  }

  /**
   * Updates all checksums with the specified array of bytes.
   */
  @Override
  public void update(byte[] bytes, int offset, int length) {
    for (AbstractChecksum algorithm : algorithms) {
      algorithm.update(bytes, offset, length);
    }
    this.length += length;
  }

  /**
   * Updates all checksums with the specified array of bytes.
   */
  @Override
  public void update(byte[] bytes) {
    for (AbstractChecksum algorithm : algorithms) {
      algorithm.update(bytes);
    }
    this.length += bytes.length;
  }

  /**
   * Returns the result of the computation as a byte array.
   *
   * @return the result of the computation as a byte array
   */
  @Override
  public byte[] getByteArray() {
    List<byte[]> byteArrays = new ArrayList<>();
    int size = 0;
    for (AbstractChecksum algorithm : algorithms) {
      byte[] byteArray = algorithm.getByteArray();
      if (byteArray != null) { // algorithms none or read can return null
        byteArrays.add(byteArray);
        size += byteArray.length;
      }
    }
    byte[] ret = new byte[size];
    int offset = 0;
    for (byte[] src : byteArrays) {
      System.arraycopy(src, 0, ret, offset, src.length);
      offset += src.length;
    }
    return ret;
  }

  @Override
  public String getName() {
    if (algorithms.isEmpty()) {
      return null;
    }

    StringBuilder sb = new StringBuilder();
    int i;
    for (i = 0; i < algorithms.size() - 1; i++) {
      sb.append((algorithms.get(i)).getName());
      sb.append('+');
    }
    sb.append((algorithms.get(i)).getName());
    return sb.toString();
  }

}
