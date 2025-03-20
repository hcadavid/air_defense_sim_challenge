package tno.airdefensesim;

import java.io.File;

import tno.airdefensesim.firingunit.FiringUnit;
import tno.airdefensesim.radar.RadarFeedSim;

public class PatriotSimulation {


    public static void main(String[] args) {
        

        RadarFeedSim radar = new RadarFeedSim(new File("/Users/hcadavid/HCADAVID/Applications/TNO-sim-arch/challenge/airdefensesim/sample_radar_feed/radar_data.csv"));

        FiringUnit firingUnit = new FiringUnit();

        radar.linkFiringUnit(firingUnit);
        
        radar.feedData();
        

    }
}
