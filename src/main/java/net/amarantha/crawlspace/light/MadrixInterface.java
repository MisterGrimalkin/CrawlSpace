package net.amarantha.crawlspace.light;

import org.javalite.http.Http;
import org.javalite.http.Post;

public class MadrixInterface {

    private String ip;

    public MadrixInterface(String ip) {
        this.ip = ip;
    }

    public void fireCommand(String panel, int s, int p) {
        try {
            Post post = Http.post("http://" + ip + "/RemoteCommands/?SetStorage" + panel + "=S2" + s + "P" + p, "");
            if (post.responseCode() != 200) {
                throw new Exception("Madrix command failed.");
            }
        } catch ( Exception e ) {
            System.out.println(e.getMessage());
        }


    }

}
