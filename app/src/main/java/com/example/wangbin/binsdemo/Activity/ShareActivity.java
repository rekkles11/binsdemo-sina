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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.wangbin.binsdemo.Model.ShareCallBack;
import com.example.wangbin.binsdemo.Model.ShareModel;
import com.example.wangbin.binsdemo.R;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;

/**
 * Created by momo on 2017/12/18.
 */

public class ShareActivity extends AppCompatActivity implements View.OnClickListener,ShareCallBack {
    EditText mEditText;
    ImageView mImageView;
    File mFile;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        initView();
    }

    private void initView() {
        mEditText = (EditText) findViewById(R.id.edit_share);
        findViewById(R.id.bt_share).setOnClickListener(this);
        mImageView = ((ImageView) findViewById(R.id.img_pick));


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_share:
                new ShareTask().execute(0);
                setResult(RESULT_OK);
                break;
            default:
                break;
        }
    }

    private void picImgFromAlbum() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 111);
    }

    private void postShare() {
        ShareModel shareModel = new ShareModel();
        Map<String, MultipartBody.Part> partMap = new HashMap<>();
        Map<String, String> map = new HashMap<>();
        map.put("access_token", AccessTokenKeeper.readAccessToken(ShareActivity.this).getToken());
        map.put("status", mEditText.getText().toString() + "        http://www.baidu.com");

        if(mFile == null) {
            shareModel.postShare(map, null,ShareActivity.this);
        }else {
            shareModel.postShare(map,mFile,ShareActivity.this);
        }
    }

    @Override
    public void isPostSuccessful(Boolean bool) {
        if (bool)
            finish();
    }

    public class ShareTask extends AsyncTask<Integer,Integer,Object>{

        @Override
        protected Object doInBackground(Integer... integers) {
            postShare();
            return null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(ShareActivity.this, "取消", Toast.LENGTH_SHORT);
            return;
        }
        Uri imageUri = data.getData();
        mFile =(UriToFile(imageUri));
        Glide.with(ShareActivity.this)
                .load(UriToFile(imageUri))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(600,600)
                .fitCenter()
                .crossFade()
                .into(mImageView);
    }

    //Uri转File
    public File UriToFile(Uri uri) {
        String res = null;
        String[] pojo = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, pojo, null, null, null);
        if (cursor.moveToFirst()) {
            int colum_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(colum_index);

        }
        cursor.close();
        return new File(res);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_addimg,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.isCheckable()){
            item.setChecked(true);
        }
        switch (item.getItemId()){
            case R.id.add_img:
                picImgFromAlbum();
            default:
                break;
        }
        return true;
    }
}
