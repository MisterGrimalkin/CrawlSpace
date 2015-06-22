package net.amarantha.crawlspace.scene;

public class StopAudioEvent extends Event {

    private StartAudioEvent audioEvent;

    public StopAudioEvent(StartAudioEvent audioEvent) {
        this.audioEvent = audioEvent;
    }

    @Override
    public void onTrigger() {
        audioEvent.getAudioFile().stop();
    }

    @Override
    protected void onDispose() { }
}
