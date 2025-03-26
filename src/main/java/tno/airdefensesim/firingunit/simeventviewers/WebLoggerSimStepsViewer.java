
package tno.airdefensesim.firingunit.simeventviewers;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.LinkedList;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import tno.airdefensesim.firingunit.FiringUnit;
import tno.airdefensesim.firingunit.SimulationStepsViewer;
import tno.airdefensesim.radar.RadarPacket;

/**
 * A simple local web-based viewer to demonstrate how alternative visualization mechanisms can easily be
 * integrated into the simulation. Only for reference. An actual web-based viewer should not work through
 * status polling as this one.
 * 
 */
public class WebLoggerSimStepsViewer implements SimulationStepsViewer{

    private static final Logger logger = LoggerFactory.getLogger(FiringUnit.class); 

    private LinkedList<String> simLogs = new LinkedList<>();   

    public WebLoggerSimStepsViewer(){
        HttpServer server=null;

        try {
            server = HttpServer.create(new InetSocketAddress(8000), 0);
            HttpRefreshHandler handler = new HttpRefreshHandler(simLogs);

            server.createContext("/sim", handler);
            server.setExecutor(null); 
            server.start();        

            System.out.println("Open a web-browser at http://localhost:8000/sim to see the simulation. Press [ENTER] to continue");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();              
            scanner.close();
            
        } catch (IOException ex) {
            logger.error("Unable to use the Web event viewer: error while opening port 8000 at localhost. Shutting down the simulation.");
            System.exit(0);
        }

        
    }

    static class HttpRefreshHandler implements HttpHandler {

        private LinkedList<String> logs = null;

        public HttpRefreshHandler(LinkedList<String> updatedLogs){
            logs=updatedLogs;
        }

        @Override
        public void handle(HttpExchange t) throws IOException {


                StringBuilder logEntries = new StringBuilder();
                for (String log : logs) {
                    logEntries.append(log).append("<br>\n");
                }

                String response = "<!DOCTYPE html>\n"
                        + "<html>\n"
                        + "<head>\n"
                        + "    <style>\n"
                        + "        body { font-family: monospace; font-size: 14px; }\n"
                        + "    </style>\n"
                        + "</head>\n"                        
                        + "<body> \n"
                        + "<h1>Patriot air defense system simulation.</h1> \n"
                        + "    <script>\n"
                        + "        setInterval(() => {\n"
                        + "            location.reload();\n"
                        + "        }, 100);\n"
                        + "    </script>\n"
                        + "    <div>\n"
                        + logEntries.toString() 
                        + "    </div>\n"
                        + "</body>\n"
                        + "</html>";


            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }


    @Override
    public void handleIncomingRadarPacker(RadarPacket packet, boolean threat) {
        if (threat){
            simLogs.add(String.format("[timestamp:%d] Radar packet received, <b>THREAT DETECTED</b>,: [%s]",packet.getTime(),packet.getContent()));
        }
        else{
            simLogs.add(String.format("[timestamp:%d] Radar packet received, NO threat detected : [%s]",packet.getTime(),packet.getContent()));
        }
        
    }

    @Override
    public void handleSuccessfullEngagement(RadarPacket packet) {
        simLogs.add(String.format("[timestamp:%d] SUCCESSFUL engagement with hostile detected with packet {%s}",packet.getTime(),packet.getContent()));        
    }

    @Override
    public void handleUnsuccessfullEngagement(RadarPacket packet) {
        simLogs.add(String.format("[timestamp:%d] UNSUCCESSFUL engagement with hostile detected with packet {%s}",packet.getTime(),packet.getContent()));                            
    }

    @Override
    public void handleSimulationEnd() {
        simLogs.add("Simulation finished");                            
        System.out.println("Simulation finished. Press Ctrl+C to stop the web server.");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();              
        scanner.close();

        System.exit(0);
    }


}


