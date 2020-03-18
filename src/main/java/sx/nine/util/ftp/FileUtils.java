package sx.nine.util.ftp;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tools.tar.TarEntry;
import org.apache.tools.tar.TarInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

/**
 * @Author: NineEr
 * @Date: 2020/3/17 14:56
 * @Description:
 */
public class FileUtils {
    private static Logger logger = LoggerFactory.getLogger(FileUtils.class);
    private static List<File> list=new ArrayList<File>();
    /**
     * 解压
     * @param srcRarPath 原始rar路径
     * @param dstDirectoryPath  dstDirectoryPath
     */
    public static void readRar(String srcRarPath,String dstDirectoryPath){
        UnUtils.unRarFile(srcRarPath, dstDirectoryPath);
    }
    /**
     * 解压缩tar.gz文件
     * @param file 压缩包文件
     * @param targetPath 目标文件夹
     * @param delete 解压后是否删除原压缩包文件
     */
    public static void decompressTarGz(File file, String targetPath, boolean delete){
        FileInputStream fileInputStream = null;
        BufferedInputStream bufferedInputStream = null;
        GZIPInputStream gzipIn = null;
        TarInputStream tarIn = null;
        OutputStream out = null;
        try {
            fileInputStream = new FileInputStream(file);
            bufferedInputStream = new BufferedInputStream(fileInputStream);
            gzipIn = new GZIPInputStream(bufferedInputStream);
            tarIn = new TarInputStream(gzipIn, 1024 * 2);
            // 创建输出目录
            createDirectory(targetPath, null);
            TarEntry entry = null;
            while((entry = tarIn.getNextEntry()) != null){
                String fileName= entry.getName().trim();
                if(!existZH(fileName)){
                    // fileName = entry.get
                }
                if(entry.isDirectory()){
                    // 是目录
                    createDirectory(targetPath, fileName);
                    // 创建子目录
                }else{
                    // 是文件
                    File tempFIle = new File(targetPath + File.separator + fileName);
                    createDirectory(tempFIle.getParent() + File.separator, null);
                    out = new FileOutputStream(tempFIle);
                    int len =0; byte[] b = new byte[2048];
                    while ((len = tarIn.read(b)) != -1){
                        out.write(b, 0, len);
                    }
                    out.toString();
                    out.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(out != null){
                    out.close();
                }
                if(tarIn != null){
                    tarIn.close();
                }
                if(gzipIn != null){
                    gzipIn.close();
                }
                if(bufferedInputStream != null){
                    bufferedInputStream.close();
                }
                if(fileInputStream != null){
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static boolean existZH(String str) {
        String regEx = "[\\u4e00-\\u9fa5]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        while (m.find()) {
            return true;
        }
        return false;
    }
    /**
     *  构建目录
     * @param outputDir 输出目录
     * @param subDir 子目录
     */
    private static void createDirectory(String outputDir, String subDir){
        File file = new File(outputDir);
        if(!(subDir == null || subDir.trim().equals(""))) {
            //子目录不为空
            file = new File(outputDir + File.separator + subDir);
        }
        if(!file.exists()){
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            file.mkdirs();
        }
    }
    /**
     * 读取文件夹内所有内容
     * @param path
     */
    public static List<File> readFile(String path){
        File file=new File(path);
        File[] tempList = file.listFiles();
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                list.add(tempList[i]);
            }
            if (tempList[i].isDirectory()) {
                readFile(tempList[i].getPath());
            }
        }
        return list;
    }
    /**
     * 根据路径读取数据
     * @param path
     * @return  返回 String[] 数据数组
     */
    @SuppressWarnings("unchecked")
    public static List<String[]> readTxtData(String path) {
        List<String[]> list=readTxt(path);
        return list;
    }
    /**
     * 读本txt数据库数据文件
     */
    public static List<String[]> readTxt(String path){
        List<String[]> list = new ArrayList<String[]>();
        try {
            File file = new File(path);
            BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file));
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis,"GBK"),10*1024*1024);// 用5M的缓冲读取文本文件
            String str = "";
            while (null != (str = reader.readLine())) { //使用readLine方法，一次读一行
                String[] split = str.split("@\\|@");
                list.add(split);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("读取文件:" + path + "失败!");
        }
        return list;
    }
    /**
     * 根据路径读取数据
     * @param path
     * @return  返回 String[] 数据数组
     */
    public static List<String[]> readCsvData(String path) {
        try {
            BufferedReader in =new BufferedReader(new InputStreamReader(new FileInputStream(path), "GBK"));
            List<String[]> list = new ArrayList<String[]>();
            String line;
            String[] onerow;
            while ((line=in.readLine())!=null) {
                onerow = line.split(","); //默认分割符为逗号，可以不使用逗号
                list.add(onerow);
            }
            in.close();
            return list;
        } catch (Exception e) {
            return null;
        }
    }
    /**
     * 判断文件格式
     * @param path
     * @return
     * @throws Exception
     */
    public static String resolveCode(String path) throws Exception {
        InputStream inputStream = new FileInputStream(path);
        byte[] head = new byte[3];
        inputStream.read(head);
        String code = "gb2312";  //或GBK
        if (head[0] == -1 && head[1] == -2 ) {
            code = "UTF-16";
        } else if (head[0] == -2 && head[1] == -1 ) {
            code = "Unicode";
        } else if(head[0]==-17 && head[1]==-69 && head[2] ==-65) {
            code = "UTF-8";
        }
        inputStream.close();
        return code;
    }
    /**
     * 清空目录
     * @param path
     * @return
     */
    public static void deleteDir(String path){
        File file = new File(path);
        if(!file.exists()){//判断是否待删除目录是否存在
            file.mkdirs();//创建文件夹
        }else {
            String[] content = file.list();
            for(String name : content){
                File temp = new File(path, name);
                if(temp.isDirectory()){
                    deleteDir(temp.getAbsolutePath());
                    temp.delete();//删除空目录
                }else{
                    temp.delete();
                }
            }
        }
    }
    /**
     * 判断文件类型
     * @param fileName
     * @return
     */
    public static String fileType(String fileName){
        String type="";
        int lastIndexOf = fileName.lastIndexOf(".");
        if(lastIndexOf==-1) {
            type="logs";
        }else {
            type = fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        return type;
    }
    /**
     * 判断文件类型
     * @param fileName
     * @return
     */
    public static String fileTypetargz(String fileName){
        String type="";
        type = fileName.substring(fileName.indexOf(".") + 1);
        return type;
    }
    /**
     * 获取数据库名
     * @param fileName
     * @return
     */
    public static String fileName(String fileName){
        int indexOf = fileName.indexOf("-");
        int lastIndexOf = fileName.lastIndexOf("-");
        String substring = fileName.substring(indexOf+1, lastIndexOf);
        return substring;
    }
    public static String fileNamecsv(String fileName){
        String substring = fileName.substring(0,fileName.lastIndexOf("."));
        return substring;
    }
    /**
     * 读取excle
     * @param filePath
     * @return
     */
    @SuppressWarnings("resource")
    public static Workbook readExcel(String filePath){
        Workbook wb = null;
        if(filePath==null){
            return null;
        }
        Boolean existFilePath = existFilePath(filePath);
        if(!existFilePath) {
            return null;
        }else {
            String extString = filePath.substring(filePath.lastIndexOf("."));
            InputStream is = null;
            try {
                is = new FileInputStream(filePath);
                if(".xls".equals(extString)){
                    return wb = new HSSFWorkbook(is);
                }else if(".xlsx".equals(extString)){
                    return wb = new XSSFWorkbook(is);
                }else{
                    return wb = null;
                }
            } catch (FileNotFoundException e) {
                logger.info("excle文件读取失败");
                logger.info(e.toString());
                e.printStackTrace();
            } catch (IOException e) {
                logger.info("excle文件读取失败");
                logger.info(e.toString());
                e.printStackTrace();
            }
        }
        return wb;
    }
    public static  List<List<String>> getRowData(Workbook wb){
        List<List<String>> list = null;
        Sheet sheet = null;
        Row row = null;
        //用来存放表中数据
        list = new ArrayList<List<String>>();
        //获取第一个sheet
        sheet = wb.getSheetAt(3);
        int firstRowIndex = sheet.getFirstRowNum()+1;   //第一行是列名
        int lastRowIndex = sheet.getLastRowNum();
        for(int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {
            //遍历行
            row = sheet.getRow(rIndex);
            List<String> lis= new ArrayList<String>();
            if (row != null) {
                int firstCellIndex = row.getFirstCellNum();
                int lastCellIndex = row.getLastCellNum();
                for (int cIndex = firstCellIndex; cIndex < lastCellIndex; cIndex++) {
                    //遍历列
                    Cell cell = row.getCell(cIndex);
                    if (cell != null) {
                        lis.add(cell.toString());
                    }
                }
            }
            list.add(lis);
        }
        return list;
    }
    /**
     * 解析excle文件，分析全量与增量
     * @param filePath excle路径
     * @return
     */
    public static List<List<String>> getExcleData(String filePath){
        List<List<String>> list = null;
        Workbook wb =null;
        wb = readExcel(filePath);
        if(wb != null){
            list= getRowData(wb);
        }
        return list;
    }
    /**
     * 判断文件是否存在
     * @param filepath
     * @return
     */
    private static Boolean existFilePath(String filepath){
        File file = new File(filepath);
        if(file.exists()) {
            return true;
        }else {
            logger.info(filepath + "文件不存在");
            return false;
        }
    }
    /**
     * 判断文件夹是否存在
     * @param filepath
     * @return
     */
    public static Boolean isDirExists(String filepath) {
        File file = new File(filepath);
        if (file.exists()) {
            if (file.isDirectory()) {
                return true;
            } else {
                return false;
            }
        }else {
            return false;
        }
    }
}
