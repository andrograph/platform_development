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
 * This file BasicTransform.java is part of JWave.
 *
 * @author Christian Scheiblich
 * date 23.02.2010 05:42:23
 * contact source@linux23.de
 */
package cs.jwave.handlers;

import cs.jwave.types.Complex;

/**
 * Basic Wave for transformations like Fast Fourier Transform (FFT), Fast
 * Wavelet Transform (FWT), or Fast Wavelet Packet Transform (WPT). Naming of
 * this class due to en.wikipedia.org; to write Fourier series in terms of the
 * 'basic waves' of function: e^(2*pi*i*w).
 * 
 * @date 08.02.2010 11:11:59
 * @author Christian Scheiblich
 */
public abstract class BasicTransform {

  /**
   * Constructor; does nothing
   * 
   * @date 08.02.2010 11:11:59
   * @author Christian Scheiblich
   */
  protected BasicTransform( ) {
  } // BasicTransform

  /**
   * Performs the forward transform from time domain to frequency or Hilbert
   * domain for a given array depending on the used transform algorithm by
   * inheritance.
   * 
   * @date 10.02.2010 08:23:24
   * @author Christian Scheiblich
   * @param arrTime
   *          coefficients of 1-D time domain
   * @return coefficients of 1-D frequency or Hilbert domain
   */
  public abstract double[ ] forward( double[ ] arrTime );

  /**
   * Performs the reverse transform from frequency or Hilbert domain to time
   * domain for a given array depending on the used transform algorithm by
   * inheritance.
   * 
   * @date 10.02.2010 08:23:24
   * @author Christian Scheiblich
   * @param arrFreq
   *          coefficients of 1-D frequency or Hilbert domain
   * @return coefficients of 1-D time domain
   */
  public abstract double[ ] reverse( double[ ] arrFreq );

  /**
   * Performs the forward transform from time domain to frequency or Hilbert
   * domain for a given array depending on the used transform algorithm by
   * inheritance.
   * 
   * @date 23.11.2010 19:17:46
   * @author Christian Scheiblich
   * @param arrTime
   *          coefficients of 1-D time domain
   * @return coefficients of 1-D frequency or Hilbert domain
   */
  public Complex[ ] forward( Complex[ ] arrTime ) {
    return null;
  } // forward

  /**
   * Performs the reverse transform from frequency or Hilbert domain to time
   * domain for a given array depending on the used transform algorithm by
   * inheritance.
   * 
   * @date 23.11.2010 19:17:59
   * @author Christian Scheiblich
   * @param arrFreq
   *          coefficients of 1-D frequency or Hilbert domain
   * @return coefficients of 1-D time domain
   */
  public Complex[ ] reverse( Complex[ ] arrFreq ) {
    return null;
  } // reverse

  /**
   * Performs the forward transform from time domain to frequency or Hilbert
   * domain for a given array depending on the used transform algorithm by
   * inheritance. The number of transformation levels applied is limited by
   * threshold.
   * 
   * @date 15.07.2010
   * @author Thomas Haider
   * @date 15.08.2010 00:32:09
   * @author Christian Scheiblich
   * @param arrTime
   *          coefficients of 1-D time domain
   * @param toLevel
   *          threshold for number of iterations
   * @return coefficients of 1-D frequency or Hilbert domain
   */
  public abstract double[ ] forward( double[ ] arrTime, int toLevel );

  /**
   * Performs the reverse transform from frequency or Hilbert domain to time
   * domain for a given array depending on the used transform algorithm by
   * inheritance. The number of transformation levels applied is limited by
   * threshold.
   * 
   * @date 15.07.2010
   * @author Thomas Haider
   * @date 15.08.2010 00:32:24
   * @author Christian Scheiblich
   * @param arrFreq
   *          coefficients of 1-D frequency or Hilbert domain
   * @param fromLevel
   *          threshold for number of iterations
   * @return coefficients of 1-D time domain
   */
  public abstract double[ ] reverse( double[ ] arrFreq, int fromLevel );

