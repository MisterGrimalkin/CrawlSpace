package net.amarantha.crawlspace.light;

import net.amarantha.crawlspace.event.Event;

public class MadrixEvent extends Event {

    private MadrixInterface madrix;

    private String panel;
    private int p;
    private int s;

    public MadrixEvent(MadrixInterface madrix, int s, int p) {
        this(madrix, s==1?"Left":"Right",s,p);

    }
    public MadrixEvent(MadrixInterface madrix, String panel, int s, int p) {
        this.madrix = madrix;
        this.panel = panel;
        this.s = s;
        this.p = p;
    }

    @Override
    public void onTrigger() {
        madrix.fireCommand(panel, s, p);
    }

    @Override
    protected void onReset() {

    }
}
