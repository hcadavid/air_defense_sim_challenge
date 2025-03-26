
package tno.airdefensesim.radar;

/**
 *
 * @author hcadavid
 */
public class RadarPacket {

    private final String content;
    private final int time;

    public RadarPacket(String content, int time) {
        this.content = content;
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public int getTime(){
        return time;
    }

}
