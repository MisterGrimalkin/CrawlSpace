package net.amarantha.crawlspace.scene;

public abstract class Event {

    private boolean loop;

    private boolean triggered = false;

    public abstract void onTrigger();

    protected abstract void onDispose();

    public final void trigger() {
        triggered = true;
        onTrigger();
    }

    public final void reset() {
        triggered = false;
        onDispose();
    }

    public final boolean isLoop() {
        return loop;
    }

    public final boolean hasBeenTriggered() {
        return triggered;
    }

}
