package sx.nine.util.ftp;

import com.github.junrar.Archive;
import com.github.junrar.rarfile.FileHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.GZIPInputStream;

import org.apache.tools.tar.TarEntry;
import org.apache.tools.tar.TarInputStream;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

/**
 * @Author: NineEr
 * @Date: 2020/3/17 14:58
 * @Description:解压文件公用类
 */
public class UnUtils {
    private static Logger logger = LoggerFactory.getLogger(UnUtils.class);

    /***防止出现并发问题 ***/
    public static synchronized void unRar(String tarFileName, String extPlace)
            throws Exception {
        unRarFile(tarFileName, extPlace);
    }
    public static synchronized void unzip(String zipFileName, String extPlace)
            throws Exception {
        unZipFiles(zipFileName, extPlace);
    }
    /**
     * 解压zip格式的压缩文件到指定位置
     *
     * @param zipFileName
     *            压缩文件
     * @param extPlace
     *            解压目录
     * @throws Exception
     */
    public static boolean unZipFiles(String zipFileName, String extPlace)
            throws Exception {
        System.setProperty("sun.zip.encoding",
                System.getProperty("sun.jnu.encoding"));
        try {
            (new File(extPlace)).mkdirs();
            File f = new File(zipFileName);
            // 处理中文文件名乱码的问题
            ZipFile zipFile = new ZipFile(zipFileName, "GBK");
            if ((!f.exists()) && (f.length() <= 0)) {
                throw new Exception("要解压的文件不存在!");
            }
            String strPath, gbkPath, strtemp;
            File tempFile = new File(extPlace);
            strPath = tempFile.getAbsolutePath();
            Enumeration<?> e = zipFile.getEntries();
            while (e.hasMoreElements()) {
                ZipEntry zipEnt = (ZipEntry) e.nextElement();
                gbkPath = zipEnt.getName();
                if (zipEnt.isDirectory()) {
                    strtemp = strPath + File.separator + gbkPath;
                    File dir = new File(strtemp);
                    dir.mkdirs();
                    continue;
                } else { // 读写文件
                    InputStream is = zipFile.getInputStream(zipEnt);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    gbkPath = zipEnt.getName();
                    strtemp = strPath + File.separator + gbkPath;// 建目录
                    String strsubdir = gbkPath;
                    for (int i = 0; i < strsubdir.length(); i++) {
                        if (strsubdir.substring(i, i + 1).equalsIgnoreCase("/")) {
                            String temp = strPath + File.separator
                                    + strsubdir.substring(0, i);
                            File subdir = new File(temp);
                            if (!subdir.exists()) {
                                subdir.mkdir();
                            }
                        }
                    }
                    FileOutputStream fos = new FileOutputStream(strtemp);
                    BufferedOutputStream bos = new BufferedOutputStream(fos);
                    int c;
                    while ((c = bis.read()) != -1) {
                        bos.write((byte) c);
                    }
                    bos.close();
                    fos.close();
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 根据原始rar路径，解压到指定文件夹下.
     *
     * @param srcRarPath
     *            原始rar路径
     * @param dstDirectoryPath
     *            解压到的文件夹
     */
    public static void unRarFile(String srcRarPath, String dstDirectoryPath) {
        if (!srcRarPath.toLowerCase().endsWith(".rar")) {
            System.out.println("非rar文件！");
            return;
        }
        File dstDiretory = new File(dstDirectoryPath);
        if (!dstDiretory.exists()) {// 目标目录不存在时，创建该文件夹
            dstDiretory.mkdirs();
        }
        Archive a = null;
        try {
            a = new Archive(new File(srcRarPath));
            if (a != null) {
                // a.getMainHeader().print(); // 打印文件信息.
                FileHeader fh = a.nextFileHeader();
                while (fh != null) {
                    // 防止文件名中文乱码问题的处理
                    String fileName = fh.getFileNameW().isEmpty() ? fh
                            .getFileNameString() : fh.getFileNameW();
                    if (fh.isDirectory()) { // 文件夹
                        File fol = new File(dstDirectoryPath + File.separator
                                + fileName);
                        fol.mkdirs();
                    } else { // 文件
                        File out = new File(dstDirectoryPath + File.separator
                                + fileName.trim());
                        try {
                            if (!out.exists()) {
                                if (!out.getParentFile().exists()) {// 相对路径可能多级，可能需要创建父目录.
                                    out.getParentFile().mkdirs();
                                }
                                out.createNewFile();
                            }
                            FileOutputStream os = new FileOutputStream(out);
                            a.extractFile(fh, os);
                            os.close();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    fh = a.nextFileHeader();
                }
                a.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 解压tar.gz文件
     * @param file
     * @param outputDir
     * @throws IOException
     */
    public static void unTarGz(File file,String outputDir) throws IOException{
        TarInputStream tarIn = null;
        try{
            tarIn = new TarInputStream(new GZIPInputStream(
                    new BufferedInputStream(new FileInputStream(file))),
                    10*1024 * 1024);
            createDirectory(outputDir,null);//创建输出目录
            TarEntry entry = null;
            while( (entry = tarIn.getNextEntry()) != null ){
                if(entry.isDirectory()){//是目录
                    //createDirectory(outputDir,entry.getName());//创建空目录
                    logger.info("存在一个目录");
                }else{//是文件
                    String name = entry.getName();
                    name=name.substring(name.lastIndexOf("/")+1);
                    File tmpFile = new File(outputDir + System.getProperty("file.separator") + name);
                    createDirectory(tmpFile.getParent() + System.getProperty("file.separator"),null);//创建输出目录
                    OutputStream out = null;
                    try{
                        out = new FileOutputStream(tmpFile);
                        int length = 0;
                        byte[] b = new byte[2048];
                        while((length = tarIn.read(b)) != -1){
                            out.write(b, 0, length);
                        }

                    }catch(IOException ex){
                        logger.info(ex.getLocalizedMessage());
                        throw ex;
                    }finally{
                        if(out!=null) {
                            out.close();
                        }
                    }
                }
            }
        }catch(IOException ex){
            /*throw new IOException("解压归档文件出现异常",ex);  */
            logger.info(ex.toString());
        } finally{
            try{
                if(tarIn != null){
                    tarIn.close();
                }
            }catch(IOException ex){
                logger.info(ex.toString());
                throw new IOException("关闭tarFile出现异常",ex);
            }
        }
    }

    public static void createDirectory(String outputDir, String subDir) {
        File file = new File(outputDir);
        //子目录不为空
        if (!(subDir == null || subDir.trim().equals(""))) {
            file = new File(outputDir + File.separator + subDir);
        }
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.mkdirs();
        }
    }

}
