package bluevisionit.android.resusrunner;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by tzimonjic on 5/4/16.
 */
public class ReportCycle {
    public String startSystemDateTime;
    public String endSystemDateTime;
    public String startTime = "00:00:00";
    public String startCPR = "00:00:00";
    public String endCprTime = "00:00:00";
    public String endTime = "00:00:00";
    public ArrayList<String> cprPauseTimes= new ArrayList<>();
    public int checkPulse = 0;
    public int giveShock = 0;
    public ArrayList<Drug> drugsGiven = new ArrayList<>();

}
