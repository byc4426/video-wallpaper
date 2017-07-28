package com.dingmouren.sample;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dingmouren.videowallpaper.SharePreferenceUtil;
import com.dingmouren.videowallpaper.VideoWallpaper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private File mFile1;
    private File mFile2;
    private static final String IS_VIDEO1 = "is_video1";
    private VideoWallpaper mVideoWallpaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mVideoWallpaper = new VideoWallpaper();
        initFile();
    }

    private void initFile() {
        AssetManager asset = getAssets();
        mFile1 = new File(Environment.getExternalStorageDirectory() + "/video1.mp4");
        if (!mFile1.exists()) {
            try {
                mFile1.createNewFile();
                InputStream is = asset.open("video1.mp4");
                writeMp4ToNative(mFile1, is);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        mFile2 = new File(Environment.getExternalStorageDirectory() + "/video2.mp4");
        if (!mFile2.exists()) {
            try {
                mFile2.createNewFile();
                InputStream is = asset.open("video2.mp4");
                writeMp4ToNative(mFile2, is);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeMp4ToNative(File file, InputStream is) {

        try {
            FileOutputStream os = new FileOutputStream(file);
            int len = -1;
            byte[] buffer = new byte[1024];
            while ((len = is.read(buffer)) != -1) {
                os.write(buffer, 0, buffer.length);
            }
            os.flush();
            os.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setWallpaper(View view) {
//        Intent intent = new Intent();
// /* 开启Pictures画面Type设定为image */
//        intent.setType("video/*");
// /* 使用Intent.ACTION_GET_CONTENT这个Action */
//        intent.setAction(Intent.ACTION_GET_CONTENT);
// /* 取得相片后返回本画面 */
//        startActivityForResult(intent, 1);
        if (SPUtil.get(this,IS_VIDEO1,true)){
            SPUtil.put(this,IS_VIDEO1,false);
            mVideoWallpaper.setToWallPaper(this,mFile1.getAbsolutePath());
        }else {
            SPUtil.put(this,IS_VIDEO1,true);
            mVideoWallpaper.setToWallPaper(this,mFile2.getAbsolutePath());
        }
    }

    public void setSilence(View view) {
        VideoWallpaper.setVoiceSilence(this);
    }

    public void cancelSilence(View view) {
        VideoWallpaper.setVoiceNormal(this);
    }

    public void toBack(View view) {
        finish();
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == Activity.RESULT_OK) {//是否选择，没选择就不会继续
//            Uri uri = data.getData();//得到uri，后面就是将uri转化成path的过程。
//            String[] proj = {MediaStore.Images.Media.DATA};
//            Cursor actualimagecursor = managedQuery(uri, proj, null, null, null);
//            int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//            actualimagecursor.moveToFirst();
//            String img_path = actualimagecursor.getString(actual_image_column_index);
//            mVideoWallpaper.setToWallPaper(this, img_path);
//        }
//    }


}
