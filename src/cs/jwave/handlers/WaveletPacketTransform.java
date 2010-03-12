/**
 * JWave - Java implementation of wavelet transform algorithms
 *
 * Copyright 2010 Christian Scheiblich
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
 * This file WaveletPacketTransform.java is part of JWave.
 *
 * @author Christian Scheiblich
 * date 23.02.2010 13:44:05
 * contact source@linux23.de
 */
package cs.jwave.handlers;

import cs.jwave.handlers.wavelets.Wavelet;

/**
 * Base class for the forward and reverse Wavelet Packet Transform using a
 * specified Wavelet by inheriting class.
 * 
 * @date 23.02.2010 13:44:05
 * @author Christian Scheiblich
 */
public class WaveletPacketTransform extends BasicTransform {

  /**
   * The used wavelet for the specified transform algorithm.
   */
  Wavelet _wavelet;

  /**
   * Constructor receiving a Wavelet object.
   * 
   * @date 23.02.2010 13:44:05
   * @author Christian Scheiblich
   * @param wavelet
   *          object of type Wavelet; Haar02, Daub04, Coif06, ...
   */
  public WaveletPacketTransform( Wavelet wavelet ) {
    _wavelet = wavelet;
  } // WaveletPacketTransform

  /**
   * Implementation of the 1-D forward wavelet packet transform by filtering
   * with the longest wavelet first and the always both sub bands -- low and
   * high -- by the next smaller wavelet.
   * 
   * @date 23.02.2010 13:44:05
   * @author Christian Scheiblich
   * @see cs.jwave.handlers.BasicTransform#forward(double[])
   */
  @Override
  public double[ ] forward( double[ ] arrTime ) {

    // TODO think about not to allocating new MEM; return arrTime as arrHilb
    double[ ] arrHilb = new double[ arrTime.length ];
    for( int i = 0; i < arrTime.length; i++ )
      arrHilb[ i ] = arrTime[ i ];

    int level = 0;
    int k = arrTime.length;
    int h = arrTime.length;
    int minWaveLength = _wavelet.getWaveLength( );
    if( h >= minWaveLength ) {

      while( h >= minWaveLength ) {

        int g = k / h; // 1 -> 2 -> 4 -> 8 -> ...

        for( int p = 0; p < g; p++ ) {

          double[ ] iBuf = new double[ h ];
          for( int i = 0; i < h; i++ )
            iBuf[ i ] = arrHilb[ i + ( p * h ) ];

          double[ ] oBuf = _wavelet.forward( iBuf );

          for( int i = 0; i < h; i++ )
            arrHilb[ i + ( p * h ) ] = oBuf[ i ];

        } // packets

        h = h >> 1;

        level++;

      } // levels

    } // if

    return arrHilb;
  } // forward

  /**
   * Implementation of the 1-D reverse wavelet packet transform by filtering
   * with the smallest wavelet for all sub bands -- low and high bands -- and
   * the by the next greater wavelet combining two smaller and sub bands.
   * 
   * @date 23.02.2010 13:44:05
   * @author Christian Scheiblich
   * @see cs.jwave.handlers.BasicTransform#reverse(double[])
   */
  @Override
  public double[ ] reverse( double[ ] arrHilb ) {

    // TODO think about not to allocating new MEM; return arrHilb as arrTime
    double[ ] arrTime = new double[ arrHilb.length ];

    for( int i = 0; i < arrHilb.length; i++ )
      arrTime[ i ] = arrHilb[ i ];

    int level = 0;
    int minWaveLength = _wavelet.getWaveLength( );
    int k = arrTime.length;
    int h = minWaveLength;
    if( arrHilb.length >= minWaveLength ) {

      while( h <= arrTime.length && h >= minWaveLength ) {

        int g = k / h; // ... -> 8 -> 4 -> 2 -> 1

        for( int p = 0; p < g; p++ ) {

          double[ ] iBuf = new double[ h ];
          for( int i = 0; i < h; i++ )
            iBuf[ i ] = arrTime[ i + ( p * h ) ];

          double[ ] oBuf = _wavelet.reverse( iBuf );

          for( int i = 0; i < h; i++ )
            arrTime[ i + ( p * h ) ] = oBuf[ i ];
        } // packets

        h = h << 1;

        level++;
      } // levels

    } // if

    return arrTime;
  } // reverse

  /**
   * TODO Christian Scheiblich explainMeShortly
   * 
   * @date 23.02.2010 13:44:05
   * @author Christian Scheiblich
   * @see cs.jwave.handlers.BasicTransform#forward(double[][])
   */
  @Override
  public double[ ][ ] forward( double[ ][ ] matrixTime ) {
    // TODO Christian Scheiblich should implement this method
    return null;
  } // forward

  /**
   * TODO Christian Scheiblich explainMeShortly
   * 
   * @date 23.02.2010 13:44:05
   * @author Christian Scheiblich
   * @see cs.jwave.handlers.BasicTransform#reverse(double[][])
   */
  @Override
  public double[ ][ ] reverse( double[ ][ ] matrixFreq ) {
    // TODO Christian Scheiblich should implement this method
    return null;
  } // reverse

}
