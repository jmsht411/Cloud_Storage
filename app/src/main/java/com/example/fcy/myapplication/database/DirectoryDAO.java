package com.example.fcy.myapplication.database;

import com.example.fcy.myapplication.DirectoryInfo;

import java.util.List;

/**
 * Created by fcy on 17-2-12.
 */

public interface DirectoryDAO {
    public void insertDirectory(DirectoryInfo directoryInfo);

//    public void deleteDirectory(int m_fileId);
//
//    public void updateDirectory(DirectoryInfo directoryInfo);

    public List<DirectoryInfo> getSpecDirectorys(int m_parentFileId);

    public boolean isExist(int m_fileId);
}
