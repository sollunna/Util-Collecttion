package sx.nine.util.ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;

/**
 * @Author: NineEr
 * @Date: 2020/3/17 14:45
 * @Description:FTP客户端连接工具
 */
public class FTPClientUtil {
    private static Logger logger = LoggerFactory.getLogger(FTPClientUtil.class);
    public static FTPClient getFTPClient(String ftpHost, String ftpUserName, String ftpPassword, int ftpPort) {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient = new FTPClient();
            ftpClient.connect(ftpHost, ftpPort);// 连接FTP服务器
            ftpClient.login(ftpUserName, ftpPassword);
            //超时时间
            int defaultTimeoutSecond=30*60 * 1000;
            ftpClient.setDefaultTimeout(defaultTimeoutSecond);
            ftpClient.setConnectTimeout(defaultTimeoutSecond );
            ftpClient.setDataTimeout(defaultTimeoutSecond);
            // 登陆FTP服务器
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                logger.info("未连接到FTP，用户名或密码错误。");
                ftpClient.disconnect();
            }
            else {
                logger.info("FTP连接成功。");
            }
        } catch (SocketException e) {
            e.printStackTrace();
            logger.info("FTP的IP地址可能错误，请正确配置。");
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("FTP的端口错误,请正确配置。");
        }
        return ftpClient;
    }
    /** * 下载文件 *
     * @param pathname FTP服务器文件目录 *
     * @param ftpClient 文件名称 *
     * @param localpath 下载后的文件路径 *
     * @return */
    public static  boolean downloadFile(String pathname, String localpath,FTPClient ftpClient){
        boolean flag = false;
        OutputStream os=null;
        Boolean dirExists = FileUtils.isDirExists(localpath);
        if(!dirExists) {
            File f=new File(localpath);
            if(!f.exists()){
                f.mkdirs();
            }
        }else {
            FileUtils.deleteDir(localpath);
        }
        try {
            logger.info("下载路径："+pathname);
            //切换FTP目录
            ftpClient.changeWorkingDirectory(pathname);
            String reply = ftpClient.getReplyString();
            logger.info("reply----"+reply);
            if("250".equals(reply)) {
                logger.info("开始下载文件");
                // 设置PassiveMode被动模式-向服务发送传输请求
                ftpClient.enterLocalPassiveMode();
                // 设置以二进制流的方式传输
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                //得到指定目录下所有的文件
                FTPFile[] ftpFiles = ftpClient.listFiles();
                if(ftpFiles!=null&&ftpFiles.length>0) {
                    logger.info("需要下载文件数量："+ftpFiles.length);
                    for(FTPFile file : ftpFiles){
                        try {
                            File localFile = new File(localpath +System.getProperty("file.separator") + file.getName());
                            logger.info("下载文件保存路径是："+localFile.getPath());
                            os = new FileOutputStream(localFile);
                            try {
                                ftpClient.retrieveFile(file.getName(), os);
                            } catch (Exception e) {
                                logger.info("下载文件失败");
                                e.printStackTrace();
                            }
                            os.close();
                            logger.info("下载文件成功");
                        } catch (Exception e) {
                            logger.info("下载文件失败");
                            e.printStackTrace();
                        }
                    }
                    logger.info("下载完成");
                }
                else {
                    logger.info("下载目录没有文件");
                }
            }else {
                logger.info("下载目录不存在");
            }
            ftpClient.logout();
            flag = true;
        } catch (Exception e) {
            logger.info("下载文件失败");
            e.printStackTrace();
        } finally{
            if(ftpClient.isConnected()){
                try{
                    ftpClient.disconnect();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
            if(null != os){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }
}
