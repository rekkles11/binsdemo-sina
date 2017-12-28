package com.example.wangbin.binsdemo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wangbin.binsdemo.Activity.ImageActivity;
import com.example.wangbin.binsdemo.Entity.PicUrl;
import com.example.wangbin.binsdemo.Entity.Status;
import com.example.wangbin.binsdemo.R;
import com.example.wangbin.binsdemo.Utils.GlideLoader;
import com.example.wangbin.binsdemo.Utils.MyGridView;
import com.example.wangbin.binsdemo.Utils.PicSize;
import com.github.lisicnu.log4android.LogManager;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by momo on 2017/12/12.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    List<Status> mList;
    Context mContext;
    int width;
    SimpleExoPlayer player;
    List<SimpleExoPlayer> mPlayers = new ArrayList<>();

    public RecyclerAdapter(List<Status> list, Context context,int x){
        this.mList = list;
        this.mContext = context;
        this.width = x;
    }

    public  void setVideo(Boolean isPlay,int index){
        mPlayers.get(index).setPlayWhenReady(isPlay);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.usertimeline_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Status status = mList.get(position);
        if (status.getText().length()<=140)
            holder.text.setText(status.getText().toString());
        else {
            SpannableStringBuilder spannable = new SpannableStringBuilder(
                    status.getText().substring(0,139)+"...全文");
            spannable.setSpan(new ForegroundColorSpan(Color.BLUE),139,144,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.text.setText(spannable);
            holder.text.setMovementMethod(LinkMovementMethod.getInstance());
            spannable.setSpan(new TextClick(),0,4,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        holder.reposts.setText(status.getRepostsCount().toString());
        holder.comments.setText(status.getCommentsCount().toString());
        holder.attltudes.setText(status.getAttitudesCount().toString());
        holder.createdAt.setText(status.getCreatedAt());
        holder.source.setText(status.getSource());
        holder.screenName.setText(status.getUser().getScreenName());
        initPlayer(holder.mTextureView,position);
        new GlideLoader().displayCircleImg(mContext,status.getUser().getAvatarLarge(),
                width/5,width/5,holder.imageView);
        if(status.getPicUrls()!=null) {
            if (status.getPicUrls().size() == 1)
                status.getPicUrls().get(0).setThumbnailPic(status.getOriginalPic());
            setGridView(new PicSize().getMiddlePicUrls(status.getPicUrls()), holder.gridView);
            holder.gridView.setOnItemClickListener(new GridItemClickListener(holder.getLayoutPosition()) );
        }

    }

    private void initPlayer(TextureView textureView,int index) {
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory factory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(factory);

        LoadControl loadControl = new DefaultLoadControl();

        mPlayers.add(ExoPlayerFactory.newSimpleInstance(mContext,trackSelector,loadControl));
        mPlayers.get(index).setVideoTextureView(textureView);
        playVideo(index);

    }
    public void playVideo(int index){
        DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter();
        DataSource.Factory factory = new DefaultDataSourceFactory(mContext,
                Util.getUserAgent(mContext,"userExoPlayer"),defaultBandwidthMeter);
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        Uri playerUri = Uri.parse("https://storage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%20Hangin'%20with%20the%20Google%20Search%20Bar.mp4");
        MediaSource videoSource = new ExtractorMediaSource(playerUri,factory,extractorsFactory,
                null,null);
        mPlayers.get(index).prepare(videoSource);
        mPlayers.get(index).setPlayWhenReady(false);
    }



    public void setGridView(List<PicUrl> picUrls,MyGridView view) {
        LogManager.d("picUrls1:::::",picUrls.size());
        view.setColumn(picUrls.size(),width);
        view.setAdapter(new GridAdapter(mContext,picUrls,view.getOneItemWidth(),false));


    }



    @Override
    public int getItemCount() {
        return mList.size();
    }


    public static class ViewHolder extends  RecyclerView.ViewHolder{
        public TextView text;
        public TextView reposts;
        public TextView comments;
        public TextView attltudes;
        public TextView createdAt;
        public TextView source;
        public MyGridView gridView;
        public TextView screenName;
        public ImageView imageView;
        public TextureView mTextureView;
        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.tv_text);
            reposts = (TextView) itemView.findViewById(R.id.tv_reposts);
            comments = (TextView) itemView.findViewById(R.id.tv_comments);
            attltudes = (TextView) itemView.findViewById(R.id.tv_attltudes);
            createdAt = (TextView) itemView.findViewById(R.id.tv_usertimeline_created_at);
            source = (TextView) itemView.findViewById(R.id.tv_usertimeline_source);
            gridView = (MyGridView) itemView.findViewById(R.id.grid_usertimeline_pic_ids);
            screenName = (TextView) itemView.findViewById(R.id.tv_usertimeline_screen_name);
            imageView = (ImageView) itemView.findViewById(R.id.img_usertimeline_head);
            mTextureView = (TextureView) itemView.findViewById(R.id.view_exoplayer);
        }
    }

    private class TextClick extends ClickableSpan {
        @Override
        public void onClick(View widget) {
            Toast.makeText(mContext,"不给看！",Toast.LENGTH_SHORT);
            LogManager.d("text::","click");
        }
        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(Color.RED);
            ds.setUnderlineText(true);
        }
    }

    private class GridItemClickListener implements AdapterView.OnItemClickListener {


        int mHodlerPostion;
        public GridItemClickListener(int pos){
            this.mHodlerPostion = pos;
        }
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(mContext,ImageActivity.class);

            List<PicUrl> list = new PicSize().getLaragePicUrls(mList.get(mHodlerPostion).getPicUrls());
            String[] strings = new String[list.size()];
            for (int i =0;i<list.size();i++){
                strings[i] = list.get(i).getThumbnailPic();
            }
            intent.putExtra("pics",strings);
            intent.putExtra("index",position);
            LogManager.d("index:::",position);
            mContext.startActivity(intent);
        }
    }


}
