
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
