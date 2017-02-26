package com.example.fcy.myapplication.videoplayer;

import org.videolan.libvlc.LibVLC;

import java.util.ArrayList;

/**
 * Created by fcy on 17-2-23.
 */

public class LibVLCUtil {
    private static LibVLC libVLC = null;

    public synchronized static LibVLC getLibVLC(ArrayList<String> options) throws IllegalStateException {
        if (libVLC == null) {
            if (options == null) {
                libVLC = new LibVLC();
            } else {
                libVLC = new LibVLC(options);
            }
        }
        return libVLC;
    }
}
