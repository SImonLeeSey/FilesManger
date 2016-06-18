package com.scansdcardfiles.simon.scansdcardfiles;

import android.text.TextUtils;
import android.util.Log;

import com.se7en.utils.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SImon on 2016/6/17.
 */
public class ScanFiles {

    public static void scanType(String path, int TYPE,List<FileInfo> list) {
        File dir = new File(path);
        if (TextUtils.isEmpty(path) || !dir.exists()) {
            return ;
        }
        File[] files = dir.listFiles();
        for (File f : files) {
            final File tempFile = f;
            if (tempFile.isDirectory()) {
                scanType(tempFile.getAbsolutePath(), TYPE,list);
            } else {

                FileInfo fileInfo = new FileInfo();
                fileInfo.setName(tempFile.getName());
                fileInfo.setPath(tempFile.getAbsolutePath());
                fileInfo.setSize(FileUtil.getFileLength(tempFile.length()));
                if (fileInfo.getCategory() == TYPE) {
                    list.add(fileInfo);
                }
            }
        }

    }

    public static void scanAll(String path,List<FileInfo> list) {
        File dir = new File(path);
        if (TextUtils.isEmpty(path) || !dir.exists()) {
            return;
        }
        File[] files = dir.listFiles();
        for (File f : files) {
            final File tempFile = f;
            if (tempFile.isDirectory()) {
                // scanAll(tempFile.getAbsolutePath(),list);
            } else {

                FileInfo fileInfo = new FileInfo();
                fileInfo.setName(tempFile.getName());
                fileInfo.setPath(tempFile.getAbsolutePath());
                fileInfo.setSize(FileUtil.getFileLength(tempFile.length()));
                list.add(fileInfo);
            }
        }
    }
}
