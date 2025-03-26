/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package tno.airdefensesim.firingunit.simeventviewers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tno.airdefensesim.firingunit.FiringUnit;
import tno.airdefensesim.firingunit.SimulationStepsViewer;
import tno.airdefensesim.radar.RadarPacket;

/**
 *
 * @author hcadavid
 */
public class StdoutLoggerSimStepsViewer implements SimulationStepsViewer{

    private static final Logger logger = LoggerFactory.getLogger(FiringUnit.class);    

    @Override
    public void handleIncomingRadarPacker(RadarPacket packet, boolean threat) {
        if (threat){
            logger.info(String.format("[timestamp:%d] \uD83D\uDEA8 Radar packet received, threat detected : [%s]",packet.getTime(),packet.getContent()));
            logger.info(String.format("\t [timestamp:%d] \uD83D\uDE80 Missile launched.",packet.getTime()));
        }
        else{
            logger.info(String.format("[timestamp:%d] \u2705 Radar packet received, no threat detected : [%s]",packet.getTime(),packet.getContent()));
        }
    }

    @Override
    public void handleSuccessfullEngagement(RadarPacket packet) {
        logger.info(String.format("\t [timestamp:%d] \uD83D\uDCA5 Successful engagement - radar packet {%s}",packet.getTime(),packet.getContent()));
    }

    @Override
    public void handleUnsuccessfullEngagement(RadarPacket packet) {
        logger.info(String.format("\t [timestamp:%d] \u26A0 \u2708 Unsuccessful engagement - radar packet {%s}",packet.getTime(),packet.getContent()));                    
    }

    @Override
    public void handleSimulationEnd() {
        System.exit(0);
    }




}



            
            
            



            
