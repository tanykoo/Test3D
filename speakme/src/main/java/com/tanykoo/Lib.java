package com.tanykoo;

import com.sun.jna.Library;
import com.sun.jna.Native;

public interface Lib extends Library {
    int MSPLogin(String usr,String pwd,String params);
    Lib instance = Native.loadLibrary("msc_x64",Lib.class);
}
