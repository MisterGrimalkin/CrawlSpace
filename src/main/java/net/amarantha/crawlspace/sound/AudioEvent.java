package net.amarantha.crawlspace.sound;

import net.amarantha.crawlspace.event.Event;

public class AudioEvent extends Event {

    private String fileName;
    private AudioFile audioFile;

    public AudioEvent(String fileName) {
        this.fileName = fileName;
        audioFile = new AudioFile(fileName);
    }

    @Override
    public void onTrigger() {
        audioFile.play();
    }

    @Override
    protected void onReset() {
        audioFile.stop();
    }

    public AudioFile getAudioFile() {
        return audioFile;
    }

    public Event stop() {
        return new StopAudioEvent(this);
    }
}
