package net.amarantha.crawlspace.light;

import org.javalite.http.Get;
import org.javalite.http.Http;

public class EStreamer {

    private static String streamerIp;

    public static void setStreamerIp(String streamerIp) {
        EStreamer.streamerIp = streamerIp;
    }

    private static EStreamer instance;

    public static EStreamer getInstance() {
        if ( instance==null ) {
            instance = new EStreamer(streamerIp);
        }
        return instance;
    }

    private String ip;

    public EStreamer(String ip) {
        this.ip = ip;
    }

    public void sendCommand(Action action, int showId) {
        Get get = Http.get("http://" + ip + "/includes/control_handler.php" +
                "?process=1" +
                "&show_id="+showId +
                "&action=" + action.getNumber() +
                "&ch_val=-1");
        if ( get.responseCode()==200 ) {
            System.out.println("Command Send");
        } else {
            System.out.println("FAILED!");
        }
    }

    public enum Action {

        STOP(0), PLAY(1), PAUSE(2), RECORD(3), STATUS(4), INIT(5), DELETE(6);

        private int number;

        Action(int number) {
            this.number = number;
        }

        public int getNumber() {
            return number;
        }
    }

}
