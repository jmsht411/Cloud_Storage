package com.example.fcy.myapplication;


import java.io.Serializable;
import java.util.List;

/**
 * Created by fcy on 17-2-19.
 */

public class DirectoryInfoBean implements Serializable {
    private List<DirectoryInfo> m_list;

    public DirectoryInfoBean(List<DirectoryInfo> m_list) {
        this.m_list = m_list;
    }

    public List<DirectoryInfo> getM_list() {
        return m_list;
    }

    public void setM_list(List<DirectoryInfo> m_list) {
        this.m_list = m_list;
    }

}

