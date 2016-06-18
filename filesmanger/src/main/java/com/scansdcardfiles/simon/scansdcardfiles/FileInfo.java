package com.scansdcardfiles.simon.scansdcardfiles;


import com.se7en.utils.FileUtil;

import java.util.Comparator;

/**
 * Created by SImon on 2016/6/16.
 */
public class FileInfo {
    private String name;
    private String path;
    private String size;
    private int category;//前缀
    private String categoryName;//前缀名
    private String prefix;//类型

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
        this.prefix = FileUtil.getPrifix(path);
        this.category = FileUtil.getCategory(prefix);
        this.categoryName = FileUtil.getCategoryName(category);

    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", size='" + size + '\'' +
                ", category=" + category +
                ", categoryName='" + categoryName + '\'' +
                ", prefix='" + prefix + '\'' +
                '}';
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public static class FileComparator implements Comparator<FileInfo>
    {

        @Override
        public int compare(FileInfo lhs, FileInfo rhs)
        {
            return lhs.getCategory() - rhs.getCategory();
        }
    }
}
