package net.amarantha.crawlspace.scene;

import java.util.*;

public class EventManager {

    private Map<Long, List<Event>> events = new HashMap<>();

    public boolean currentTimeBetween(double from, double to) {
        return currentShowTimeMilliseconds >= inMilliseconds(from)
                && currentShowTimeMilliseconds <= inMilliseconds(to);
    }

    public long getCurrentShowTimeMilliseconds() {
        return currentShowTimeMilliseconds;
    }

    public double getCurrentShowTime() { return currentShowTimeMilliseconds/1000.0; }

    public EventManager addEvent(double seconds, Event event) {
        long timeMilliseconds = inMilliseconds(seconds);
        List<Event> eventsAtThisTime = events.get(timeMilliseconds);
        if ( eventsAtThisTime==null ) {
            eventsAtThisTime = new ArrayList<>();
            events.put(timeMilliseconds, eventsAtThisTime);
        }
        eventsAtThisTime.add(event);
        return this;
    }

    public EventManager loop(double from, double to) {
        EventLoop loop = new EventLoop(this, from, to);
        addEvent(to, loop);
        return this;
    }

    public boolean isRunning() {
        return running;
    }

    public void jumpTo(double seconds) {
        long newTime = inMilliseconds(seconds);
        if ( newTime < currentShowTimeMilliseconds ) {
            resetEventsBetween(newTime, currentShowTimeMilliseconds);
        } else {
            invalidateEventsBetween(currentShowTimeMilliseconds, newTime);
        }
        currentShowTimeMilliseconds = inMilliseconds(seconds);
    }

    public void invalidateEventsBetween(long from, long to) {
        for (Map.Entry<Long, List<Event>> entry : events.entrySet()) {
            if (entry.getKey() >= from && entry.getKey() <= to) {
                for (Event event : entry.getValue()) {
                    event.invalidate();
                }
            }
        }
    }

    public void resetEventsBetween(long from, long to) {
        for ( Map.Entry<Long, List<Event>> entry : events.entrySet() ) {
            if ( entry.getKey() >= from && entry.getKey()<=to) {
                entry.getValue().forEach(Event::reset);
            }
        }
    }

    private long inMilliseconds(double seconds) {
        return (long)Math.floor(seconds*1000);
    }

    private long currentShowTimeMilliseconds = 0;

    private Timer timer = null;

    private static final long TIME_RESOLUTION_MILLISECONDS = 100;

    private boolean running = false;

    public void startShow() {
        running = true;
        currentShowTimeMilliseconds = 0;
        if ( timer==null ) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if ( running ) {
                        double timeSeconds = (double) currentShowTimeMilliseconds / 1000.0;
                        if (timeSeconds == (int) timeSeconds) {
                            System.out.println(timeSeconds);
                        }
                        checkAndTriggerEvents();
                        currentShowTimeMilliseconds += TIME_RESOLUTION_MILLISECONDS;
                    }
                }
            }, 0, TIME_RESOLUTION_MILLISECONDS);
        }
    }

    private void checkAndTriggerEvents() {
        for ( Map.Entry<Long, List<Event>> entry : events.entrySet() ) {
            for ( Event event : entry.getValue() ) {
                if (!event.hasBeenTriggered() && entry.getKey() <= currentShowTimeMilliseconds
                        ) {
                        System.out.println("Fire event " + event.id + " (" + event.getClass().getSimpleName() + ") at " + ((double) currentShowTimeMilliseconds / 1000));
                        event.trigger();
                }
            }
        }
    }

    public void stopShow() {
        running = false;
        for ( Map.Entry<Long, List<Event>> entry : events.entrySet() ) {
            entry.getValue().forEach(Event::reset);
        }
    }

}
