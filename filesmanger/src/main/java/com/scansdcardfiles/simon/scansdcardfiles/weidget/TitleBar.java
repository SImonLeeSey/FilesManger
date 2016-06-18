package com.scansdcardfiles.simon.scansdcardfiles.weidget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scansdcardfiles.simon.scansdcardfiles.R;


public class TitleBar extends RelativeLayout {
    private TextView tvName,tvInfo;
    private ImageView ivSearch, ivCollect,ivEdit;

    private SearchClickListener searchClickListener;//查询

    private CollectClickListener collectClickListener;//搜藏

    private EditClickListener editClickListener;//搜藏

    public TitleBar(Context context) {
        super(context);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        /**
         * 将自定义控件和布局文件进行绑定
         * 参数一:View中可以通过getContext方法获取上下文对象
         * 参数二:布局文件id
         * 参数三:放置该布局的容器,也就是本类
         */
        View view = View.inflate(getContext(), R.layout.layout_titlebar, this);
        ivCollect = (ImageView) view.findViewById(R.id.iv_collect);
        tvName = (TextView) view.findViewById(R.id.tv_appname);
        tvInfo = (TextView) view.findViewById(R.id.tv_content);
        ivSearch = (ImageView) view.findViewById(R.id.iv_search);
        ivEdit = (ImageView) view.findViewById(R.id.iv_edit);
        ivSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                searchClickListener.onClick(v);
            }
        });

       ivCollect.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                collectClickListener.onClick(v);
            }
        });
        ivEdit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                editClickListener.onClick(v);
            }
        });
    }
    public void setTitle(String title) {
        tvName.setText(title);
    }
    public void setTitleColor(int color) {
        tvName.setTextColor(color);
    }

    public  void  setTextContext(String context){
        tvInfo.setText(context);
    }
    public  void  setTextColor(int color){
        tvInfo.setTextColor(color);;
    }

    public void setSearchImageView(int resId) {
        ivSearch.setImageResource(resId);
    }
    public void showSearchImageView() {
        ivSearch.setVisibility(VISIBLE);
    }

    public void hideSearchImageView() {
        ivSearch.setVisibility(GONE);
    }

    public void setCollectImageView(int resId) {
        ivCollect.setImageResource(resId);
    }
    public void showCollectImageView() {
        ivCollect.setVisibility(VISIBLE);
    }

    public void hideCollectImageView() {
        ivCollect.setVisibility(GONE);
    }

    public void setEditImageView(int resId) {
        ivEdit.setImageResource(resId);
    }
    public void showEditImageVie() {
        ivEdit.setVisibility(VISIBLE);
    }

    public void hideEditImageVie() {
        ivEdit.setVisibility(GONE);
    }

    /**
     * 搜索按钮点击的接口
     */
    public interface SearchClickListener {
        void onClick(View view);
    }

    /**
     * 收藏图片点击时的接口回调
     */
    public interface CollectClickListener {
        void onClick(View view);
    }
    /**
     * 编辑图片点击时的接口回调
     */
    public interface EditClickListener {
        void onClick(View view);
    }

    public void setSearchClickListener(SearchClickListener listener) {
        this.searchClickListener = listener;
    }

    public void setCollectClickListener(CollectClickListener listener) {
        this.collectClickListener = listener;
    }
    public void setEditClickListener(EditClickListener listener) {
        this.editClickListener = listener;
    }
}
