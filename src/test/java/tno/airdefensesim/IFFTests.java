/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package tno.airdefensesim;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

import tno.airdefensesim.firingunit.iff.IFFModule;
import tno.airdefensesim.firingunit.iff.InboundThreatStatus;
import tno.airdefensesim.firingunit.iff.InvalidRadarInputException;
import tno.airdefensesim.radar.RadarPacket;
 
/**
 *
 * @author hcadavid
 */
public class IFFTests {

    public IFFTests() {
    }

    //The IFF module (Identiﬁcation Friend or Foe), in turn, checks if a
    //hostile entity is detected. This is the case when there are more odd value entries compared to even value entries in
    //the decimal representation of the radar output.
    
    @Test
    public void testUnfriendlyIncome() throws InvalidRadarInputException{
        
        //Seven odds, four even
        RadarPacket unfriendlyRadarInput = new RadarPacket("00000001;00000011;00000101;00000111;00001001;00001011;00000010;00000100;00001000;00010000;00100001",0);        
        assertEquals(IFFModule.checkStatus(unfriendlyRadarInput),InboundThreatStatus.FOE);

        //Six even, five odd
        RadarPacket friendlyRadarInput = new RadarPacket("00000001;00010010;00000101;00000110;00001001;00001011;00000010;00000100;00001000;00010000;00100001",0);        
        assertEquals(IFFModule.checkStatus(friendlyRadarInput),InboundThreatStatus.FRIEND);

        //Six even, five odd
        RadarPacket nonConventionallyFormattedRadarInput = new RadarPacket("0;0;0;0;0;0;0",0);        
        assertEquals(IFFModule.checkStatus(nonConventionallyFormattedRadarInput),InboundThreatStatus.FRIEND);

        try {
            RadarPacket invalidRadarInput = new RadarPacket("00000003;00010010;00000101;00000110;00001001;00001011;00000010;00000100;00001000;00010000;00100001",0);    
            assertEquals(IFFModule.checkStatus(invalidRadarInput), InboundThreatStatus.FRIEND);            
            fail("InvalidRadarInputException expected when computing an invalid input");
        } catch (InvalidRadarInputException e) {

        }

    }


}