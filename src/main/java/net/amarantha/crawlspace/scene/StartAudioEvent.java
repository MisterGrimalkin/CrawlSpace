package net.amarantha.crawlspace.scene;

import net.amarantha.crawlspace.sound.AudioFile;

public class StartAudioEvent extends Event {

    private String fileName;
    private AudioFile audioFile;

    public StartAudioEvent(String fileName) {
        this.fileName = fileName;
        audioFile = new AudioFile(fileName);
    }

    @Override
    public void onTrigger() {
        audioFile.play();
    }

    @Override
    protected void onDispose() {
        audioFile.stop();
    }

    public AudioFile getAudioFile() {
        return audioFile;
    }
}
