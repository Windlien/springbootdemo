package com.example.springbootdemo.utils.my;

import com.jcraft.jsch.*;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import org.apache.log4j.Logger;
import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

public class SftpClientUtil
{
    private static final Logger logger = Logger.getLogger(SftpClientUtil.class);

    /** Sftp */
    private ChannelSftp sftp = null;
    /** 主机 */
    private String host = "";
    /** 端口 */
    private int port = 0;
    /** 用户名 */
    private String username = "";
    /** 密码 */
    private String password = "";

    /**
     * 构造函数
     *
     * @param host
     *            主机
     * @param port
     *            端口
     * @param username
     *            用户名
     * @param password
     *            密码
     *
     */
    public SftpClientUtil(String host, int port, String username, String password)
    {

        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    /**
     * 连接sftp服务器
     *
     * @throws Exception
     */
    public void connect() throws Exception
    {

        JSch jsch = new JSch();
        Session sshSession = jsch.getSession(this.username, this.host, this.port);
        logger.debug(SftpClientUtil.class + "Session created.");

        sshSession.setPassword(password);
        Properties sshConfig = new Properties();
        sshConfig.put("StrictHostKeyChecking", "no");
        sshSession.setConfig(sshConfig);
        sshSession.connect(20000);
        logger.debug(SftpClientUtil.class + " Session connected.");

        logger.debug(SftpClientUtil.class + " Opening Channel.");
        Channel channel = sshSession.openChannel("sftp");
        channel.connect();
        this.sftp = (ChannelSftp) channel;
        logger.debug(SftpClientUtil.class + " Connected to " + this.host + ".");
    }

    /**
     * Disconnect with server
     *
     * @throws Exception
     */
    public void disconnect() throws Exception
    {
        if (this.sftp != null)
        {
            if (this.sftp.isConnected())
            {
                this.sftp.disconnect();
            } else if (this.sftp.isClosed())
            {
                logger.debug(SftpClientUtil.class + " sftp is closed already");
            }
        }
    }

    /**
     * 下载单个文件
     *
     * @param directory
     *            下载目录
     * @param downloadFile
     *            下载的文件
     * @param saveDirectory
     *            存在本地的路径
     *
     * @throws Exception
     */
    public void download(String directory, String downloadFile, String saveDirectory) throws Exception
    {
        File pathFile = new File(saveDirectory);
        if (!pathFile.exists())
        {
            pathFile.mkdirs();
        }

        String saveFile = saveDirectory + "//" + downloadFile;

        this.sftp.cd(directory);
        File file = new File(saveFile);
        this.sftp.get(downloadFile, new FileOutputStream(file));
    }

    /**
     * 下载目录下全部文件
     *
     * @param directory
     *            下载目录
     *
     * @param saveDirectory
     *            存在本地的路径
     *
     * @throws Exception
     */
    public void downloadByDirectory(String directory, String saveDirectory) throws Exception
    {
        String downloadFile = "";
        List<String> downloadFileList = this.listFiles(directory);
        Iterator<String> it = downloadFileList.iterator();

        while (it.hasNext())
        {
            downloadFile = it.next().toString();
            if (downloadFile.toString().indexOf(".") < 0)
            {
                continue;
            }
            this.download(directory, downloadFile, saveDirectory);
        }
    }

    /**
     * 新建子目录
     *
     * @param dst 远程服务器路径
     *
     * @throws Exception
     */
    public void mkdir(String dst, String subDir) throws Exception
    {
        this.sftp.cd(dst);
        try {
            if(this.sftp.ls(subDir).size() > 0) {
                return;
            }
        } catch (SftpException se) {
            logger.error(se.getMessage());
        }

        this.sftp.mkdir(subDir);
    }

    /**
     * 上传单个文件
     *
     * @param src 本地文件
     * @param dst 远程服务器路径
     *
     * @throws Exception
     */
    public void upload(String src, String dst) throws Exception
    {
        this.sftp.put(src, dst);
    }

    /**
     * 列出目录下的文件
     *
     * @param directory
     *            要列出的目录
     *
     * @return list 文件名列表
     *
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<String> listFiles(String directory) throws Exception
    {

        Vector fileList;
        List<String> fileNameList = new ArrayList<String>();

        fileList = this.sftp.ls(directory);
        Iterator it = fileList.iterator();

        while (it.hasNext())
        {
            String fileName =((LsEntry) it.next()).getFilename();
            if (".".equals(fileName) || "..".equals(fileName))
            {
                continue;
            }
            fileNameList.add(fileName);

        }

        return fileNameList;
    }

    public ChannelSftp getSftp()
    {
        return sftp;
    }

    public void setSftp(ChannelSftp sftp)
    {
        this.sftp = sftp;
    }

    public static void main(String[] args) {
        String host = "192.xx.xx.xxx";// 主机地址
        String port = "22";// 主机端口
        String username = "xxxx";// 服务器用户名
        String password = "xxxxx";// 服务器密码
        SftpClientUtil sftpClientUtil = new SftpClientUtil(host, Integer.parseInt(port), username, password);
        try {
            logger.info("开始连接sftp服务器");
            try {
                sftpClientUtil.connect();
            } catch (Exception e) {
                logger.error("开始连接sftp服务器报错:{}" + e.getMessage());
            }
            // 文件所在本地路径
            String srcUrl = "/Users/zhuangjy/Desktop/";
            // 上传到服务器路径
            String dstUrl = "/usr/local/test" + File.separator;
            // SFTP 创建路径
            sftpClientUtil.mkdir("/usr/local/", "test");
            // 上传文件
            sftpClientUtil.upload("/Users/zhuangjy/Desktop/test.txt", dstUrl + "test.txt");
            // 下载文件
            sftpClientUtil.download("/usr/local/test/", "test.txt", "/Users/zhuangjy/Desktop/");
        } catch (Exception e) {
            logger.error("下载文件出错" + e.getMessage());
        }  finally {
            try {
                sftpClientUtil.disconnect();
            } catch (Exception e) {
                logger.error("获取文件：" + e.getMessage());
            }
        }
    }
}
