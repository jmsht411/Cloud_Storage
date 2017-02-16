package com.example.fcy.myapplication.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.fcy.myapplication.DirectoryInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fcy on 17-2-12.
 */

public class DirectoryDAOImp implements DirectoryDAO {
    private DirectoryDBHelper mHelper;

    public DirectoryDAOImp(Context context) {
        mHelper = DirectoryDBHelper.getInstance(context);
    }

    @Override
    public synchronized void insertDirectory(DirectoryInfo directoryInfo) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.execSQL("insert into directory_info(m_fileName," +
                        "m_parentFileId,m_file_type," +
                        "m_fileSize,m_fileModifyTime," +
                        "m_fileUrl, m_fileMD5)values(?,?,?,?,?,?,?)",
                new Object[]{directoryInfo.getM_fileName(),
                directoryInfo.getM_parentFileId(),directoryInfo.getM_file_type(),
                directoryInfo.getM_fileSize(),directoryInfo.getM_fileModifyTime(),
                directoryInfo.getM_fileUrl(),directoryInfo.getM_fileMD5()});
        db.close();
    }
//
//    @Override
//    public synchronized void deleteDirectory(int m_fileId) {
//        SQLiteDatabase db = mHelper.getWritableDatabase();
//        db.execSQL("delete from thread_info where url = ? and thread_id = ?",
//                new Object[]{url});
//        db.close();
//
//    }
//
//    @Override
//    public synchronized void updateDirectory(DirectoryInfo directoryInfo) {
//        SQLiteDatabase db = mHelper.getWritableDatabase();
//        db.execSQL("update thread_info set finished = ? where url = ? and thread_id = ?",
//                new Object[]{finished, url, thread_id});
//        db.close();
//    }

    @Override
    public synchronized ArrayList<DirectoryInfo> getSpecDirectorys(int m_parentFileId) {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from directory_info where m_parentFileId = ?", new String[]{m_parentFileId+""});
        ArrayList<DirectoryInfo> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            DirectoryInfo directoryInfo = new DirectoryInfo();
            directoryInfo.setM_fileId(cursor.getInt(cursor.getColumnIndex("m_fileId")));
            directoryInfo.setM_fileName(cursor.getString(cursor.getColumnIndex("m_fileName")));
            directoryInfo.setM_parentFileId(cursor.getInt(cursor.getColumnIndex("m_parentFileId")));
            directoryInfo.setM_file_type(cursor.getInt(cursor.getColumnIndex("m_file_type")));
            directoryInfo.setM_fileSize(cursor.getDouble(cursor.getColumnIndex("m_fileSize")));
            directoryInfo.setM_fileModifyTime(cursor.getString(cursor.getColumnIndex("m_fileModifyTime")));
            directoryInfo.setM_fileUrl(cursor.getString(cursor.getColumnIndex("m_fileUrl")));
            directoryInfo.setM_fileMD5(cursor.getString(cursor.getColumnIndex("m_fileMD5")));
            list.add(directoryInfo);
        }
        cursor.close();
        db.close();
        return list;
    }

    @Override
    public synchronized boolean isExist(int m_fileId) {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from directory_info where m_fileId = ?",
                new String[]{m_fileId+""});
        boolean exists = cursor.moveToNext();
        cursor.close();
        db.close();
        return exists;
    }



}
