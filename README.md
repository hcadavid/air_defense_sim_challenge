# Coding assessment

#### By: Héctor Cadavid Rengifo


## System Design assumptions


There are multiple potential interpretations for this exercise depending on how the Radar, IFF, and Firing Unit, on the physical system, work together to fulfill its mission. For this particular implementation, the following assumptions were made:

- The Radar and Firing unit are sub-systems that operate independently. Given this, an underlying messaging middleware that allows the Firing unit to get notified of new 'packets' emitted by the radar is assumed.
- The radar functionality is limited to deliver the 'raw' data packets to the firing unit.
- The IFF is a module embedded and controlled by the Firing unit to check whether a data 'packet' received from the radar is a threat, and then act accordingly.

## Design considerations

- Given the assumptions described above, the Radar (`tno.airdefensesim.radar.RadarFeedSim`) and the Firing Unit (`tno.airdefensesim.firingunit.FiringUnit`) work as a Publisher and Subscriber of `RadarPacket` events, respectively. This way, in the simulation logic, the Radar and Firing Unit operate as two operationally- independendent elements, which share data, indirectly, on an event-based fashion. Although the exercise implies the use of a global clock and synchronized actions that are performed instantaneously (e.g., firing at a target takes 0s), this design enables the inclusion (in future releases) of additional simulation elements and parameters such as:

  - Delta values of the time required by each action (e.g., 'fire') or the steps required for it.
  - Whether such operations are blocking ones (e.g., no further data can be processed during a 'fire' operation).
  - Strategies (e.g., data buffering) for handling the difference between the speed of data transmission (radar packets) and data processing  -considering the delta values-, and which packets to prioritize depending on the overall system strategy.

- It is expected that as the simulation software evolves, it may require more advanced visualization approaches, e.g., when the radar packets and the firing actions can be graphically represented on a 2D or 3D space. Given this, the design decouples the execution of the simulation from its representation. To this end, the FiringUnit depends on an abstraction of such a visualization mechanism: the  `SimulationStepsViewer` interface (considering the dependency inversion principle). To illustrate how different UIs can be used on the simulation, two implementations of this interface are provided: one for showing the simulation steps through Logs on STDOUT, and another that enables the visualization of such steps through a web browser (`http://localhost:8000/sim`).

- The simulation parameters (pk/probability of kill ratio, and time_step length) can be defined when running the simulation. If these are not set, the values given on the excersie description are used.


## Requirements

For running the simulation:

 - JRE >= 11    

For building the tool:
 - JDK >= 11 
 - Maven >= 3.9


## Running the tool using a pre-built jar

Download and run [one of the released jars](https://github.com/hcadavid/air_defense_sim_challenge/releases). For example:

```shell

# Download the runnable jar
$ wget https://github.com/hcadavid/air_defense_sim_challenge/releases/download/v0.0.1/target.airdefensesim-0.0.1-jar-with-dependencies.jar

# Show the CLI options
$ java -jar target.airdefensesim-0.0.1-jar-with-dependencies.jar --help

# Run a simulation
$ java -jar target.airdefensesim-0.0.1-jar-with-dependencies.jar path/to/the/data/radar_data.csv

# Run a simulation with a custom time step length and the web viewer
$ java -jar target.airdefensesim-0.0.1-jar-with-dependencies.jar path/to/the/data/radar_data.csv -tstep=1 --webviewer

```


## Build and setup from source code

Running test cases and build
```
mvn clean compile
```

Build and package the tool in a runnable jar (this will creates a runnable jar on the `/target` folder)
```
mvn clean package assembly:single
```

Show execution options
```shell
# java -jar target/airdefensesim-<version>-jar-with-dependencies.jar --help
$ java -jar target/airdefensesim-0.0.1-jar-with-dependencies.jar --help

Usage: <main class> [--webviewer] [-pk=<pk>] [-tstep=<tstep>] <filepath>
      <filepath>       Filepath
      -pk=<pk>         Probabilty of kill (pk) ratio (default 0.8)
      -tstep=<tstep>   Time step (default 1000)
      --webviewer      Use the web viewer insted of the stdout-based one

```

For example, to run a simulation using the standard settings, and the data available on the `sample_radar_feed` folder:

```shell
# java -jar target/airdefensesim-<version>-jar-with-dependencies.jar path/to/file/input_file.csv
$ java -jar target/airdefensesim-0.0.1-jar-with-dependencies.jar sample_radar_feed/radar_data.csv
```

To run a simulation using the web-viewer, the data available on the `sample_radar_feed` folder, and using 1ms time-steps:

```shell
$ java -jar target/airdefensesim-0.0.1-jar-with-dependencies.jar sample_radar_feed/radar_data.csv  -tstep=1 --webviewer
```