  /**
   * Performs the 2-D forward transform from time domain to frequency or Hilbert
   * domain for a given array depending on the used transform algorithm by
   * inheritance.
   * 
   * @date 10.02.2010 11:00:29
   * @author Christian Scheiblich
   * @param matTime
   *          coefficients of 2-D time domain
   * @return coefficients of 2-D frequency or Hilbert domain
   */
  public double[ ][ ] forward( double[ ][ ] matTime ) {

    int noOfRows = matTime.length;
    int noOfCols = matTime[ 0 ].length;

    double[ ][ ] matHilb = new double[ noOfRows ][ noOfCols ];

    for( int i = 0; i < noOfRows; i++ ) {

      double[ ] arrTime = new double[ noOfCols ];

      for( int j = 0; j < noOfCols; j++ )
        arrTime[ j ] = matTime[ i ][ j ];

      double[ ] arrHilb = forward( arrTime );

      for( int j = 0; j < noOfCols; j++ )
        matHilb[ i ][ j ] = arrHilb[ j ];

    } // rows

    for( int j = 0; j < noOfCols; j++ ) {

      double[ ] arrTime = new double[ noOfRows ];

      for( int i = 0; i < noOfRows; i++ )
        arrTime[ i ] = matHilb[ i ][ j ];

      double[ ] arrHilb = forward( arrTime );

      for( int i = 0; i < noOfRows; i++ )
        matHilb[ i ][ j ] = arrHilb[ i ];

    } // cols

    return matHilb;
  } // forward

  /**
   * Performs the 2-D reverse transform from frequency or Hilbert or time domain
   * to time domain for a given array depending on the used transform algorithm
   * by inheritance.
   * 
   * @date 10.02.2010 11:01:38
   * @author Christian Scheiblich
   * @param matFreq
   *          coefficients of 2-D frequency or Hilbert domain
   * @return coefficients of 2-D time domain
   */
  public double[ ][ ] reverse( double[ ][ ] matFreq ) {

    int noOfRows = matFreq.length;
    int noOfCols = matFreq[ 0 ].length;

    double[ ][ ] matTime = new double[ noOfRows ][ noOfCols ];

    for( int j = 0; j < noOfCols; j++ ) {

      double[ ] arrFreq = new double[ noOfRows ];

      for( int i = 0; i < noOfRows; i++ )
        arrFreq[ i ] = matFreq[ i ][ j ];

      double[ ] arrTime = reverse( arrFreq ); // AED 

      for( int i = 0; i < noOfRows; i++ )
        matTime[ i ][ j ] = arrTime[ i ];

    } // cols

    for( int i = 0; i < noOfRows; i++ ) {

      double[ ] arrFreq = new double[ noOfCols ];

      for( int j = 0; j < noOfCols; j++ )
        arrFreq[ j ] = matTime[ i ][ j ];

      double[ ] arrTime = reverse( arrFreq ); // AED 

      for( int j = 0; j < noOfCols; j++ )
        matTime[ i ][ j ] = arrTime[ j ];

    } // rows

    return matTime;
  } // reverse

