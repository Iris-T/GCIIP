package cn.iris.gciip.util;

import ai.onnxruntime.OnnxTensor;
import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtException;
import ai.onnxruntime.OrtSession;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * 识别工具类
 */
public class DiscernUtil {
    //预测使用的模型
    private static byte[] model;
    //要加载到model中的模型文件名称
    private static final String MODEL_NAME = "ai/model.onnx";
    //小分类标签
    private static String[] labels;
    private static final int NUM_CLASSES = 60;
    //大分类标签
    private static String[] types;
    private static final int NUM_CLASSES_OUTER = 4;
    private static final String[] TYPE_PATHS = {"ai/Recyclable.txt", "ai/Kitchen.txt", "ai/Other.txt", "ai/Hazardous.txt"};
    private static final String[] TYPES_CN = {"可回收垃圾", "厨余垃圾", "其他垃圾", "有害垃圾"};

    //分类信息文件名
    private static final String CLASSES_TXT = "ai/classes.txt";

    private static OrtEnvironment env;
    private static OrtSession session;

    //初始化静态资源
    static {
        try {
            //加载模型
            model = Objects.requireNonNull(DiscernUtil.class.getClassLoader().getResourceAsStream(MODEL_NAME)).readAllBytes(); // loading onnx model from ResourcePath

            //小分类标签加载
            int index = 0;
            labels = new String[NUM_CLASSES];
            String path = Objects.requireNonNull(DiscernUtil.class.getClassLoader().getResource(CLASSES_TXT)).getFile();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            while (bufferedReader.ready()) {
                labels[index++] = bufferedReader.readLine();
            }
            bufferedReader.close();

            //大分类标签加载
            index = 0;
            types = new String[NUM_CLASSES_OUTER];
            for (String type_name : TYPE_PATHS) {
                String type_path = Objects.requireNonNull(DiscernUtil.class.getClassLoader().getResource(type_name)).getFile();
                BufferedReader bufferedReader1 = new BufferedReader(new FileReader(type_path));
                while (bufferedReader1.ready()) {
                    types[index] += bufferedReader1.readLine();
                }
                index++;
            }

            //初始化计算资源
            env = OrtEnvironment.getEnvironment();
            session = env.createSession(model);
        } catch (NullPointerException | IOException | OrtException e) {
            e.printStackTrace();
        }
    }

    //输入图片的处理
    private static BufferedImage scaleImg(BufferedImage image) {
        Image scaledImg = image.getScaledInstance(224, 224, Image.SCALE_FAST);
        BufferedImage img = new BufferedImage(224, 224, BufferedImage.TYPE_INT_RGB);
        img.getGraphics().drawImage(scaledImg, 0, 0, null);
        return img;
    }

    //将图像转换为矩阵
    private static float[][][][] imageToMatrix(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[] pixels = new int[width * height];
        PixelGrabber pg = new PixelGrabber(image, 0, 0, width, height, pixels, 0, width);
        try {
            pg.grabPixels();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        float[][][][] ret = new float[1][pg.getHeight()][pg.getWidth()][3];
        int pixel = 0;
        int row = 0;
        int col = 0;
        while (row * width + col < pixels.length) {
            pixel = row * width + col;
            ret[0][row][col][2] = (pixels[pixel] & 0x000000FF) / 255f; // blue
            ret[0][row][col][1] = (pixels[pixel] >> 8 & 0x000000FF) / 255f; // green
            ret[0][row][col][0] = (pixels[pixel] >> 16 & 0x000000FF) / 255f; // red
            col++;
            if (col == width - 1) {
                col = 0;
                row++;
            }
        }
        return ret;
    }


    //对预测的输出值转换成目标结果
    private static String parsing_result(OrtSession.Result tensorResult) {
        try {
            float[][] outputProbes = (float[][]) tensorResult.get(0).getValue();
            float[] probabilities = outputProbes[0];
            float maxVal = Float.NEGATIVE_INFINITY;
            String result = null;

            //找到probabilities中的最大值的索引
            int max_index = 0;
            int index = 0;
            for (float probability : probabilities) {
                if (probability > maxVal) {
                    maxVal = probability;
                    max_index = index;
                }
                index++;
            }

            //输出结果
            result = labels[max_index];
            index = 0;
            //搜寻大分类
            for (String type : types) {
                if (type.contains(result)) {
                    result = TYPES_CN[index].concat(" ").concat(result);
                    break;
                }
                index++;
            }

            tensorResult.close();
            return result;
        } catch (OrtException e) {
            e.printStackTrace();
        }
        return null;
    }


    //调用该类方法实现数据输入处理和模型预测：直接输出最终的结果值
    public static String predict(BufferedImage image) throws IOException {
        //数据输入处理
        BufferedImage scaled = scaleImg(image);
        float[][][][] inputArray = imageToMatrix(scaled);

        //模型预测
        try {
            OnnxTensor onnxTensor = OnnxTensor.createTensor(OrtEnvironment.getEnvironment(), inputArray);
            OrtSession.Result result = session.run(
                    Map.of("input_20", onnxTensor)
            );
            onnxTensor.close();
            return parsing_result(result);
        } catch (OrtException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void close_all() throws OrtException {
        env.close();
        session.close();
    }

}
