package net.amarantha.crawlspace.light;

import net.amarantha.crawlspace.event.Event;
import org.javalite.http.Http;
import org.javalite.http.Post;

public class MadrixInterface {

    private String ip;

    public MadrixInterface(String ip) {
        this.ip = ip;
    }

    public void fireSceneChange(String panel, int s, int p) {
        try {
            Post post = Http.post("http://" + ip + "?SetStorage" + panel + "=S" + s + "P" + p, "");
            if (post.responseCode() != 200) {
                throw new Exception("Madrix command failed.");
            }
        } catch ( Exception e ) {
            System.out.println(e.getMessage());
        }


    }

    public void fireFade(int value) {
        try {
            Post post = Http.post("http://" + ip + "?SetFadeValue=" + value, "");
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

    public MadrixEvent sparkly(int s) { return new MadrixEvent(this, s, 57); }
    public MadrixEvent bulkhead(int s) { return new MadrixEvent(this, s, 13);}
    public MadrixEvent spot2(int s) { return new MadrixEvent(this, s, 10);}
    public MadrixEvent spot23(int s) { return new MadrixEvent(this, s, 45);}
    public MadrixEvent spot234(int s) { return new MadrixEvent(this, s, 14);}
    public MadrixEvent spot345(int s) { return new MadrixEvent(this, s, 15);}
//    public MadrixEvent spot456() { return new MadrixEvent(this, 1, 16);}
//    public MadrixEvent spot567() { return new MadrixEvent(this, 1, 29);}
//    public MadrixEvent spot678() { return new MadrixEvent(this, 1, 30);}
    public MadrixEvent spot789(int s) { return new MadrixEvent(this, s, 31);}
    public MadrixEvent midspot(int s) { return new MadrixEvent(this, s, 25);}
//    public MadrixEvent spot89() { return new MadrixEvent(this, 1, 32);}
//    public MadrixEvent spot9() { return new MadrixEvent(this, 1, 41);}


    public MadrixEvent fullLineWhiteStrobe(int s) { return new MadrixEvent(this, s, 17); }

    public MadrixEvent fallingWhite(int s) {
        return new MadrixEvent(this, s, 19);
    }

    public MadrixEvent fallingRedSlow(int s) {
        return new MadrixEvent(this, s, 18);
    }

    public MadrixEvent fallingRedFast(int s) {
        return new MadrixEvent(this, s, 20);
    }

    public MadrixEvent watery(int s) {
        return new MadrixEvent(this, s, 49);
    }

    public MadrixEvent fullOn(int s) {
        return new MadrixEvent(this, s, 5);
    }

    public MadrixEvent darkness(int s) {
        return new MadrixEvent(this, s, 1);
    }

    public MadrixEvent wavesLow(int s) {
        return new MadrixEvent(this, s, 2);
    }

    public MadrixEvent wavesHigh(int s) {
        return new MadrixEvent(this, s, 2);
    }

    public Event fade(final int value) {
        return new Event() {
            @Override
            public void onTrigger() {
                MadrixInterface.this.fireFade(value);
            }

            @Override
            protected void onReset() {

            }
        };
    }


}