  /**
   * Performs the 3-D forward transform from time domain to frequency or Hilbert
   * domain for a given array depending on the used transform algorithm by
   * inheritance.
   * 
   * @date 10.07.2010 18:08:17
   * @author Christian Scheiblich
   * @param spcTime
   *          coefficients of 3-D time domain domain
   * @return coefficients of 3-D frequency or Hilbert domain
   */
  public double[ ][ ][ ] forward( double[ ][ ][ ] spcTime ) {

    int noOfRows = spcTime.length; // first dimension
    int noOfCols = spcTime[ 0 ].length; // second dimension
    int noOfHigh = spcTime[ 0 ][ 0 ].length; // third dimension

    double[ ][ ][ ] spcHilb = new double[ noOfRows ][ noOfCols ][ noOfHigh ];

    for( int i = 0; i < noOfRows; i++ ) {

      double[ ][ ] matTime = new double[ noOfCols ][ noOfHigh ];

      for( int j = 0; j < noOfCols; j++ ) {

        for( int k = 0; k < noOfHigh; k++ ) {

          matTime[ j ][ k ] = spcTime[ i ][ j ][ k ];

        } // high

      } // cols      

      double[ ][ ] matHilb = forward( matTime ); // 2-D forward

      for( int j = 0; j < noOfCols; j++ ) {

        for( int k = 0; k < noOfHigh; k++ ) {

          spcHilb[ i ][ j ][ k ] = matHilb[ j ][ k ];

        } // high

      } // cols

    } // rows  

    for( int j = 0; j < noOfCols; j++ ) {

      for( int k = 0; k < noOfHigh; k++ ) {

        double[ ] arrTime = new double[ noOfRows ];

        for( int i = 0; i < noOfRows; i++ )
          arrTime[ i ] = spcHilb[ i ][ j ][ k ];

        double[ ] arrHilb = forward( arrTime ); // 1-D forward

        for( int i = 0; i < noOfRows; i++ )
          spcHilb[ i ][ j ][ k ] = arrHilb[ i ];

      } // high

    } // cols

    return spcHilb;

  } // forward

  /**
   * Performs the 3-D reverse transform from frequency or Hilbert domain to time
   * domain for a given array depending on the used transform algorithm by
   * inheritance.
   * 
   * @date 10.07.2010 18:09:54
   * @author Christian Scheiblich
   * @param spcHilb
   *          coefficients of 3-D frequency or Hilbert domain
   * @return coefficients of 3-D time domain
   */
  public double[ ][ ][ ] reverse( double[ ][ ][ ] spcHilb ) {

    int noOfRows = spcHilb.length; // first dimension
    int noOfCols = spcHilb[ 0 ].length; // second dimension
    int noOfHigh = spcHilb[ 0 ][ 0 ].length; // third dimension

    double[ ][ ][ ] spcTime = new double[ noOfRows ][ noOfCols ][ noOfHigh ];

    for( int i = 0; i < noOfRows; i++ ) {

      double[ ][ ] matHilb = new double[ noOfCols ][ noOfHigh ];

      for( int j = 0; j < noOfCols; j++ ) {

        for( int k = 0; k < noOfHigh; k++ ) {

          matHilb[ j ][ k ] = spcHilb[ i ][ j ][ k ];

        } // high

      } // cols      

      double[ ][ ] matTime = reverse( matHilb ); // 2-D reverse

      for( int j = 0; j < noOfCols; j++ ) {

        for( int k = 0; k < noOfHigh; k++ ) {

          spcTime[ i ][ j ][ k ] = matTime[ j ][ k ];

        } // high

      } // cols

    } // rows  

    for( int j = 0; j < noOfCols; j++ ) {

      for( int k = 0; k < noOfHigh; k++ ) {

        double[ ] arrHilb = new double[ noOfRows ];

        for( int i = 0; i < noOfRows; i++ )
          arrHilb[ i ] = spcTime[ i ][ j ][ k ];

        double[ ] arrTime = reverse( arrHilb ); // 1-D reverse

        for( int i = 0; i < noOfRows; i++ )
          spcTime[ i ][ j ][ k ] = arrTime[ i ];

      } // high

    } // cols

    return spcTime;

  } // reverse

