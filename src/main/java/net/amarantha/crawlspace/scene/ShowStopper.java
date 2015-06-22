package net.amarantha.crawlspace.scene;

public class ShowStopper extends Event {

    private EventManager events;

    public ShowStopper(EventManager events) {
        this.events = events;
    }

    @Override
    public void onTrigger() {
        events.stopShow();
    }

    @Override
    protected void onDispose() {

    }
}
