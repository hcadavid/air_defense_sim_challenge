package tno.airdefensesim;

import java.io.File;

import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import tno.airdefensesim.firingunit.FiringUnit;
import tno.airdefensesim.firingunit.SimulationEventsViewer;
import tno.airdefensesim.firingunit.simeventhandlers.StdoutLoggerEventViewer;
import tno.airdefensesim.firingunit.simeventviewers.WebLoggerEventViewer;
import tno.airdefensesim.radar.RadarDataFeedException;
import tno.airdefensesim.radar.RadarFeedSim;

public class PatriotSimulation implements Runnable {

    @Parameters(index = "0", description = "Filepath")
    private String filepath;

    @Option(names = "-pk", description = "Probabilty of kill (pk) ratio (default 0.8)", defaultValue="0.8")
    private Double pk;

    @Option(names = "-tstep", description = "Time step (default 1000)", defaultValue="1000")
    private Integer tstep;

    @Option(names = "--webviewer", description = "Use the web viewer insted of the stdout-based one")
    private boolean webviewer;

    public static void main(String[] args) {
        CommandLine.run(new PatriotSimulation(), args);
    }

    @Override
    public void run(){
        
        String radarDataPath = filepath;

        RadarFeedSim radar = new RadarFeedSim(new File(radarDataPath));

        SimulationEventsViewer eventsViewer;

        initializeSimulationSettings();

        if (webviewer){
            eventsViewer = new WebLoggerEventViewer();
        }
        else{
            eventsViewer = new StdoutLoggerEventViewer();
        }


        FiringUnit firingUnit = new FiringUnit(eventsViewer);

        radar.linkFiringUnit(firingUnit);
        
        try {
            radar.feedData();    
        } catch (RadarDataFeedException e) {
            System.err.print("Unable to feed data to the simulation:"+e.getCause());
            //e.printStackTrace();
            System.exit(1);
        }
        
        

    }


    public void initializeSimulationSettings(){
        SimulationSettings.pkRatio = pk;
        SimulationSettings.timeStepInMs = tstep;
    }



}

