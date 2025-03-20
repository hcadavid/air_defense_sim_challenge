/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

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
