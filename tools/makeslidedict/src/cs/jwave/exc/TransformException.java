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
 * This file TransformException.java is part of JWave.
 *
 * @author Christian Scheiblich
 * date 23.02.2010 05:42:23
 * contact source@linux23.de
 */
package cs.jwave.exc;

/**
 * Class to be generally thrown in this package to mark an exception
 * 
 * @date 16.10.2008 07:30:20
 * @author Christian Scheiblich
 */
public class TransformException extends Throwable {

  /**
   * Generated serial version ID for this exception
   * 
   * @date 27.05.2009 06:58:27
   * @author Christian Scheiblich
   */
  private static final long serialVersionUID = -4165486739091019056L;

  /**
   * Member var for the stored exception message
   */
  protected String _message; // exception message

  /**
   * Constructor for storing a handed exception message
   * 
   * @date 27.05.2009 06:51:57
   * @author Christian Scheiblich
   * @param message
   *          this message should tell exactly what went wrong
   */
  public TransformException( String message ) {
    _message = "JWave"; // empty
    _message += ":"; // separator
    _message += message; // add message
    _message += "\n"; // break line
  } // TransformException

  /**
   * Copy constructor; use this for a quick fix of sub types
   * 
   * @date 29.07.2009 07:03:45
   * @author Christian Scheiblich
   * @param e
   *          an object of this class
   */
  public TransformException( Exception e ) {
    _message = e.getMessage( );
  } // TransformException

  /**
   * Returns the stored exception message as a string
   * 
   * @date 27.05.2009 06:52:46
   * @author Christian Scheiblich
   * @return exception message that should tell exactly what went wrong
   */
  @Override
  public String getMessage( ) {
    return _message;
  } // getMessage

  /**
   * Displays the stored exception message at console out
   * 
   * @date 27.05.2009 06:53:23
   * @author Christian Scheiblich
   */
  public void showMessage( ) {
    System.out.println( _message );
  } // showMessage

  /**
   * Nuke the run and print stack trace
   * 
   * @date 02.07.2009 05:07:42
   * @author Christian Scheiblich
   */
  public void nuke( ) {
    System.out.println( "" );
    System.out.println( "                  ____             " );
    System.out.println( "          __,-~~/~    `---.        " );
    System.out.println( "        _/_,---(      ,    )       " );
    System.out.println( "    __ /        NUKED     ) \\ __  " );
    System.out.println( "   ====------------------===;;;==  " );
    System.out.println( "      /  ~\"~\"~\"~\"~\"~~\"~)     " );
    System.out.println( "      (_ (      (     >    \\)     " );
    System.out.println( "       \\_( _ <         >_>\'      " );
    System.out.println( "           ~ `-i' ::>|--\"         " );
    System.out.println( "               I;|.|.|             " );
    System.out.println( "              <|i::|i|>            " );
    System.out.println( "               |[::|.|             " );
    System.out.println( "                ||: |              " );
    System.out.println( "" );
    this.showMessage( );
    this.printStackTrace( );
  } // nuke

} // class
