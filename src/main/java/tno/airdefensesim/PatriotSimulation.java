package tno.airdefensesim;

import java.io.File;

import tno.airdefensesim.firingunit.FiringUnit;
import tno.airdefensesim.firingunit.SimulationEventsViewer;
import tno.airdefensesim.firingunit.simeventviewers.WebLoggerEventViewer;
import tno.airdefensesim.radar.RadarFeedSim;

public class PatriotSimulation {


    public static void main(String[] args) {
        
        if (args.length < 1) {
            System.err.println("Usage: java PatriotSimulation <path_to_radar_data>");
            System.exit(1);
        }

        String radarDataPath = args[0];

        RadarFeedSim radar = new RadarFeedSim(new File(radarDataPath));

        SimulationEventsViewer eventsViewer = new WebLoggerEventViewer();
        //StdoutLoggerEventViewer simViewer = new StdoutLoggerEventViewer();

        FiringUnit firingUnit = new FiringUnit(eventsViewer);

        radar.linkFiringUnit(firingUnit);
        
        radar.feedData();
        

    }
}
