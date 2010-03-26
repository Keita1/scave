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
 * This file JWave.java is part of JWave.
 *
 * @author Christian Scheiblich
 * date 23.02.2010 14:26:47
 * contact source@linux23.de
 */
package cs.jwave;

import cs.jwave.handlers.BasicTransform;
import cs.jwave.handlers.DiscreteFourierTransform;
import cs.jwave.handlers.FastWaveletTransform;
import cs.jwave.handlers.WaveletPacketTransform;
import cs.jwave.handlers.wavelets.Coif06;
import cs.jwave.handlers.wavelets.Daub04;
import cs.jwave.handlers.wavelets.Daub06;
import cs.jwave.handlers.wavelets.Daub08;
import cs.jwave.handlers.wavelets.Haar02;
import cs.jwave.handlers.wavelets.Wavelet;

/**
 * Main class for doing little test runs for different transform types and
 * different wavelets without JUnit.
 * 
 * @date 23.02.2010 14:26:47
 * @author Christian Scheiblich
 */
public class JWave {

  /**
   * Constructor.
   * 
   * @date 23.02.2010 14:26:47
   * @author Christian Scheiblich
   */
  public JWave( ) {
  } // JWave

  /**
   * Main method for doing little test runs for different transform types and
   * different wavelets without JUnit. Requesting the transform type and the
   * type of wavelet to be used else usage is printed.
   * 
   * @date 23.02.2010 14:26:47
   * @author Christian Scheiblich
   * @param args
   *          [transformType] [waveletType]
   */
  public static void main( String[ ] args ) {

    if( args.length != 2 ) {
      System.err.println( "usage: JWave [transformType] {waveletType}" );
      System.err.println( "" );
      System.err.println( "transformType: DFT, FWT, WPT" );
      System.err
          .println( "waveletType  : Haar02, Daub04, Daub06, Coif06, Daub08" );
      return;
    }

    String wType = args[ 1 ];
    Wavelet wavelet = null;
    if( wType.equalsIgnoreCase( "haar02" ) )
      wavelet = new Haar02( );
    else if( wType.equalsIgnoreCase( "daub04" ) )
      wavelet = new Daub04( );
    else if( wType.equalsIgnoreCase( "daub06" ) )
      wavelet = new Daub06( );
    else if( wType.equalsIgnoreCase( "coif06" ) )
      wavelet = new Coif06( );
    else if( wType.equalsIgnoreCase( "daub08" ) )
      wavelet = new Daub08( );
    else {
      System.err.println( "usage: JWave [transformType] {waveletType}" );
      System.err.println( "" );
      System.err
          .println( "available wavelets are Haar02, Daub04, Daub06, Coif06, Daub08" );
      return;
    } // if wType

    String tType = args[ 0 ];
    BasicTransform bWave = null;
    if( tType.equalsIgnoreCase( "dft" ) )
      bWave = new DiscreteFourierTransform( );
    else if( tType.equalsIgnoreCase( "fwt" ) )
      bWave = new FastWaveletTransform( wavelet );
    else if( tType.equalsIgnoreCase( "wpt" ) )
      bWave = new WaveletPacketTransform( wavelet );
    else {
      System.err.println( "usage: JWave [transformType] {waveletType}" );
      System.err.println( "" );
      System.err.println( "available transforms are DFT, FWT, WPT" );
      return;
    } // if tType

    Transform t = new Transform( bWave );

    double[ ] arrTime = { 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1. };

    System.out.println( "" );
    System.out.println( "time domain:" );
    for( int p = 0; p < arrTime.length; p++ )
      System.out.printf( "%9.6f", arrTime[ p ] );
    System.out.println( "" );

    double[ ] arrFreqOrHilb = t.forward( arrTime ); // 1-D forward transform

    if( bWave instanceof DiscreteFourierTransform )
      System.out.println( "frequency domain:" );
    else
      System.out.println( "hilbert domain:" );
    for( int p = 0; p < arrTime.length; p++ )
      System.out.printf( "%9.6f", arrFreqOrHilb[ p ] );
    System.out.println( "" );

    double[ ] arrReco = t.reverse( arrFreqOrHilb ); // 1-D reverse transform

    System.out.println( "reconstruction:" );
    for( int p = 0; p < arrTime.length; p++ )
      System.out.printf( "%9.6f", arrReco[ p ] );
    System.out.println( "" );

  } // main

} // class
