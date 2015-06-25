package net.amarantha.crawlspace.light;

import net.amarantha.crawlspace.event.Event;

public class MadrixEvent extends Event {

    private MadrixInterface madrix;
    private Integer fadeValue = null;

    private String panel;
    private int p;
    private int s;

    public MadrixEvent(MadrixInterface madrix, int s, int p) {
        this(madrix, s==1?"Left":"Right",s,p);

    }
    public MadrixEvent(MadrixInterface madrix, String panel, int s, int p) {
        this(madrix, panel, s, p, null);
    }
    public MadrixEvent(MadrixInterface madrix, String panel, int s, int p, Integer fadeValue) {
        this.madrix = madrix;
        this.panel = panel;
        this.s = s;
        this.p = p;
        this.fadeValue = fadeValue;
    }

    @Override
    public void onTrigger() {


        madrix.fireSceneChange(panel, s, p);
//        if ( fadeValue!=null ) {
//            madrix.fireFade(fadeValue);
//        }
    }

    @Override
    protected void onReset() {

    }
}
