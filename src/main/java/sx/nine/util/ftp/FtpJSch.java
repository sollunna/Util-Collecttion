package sx.nine.util.ftp;

import com.jcraft.jsch.*;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.net.SocketException;
import java.util.Properties;
import java.util.Vector;


/**
 * @Author: NineEr
 * @Date: 2020/3/19 9:35
 * @Description:ftp链接下载
 */
public class FtpJSch {
	private static Logger logger = LoggerFactory.getLogger(FtpJSch.class);
	@SuppressWarnings("unused")
	public static ChannelSftp getConnect(String user,String host,String password,int port,String directory){
		ChannelSftp sftp=null;
		Channel channel=null;
		Session sshSession=null;
		FtpJSch ftp = new FtpJSch();
		try {
			JSch jsch = new JSch();
			//获取sshSession  账号-ip-端口
			sshSession=jsch.getSession(user, host,port);
			if (sshSession == null) {  
	            try {
					throw new Exception("session is null");
				} catch (Exception e) {
					logger.info("session获取失败");
				}  
	        }else {
	        	//添加密码
				sshSession.setPassword(password);
				Properties sshConfig = new Properties();
				//严格主机密钥检查
				sshConfig.put("StrictHostKeyChecking", "no");
				sshSession.setConfig(sshConfig);
				//开启sshSession链接
				sshSession.connect();
				//获取sftp通道
				channel = sshSession.openChannel("sftp");
				//开启
				channel.connect();
				sftp = (ChannelSftp) channel;
	        }
		} catch (JSchException e) {
			logger.info("创建sftp通道失败");
			logger.info(e.getMessage());
		} finally {
			if (sftp != null) {
				sftp.disconnect();
			}
			 if (channel != null) {
				 channel.disconnect();
			 }
			 if (sshSession != null) {
				 sshSession.disconnect();
			 }
		}
		return sftp;
	}
	

	/**
	 * 下载文件
	 * @param sftp
	 * @param downloadFileName 下载的文件名
	 * @param savePath 存在本地的路径
	 * @param directory 下载目录
	 */
	@SuppressWarnings("unchecked")
	public static void download(ChannelSftp sftp,String downloadFileName,String savePath,String directory) {
		if(directory!=null) {
			try {
				boolean dirExist = isDirExist(savePath, sftp);
				if(dirExist) {
					sftp.cd(directory);
					Vector<ChannelSftp.LsEntry> listFiles = sftp.ls(sftp.pwd());
					for (ChannelSftp.LsEntry ff : listFiles) { 
						String fileName = ff.getFilename(); 
						if(fileName.equals(downloadFileName)) {
							String ftpFilePath = directory + "/" + fileName;
							sftp.get(ftpFilePath, savePath);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	 /** * 判断目录是否存在 */ 
	public static boolean isDirExist(String directory,ChannelSftp sftp) { 
		 boolean isDirExistFlag = false; 
		 try { 
			 SftpATTRS sftpATTRS = sftp.lstat(directory);
			 isDirExistFlag = true; 
			 return sftpATTRS.isDir(); 
			 } catch (Exception e) { 
				 if (e.getMessage().toLowerCase().equals("no such file")) { 
					 isDirExistFlag = false; 
				} 
			} 
		 return isDirExistFlag; 
	}
	 public static FTPClient getFTPClient(String ftpHost, String ftpUserName,
	            String ftpPassword, int ftpPort) {
	        FTPClient ftpClient = new FTPClient();
	        try {
	            ftpClient = new FTPClient();
	            ftpClient.connect(ftpHost, ftpPort);// 连接FTP服务器
	            ftpClient.login(ftpUserName, ftpPassword);// 登陆FTP服务器
	            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
	                logger.info("未连接到FTP，用户名或密码错误。");
	                ftpClient.disconnect();
	            } else {
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

}