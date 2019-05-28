package com.tanykoo;

import com.sun.jna.Structure;
import com.sun.jna.platform.win32.BaseTSD.ULONG_PTR;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinDef.HWND;

import java.util.Arrays;
import java.util.List;
 
/**
 * 定义鼠标钩子数据结构体
 */
public class MouseHookStruct extends Structure{

    public WinDef.POINT pt; //点坐标
    public WinDef.LPARAM lparam;
    public HWND hwnd;//窗口句柄
    public int wHitTestCode;
    public ULONG_PTR dwExtraInfo; //扩展信息
   
    //返回属性顺序
 @Override
 protected List getFieldOrder() {
  return Arrays.asList("pt","lparam","hwnd","wHitTestCode","dwExtraInfo");
 }

}
