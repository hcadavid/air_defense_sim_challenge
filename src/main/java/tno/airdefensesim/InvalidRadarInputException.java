/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */

package tno.airdefensesim;

/**
 *
 * @author hcadavid
 */
public class InvalidRadarInputException extends Exception {


    /**
     * Constructs an instance of <code>InvalidRadarInputException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public InvalidRadarInputException(String msg, Exception rootCause) {
        super(msg,rootCause);
    }
}
