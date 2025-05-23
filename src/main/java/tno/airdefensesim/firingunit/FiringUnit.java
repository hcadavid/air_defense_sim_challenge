
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
 * Representation of the Firing Unit for the Patriot air defense system.
 * @author hcadavid
 */
public class FiringUnit implements Subscriber<RadarPacket>{


    private Flow.Subscription subscription;

    private static final Logger logger = LoggerFactory.getLogger(FiringUnit.class);    

    private SimulationStepsViewer simEventsViewer = null;

    /**
     * Requires a concrete viewer to show the simulation steps
     */
    public FiringUnit(SimulationStepsViewer viewer){
        this.simEventsViewer = viewer;
    }

    /**
     * Handler of the RadarPackets recieved from the Radar the unit is suscribed to.
     */
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
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        logger.info("Subscription to the radar events terminated.");    
        simEventsViewer.handleSimulationEnd();    
    }

    /**
     * 
     */
    private void handleIncomingPacket(RadarPacket packet) throws InvalidRadarInputException{

        if (IFFModule.checkStatus(packet) == InboundThreatStatus.FOE){
            simEventsViewer.handleIncomingRadarPacker(packet, true);

            Random r=new Random(System.currentTimeMillis());
            int rand = r.nextInt(10);
            if (rand <= SimulationSettings.pkRatio*10){
                simEventsViewer.handleSuccessfullEngagement(packet);
            }
            else{
                simEventsViewer.handleUnsuccessfullEngagement(packet);
            }

        }
        else{
            simEventsViewer.handleIncomingRadarPacker(packet, false);            
        }

    }


}


