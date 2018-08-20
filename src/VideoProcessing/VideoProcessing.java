package VideoProcessing;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * TODO：处理视频.（1.将视频提取成帧图片）
 *
 * @author dayao
 */
public class VideoProcessing {

    //视频文件路径
    private static String videoPath = "F:\\test";
    /*
        测试.....
     */
    public static void main(String[] args) {
       File file = new File(videoPath);
       if (file.exists()){
           File[] files = file.listFiles();
           if (null == files || files.length == 0){
               System.out.println("文件夹是空的");
               return;
           }else {
               for (File file2 : files){
                   if (file2.isDirectory()){
                       System.out.println("文件夹名:" + file2.getAbsolutePath());
                   }else {
                       String fName = file2.getAbsolutePath().toString();
                       String dirName = fName.substring(0, fName.length()-4);
                       // 获取文件名
                       File tempFile = new File(fName.trim());
                       String fileName = tempFile.getName();
                       System.out.println("将要截取的文件名为:" + fileName);
                       grabberVideoFramer(fileName, dirName);
                   }

               }
           }
       }
    }

    /**
     * TODO 将视频文件帧处理并以“jpg”格式进行存储。
     * 依赖FrameToBufferedImage方法：将frame转换为bufferedImage对象
     *
     * @param videoFileName
     */
    public static void grabberVideoFramer(String videoFileName, String CreataDirName) {
        //以视屏名字创建文件夹
        File DirName = new File(CreataDirName);
        DirName.mkdir();
        //Frame对象
        Frame frame = null;
        //标识
        int flag = 0;
        /*
            获取视频文件
         */
        FFmpegFrameGrabber fFmpegFrameGrabber = new FFmpegFrameGrabber(videoPath + "/" + videoFileName);

        try {
            fFmpegFrameGrabber.start();
            /*
            .getFrameRate()方法：获取视频文件信息,总帧数
             */
            int ftp = fFmpegFrameGrabber.getLengthInFrames();

            BufferedImage bImage = null;
            System.out.println("开始运行视频提取帧，耗时较长");

            while (flag <= ftp) {
                //文件绝对路径+名字
                String fileName = DirName + "/" + String.valueOf(flag) + ".jpg";
                //文件储存对象
                File outPut = new File(fileName);
                //获取帧
                frame = fFmpegFrameGrabber.grabImage();
                if (frame != null) {
                    ImageIO.write(FrameToBufferedImage(frame), "jpg", outPut);
                }
                flag++;
            }
            System.out.println("============运行结束============");
            fFmpegFrameGrabber.stop();
        } catch (IOException E) {
            System.out.println(E.toString());
        }
    }
    public static BufferedImage FrameToBufferedImage(Frame frame) {
        //创建BufferedImage对象
        Java2DFrameConverter converter = new Java2DFrameConverter();
        BufferedImage bufferedImage = converter.getBufferedImage(frame);
        return bufferedImage;
    }

    public static String getVideoPath() {
        return videoPath;
    }
    public static void setVideoPath(String videoPath) {
        VideoProcessing.videoPath = videoPath;
    }
}
