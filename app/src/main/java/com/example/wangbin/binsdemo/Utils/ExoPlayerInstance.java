package com.example.wangbin.binsdemo.Utils;

import android.content.Context;
import android.net.Uri;
import android.view.TextureView;

import com.github.lisicnu.log4android.LogManager;
import com.github.lisicnu.log4android.Logger;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

/**
 * Created by momo on 2017/12/29.
 */

public class ExoPlayerInstance {
    private static volatile ExoPlayerInstance mInstance;
    private SimpleExoPlayer mPlayer = null;

    ExoPlayerInstance() {
    }

    public static ExoPlayerInstance getInstance() {
        if (mInstance == null) {
            synchronized (ExoPlayerInstance.class) {
                if (mInstance == null)
                    mInstance = new ExoPlayerInstance();
            }
        }
        return mInstance;
    }

    public SimpleExoPlayer getPlayer(Context mContext) {
        if (mPlayer == null) {
            synchronized (SimpleExoPlayer.class) {
                if (mPlayer == null) {
                    BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
                    TrackSelection.Factory factory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
                    TrackSelector trackSelector = new DefaultTrackSelector(factory);
                    LoadControl loadControl = new DefaultLoadControl();
                    mPlayer = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector, loadControl);
                }
            }
        }
        return mPlayer;
    }

    public void releasePlayer() {
        try {

            if (mPlayer != null) {
                mPlayer.release();
                mPlayer = null;
            }
        } catch (Exception e) {
            LogManager.d("exoplayer", e.getMessage());
        }
    }

    public void setmExoPlayer(Uri uri, TextureView textureView, Boolean isPlay, Context mContext) {
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory factory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(factory);
        LoadControl loadControl = new DefaultLoadControl();
        mPlayer = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector, loadControl);
        mPlayer.setVideoTextureView(textureView);
        DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter();
        DataSource.Factory dataFactory = new DefaultDataSourceFactory(mContext, Util.getUserAgent(
                mContext, "userExoPlayer"), defaultBandwidthMeter);
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        MediaSource videoSource = new ExtractorMediaSource(uri, dataFactory, extractorsFactory,
                null, null);
        mPlayer.prepare(videoSource);
        mPlayer.setPlayWhenReady(isPlay);
    }
}
