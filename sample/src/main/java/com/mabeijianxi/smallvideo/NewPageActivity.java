package com.mabeijianxi.smallvideo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.mabeijianxi.smallvideo.Event.DeleteEvent;

import java.io.File;

import de.greenrobot.event.EventBus;
import mabeijianxi.camera.Listener.MediaRecorderListener;
import mabeijianxi.camera.MediaRecorderActivity;
import mabeijianxi.camera.VCamera;
import mabeijianxi.camera.model.AutoVBRMode;
import mabeijianxi.camera.model.BaseMediaBitrateConfig;
import mabeijianxi.camera.model.MediaRecorderConfig;
import mabeijianxi.camera.util.DeviceUtils;
import mabeijianxi.camera.util.MediaRecorder;

/**
 * Created by Administrator on 2017/4/7.
 */
public class NewPageActivity extends AppCompatActivity implements View.OnClickListener{

    private String videoScreenshot;
    private String videoUri;
    private ImageView imageView;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_page);
        EventBus.getDefault().register(this);
        initSmallVideo(this);
        initView();

    }

    public void onEventMainThread(DeleteEvent deleteEvent){
        videoUri=null;
        videoScreenshot=null;
        Bitmap bitmap = BitmapFactory.decodeFile(videoScreenshot);
        imageView.setImageBitmap(bitmap);
    }

    private void initView() {
        imageView=(ImageView)findViewById(R.id.iv_video_screenshot);
        imageView.setOnClickListener(this);
        Intent intent = getIntent();

    }

    private void initData() {

    }

    public static void initSmallVideo(Context context) {
        // 设置拍摄视频缓存路径
        File dcim = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        if (DeviceUtils.isZte()) {
            if (dcim.exists()) {
                VCamera.setVideoCachePath(dcim + "/mabeijianxi/");
            } else {
                VCamera.setVideoCachePath(dcim.getPath().replace("/sdcard/",
                        "/sdcard-ext/")
                        + "/mabeijianxi/");
            }
        } else {
            VCamera.setVideoCachePath(dcim + "/mabeijianxi/");
        }
        // 开启log输出,ffmpeg输出到logcat
        VCamera.setDebugMode(true);
        // 初始化拍摄SDK，必须
        VCamera.initialize(context);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_video_screenshot:
                if(videoUri!=null){
                    startActivity(new Intent(this, VideoPlayerActivity.class).putExtra(
                            "path", videoUri));
                }else {
                    MediaRecorderConfig config = new MediaRecorderConfig.Buidler()
                            .doH264Compress(new AutoVBRMode()
                                    .setVelocity(BaseMediaBitrateConfig.Velocity.ULTRAFAST)
                            )
                            .setMediaBitrateConfig(new AutoVBRMode()
                                    .setVelocity(BaseMediaBitrateConfig.Velocity.ULTRAFAST)
                            )
                            .smallVideoWidth(480)
                            .smallVideoHeight(360)
                            .recordTimeMax(6 * 1000)
                            .maxFrameRate(20)
                            .minFrameRate(8)
                            .captureThumbnailsTime(1)
                            .recordTimeMin((int) (1.5 * 1000))
                            .build();
//                    MediaRecorderListener mListener=
                    MediaRecorderActivity.goSmallVideoRecorder(this, new MediaRecorderListener() {
                        @Override
                        public void onEncodeComplete(MediaRecorder mediaRecorder) {
                            videoUri =mediaRecorder.getVIDEO_URI();
                            videoScreenshot = mediaRecorder.getVIDEO_SCREENSHOT();
                            if(videoUri!=null && videoScreenshot!=null) {
                                Bitmap bitmap = BitmapFactory.decodeFile(videoScreenshot);
                                imageView.setImageBitmap(bitmap);
                            }
                        }
                    }, config);
                }
                break;
        }
    }
}