  /**
   * Performs the 2-D forward transform from time domain to frequency or Hilbert
   * domain for a given array depending on the used transform algorithm by
   * inheritance. The number of transformation levels applied is limited by
   * threshold.
   * 
   * @date 15.07.2010
   * @author Thomas Haider
   * @date 15.08.2010 00:32:52
   * @author Christian Scheiblich
   * @param matTime
   *          coefficients of 2-D time domain
   * @param toLevel
   *          threshold for number of iterations
   * @return coefficients of 2-D frequency or Hilbert domain
   */
  public double[ ][ ] forward( double[ ][ ] matTime, int toLevel ) {

    int noOfRows = matTime.length;
    int noOfCols = matTime[ 0 ].length;

    double[ ][ ] matHilb = new double[ noOfRows ][ noOfCols ];

    for( int i = 0; i < noOfRows; i++ ) {

      double[ ] arrTime = new double[ noOfCols ];

      for( int j = 0; j < noOfCols; j++ )
        arrTime[ j ] = matTime[ i ][ j ];

      double[ ] arrHilb = forward( arrTime, toLevel );

      for( int j = 0; j < noOfCols; j++ )
        matHilb[ i ][ j ] = arrHilb[ j ];

    } // rows

    for( int j = 0; j < noOfCols; j++ ) {

      double[ ] arrTime = new double[ noOfRows ];

      for( int i = 0; i < noOfRows; i++ )
        arrTime[ i ] = matHilb[ i ][ j ];

      double[ ] arrHilb = forward( arrTime, toLevel );

      for( int i = 0; i < noOfRows; i++ )
        matHilb[ i ][ j ] = arrHilb[ i ];

    } // cols

    return matHilb;
  } // forward

  /**
   * Performs the 2-D reverse transform from frequency or Hilbert or time domain
   * to time domain for a given array depending on the used transform algorithm
   * by inheritance. The number of transformation levels applied is limited by
   * threshold.
   * 
   * @date 15.07.2010
   * @author Thomas Haider
   * @date 15.08.2010 00:33:10
   * @author Christian Scheiblich
   * @param matFreq
   *          coefficients of 2-D frequency or Hilbert domain
   * @param fromLevel
   *          threshold for number of iterations
   * @return coefficients of 2-D time domain
   */
  public double[ ][ ] reverse( double[ ][ ] matFreq, int fromLevel ) {

    int noOfRows = matFreq.length;
    int noOfCols = matFreq[ 0 ].length;

    double[ ][ ] matTime = new double[ noOfRows ][ noOfCols ];

    for( int j = 0; j < noOfCols; j++ ) {

      double[ ] arrFreq = new double[ noOfRows ];

      for( int i = 0; i < noOfRows; i++ )
        arrFreq[ i ] = matFreq[ i ][ j ];

      double[ ] arrTime = reverse( arrFreq, fromLevel );

      for( int i = 0; i < noOfRows; i++ )
        matTime[ i ][ j ] = arrTime[ i ];

    } // cols

    for( int i = 0; i < noOfRows; i++ ) {

      double[ ] arrFreq = new double[ noOfCols ];

      for( int j = 0; j < noOfCols; j++ )
        arrFreq[ j ] = matTime[ i ][ j ];

      double[ ] arrTime = reverse( arrFreq, fromLevel );

      for( int j = 0; j < noOfCols; j++ )
        matTime[ i ][ j ] = arrTime[ j ];

    } // rows

    return matTime;
  }

