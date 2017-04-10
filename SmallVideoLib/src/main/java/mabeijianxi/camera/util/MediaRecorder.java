package mabeijianxi.camera.util;

/**
 * Created by Administrator on 2017/4/7.
 */
public class MediaRecorder {
    /**
     * 视屏地址
     */
    public   String VIDEO_URI = "video_uri";
    /**
     * 本次视频保存的文件夹地址
     */
    public   String OUTPUT_DIRECTORY = "output_directory";
    /**
     * 视屏截图地址
     */
    public   String VIDEO_SCREENSHOT = "video_screenshot";
    /**
     * 录制完成后需要跳转的activity
     */
    public   String OVER_ACTIVITY_NAME = "over_activity_name";

    public void  setVideoUri(String video_uri){
        this.VIDEO_URI=video_uri;
    }
    public void setOUTPUTDIRECTORY(String output_directory){
        this.OUTPUT_DIRECTORY=output_directory;
    }
    public void setVIDEOSCREENSHOT(String video_screenshot){
        this.VIDEO_SCREENSHOT=video_screenshot;
    }

    public String getVIDEO_URI(){
        return VIDEO_URI;
    }
    public String getVIDEO_SCREENSHOT(){
        return VIDEO_SCREENSHOT;
    }
}
