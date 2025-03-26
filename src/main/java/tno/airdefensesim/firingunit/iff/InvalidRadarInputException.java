

package tno.airdefensesim.firingunit.iff;

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