  /**
   * Performs the 3-D forward transform from time domain to frequency or Hilbert
   * domain for a given array depending on the used transform algorithm by
   * inheritance. The number of transformation levels applied is limited by
   * threshold.
   * 
   * @date 15.07.2010
   * @author Thomas Haider
   * @date 15.08.2010 00:33:30
   * @author Christian Scheiblich
   * @param matrixFreq
   *          coefficients of 3-D frequency or Hilbert domain
   * @param toLevel
   *          threshold for number of iterations
   * @return coefficients of 3-D time domain
   */
  public double[ ][ ][ ] forward( double[ ][ ][ ] spcTime, int toLevel ) {

    int noOfRows = spcTime.length; // first dimension
    int noOfCols = spcTime[ 0 ].length; // second dimension
    int noOfHigh = spcTime[ 0 ][ 0 ].length; // third dimension

    double[ ][ ][ ] spcHilb = new double[ noOfRows ][ noOfCols ][ noOfHigh ];

    for( int i = 0; i < noOfRows; i++ ) {

      double[ ][ ] matTime = new double[ noOfCols ][ noOfHigh ];

      for( int j = 0; j < noOfCols; j++ ) {

        for( int k = 0; k < noOfHigh; k++ ) {

          matTime[ j ][ k ] = spcTime[ i ][ j ][ k ];

        } // high

      } // cols      

      double[ ][ ] matHilb = forward( matTime, toLevel ); // 2-D forward

      for( int j = 0; j < noOfCols; j++ ) {

        for( int k = 0; k < noOfHigh; k++ ) {

          spcHilb[ i ][ j ][ k ] = matHilb[ j ][ k ];

        } // high

      } // cols

    } // rows  

    for( int j = 0; j < noOfCols; j++ ) {

      for( int k = 0; k < noOfHigh; k++ ) {

        double[ ] arrTime = new double[ noOfRows ];

        for( int i = 0; i < noOfRows; i++ )
          arrTime[ i ] = spcHilb[ i ][ j ][ k ];

        double[ ] arrHilb = forward( arrTime, toLevel ); // 1-D forward

        for( int i = 0; i < noOfRows; i++ )
          spcHilb[ i ][ j ][ k ] = arrHilb[ i ];

      } // high

    } // cols

    return spcHilb;
  } // forward

  /**
   * Performs the 3-D reverse transform from frequency or Hilbert domain to time
   * domain for a given array depending on the used transform algorithm by
   * inheritance. The number of transformation levels applied is limited by
   * threshold.
   * 
   * @date 15.07.2010
   * @author Thomas Haider
   * @date 15.08.2010 00:33:44
   * @author Christian Scheiblich
   * @param matrixFreq
   *          coefficients of 3-D frequency or Hilbert domain
   * @param threshold
   *          threshold for number of iterations
   * @return coefficients of 3-D time domain
   */
  public double[ ][ ][ ] reverse( double[ ][ ][ ] spcHilb, int fromLevel ) {

    int noOfRows = spcHilb.length; // first dimension
    int noOfCols = spcHilb[ 0 ].length; // second dimension
    int noOfHigh = spcHilb[ 0 ][ 0 ].length; // third dimension

    double[ ][ ][ ] spcTime = new double[ noOfRows ][ noOfCols ][ noOfHigh ];

    for( int i = 0; i < noOfRows; i++ ) {

      double[ ][ ] matHilb = new double[ noOfCols ][ noOfHigh ];

      for( int j = 0; j < noOfCols; j++ ) {

        for( int k = 0; k < noOfHigh; k++ ) {

          matHilb[ j ][ k ] = spcHilb[ i ][ j ][ k ];

        } // high

      } // cols      

      double[ ][ ] matTime = reverse( matHilb, fromLevel ); // 2-D reverse

      for( int j = 0; j < noOfCols; j++ ) {

        for( int k = 0; k < noOfHigh; k++ ) {

          spcTime[ i ][ j ][ k ] = matTime[ j ][ k ];

        } // high

      } // cols

    } // rows  

    for( int j = 0; j < noOfCols; j++ ) {

      for( int k = 0; k < noOfHigh; k++ ) {

        double[ ] arrHilb = new double[ noOfRows ];

        for( int i = 0; i < noOfRows; i++ )
          arrHilb[ i ] = spcTime[ i ][ j ][ k ];

        double[ ] arrTime = reverse( arrHilb, fromLevel ); // 1-D reverse

        for( int i = 0; i < noOfRows; i++ )
          spcTime[ i ][ j ][ k ] = arrTime[ i ];

      } // high

    } // cols

    return spcTime;
  } // reverse

} // class
