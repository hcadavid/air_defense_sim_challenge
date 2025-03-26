
package tno.airdefensesim.firingunit;

import tno.airdefensesim.radar.RadarPacket;

/**
 *
 * @author hcadavid
 */
public interface SimulationStepsViewer {

    /**
     * 
     */
    public void handleIncomingRadarPacker(RadarPacket packet,boolean threat);

    /**
     * 
     */
    public void handleSuccessfullEngagement(RadarPacket packet);

    /**
     * 
     */
    public void handleUnsuccessfullEngagement(RadarPacket packet);

    /**
     * 
     */
    public void handleSimulationEnd();


}
