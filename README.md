# Coding assessment

#### By: Héctor Cadavid Rengifo


## System Design assumptions


There are multiple potential interpretations for this exercise depending on how the three elements, Radar, IFF, and Firing Unit work together to fulfill the system goal. For this particular implementation, the following assumptions based on the author's (limited) understanding of the subject, were made:

- The Radar and Firing unit are systems that operate independently (hence, concurrently). There is an underlying messaging middleware that allows the Firing unit to get access to the data 'packets' produced by the radar.
- The IFF is a module embedded on the Firing unit to check whether a data 'packet' received by the radar is a threat, to act accordingly.

## Design considerations

Given the above, 

- To simulate the Radar (`RadarFeedSim`) and the Firing Unit (`FiringUnit`) as two elements that operate independently on an event-based fashion, they work as a Publisher and Subscriber of `RadarPacket` events, respectively. Although the exercise imples the use of global time-steps, and this implementation work as such, this design enables exploring aspects such as:
  - Different transmission and processing speeds ('fire' operation that blocks others for a delta-t period of time), and alternative buffering strategies to handle these.

- Considering that a more sophisticated UI may be required in the future, the design decouples the execution of the simulation from its representation. To this end, the FiringUnit is implemented on top of the  `SimulationEventsViewer` interface. For illustration purposes, two implementations of this interface are provided: one for showing the events of the simulation through Logs on STDOUT, and another that enables the access to such events through a web browser (`http://localhost:8000/sim`).
- The simulation parameters (pk/probability of kill ratio, and time_step length) can be define when running the simulation. By default, the values given on the excersie description can be used.

## Requirements

- Java >= 9 
- Maven >= 3.9

## Setup

Running test cases and build
```
mvn clean compile
```

Build and package the tool in a runnable jar (this will creates a runnable jar on the `/target` folder)
```
mvn clean package assembly:single
```

Show execution options
```
$ java -jar target/airdefensesim-0.1-jar-with-dependencies.jar --help

Usage: <main class> [--webviewer] [-pk=<pk>] [-tstep=<tstep>] <filepath>
      <filepath>       Filepath
      -pk=<pk>         Probabilty of kill (pk) ratio (default 0.8)
      -tstep=<tstep>   Time step (default 1000)
      --webviewer      Use the web viewer insted of the stdout-based one

```

For example, to run a simulation using the standard settings, and the data available on the `sample_radar_feed` folder:

```shell
$ java -jar target/airdefensesim-0.1-jar-with-dependencies.jar sample_radar_feed/radar_data.csv
```

To run a simulation using the web-viewer, the data available on the `sample_radar_feed` folder, and using 1ms time-steps:

```shell
$ java -jar target/airdefensesim-0.1-jar-with-dependencies.jar sample_radar_feed/radar_data.csv  -tstep=1 --webviewer
```
