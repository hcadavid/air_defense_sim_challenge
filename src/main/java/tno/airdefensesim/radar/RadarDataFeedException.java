/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */

package tno.airdefensesim.radar;

/**
 *
 * @author hcadavid
 */
public class RadarDataFeedException extends Exception {


    /**
     * Constructs an instance of <code>RadarDataFeedException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public RadarDataFeedException(String msg) {
        super(msg);
    }

    public RadarDataFeedException(String msg, Exception cause) {
        super(msg, cause);
    }


}
