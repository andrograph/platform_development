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
 * This file AncientEgyptianDecomposition.java is part of JWave.
 *
 * @author Christian Scheiblich
 * date 14.08.2010 10:43:28
 * contact source@linux23.de
 */
package cs.jwave.handlers;

/**
 * A wavelet transform method for arrays and signals of arbitrary lengths, even
 * odd lengths. The array is decomposed in several parts of optimal lengths by
 * applying the ancient Egyptian decomposition. Hereby, the array or signal is
 * decomposed to the largest possible sub arrays of two the power of p.
 * Afterwards each sub array is transformed forward and copied back to the
 * discrete position of the input array. The reverse transform applies the same
 * vice versa. In more detail the ancient Egyptian Multiplication can be easily
 * explained by the following example: 42 = 2^5 + 2^3 + 2^1 = 32 + 8 + 2.
 * However, an array or signal of odd length produces the smallest ancient
 * Egyptian multiplier 2^0 which is actually 1. Therefore, the matching sub
 * array or signal is untouched an the coefficient is actually the wavelet
 * coefficient of wavelet space of level 0. For an "orthonormal" wavelet this
 * holds. See: http://en.wikipedia.org/wiki/Ancient_Egyptian_multiplication
 * 
 * @date 14.08.2010 10:43:28
 * @author Christian Scheiblich
 */
public class AncientEgyptianDecomposition extends BasicTransform {

  /**
   * The selected Transform (FWT or WPT) used for the sub arrays of the ancient
   * Egyptian decomposition. Actually, this displays somehow the Composite
   * Pattern of software design pattern. See:
   * http://en.wikipedia.org/wiki/Composite_pattern#Java
   */
  protected BasicTransform _transform;

  /**
   * Constructor taking the
   * 
   * @date 14.08.2010 10:43:28
   * @author Christian Scheiblich
   */
  public AncientEgyptianDecomposition( BasicTransform transform ) {
    _transform = transform;
  } // FastWaveletTransformArbitrary

  /**
   * The method converts a positive integer to the ancient Egyptian multipliers
   * which are actually the multipliers to display the number by a sum of the
   * largest possible powers of two. E.g. 42 = 2^5 + 2^3 + 2^1 = 32 + 8 + 2.
   * However, odd numbers always 2^0 = 1 as the last entry. Also see:
   * http://en.wikipedia.org/wiki/Ancient_Egyptian_multiplication
   * 
   * @date 14.08.2010 13:40:54
   * @author Christian Scheiblich
   * @param number
   *          positive integer
   * @return an integer array keeping the ancient Egyptian multipliers
   */
  public int[ ] convertInteger2AncientEgyptianMultiplipliers( int number ) {

    if( number < 1 )
      return null;

    int power = getExponent(number);

    int[ ] tmpArr = new int[ power + 1 ]; // max no of possible multipliers

    int pos = 0;
    double current = (double)number;
    while( current >= 1. ) {

      power = getExponent(current);
      tmpArr[ pos ] = power;
      current = current - scalb(1., power); // 1. * 2 ^ power
      pos++;

    } // while

    int[ ] ancientEgyptianMultipliers = new int[ pos ]; // shrink
    for( int c = 0; c < pos; c++ )
      ancientEgyptianMultipliers[ c ] = tmpArr[ c ];

    return ancientEgyptianMultipliers;

  } // convertInteger2AncientEgyptianMultiplipliers

  private int getExponent(int number) {
      //return Math.getExponent( (double)number );
      return (int)(Math.log((double)number) / Math.log(2));
  }
  private int getExponent(double number) {
      //return Math.getExponent( (double)number );
      return (int)(Math.log(number) / Math.log(2));
  }

private double scalb(double d, int scaleFactor) {
    //Math.scalb( 1., scaleFactor );
    return 1. * Math.pow(2., scaleFactor);
}

  /**
   * The method converts a list of ancient Egyptian multipliers to the
   * corresponding integer. The ancient Egyptian multipliers are actually the
   * multipliers to display am integer by a sum of the largest possible powers
   * of two. E.g. 43 = 2^5 + 2^3 + 2^1 + 1^0 = 32 + 8 + 2 + 1. Also see:
   * http://en.wikipedia.org/wiki/Ancient_Egyptian_multiplication
   * 
   * @date 14.08.2010 16:48:44
   * @author Christian Scheiblich
   * @param ancientEgyptianMultipliers
   *          an integer array keeping the ancient Egyptian multipliers
   * @return resulting integer as sum of powers of two
   */
  public int convertAncientEgyptianMultiplipliers2Integer(
      int[ ] ancientEgyptianMultipliers ) {

    int number = 0;

    int noOfAncientEgyptianMultipliers = ancientEgyptianMultipliers.length;
    for( int m = 0; m < noOfAncientEgyptianMultipliers; m++ ) {

      int ancientEgyptianMultiplier = ancientEgyptianMultipliers[ m ];

      number += (int)scalb(1., ancientEgyptianMultiplier); // 1. * 2^p

    } // m

    return number;

  } // convertAncientEgyptianMultiplipliers2Integer

