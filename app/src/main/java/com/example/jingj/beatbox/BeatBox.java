package com.example.jingj.beatbox;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BeatBox {
    private static final String TAG = "BeatBox";

    private static final String SOUNDS_FOLDER = "sample_sounds";
    private static final int MAX_SOUNDS = 5;

    private AssetManager mAssets;
    private List<Sound> mSounds = new ArrayList<>();
    private SoundPool mSoundPool;
    public int now_music_id;
    public float speed = 1.0f;

    public BeatBox(Context context) {
        //获取Assets
        mAssets = context.getAssets();

        //此处书上给的构造方法已经不能用了
        mSoundPool = new SoundPool.Builder().setMaxStreams(MAX_SOUNDS)
                .setAudioAttributes(new AudioAttributes
                                .Builder().setUsage(AudioAttributes.USAGE_MEDIA)
                                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                .build())
                .build();
        loadSounds();
    }

    private void loadSounds() {
        String[] soundNames;
        try {
            //list方法可以把文件夹下面的所有文件的名字并入一个列表中
            soundNames = mAssets.list(SOUNDS_FOLDER);
            Log.i(TAG, "Found" + soundNames.length + "sounds");
        } catch (IOException e) {
            Log.e(TAG, "Could not list assets", e);
            return;
        }
        //创建音乐列表
        for (String filename : soundNames) {
            try {
                String assetPath = SOUNDS_FOLDER + "/" + filename;
                Sound sound = new Sound(assetPath);
                load(sound);
                mSounds.add(sound);
            } catch (IOException ioe) {
                Log.e(TAG, "Could not load sound " + filename, ioe);
            }

        }
    }

    //load方法就是为sound添加Id
    private void load(Sound sound) throws IOException {
        AssetFileDescriptor afd = mAssets.openFd(sound.getAssetPath());
        //load方法可以把文件放入SoundPool中待播，其为了方便管理重播文件，会返回一个int型值
        int soundId = mSoundPool.load(afd, 1);
        sound.setSoundId(soundId);
    }

    public void play(Sound sound) {
        Integer soundID = sound.getSoundId();
        if (soundID == null) {
            return;
        }
        mSoundPool.play(soundID, 1.0f, 1.0f, 1, 0, speed);
        now_music_id = soundID;
    }

    public List<Sound> getSounds() {
        return mSounds;
    }

    public void release() {
        mSoundPool.release();
    }

    //设置播放速度
    public void setSpeed(float sspeed) {
        speed = sspeed;
    }
}
