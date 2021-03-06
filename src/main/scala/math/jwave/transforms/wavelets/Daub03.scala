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
 * This file Daub03.java is part of JWave.
 *
 * @author Christian Scheiblich
 * date 25.03.2010 14:03:20
 * contact graetz@mailfish.de
 */
package math.jwave.transforms.wavelets;

/**
 * Ingrid Daubechies' orthonormal wavelet of six coefficients and the scales;
 * normed, due to ||*||2 - euclidean norm.
 */

@Deprecated
object Daub03 extends Wavelet(Daub03Constants.scales)

object Daub03Constants {
  val sqrt02 = 1.4142135623730951; // Math.sqrt( 2.0 )
  val sqrt10 = Math.sqrt(10.0);
  val constA = Math.sqrt(5.0 + 2.0 * sqrt10);

  val scales = Array[Double](
    (1.0 + 1.0 * sqrt10 + 1.0 * constA) / 16.0 / sqrt02,
    (5.0 + 1.0 * sqrt10 + 3.0 * constA) / 16.0 / sqrt02,
    (10.0 - 2.0 * sqrt10 + 2.0 * constA) / 16.0 / sqrt02,
    (10.0 - 2.0 * sqrt10 - 2.0 * constA) / 16.0 / sqrt02,
    (5.0 + 1.0 * sqrt10 - 3.0 * constA) / 16.0 / sqrt02,
    (1.0 + 1.0 * sqrt10 - 1.0 * constA) / 16.0 / sqrt02)
}
