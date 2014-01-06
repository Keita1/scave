/**
 * JWave - Java implementation of wavelet transform algorithms
 *
 * Copyright 2010-2012 Christian Scheiblich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * This file Wavelet.java is part of JWave.
 *
 * @author Christian Scheiblich
 * date 23.02.2010 05:42:23
 * contact graetz@mailfish.de
 */
package math.jwave.transforms.wavelets

import scala.annotation.meta.field
import scala.annotation.meta.beanSetter

/**
 * Basic class for one wavelet keeping coefficients of the wavelet function, the
 * scaling function, the base wavelength, the forward transform method, and the
 * reverse transform method.
 *
 * @param _wavelength minimal wavelength of the used wavelet and scaling coefficients
 * @param _coeffs coefficients of the wavelet; wavelet function
 * @param _scales coefficients of the scales; scaling function
 */

abstract class Wavelet(@beanSetter var _waveLength: Int, @beanSetter var _coeffs: Array[Double], @beanSetter var _scales: Array[Double]) extends WaveletInterface {

  /**
   * Constructor; predefine members to init values
   */
  def this() = this(0, null, null)

  /**
   * Performs the forward transform for the given array from time domain to
   * Hilbert domain and returns a new array of the same size keeping
   * coefficients of Hilbert domain and should be of length 2 to the power of p
   * -- length = 2^p where p is a positive integer.
   *
   * @param arrTime
   *          array keeping time domain coefficients
   * @return coefficients represented by frequency domain
   */
  def forward(arrTime: Array[Double]): Array[Double] = {

    val arrHilb = new Array[Double](arrTime.length);

    var k = 0;
    var h = arrTime.length >> 1;

    for (i <- 0 until h) {
      for (j <- 0 until _waveLength) {
        k = (i << 1) + j;
        while (k >= arrTime.length)
          k -= arrTime.length;
        arrHilb(i) += arrTime(k) * _scales(j) // low pass filter - energy (approximation)
        arrHilb(i + h) += arrTime(k) * _coeffs(j) // high pass filter - details 

      } // wavelet

    } // h

    return arrHilb;
  } // forward

  /**
   * Performs the reverse transform for the given array from Hilbert domain to
   * time domain and returns a new array of the same size keeping coefficients
   * of time domain and should be of length 2 to the power of p -- length = 2^p
   * where p is a positive integer.
   *
   * @date 10.02.2010 08:19:24
   * @author Christian Scheiblich
   * @param arrHilb
   *          array keeping frequency domain coefficients
   * @return coefficients represented by time domain
   */
  def reverse(arrHilb: Array[Double]): Array[Double] = {

    val arrTime = new Array[Double](arrHilb.length)

    var k = 0;
    var h = arrHilb.length >> 1;
    for (i <- 0 until h) {
      for (j <- 0 until _waveLength) {
        k = (i << 1) + j;
        while (k >= arrHilb.length)
          k -= arrHilb.length;

        arrTime(k) += (arrHilb(i) * _scales(j) + arrHilb(i + h) * _coeffs(j)) // adding up details times energy (approximation)

      } // wavelet

    } //  h

    return arrTime;
  }

  /**
   * Returns the minimal wavelength for the used wavelet.
   *
   * @date 10.02.2010 08:13:59
   * @author Christian Scheiblich
   * @return the minimal wavelength for this basic wave
   */
  def getWaveLength(): Int = _waveLength

  /**
   * Returns the number of coeffs (and scales).
   *
   * @date 08.02.2010 13:11:47
   * @author Christian Scheiblich
   * @return integer representing the number of coeffs.
   */
  def getLength(): Int = _coeffs.length;

  /**
   * Returns a double array with the coeffs.
   *
   * @date 08.02.2010 13:14:54
   * @author Christian Scheiblich
   * @return double array keeping the coeffs.
   */
  def getCoeffs(): Array[Double] = _coeffs.clone

  /**
   * Returns a double array with the scales (of a wavelet).
   *
   * @date 08.02.2010 13:15:25
   * @author Christian Scheiblich
   * @return double array keeping the scales.
   */
  def getScales(): Array[Double] = _scales.clone
}