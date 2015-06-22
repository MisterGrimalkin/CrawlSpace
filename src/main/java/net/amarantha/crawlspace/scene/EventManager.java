package net.amarantha.crawlspace.scene;

import java.util.*;

public class EventManager {

    private Map<Long, Event> events = new HashMap<>();

    public boolean currentTimeBetween(double from, double to) {
        return currentShowTimeMilliseconds >= inMilliseconds(from)
                && currentShowTimeMilliseconds <= inMilliseconds(to);
    }

    public EventManager addEvent(double seconds, Event event) {
        events.put(inMilliseconds(seconds), event);
        System.out.println(events.size() );
        return this;
    }

    public EventLoop loop(double from, double to) {
        EventLoop loop = new EventLoop(this, from, to);
//        addEvent(from, loop.disposeEvent());
        addEvent(to, loop);
        return loop;
    }

    public void jumpTo(double seconds) {
        long newTime = inMilliseconds(seconds);
        if ( newTime < currentShowTimeMilliseconds ) {
            resetEventsBetween(newTime, currentShowTimeMilliseconds);
        }
        currentShowTimeMilliseconds = inMilliseconds(seconds);
    }

    public void resetEventsBetween(long from, long to) {
        for ( Map.Entry<Long, Event> entry : events.entrySet() ) {
            if ( entry.getKey() >= from && entry.getKey()<=to && !(entry.getValue() instanceof EventLoop) ) {
                entry.getValue().reset();
            }
        }
    }

    private long inMilliseconds(double seconds) {
        return (long)Math.floor(seconds*1000);
    }

    private long currentShowTimeMilliseconds = 0;

    private Timer timer = null;

    private static final long TIME_RESOLUTION_MILLISECONDS = 100;

    public void startShow() {
        currentShowTimeMilliseconds = 0;
        if ( timer==null ) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    currentShowTimeMilliseconds += TIME_RESOLUTION_MILLISECONDS;
                    checkAndTriggerEvents();
                    System.out.println((double)currentShowTimeMilliseconds/1000.0);
                }
            }, 0, TIME_RESOLUTION_MILLISECONDS);
        }
    }

    private void checkAndTriggerEvents() {
        for ( Map.Entry<Long, Event> entry : events.entrySet() ) {
            Event event = entry.getValue();
            if ( !event.hasBeenTriggered() && entry.getKey()<currentShowTimeMilliseconds ) {
                System.out.println("Fire event");
                event.trigger();
            }
        }
    }

    public void stopShow() {
        System.exit(0);
    }

}
