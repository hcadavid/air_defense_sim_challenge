
package tno.airdefensesim.firingunit.iff;

import tno.airdefensesim.radar.RadarPacket;

/**
 *
 * @author hcadavid
 */

public class IFFModule {

    /**
     *  Determines the status of an inbound reading from a radar.
     *  @Assumption: malformed inputs from the radar may be included (see assumption
     *  on the Radar work as an external module), so these
     *  are treated with a third status of 'UNKNOWN' 
     * 
     *  @param radarInput 
     *  @return FRIEND when the input contains more even values than odd ones
     *          FOE otherwise
     * 
     *  
     */
    public static InboundThreatStatus checkStatus(RadarPacket radarInput) throws InvalidRadarInputException{
        int[] input;
        
        input = parseInput(radarInput.getContent());

        int evenVals = 0;
        int oddVals = 0;

        for (int i=0;i<input.length;i++){
            //even
            if (input[i]%2==0){
                evenVals++;
            }
            else{
                oddVals++;
            }
        }
        
        if (oddVals > evenVals){
            return InboundThreatStatus.FOE;
        } else{
            return InboundThreatStatus.FRIEND;
        }
        

    }

    
    /**
     * Expected input format:
     * @Assumption: the input may not be fixed (11)
     * 0001010;0110011;0100110;0010000;0011100;0101101;1111010;1011101;0110100;1101011;0010011
     */
    private static int[] parseInput(String radarInput) throws InvalidRadarInputException{
        String[] entries = radarInput.split(";");
        int[] output = new int[entries.length];

        for (int i = 0; i < entries.length; i++) {
            try {
                output[i] = Integer.parseInt(entries[i],2);
            } catch (NumberFormatException e) {
                throw new InvalidRadarInputException("Unexpected radar input:"+entries[i],e);
            }
        }

        return output;
    }

}