  /**
   * This forward method decomposes the given array of arbitrary length to sub
   * arrays while applying the ancient Egyptian decomposition. Each sub array is
   * transformed by the selected basic transform and the resulting wavelet
   * coefficients are copied back to their original discrete positions.
   * 
   * @date 14.08.2010 10:43:28
   * @author Christian Scheiblich
   * @see cs.jwave.handlers.BasicTransform#forward(double[])
   */
  @Override
  public double[ ] forward( double[ ] arrTime ) {

    double[ ] arrHilb = new double[ arrTime.length ];

    int[ ] ancientEgyptianMultipliers = convertInteger2AncientEgyptianMultiplipliers( arrTime.length );

    int offSet = 0;
    for( int m = 0; m < ancientEgyptianMultipliers.length; m++ ) {

      int ancientEgyptianMultiplier = ancientEgyptianMultipliers[ m ];

      int arrTimeSubLength = (int)scalb( 1., ancientEgyptianMultiplier );

      double[ ] arrTimeSub = new double[ arrTimeSubLength ];
      for( int i = 0; i < arrTimeSub.length; i++ )
        arrTimeSub[ i ] = arrTime[ i + offSet ];

      double[ ] arrHilbSub = _transform.forward( arrTimeSub );

      for( int i = 0; i < arrHilbSub.length; i++ )
        arrHilb[ i + offSet ] = arrHilbSub[ i ];

      offSet += arrHilbSub.length;

    } // m - no of sub transforms

    return arrHilb;
  } // forward

  /**
   * This reverse method awaits an array of arbitrary length in wavelet space
   * keeping the wavelet already decomposed by the ancient Egyptian
   * decomposition. Therefore, each of the existing sub arrays of length 2^p is
   * reverse transformed by the selected basic transform and the resulting
   * coefficients of time domain are copied back to their original discrete
   * positions.
   * 
   * @date 14.08.2010 10:43:28
   * @author Christian Scheiblich
   * @see cs.jwave.handlers.BasicTransform#reverse(double[])
   */
  @Override
  public double[ ] reverse( double[ ] arrHilb ) {

    double[ ] arrTime = new double[ arrHilb.length ];

    int[ ] ancientEgyptianMultipliers = convertInteger2AncientEgyptianMultiplipliers( arrHilb.length );

    int offSet = 0;
    for( int m = 0; m < ancientEgyptianMultipliers.length; m++ ) {

      int ancientEgyptianMultiplier = ancientEgyptianMultipliers[ m ];

      int arrHilbSubLength = (int)scalb( 1., ancientEgyptianMultiplier );

      double[ ] arrHilbSub = new double[ arrHilbSubLength ];
      for( int i = 0; i < arrHilbSub.length; i++ )
        arrHilbSub[ i ] = arrHilb[ i + offSet ];

      double[ ] arrTimeSub = _transform.reverse( arrHilbSub );

      for( int i = 0; i < arrTimeSub.length; i++ )
        arrTime[ i + offSet ] = arrTimeSub[ i ];

      offSet += arrHilbSub.length;

    } // m - no of sub transforms

    return arrTime;
  } // reverse

  /**
   * TODO Christian Scheiblich explainMeShortly
   * 
   * @date 14.08.2010 10:43:28
   * @author Christian Scheiblich
   * @see cs.jwave.handlers.BasicTransform#forward(double[], int)
   */
  @Override
  public double[ ] forward( double[ ] arrTime, int toLevel ) {

    double[ ] arrHilb = new double[ arrTime.length ];

    int[ ] ancientEgyptianMultipliers = convertInteger2AncientEgyptianMultiplipliers( arrTime.length );

    int offSet = 0;
    for( int m = 0; m < ancientEgyptianMultipliers.length; m++ ) {

      int ancientEgyptianMultiplier = ancientEgyptianMultipliers[ m ];

      int arrTimeSubLength = (int)scalb( 1., ancientEgyptianMultiplier );

      double[ ] arrTimeSub = new double[ arrTimeSubLength ];
      for( int i = 0; i < arrTimeSub.length; i++ )
        arrTimeSub[ i ] = arrTime[ i + offSet ];

      double[ ] arrHilbSub = _transform.forward( arrTimeSub, toLevel );

      for( int i = 0; i < arrHilbSub.length; i++ )
        arrHilb[ i + offSet ] = arrHilbSub[ i ];

      offSet += arrHilbSub.length;

    } // m - no of sub transforms

    return arrHilb;
  } // forward

  /**
   * TODO Christian Scheiblich explainMeShortly
   * 
   * @date 14.08.2010 10:43:28
   * @author Christian Scheiblich
   * @see cs.jwave.handlers.BasicTransform#reverse(double[], int)
   */
  @Override
  public double[ ] reverse( double[ ] arrHilb, int fromLevel ) {

    double[ ] arrTime = new double[ arrHilb.length ];

    int[ ] ancientEgyptianMultipliers = convertInteger2AncientEgyptianMultiplipliers( arrHilb.length );

    int offSet = 0;
    for( int m = 0; m < ancientEgyptianMultipliers.length; m++ ) {

      int ancientEgyptianMultiplier = ancientEgyptianMultipliers[ m ];

      int arrHilbSubLength = (int)scalb( 1., ancientEgyptianMultiplier );

      double[ ] arrHilbSub = new double[ arrHilbSubLength ];
      for( int i = 0; i < arrHilbSub.length; i++ )
        arrHilbSub[ i ] = arrHilb[ i + offSet ];

      double[ ] arrTimeSub = _transform.reverse( arrHilbSub, fromLevel );

      for( int i = 0; i < arrTimeSub.length; i++ )
        arrTime[ i + offSet ] = arrTimeSub[ i ];

      offSet += arrHilbSub.length;

    } // m - no of sub transforms

    return arrTime;
  } // reverse

} // class
