package com.example.fcy.myapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by fcy on 17-2-12.
 */

public class DirectoryDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "directory.db";
    private static DirectoryDBHelper directoryDBHelper;
    private static int VERSION = 1;
    private static final String SQL_CREATE = "create table directory_info(m_fileId integer primary key autoincrement," +
            "m_fileName integer,m_parentFileId integer,m_file_type integer,m_fileSize double, m_fileModifyTime text, " +
            "m_fileUrl text,m_fileMD5 text)";
    private static final String SQL_DROP = "drop table if exits directory_info";

    public static DirectoryDBHelper getInstance(Context context) {
        if (directoryDBHelper == null) {
            directoryDBHelper = new DirectoryDBHelper(context);
        }
        return directoryDBHelper;
    }

    private DirectoryDBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP);
        db.execSQL(SQL_CREATE);
    }

}
