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
 * This file TransformError.java is part of JWave.
 *
 * @author Christian Scheiblich
 * date 23.02.2010 05:42:23
 * contact source@linux23.de
 */
package cs.jwave.exc;

/**
 * Marking errors for this package; failures that are not recoverable
 * 
 * @date 19.05.2009 09:28:17
 * @author Christian Scheiblich
 */
public class TransformError extends TransformException {

  /**
   * Generated serial ID for this error
   * 
   * @date 19.05.2009 09:29:04
   * @author Christian Scheiblich
   */
  private static final long serialVersionUID = -2757378141408012245L;

  /**
   * constructor taking an error message
   * 
   * @date 19.05.2009 09:28:17
   * @author Christian Scheiblich
   * @param message
   *          stored message for this error
   */
  public TransformError( String message ) {
    super( message );
  } // TransformError

} // class
