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
import com.example.wangbin.binsdemo.Utils.ImageLoader;
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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        initView();
    }

    private void initView() {
        mEditText = (EditText) findViewById(R.id.edit_share);
//        findViewById(R.id.bt_share).setOnClickListener(this);
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

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.bt_share:
//                new ShareTask().execute(0);
//                setResult(RESULT_OK);
//                break;
//            default:
//                break;
//        }
//    }

    private void picImgFromAlbum() {
        ImageConfig imageConfig = new ImageConfig.Builder(new ImageLoader())
                .mutiSelect()
                .mutiSelectMaxSize(9)
                .pathList(paths)
                .build();
        ImageSelector.open(ShareActivity.this,imageConfig);

    }

    private void postShare() {
        ShareModel shareModel = new ShareModel();
        Map<String, MultipartBody.Part> partMap = new HashMap<>();
        Map<String, String> map = new HashMap<>();
        map.put("access_token", AccessTokenKeeper.readAccessToken(ShareActivity.this).getToken());
        map.put("status", mEditText.getText().toString() + "        http://www.baidu.com");

        if(mFiles == null) {
            shareModel.postShare(map, null,ShareActivity.this);
        }else {
            shareModel.postShare(map,mFiles,ShareActivity.this);
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
        for (int i=0;i<paths.size();i++){
            File file = new File(paths.get(i));
            mFiles.add(file);
        }
//        Uri imageUri = Uri.parse(paths.get(0));
//        mFile =(UriToFile(imageUri));
    }

    //Uri转File
    public File UriToFile(Uri uri) {
        String res = null;
        String[] pojo = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, pojo, null, null, null);
        if (cursor!= null) {
            cursor.moveToFirst();
            int colum_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(colum_index);
            cursor.close();

        }else
            res =uri.getPath();
        return new File(res);
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
                new ShareTask().execute(0);
                setResult(RESULT_OK);
            default:
                break;
        }
        return true;
    }
}
