package com.scansdcardfiles.simon.scansdcardfiles;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.scansdcardfiles.simon.scansdcardfiles.utils.CommonAdapter;
import com.scansdcardfiles.simon.scansdcardfiles.utils.ThreadTask;
import com.scansdcardfiles.simon.scansdcardfiles.utils.ViewHolder;
import com.scansdcardfiles.simon.scansdcardfiles.weidget.TitleBar;
import com.se7en.utils.FileUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    @ViewInject(R.id.iv_listview)
    private ListView lv;

    @ViewInject(R.id.tv_path)
    private TextView tv;

    @ViewInject(R.id.titlebar)
    private com.scansdcardfiles.simon.scansdcardfiles.weidget.TitleBar titleBar;

    private View headView;
    private List<FileInfo> list;

    private CommonAdapter<FileInfo> adapter;

    private View llFiles, llVideo, llMusic, llApp, llDocument, llOther;

    private int Type = 0;

    private AlertDialog builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.Ext.init(getApplication());
        x.view().inject(this);
        init();
        list = new ArrayList<>();
        adapter = new CommonAdapter<FileInfo>(this, list, R.layout.listview_adapter) {
            @Override
            public void convert(ViewHolder helper, int position, FileInfo item) {
                helper.setText(R.id.tv_name, item.getName());
                helper.setText(R.id.tv_content, "文件类型:" + item.getCategoryName() + "\t\t文件大小：" + item.getSize());
                if (item.getCategory() == FileUtil.FileCagegorys.FILE_CATEGORY_PICTURE) {
                    helper.setImageByUrl(R.id.iv_icon, item.getPath());
                } else {
                    switch (item.getCategory()) {
                        case FileUtil.FileCagegorys.FILE_CATEGORY_APK:
                            helper.setImageResource(R.id.iv_icon, R.mipmap.apk);
                            break;
                        case FileUtil.FileCagegorys.FILE_CATEGORY_DOCUMENT:
                            helper.setImageResource(R.id.iv_icon, R.mipmap.doc);
                            break;
                        case FileUtil.FileCagegorys.FILE_CATEGORY_VIDEO_AUDIO:
                            helper.setImageResource(R.id.iv_icon, R.mipmap.avi);
                            break;
                        default:
                            helper.setImageResource(R.id.iv_icon, R.mipmap.other);
                            break;
                    }
                }
            }
        };
        headView = this.getLayoutInflater().inflate(R.layout.listview_headview, lv, false);
        initHeadView();
        lv.addHeaderView(headView);
        lv.setAdapter(adapter);
        scan();
        show();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //listview点击事件
                Log.e("TGA", "listview点击事件");
            }
        });
    }


    private void initHeadView() {
        llFiles = headView.findViewById(R.id.ll_files);
        llMusic = headView.findViewById(R.id.ll_mp3);
        llVideo = headView.findViewById(R.id.ll_video);
        llApp = headView.findViewById(R.id.ll_apk);
        llDocument = headView.findViewById(R.id.ll_doc);
        llOther = headView.findViewById(R.id.ll_other);
        headViewEvent();

    }

    private void headViewEvent() {
        llFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Type = FileUtil.FileCagegorys.FILE_CATEGORY_PICTURE;
                show();
                scan();
            }
        });
        llMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Type = FileUtil.FileCagegorys.FILE_CATEGORY_VIDEO_AUDIO;
                show();
                scan();
            }
        });
        llVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Type = FileUtil.FileCagegorys.FILE_CATEGORY_VIDEO_AUDIO;
                show();
                scan();
            }
        });
        llApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Type = FileUtil.FileCagegorys.FILE_CATEGORY_APK;
                show();
                scan();
            }
        });
        llDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Type = FileUtil.FileCagegorys.FILE_CATEGORY_DOCUMENT;
                show();
                scan();
            }
        });
        llOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Type = FileUtil.FileCagegorys.FILE_CATEGORY_OTHER;
                show();
                scan();
            }
        });
    }

    private void init() {
        titleBar.setCollectClickListener(new TitleBar.CollectClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TGA", "收藏文件夹");
            }
        });
        titleBar.setSearchClickListener(new TitleBar.SearchClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TGA", "搜索文件夹");

            }
        });
        titleBar.setEditClickListener(new TitleBar.EditClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TGA", "编辑文件夹");
            }
        });
    }

    private void scan() {
        final String path = "/sdcard";
        //// TODO:  scan耗时操作
        ThreadTask.getInstance().executorOtherThread(new Runnable() {
            @Override
            public void run() {

                if (Type != 0) {
                    list.clear();
                    ScanFiles.scanType(path, Type, list);
                } else if (Type == 0) {
                    list.clear();
                    ScanFiles.scanAll(path, list);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        builder.cancel();
                        Toast.makeText(MainActivity.this, "查询完成！", Toast.LENGTH_SHORT).show();
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }, ThreadTask.ThreadPeriod.PERIOD_HIGHT);
    }

    public void show() {
        LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(R.layout.layout_dialog, null);
        LinearLayout layout = (LinearLayout) view
                .findViewById(R.id.ll_dialog);

        builder = new AlertDialog.Builder(this).setView(layout).show();

    }
}
