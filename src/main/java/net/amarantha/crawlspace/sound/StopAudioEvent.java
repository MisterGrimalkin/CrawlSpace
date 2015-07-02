package net.amarantha.crawlspace.sound;

import net.amarantha.crawlspace.event.Event;

public class StopAudioEvent extends Event {

    private AudioEvent audioEvent;

    public StopAudioEvent(AudioEvent audioEvent) {
        this.audioEvent = audioEvent;
    }

    @Override
    public void onTrigger() {
        audioEvent.getAudioFile().stop();
    }

    @Override
    protected void onReset() { }
}
