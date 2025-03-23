/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package tno.airdefensesim.firingunit;

import tno.airdefensesim.radar.RadarPacket;

/**
 *
 * @author hcadavid
 */
public interface SimulationEventsViewer {

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
