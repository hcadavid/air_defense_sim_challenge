/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package tno.airdefensesim.firingunit;

import java.util.Random;
import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscriber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tno.airdefensesim.SimulationSettings;
import tno.airdefensesim.firingunit.iff.IFFModule;
import tno.airdefensesim.firingunit.iff.InboundThreatStatus;
import tno.airdefensesim.firingunit.iff.InvalidRadarInputException;
import tno.airdefensesim.radar.RadarPacket;


/**
 *
 * @author hcadavid
 */
public class FiringUnit implements Subscriber<RadarPacket>{

    int count = 0;

    private Flow.Subscription subscription;

    private static Logger logger = LoggerFactory.getLogger(FiringUnit.class);    

    private EngagementOutcomeHandler engagementHandler = new EngagementOutcomeHandler(){
        @Override
        public void handleSuccessfullEngagement(RadarPacket packet) {
            System.out.println(String.format("[timestamp:%d] Successful engagement with radar packet {%s}",packet.getTime(),packet.getContent()));
        }

        @Override
        public void handleUnsuccessfullEngagement(RadarPacket packet) {
            System.out.println(String.format("[timestamp:%d] Unsuccessful engagement with radar packet {%s}",packet.getTime(),packet.getContent()));            
        }

    };

    @Override
    public void onNext(RadarPacket item) {
        try {
            logger.debug(String.format("New RadarPacket received from the Radar."));
            handleIncomingPacket(item);
            subscription.request(1);
            logger.debug(String.format("Input processed, waiting for the next packet."));
        } catch (InvalidRadarInputException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        logger.info("Firing unit subscribed to the radar events");
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        logger.error("Subscription to the radar terminated due to an error "+throwable.getMessage());
    }

    @Override
    public void onComplete() {
        logger.info("Subscription to the radar events terminated.");        
    }

    /**
     * 
     */
    private void handleIncomingPacket(RadarPacket packet) throws InvalidRadarInputException{

        if (IFFModule.checkStatus(packet) == InboundThreatStatus.FOE){
            logger.debug(String.format("Radar packet received with timestamp %d with threat detected : [%s]",packet.getTime(),packet.getContent()));
            Random r=new Random(System.currentTimeMillis());
            int rand = r.nextInt(10);
            if (rand <= SimulationSettings.pkRatio*10){
                engagementHandler.handleSuccessfullEngagement(packet);
            }
            else{
                engagementHandler.handleUnsuccessfullEngagement(packet);
            }

        }
        else{
            logger.info(String.format("Radar packet received with timestamp %d but no threat detected : [%s]",packet.getTime(),packet.getContent()));
        }

    }


}
