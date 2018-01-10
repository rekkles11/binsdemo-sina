package com.example.wangbin.binsdemo.Utils;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.view.TextureView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.example.wangbin.binsdemo.R;
import com.github.lisicnu.log4android.LogManager;
import com.github.lisicnu.log4android.Logger;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory;
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.NoOpCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import com.google.android.exoplayer2.util.Util;

import java.io.File;
import java.net.URL;

/**
 * Created by momo on 2017/12/29.
 */

public class ExoPlayerInstance {
    private static volatile ExoPlayerInstance mInstance;
    private SimpleExoPlayer mPlayer = null;
    private Context mContext;
    private TextureView mTextureView;
    private ImageView mImageView;
    private Animation mAnimation;
    private  ExoPlayerInstance(Context context) {
        this.mContext = context;
    }

    public static ExoPlayerInstance getInstance(Context context) {
        if (mInstance == null) {
            synchronized (ExoPlayerInstance.class) {
                if (mInstance == null)
                    mInstance = new ExoPlayerInstance(context);
            }
        }
        return mInstance;
    }

    public SimpleExoPlayer getPlayer() {
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
            if (mTextureView!=null)
                mTextureView = null;
            if (mImageView!=null)
                mImageView = null;
            if (mAnimation!=null){
                mAnimation.cancel();
                mAnimation = null;
            }
        } catch (Exception e) {
            LogManager.d("exoplayer", e.getMessage());
        }
    }

    public void setmExoPlayer(Uri uri, final TextureView textureView, Boolean isPlay,
                              final ImageView imageView) {
        mTextureView = textureView;
        mImageView = imageView;
        mPlayer.setVideoTextureView(textureView);

        DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter();

        DataSource.Factory dataFactory = new DefaultDataSourceFactory(mContext, Util.getUserAgent(
                mContext, "userExoPlayer"), defaultBandwidthMeter);

        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        LeastRecentlyUsedCacheEvictor lruVideoCache = new LeastRecentlyUsedCacheEvictor(CacheDataSource.DEFAULT_MAX_CACHE_FILE_SIZE);

        DataSource.Factory cacheDataSourceFactory = new CacheDataSourceFactory(new SimpleCache(mContext.getCacheDir(),
                lruVideoCache), dataFactory, 0);

        MediaSource videoSource = new ExtractorMediaSource(uri, cacheDataSourceFactory, extractorsFactory,
                null, null);

        mPlayer.prepare(videoSource);
        mPlayer.setPlayWhenReady(isPlay);


        textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {


                LogManager.d("exoplayerlistener:::", "onSurfaceTextureAvailable");
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
                LogManager.d("exoplayerlistener:::", "onSurfaceTextureSizeChanged");

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                LogManager.d("exoplayerlistener:::", "onSurfaceTextureDestroyed");
                imageView.setImageResource(R.drawable.video_pause);
                imageView.setVisibility(View.VISIBLE);
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {

            }
        });
        mPlayer.addListener(new ExoPlayer.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {
                LogManager.d("exoplayerlistener:::", "loadingchange");

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (!playWhenReady) {
                    imageView.setImageResource(R.drawable.video_pause);
                    imageView.setVisibility(View.VISIBLE);
                }

                switch (playbackState) {
                    case ExoPlayer.STATE_IDLE:
                        LogManager.d("exoplayerlistener:::", "palyerstatechange+idle");
                        break;
                    case ExoPlayer.STATE_READY:
                        LogManager.d("exoplayerlistener:::", "palyerstatechange+ready");
                        imageView.clearAnimation();
                        imageView.setVisibility(View.GONE);
                        break;
                    case ExoPlayer.STATE_ENDED:
                        LogManager.d("exoplayerlistener:::", "palyerstatechange+ended");
                        imageView.setImageResource(R.drawable.video_pause);
                        imageView.setVisibility(View.VISIBLE);
                        break;
                    case ExoPlayer.STATE_BUFFERING:
                        mAnimation = AnimationUtils.loadAnimation(mContext, R.anim.rotate);
                        imageView.setAnimation(mAnimation);
                        imageView.startAnimation(mAnimation);
                        imageView.setImageResource(R.drawable.video_loading);
                        LogManager.d("exoplayerlistener:::", "palyerstatechange+buffring");
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {
                LogManager.d("exoplayerlistener:::", "error");
            }

            @Override
            public void onPositionDiscontinuity() {

            }
        });

    }
}
