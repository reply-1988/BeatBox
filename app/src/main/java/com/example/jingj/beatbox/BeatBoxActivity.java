package com.example.jingj.beatbox;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.SeekBar;
import android.widget.TextView;

public class BeatBoxActivity extends com.example.jingj.beatbox.SingleFragmentActivity {



    @Override
    protected Fragment createFragment() {
        return BeatBoxFragment.newInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
