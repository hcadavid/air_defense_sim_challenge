# Coding assessment

#### By: Héctor Cadavid Rengifo


## System Design assumptions


There are multiple potential interpretations for this exercise depending on how the three elements, Radar, IFF, and Firing Unit work together to fulfill the system goal. For this particular implementation, the following assumptions were made:

- The Radar and Firing unit are systems that operate independently. Given this, an underlying messaging middleware that allows the Firing unit to get notified of new 'packets' emited by the radar is assumed.
- The IFF is a module embedded and controlled by the Firing unit to check whether a data 'packet' received by the radar is a threat, to act accordingly.

## Design considerations

- The simulation parameters (pk/probability of kill ratio, and time_step length) can be define when running the simulation. If these are not set, the values given on the excersie description are used.

- Given the assumptions described above, the Radar (`tno.airdefensesim.radar.RadarFeedSim`) and the Firing Unit (`tno.airdefensesim.firingunit.FiringUnit`) work as a Publisher and Subscriber of `RadarPacket` events, respectively. This way, in the simulation logic, the Radar and Firing Unit operate as two elements with operational independence, which communicate indirectly with each other in an event-based fashion. Although the exercise implies the use of synchronized global time-steps, with actions that are performed instantaneously (e.g., firing at a target), this design enables the inclusion of additional simulation elements and parameters such as:

  - Delta values of the time required by operations (or the steps required by them) such as 'fire'.
  - Whether such operations are blocking ones (e.g., no further data can be processed during a firing operation).
  - Strategies for handling the difference between data transmissions (radar packets) and data processing (e.g., data buffering) speed -considering the delta values-, and which packets to prioritize depeding on the overall system strategy.

- It is expected that the simulation, over time, may require more advanced visualization approaches, e.g., when the radar packets and the firing actions can be graphically represented on a 2D or 3D space (or even have multiple visualizations). Given this, the design decouples the execution of the simulation from its representation. To this end, the FiringUnit, depends on an abstraction of such visualization mechanism, following the dependency inversion principle: the  `SimulationStepsViewer` interface. To illustrate how different UI can be used on the simulation, two implementations of this interface are provided: one for showing the simulation steps through Logs on STDOUT, and another that enables the visualization of such steps through a web browser (`http://localhost:8000/sim`).


## Future

Furthermore, having these two simulation elements decoupled could ease the integration of other simulated defense platforms.


## Requirements

- Java >= 9 
- Maven >= 3.9


## Running the pre-built jar


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
