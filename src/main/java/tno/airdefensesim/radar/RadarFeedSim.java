
package tno.airdefensesim.radar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.SubmissionPublisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tno.airdefensesim.SimulationSettings;
import tno.airdefensesim.firingunit.FiringUnit;

/**
 * Representation of a Radar for the Patriot air defense system. It will
 * feed the 'radar-packets' given on a CSV file to any firing unit
 * registered as a subscriber for it.
 */
public class RadarFeedSim{

    private static Logger logger = LoggerFactory.getLogger(RadarFeedSim.class);    
    private final File inputFile;
    private final SubmissionPublisher<RadarPacket> publisher;


    public RadarFeedSim(File inputFile){
        this.inputFile = inputFile;
        publisher = new SubmissionPublisher<>();
    }    

    /**
     * 
     */
    public void linkFiringUnit(FiringUnit firingUnit){
        publisher.subscribe(firingUnit);
    }

    /**
     * 
     */
    public void feedData() throws RadarDataFeedException{
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line;
            int timestamp = 0;
            while ((line = br.readLine()) != null) {
                logger.debug("Reading and submitting");
                RadarPacket packet = new RadarPacket(line,timestamp); 
                publisher.submit(packet);
                timestamp++;
                Thread.sleep(SimulationSettings.timeStepInMs);
            }
            logger.debug("Closing the subscription to the Radar");
            publisher.close();
        } catch (IOException e) {
            throw new RadarDataFeedException("Error while reading the given csv file:"+inputFile,e);
        } catch (InterruptedException ex) {
        }
    }

}