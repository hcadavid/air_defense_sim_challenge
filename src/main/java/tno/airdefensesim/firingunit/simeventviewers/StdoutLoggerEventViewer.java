/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package tno.airdefensesim.firingunit.simeventhandlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tno.airdefensesim.firingunit.FiringUnit;
import tno.airdefensesim.firingunit.SimulationEventsViewer;
import tno.airdefensesim.radar.RadarPacket;

/**
 *
 * @author hcadavid
 */
public class StdoutLoggerEventViewer implements SimulationEventsViewer{

    private static final Logger logger = LoggerFactory.getLogger(FiringUnit.class);    

    @Override
    public void handleIncomingRadarPacker(RadarPacket packet, boolean threat) {
        if (threat){
            logger.info(String.format("\uD83D\uDEA8 Radar packet received with timestamp %d with threat detected : [%s]",packet.getTime(),packet.getContent()));
        }
        else{
            logger.info(String.format("\u2705 Radar packet received with timestamp %d but no threat detected : [%s]",packet.getTime(),packet.getContent()));
        }
    }

    @Override
    public void handleSuccessfullEngagement(RadarPacket packet) {
        logger.info(String.format("\t \uD83D\uDCA5 Successful engagement [timestamp:%d] with radar packet {%s}",packet.getTime(),packet.getContent()));
    }

    @Override
    public void handleUnsuccessfullEngagement(RadarPacket packet) {
        logger.info(String.format("\t \u26A0 \u2708 Unsuccessful engagement [timestamp:%d] with radar packet {%s}",packet.getTime(),packet.getContent()));                    
    }

    @Override
    public void handleSimulationEnd() {
        System.exit(0);
    }




}



            
            
            



            
