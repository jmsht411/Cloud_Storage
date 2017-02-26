package com.example.fcy.myapplication;

/**
 * Created by fcy on 17-2-6.
 */

public class DirectoryInfo {

    //文件id
    private int m_fileId;
    //文件名
    private String m_fileName;
    //父节点id
    private int m_parentFileId;
    //文件类型，0：目录，1：没有类型的文件，2：视频文件，3：图片文件，4：文本文件
    private int m_file_type;
    //文件大小，如果为目录，默认为0
    private double m_fileSize;
    //修改时间
    private String m_fileModifyTime;
    //网盘文件路径
    private String m_fileUrl;
    //文件md5值
    private String m_fileMD5;

    //图标名
    private int m_iconName;

    public DirectoryInfo() {

    }

    public DirectoryInfo(int m_fileId, String m_fileName,
                         int m_parentFileId, int m_file_type,
                         double m_fileSize, String m_fileModifyTime,
                         String m_fileUrl, String m_fileMD5) {
        this.m_fileId = m_fileId;
        this.m_fileName = m_fileName;
        this.m_parentFileId = m_parentFileId;
        this.m_file_type = m_file_type;
        this.m_fileSize = m_fileSize;
        this.m_fileModifyTime = m_fileModifyTime;
        this.m_fileUrl = m_fileUrl;
        this.m_fileMD5 = m_fileMD5;
        this.m_iconName = calIconName(m_file_type);
    }

    private int calIconName(int m_file_type)
    {
        switch (m_file_type) {
            case 0: return R.drawable.document;
            case 1: return R.drawable.document;
            //case 2: return R.drawable.video;
            default: return R.drawable.document;
        }
    }

    public void calculateIcon()
    {
        this.m_iconName = calIconName(m_file_type);
    }

    public int getM_fileId() {
        return m_fileId;
    }

    public void setM_fileId(int m_fileId) {
        this.m_fileId = m_fileId;
    }

    public String getM_fileName() {
        return m_fileName;
    }

    public void setM_fileName(String m_fileName) {
        this.m_fileName = m_fileName;
    }

    public int getM_parentFileId() {
        return m_parentFileId;
    }

    public void setM_parentFileId(int m_parentFileId) {
        this.m_parentFileId = m_parentFileId;
    }

    public int getM_file_type() {
        return m_file_type;
    }

    public void setM_file_type(int m_file_type) {
        this.m_file_type = m_file_type;
    }

    public double getM_fileSize() {
        return m_fileSize;
    }

    public void setM_fileSize(double m_fileSize) {
        this.m_fileSize = m_fileSize;
    }

    public String getM_fileModifyTime() {
        return m_fileModifyTime;
    }

    public void setM_fileModifyTime(String m_fileModifyTime) {
        this.m_fileModifyTime = m_fileModifyTime;
    }

    public String getM_fileUrl() {
        return m_fileUrl;
    }

    public void setM_fileUrl(String m_fileUrl) {
        this.m_fileUrl = m_fileUrl;
    }

    public String getM_fileMD5() {
        return m_fileMD5;
    }

    public void setM_fileMD5(String m_fileMD5) {
        this.m_fileMD5 = m_fileMD5;
    }

    public int getM_iconName() {
        return m_iconName;
    }
}
