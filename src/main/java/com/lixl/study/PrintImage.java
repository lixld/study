package com.lixl.study;

import jxl.Sheet;
import jxl.Workbook;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PrintImage {

    //需要先在桌面创建一个文件夹叫 06
    static String number = "13";
    //数据来源
    static String dataSource = "/Users/lixl/Desktop/source/" + number + "期产品.xls";
    //目标路径
    private static String goodUserDestation = "/Users/lixl/Desktop/" + number + "/goodUsers/";
    private static String ordinaryDestation = "/Users/lixl/Desktop/" + number + "/ordinary/";


    //优秀阈值（有优秀证）
    static int youxiuThreadShould = 8;

    //参与作业次数阈值（有毕业证）
    static int canyuzuoyeCountThreshould = 7;

    //从excel第几行开始读 --excel表头中数据
    static int row = 2;
    static int nickNameColumn = 4;//昵称列
    static int canyuzuoyeCountColumn = 5;//优秀次数列
    static int smartColumn = 6;//优秀次数列


    private static Font goodFont = new Font("Menlo", Font.PLAIN, 50); // 添加字体的属性设置
    private static Font ordinaryFont = new Font("微软雅黑", Font.PLAIN, 50); // 添加字体的属性设置
    //    private static Color color = new Color(120, 120, 110);
//    private static Color color = Color.WHITE;
    private static Color color = Color.BLACK;

    //图片模板
    private static String goodImageUrl = "/Users/lixl/Desktop/source/good.jpeg";
    private static String ordinarImageUrl = "/Users/lixl/Desktop/source/ordinary.jpeg";


    //中间数据
    static Map<String, String> goodResult = new HashMap<>();
    static List<String> ordinaryList = new ArrayList<>();


    private Graphics2D g = null;

    static Font enmojiF;
    static Pattern emoji;

    static {
//        try {
//            enmojiF = Font.createFont(Font.TRUETYPE_FONT, new File("/Users/lixl/Downloads/5c491a9a0572d.ttf"));
//        } catch (FontFormatException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
    }

    /**
     * 导入本地图片到缓冲区
     */
    public BufferedImage loadImageLocal(String imgName) {
        try {
            return ImageIO.read(new File(imgName));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 导入网络图片到缓冲区
     */
    public BufferedImage loadImageUrl(String imgName) {
        try {
            URL url = new URL(imgName);
            return ImageIO.read(url);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 生成新图片到本地
     */
    public void writeImageLocal(String targetFil, String newImage, BufferedImage img) {
        boolean b = targetFil.endsWith("/");
        if (b) {
            targetFil = targetFil.substring(0, targetFil.length() - 1);
        }
        //   /Users/lixl/Desktop/06/goodUsers
        int i = targetFil.lastIndexOf("/");
        String substring = targetFil.substring(0, i);//   /Users/lixl/Desktop/06


        checkAndMake(substring);

        checkAndMake(targetFil);


        if (newImage != null && img != null) {
            try {
                File outputfile = new File(newImage);
                ImageIO.write(img, "jpg", outputfile);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void checkAndMake(String targetFil) {
        File file = new File(targetFil);
//如果文件夹不存在则创建
        if (!file.exists() && !file.isDirectory()) {
            System.out.println("//不存在");
            file.mkdir();
        } else {
//            System.out.println("//目录存在");
        }
    }


    /**
     * 修改图片,返回修改后的图片缓冲区（只输出一行文本）
     */
    public BufferedImage modifyImage(Font font, BufferedImage img, Object content, int x, int y, Object content1, int x1, int y1, Object content2, int x2, int y2) {

        try {
            int w = img.getWidth();
            int h = img.getHeight();
            System.out.println("图片的宽：" + w + "  高： " + h);
            g = img.createGraphics();
            g.setBackground(Color.WHITE);
            g.setColor(color);//设置字体颜色
            g.setFont(font);
            if (content != null) {
//                g.translate(w / 2, h / 2);
//                g.rotate(8 * Math.PI / 180);
                String tv = content.toString();
                tv = filterEmoji(tv);
                /*StringBuffer stringBuffer = new StringBuffer();
                if (containsEmoji(tv)) {

                    String name = font.getName();
                    int style = font.getStyle();
                    int size = font.getSize();
                    Font emnoji = enmojiF.deriveFont(style, size);
                    g.setFont(emnoji);
                    int chars = -1;
                    int len = x;

                    for (int i = 0; i < tv.length(); i++) {//Wesley🍏尾巴
                        char codePoint = tv.charAt(i);
                        String s;
                        if (!isEmojiCharacter(codePoint)) {
                            if (chars == i) {
                                continue;
                            }
                            chars = i + 1;
                            char c = tv.charAt(i + 1);
                            s = ascii2native("\\u" + Integer.toHexString((int) codePoint) + "\\u" + Integer.toHexString((int) c));

                        } else {
                            s = String.valueOf(codePoint);

                        }
                        System.out.println("=========" + s);
                        stringBuffer.append(s);

                    }

                }
                tv = stringBuffer.toString();*/
                System.out.println(tv);
//               tv = tv.replaceAll("\\\\", "");
                System.out.println("--------" + tv);
                g.drawString(tv, x, y);
            }
            if (content1 != null) {
                String tv = content1.toString();
                g.drawString(tv, x1, y1);

            }
            if (content2 != null) {
                g.drawString(content2.toString(), x2, y2);
            }
            g.dispose();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return img;
    }


    /**
     * 修改图片,返回修改后的图片缓冲区（只输出一行文本）
     * <p>
     * 时间:2007-10-8
     *
     * @param img
     * @return
     */
    public BufferedImage modifyImageYe(BufferedImage img) {

        try {
            int w = img.getWidth();
            int h = img.getHeight();
            g = img.createGraphics();
            g.setBackground(Color.WHITE);
            g.setColor(Color.blue);//设置字体颜色
//            if (this.font != null)
//                g.setFont(this.font);
            g.drawString("www.hi.baidu.com?xia_mingjian", w - 85, h - 5);
            g.dispose();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return img;
    }

    public BufferedImage modifyImagetogeter(BufferedImage b, BufferedImage d) {

        try {
            int w = b.getWidth();
            int h = b.getHeight();
            g = d.createGraphics();
            g.drawImage(b, 100, 10, w, h, null);
            g.dispose();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return d;
    }


    public static void readExcel(File xlsFile) {
        int innerRow = 0;
        try {
            Workbook workbook = Workbook.getWorkbook(xlsFile);
            Sheet[] sheets = workbook.getSheets();

            if (sheets != null) {
                for (Sheet sheet : sheets) {
                    int rows = sheet.getRows();
                    innerRow = row - 1;
                    for (; innerRow < rows; innerRow++) {
//                        if (StringUtils.isEmpty(sheet.getCell(0, row).getContents())) {
//                            return result;
//                        }

                        Map<String, String> map = new HashMap<>();

//                        String programName = sheet.getCell(0, row).getContents();
                        String nickName = sheet.getCell(nickNameColumn - 1, innerRow).getContents();
//                        String showTimeStart = sheet.getCell(2, row).getContents();
//                        String showTimeEnd = sheet.getCell(3, row).getContents();
                        String youxiucishu = sheet.getCell(smartColumn - 1, innerRow).getContents();


                        if (youxiu(innerRow, youxiucishu)) {
                            goodResult.put(nickName, youxiucishu);
                        }
                        if (dabiao(sheet, innerRow)) {
                            ordinaryList.add(nickName);
                        }
                    }
                }
            }
            workbook.close();
        } catch (Exception e) {
            System.out.println("------异常行数---" + innerRow);
            e.printStackTrace();
        }

    }

    private static boolean youxiu(int innerRow, String youxiucishu) {
        try {
            int i = Integer.parseInt(youxiucishu);
            if (i >= youxiuThreadShould) {
                System.out.println("___________" + innerRow);
                return true;
            }
            return false;
        } catch (NumberFormatException e) {
//            e.printStackTrace();
            System.out.println("---excel表格中第" + innerRow + "行数据出现异常，需要手动参与");
            return false;
        }
    }

    private static boolean dabiao(Sheet sheet, int innerRow) {

        try {
            String one = sheet.getCell(canyuzuoyeCountColumn - 1, innerRow).getContents();
            int canyuzuoyeCount = Integer.parseInt(one);
//        int i1 = Integer.parseInt(two);
//        int i2 = Integer.parseInt(tree);
//        int i3 = Integer.parseInt(four);
//        int i4 = Integer.parseInt(five);
//        int i5 = Integer.parseInt(six);
//        int i6 = Integer.parseInt(seven);
//        int i7 = i + i1 + i2 + i3 + i4 + i5 + i6;

            if (canyuzuoyeCount >= canyuzuoyeCountThreshould) {
                System.out.println("达标,行数：" + innerRow);
                return true;
            }
        } catch (NumberFormatException e) {

            return false;
        }
        return false;
    }

    public static void main(String[] args) {
        /**
         * 这里是准备的数据
         */

        readExcel(new File(dataSource));

        remakeGoodImages();
        remakeOrdinaryImages();


        System.out.println("success");
    }

    private static void remakeGoodImages() {


        Set<String> keySet = goodResult.keySet();
        Iterator<String> iterator = keySet.iterator();
        while (iterator.hasNext()) {
            PrintImage printImage = new PrintImage();
            BufferedImage goodImage = printImage.loadImageLocal(goodImageUrl);

            String goodUserName = iterator.next();
            String youxiuCount = goodResult.get(goodUserName);

            printImage.modifyImage(goodFont, goodImage, goodUserName, 400, 930, null, 840, 845, null, 200, 233);

            String targetImage = goodUserDestation + goodUserName + ".jpg";
            printImage.writeImageLocal(goodUserDestation, targetImage, goodImage);
        }
    }

    private static void remakeOrdinaryImages() {
        List ordinaryImageUrl = ordinaryList;

        for (int i = 0; i < ordinaryImageUrl.size(); i++) {

            PrintImage printImage = new PrintImage();
            BufferedImage ordinaryImage = printImage.loadImageLocal(ordinarImageUrl);


            String ordinaryUserName = (String) ordinaryImageUrl.get(i);

            printImage.modifyImage(ordinaryFont, ordinaryImage, ordinaryUserName, 380, 930, null, 0, 0, null, 200, 233);


            String targetImage = ordinaryDestation + ordinaryUserName + ".jpg";
            printImage.writeImageLocal(ordinaryDestation, targetImage, ordinaryImage);
        }
    }

    /**
     * 检测是否有emoji字符
     *
     * @param source
     * @return 一旦含有就抛出
     */
    public static boolean containsEmoji(String source) {
        if ("".equals(source) || source == null) {
            return false;
        }
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);

            // 文字
            if (!isEmojiCharacter(codePoint)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) ||
                (codePoint == 0x9) ||
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    /**
     * unicode 转为 string
     *
     * @param ascii
     * @return
     */
    public static String ascii2native(String ascii) {
        List<String> ascii_s = new ArrayList<String>();

        String zhengz = "\\\\u[0-9,a-f,A-F]{4}";

        Pattern p = Pattern.compile(zhengz);

        Matcher m = p.matcher(ascii);

        while (m.find()) {
            ascii_s.add(m.group());
        }
        System.out.println(ascii_s);
        for (int i = 0, j = 2; i < ascii_s.size(); i++) {
            String code = ascii_s.get(i).substring(j, j + 4);
            char ch = (char) Integer.parseInt(code, 16);

            ascii = ascii.replace(ascii_s.get(i), String.valueOf(ch));
        }

        return ascii;
    }

    public static String filterEmoji(String source) {
        if (source != null) {

            Matcher emojiMatcher = emoji.matcher(source);
            if (emojiMatcher.find()) {
                source = emojiMatcher.replaceAll("");
                return source;
            }
            return source;
        }
        return source;
    }
}
