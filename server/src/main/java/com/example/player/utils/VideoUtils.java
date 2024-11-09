package com.example.player.utils;

import cn.hutool.core.io.FileUtil;
import com.example.player.serviceImpl.VideoUploadServiceImpl;
import com.google.protobuf.ByteString;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SuppressWarnings("resource")
public class VideoUtils {


    public static double getVideoDuration(String path){
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(path);
        double duration;
        try{
            grabber.start();
            duration = grabber.getLengthInTime()/(100*100.0);

        } catch (FrameGrabber.Exception e) {
            throw new RuntimeException(e);
        } finally {
            try{
                grabber.stop();
                grabber.release();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return duration;
    }

    public static List<String> getVideoCover(String saveFile,String coverPath,String md5,double duration){
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(saveFile);
        Java2DFrameConverter converter = new Java2DFrameConverter();

        List<String> resultList = new ArrayList<>();
        Random random = new Random();
        int[] timeList = new int[4];
        timeList[0] = 1;
        resultList.add(md5 + "_" + timeList[0] + ".png");
        for(int i = 1;i < 4; i++){
            timeList[i] =(random.nextInt(timeList[i-1], (int) (duration-1)));
            resultList.add(md5 + "_" + timeList[i] + ".png");
        }

        try{
            grabber.start();
            for(int i:timeList){

                grabber.setVideoTimestamp(i * 100*100L);
                Frame frame = grabber.grabFrame();

                String outputPath = coverPath + md5 +"_" + i + ".png";
                FileUtil.mkdir(coverPath);
                if (frame != null) {
                    BufferedImage bufferedImage = converter.getBufferedImage(frame); // 转换为 BufferedImage
                    File outputfile = new File(outputPath);
                    ImageIO.write(bufferedImage, "png", outputfile); // 保存为 PNG 文件
                }

            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            try{
                grabber.stop();
                grabber.release();
            } catch (FrameGrabber.Exception e) {
                throw new RuntimeException(e);
            }

        }
        return resultList;
    }

}
