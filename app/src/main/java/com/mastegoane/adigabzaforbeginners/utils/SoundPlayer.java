package com.mastegoane.adigabzaforbeginners.utils;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.util.Log;
import java.io.FileDescriptor;

// This is a singleton class
public class SoundPlayer {
    private SoundPlayer(AssetManager assetManager) {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayerBackground = new MediaPlayer();
        mAssetManager = assetManager;
    }

    public static synchronized void initInstance(AssetManager assetManager) {
        if (mInstance == null) {
            mInstance = new SoundPlayer(assetManager);
        } else {
            // bug
            Log.e(TAG, "-B- BUG: initInstance(): Singleton has already been initialized!!");
        }
    }

    public static synchronized void deleteInstance() {
        if (mInstance != null) {
            if (mInstance.mMediaPlayer != null) {
                mInstance.mMediaPlayer.release();
            }
            if (mInstance.mMediaPlayerBackground != null) {
                mInstance.mMediaPlayerBackground.release();
            }
            mInstance.mMediaPlayer = null;
            mInstance.mMediaPlayerBackground = null;
            mInstance.mAssetManager = null;
            mInstance = null;
        }
    }

    public static SoundPlayer getInstance() {
        if (mInstance == null) {
            // bug
            Log.e(TAG, "-B- BUG: getInstance(): Singleton is not initialized!!");
        }
        return mInstance;
    }

    public void toggleBackgroundMusic() {
        if (mInstance == null) {
            // bug
            Log.e(TAG, "-B- BUG: toggleBackgroundMusic(): Singleton is not initialized!!");
            return;
        }
        if (!mBackgroundMusicInitialized) {
            mBackgroundMusicInitialized = true;
            try {
                final String fullPath = "audio/music/music1.mp3";
                final AssetFileDescriptor assetFileDescriptor = mAssetManager.openFd(fullPath);
                final FileDescriptor fileDescriptor = assetFileDescriptor.getFileDescriptor();
                mMediaPlayerBackground.setDataSource(fileDescriptor, assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
                mMediaPlayerBackground.prepare(); // ==> apparently not needed since "create()" also calls it
                mMediaPlayerBackground.setVolume(0.075f, 0.075f);
                mMediaPlayerBackground.setLooping(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (mMediaPlayerBackground.isPlaying()) {
            mMediaPlayerBackground.pause();
        } else {
            mMediaPlayerBackground.start();
        }
    }

    public void playSound(String folder, String soundFile) {
        Log.d(TAG, "playSound() - folder: " + folder + ", soundFile: " + soundFile);
        if (mInstance == null) {
            // bug
            Log.e(TAG, "-B- BUG: playSound(): Singleton is not initialized!!");
            return;
        }
        if (soundFile == null || soundFile.isEmpty()) {
            // bug/error
            return;
        }
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
        }
        mMediaPlayer.reset();
        try {
            final String fullPath = "audio/" + folder + "/" + soundFile + ".mp3";
            final AssetFileDescriptor assetFileDescriptor = mAssetManager.openFd(fullPath);
            final FileDescriptor fileDescriptor = assetFileDescriptor.getFileDescriptor();
            mMediaPlayer.setDataSource(fileDescriptor, assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
            mMediaPlayer.prepare(); // ==> apparently not needed since "create()" also calls it
            mMediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static SoundPlayer mInstance = null;
    private MediaPlayer mMediaPlayer;
    private MediaPlayer mMediaPlayerBackground;
    private boolean mBackgroundMusicInitialized = false;
    private AssetManager mAssetManager;
    private static final String TAG = SoundPlayer.class.getSimpleName();
}
