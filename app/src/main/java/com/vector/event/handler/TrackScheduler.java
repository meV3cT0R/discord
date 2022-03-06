package com.vector.event.handler;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;

import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;



public class TrackScheduler extends AudioEventAdapter implements AudioLoadResultHandler  {

    private final AudioPlayer player;
    private final BlockingQueue<AudioTrack> queue;

    public TrackScheduler(final AudioPlayer player) {
        this.player = player;
        this.queue = new LinkedBlockingQueue<>();
    }

    public void queue(AudioTrack track){
        
        if(!player.startTrack(track, true)){
            queue.offer(track);
        }
    }

    public void nextTrack() {
        // Start the next track, regardless of if something is already playing or not. In case queue was empty, we are
        // giving null to startTrack, which is a valid argument and will simply stop the player.
        player.startTrack(queue.poll(), false);
      }

    @Override
    public void trackLoaded(final AudioTrack track) {
        // LavaPlayer found an audio source for us to play

        System.out.printf("[trackLoaded] Track Identifier: %s%n[trackLoaded] Source Manager: %s%n[trackLoaded] Info: %s%n",track.getIdentifier(),track.getSourceManager().getSourceName(),track.getInfo());
        queue(track);
    }

    @Override
    public void playlistLoaded(final AudioPlaylist playlist) {
        playlist.getTracks().stream().forEach(track->{
            System.out.println("[playlist] track name:" + track.getIdentifier());
            queue(track);
        });
    }

    @Override
    public void noMatches() {
        // LavaPlayer did not find any audio to extract
    }

    @Override
    public void loadFailed(final FriendlyException exception) {
        // LavaPlayer could not parse an audio source for some reason
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        // Only start the next track if the end reason is suitable for it (FINISHED or LOAD_FAILED)
        if(endReason.mayStartNext){
            nextTrack();
        }
    }

    public String getCurrentTrack() {
        
        return "";
    }
}
