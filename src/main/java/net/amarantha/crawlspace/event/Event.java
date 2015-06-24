package net.amarantha.crawlspace.event;

public abstract class Event {

    private static int nextId = 1;

    public final int id;

    private boolean triggered = false;

    protected Event() {
        id = nextId++;
    }

    public abstract void onTrigger();

    protected abstract void onReset();

    public final void trigger() {
        triggered = true;
        onTrigger();
    }

    public final void reset() {
        triggered = false;
        onReset();
    }

    public final void invalidate() {
        triggered = true;
    }

    public final boolean hasBeenTriggered() {
        return triggered;
    }

}
