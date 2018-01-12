package com.example.wangbin.binsdemo.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.wangbin.binsdemo.Adapter.GridAdapter;
import com.example.wangbin.binsdemo.Entity.PicUrl;
import com.example.wangbin.binsdemo.Model.ShareCallBack;
import com.example.wangbin.binsdemo.Model.ShareModel;
import com.example.wangbin.binsdemo.R;
import com.example.wangbin.binsdemo.Utils.Image.GlideLoader;
import com.example.wangbin.binsdemo.Utils.TimelineTask;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.yancy.imageselector.ImageConfig;
import com.yancy.imageselector.ImageSelector;
import com.yancy.imageselector.ImageSelectorActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;

/**
 * Created by momo on 2017/12/18.
 */

public class ShareActivity extends AppCompatActivity implements ShareCallBack {
    EditText mEditText;
    GridView mGridView;
    File mFile;
    List<File> mFiles = new ArrayList<>();
    private ArrayList<String> paths = new ArrayList<>();
    List<PicUrl> mList =new ArrayList<>();
    GridAdapter mAdapter;
    Map<String, String> mMap = new HashMap<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        initView();
    }

    private void initView() {
        mEditText = (EditText) findViewById(R.id.edit_share);
        mGridView = ((GridView) findViewById(R.id.grid_pick));
        int width = (int) getResources().getDisplayMetrics().widthPixels;
         LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width,width);
        mGridView.setLayoutParams(layoutParams);
        mGridView.setNumColumns(3);
        mAdapter = new GridAdapter(ShareActivity.this,mList,width/3,true);
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==parent.getCount()-1){
                    picImgFromAlbum();
                }
            }
        });

    }

    private void picImgFromAlbum() {
        ImageConfig imageConfig = new ImageConfig.Builder(new GlideLoader())
                .mutiSelect()
                .mutiSelectMaxSize(9)
                .pathList(paths)
                .build();
        ImageSelector.open(ShareActivity.this,imageConfig);

    }

    @Override
    public void isPostSuccessful(Boolean bool) {
        if (bool)
            finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(ShareActivity.this, "取消", Toast.LENGTH_SHORT);
            return;
        }

        if (resultCode == RESULT_OK&&data!=null){
            paths.clear();
            paths.addAll(data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT));
        }

        mList.clear();
        for (int i =0;i<paths.size();i++){
            PicUrl picUrl = new PicUrl();
            picUrl.setThumbnailPic(paths.get(i));
            mList.add(picUrl);
        }
        mAdapter.notifyDataSetChanged();
        mFile = new File(paths.get(0));
        for (int i=0;i<paths.size();i++) {
            File file = new File(paths.get(i));
            mFiles.add(file);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_postimg,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.isCheckable()){
            item.setChecked(true);
        }
        switch (item.getItemId()){
            case R.id.post_img:
                initData();
                new TimelineTask(ShareActivity.this,"share",mFiles).execute(mMap);
                setResult(RESULT_OK);
            default:
                break;
        }
        return true;
    }

    private void initData() {
        mMap.put("access_token", AccessTokenKeeper.readAccessToken(ShareActivity.this).getToken());
        mMap.put("status", mEditText.getText().toString() + "        http://www.baidu.com");
    }
}
