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
            Post post = Http.post("http://" + ip + "?SetStorage" + panel + "=S" + s + "P" + p, "");
            if (post.responseCode() != 200) {
                throw new Exception("Madrix command failed.");
            }
        } catch ( Exception e ) {
            System.out.println(e.getMessage());
        }


    }

    public MadrixEvent event(int s, int p) { return new MadrixEvent(this, s, p);}

    public MadrixEvent event(String side, int s, int p) {
        return new MadrixEvent(this, side, s, p);
    }

    public MadrixEvent bulkhead() { return new MadrixEvent(this, 1, 13);}
    public MadrixEvent spot2() { return new MadrixEvent(this, 1, 10);}
    public MadrixEvent spot23() { return new MadrixEvent(this, 1, 45);}
    public MadrixEvent spot234() { return new MadrixEvent(this, 1, 14);}
    public MadrixEvent spot345() { return new MadrixEvent(this, 1, 15);}
    public MadrixEvent spot456() { return new MadrixEvent(this, 1, 16);}
    public MadrixEvent spot567() { return new MadrixEvent(this, 1, 29);}
    public MadrixEvent spot678() { return new MadrixEvent(this, 1, 30);}
    public MadrixEvent spot789() { return new MadrixEvent(this, 1, 31);}
    public MadrixEvent spot89() { return new MadrixEvent(this, 1, 32);}
    public MadrixEvent spot9() { return new MadrixEvent(this, 1, 41);}

    public MadrixEvent fullLineWhiteStrobe() {
        return new MadrixEvent(this, "Left", 1, 17);
    }

    public MadrixEvent fallingWhite() {
        return new MadrixEvent(this, "Left", 1, 19);
    }

    public MadrixEvent fallingRedSlow() {
        return new MadrixEvent(this, "Left", 1, 18);
    }

    public MadrixEvent fallingRedFast() {
        return new MadrixEvent(this, "Left", 1, 20);
    }

    public MadrixEvent watery() {
        return new MadrixEvent(this, 1, 49);
    }

    public MadrixEvent fullOn() {
        return new MadrixEvent(this, "Right", 2, 1);
    }

    public MadrixEvent darkness() {
        return new MadrixEvent(this, "Left", 1, 1);
    }

    public MadrixEvent wavesLow() {
        return new MadrixEvent(this, "Left", 1, 2);
    }

    public MadrixEvent wavesHigh() {
        return new MadrixEvent(this, "Right", 2, 2);
    }


}
