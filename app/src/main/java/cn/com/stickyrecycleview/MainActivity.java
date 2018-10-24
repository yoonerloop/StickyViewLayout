package cn.com.stickyrecycleview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * recycleview吸顶效果
 */
public class MainActivity extends AppCompatActivity {

    private ArrayList<DataBean> mDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recycleview = findViewById(R.id.recycleview);
        final TextView stickyText = findViewById(R.id.sticky_text);
        initData();
        recycleview.setLayoutManager(new LinearLayoutManager(this));
        recycleview.setAdapter(new MyAdapter(this, mDataList));
        recycleview.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //找到RecyclerView的item中，和RecyclerView的getTop 向下相距5个像素的那个item，
                //该方法是根据坐标获取item的View，坐标点是以RecyclerView控件作为坐标轴，并不是以屏幕左上角作为坐标原点。
                View stickyInfoView = recyclerView.findChildViewUnder(stickyText.getMeasuredWidth() / 2, 5);
                if (stickyInfoView != null && stickyInfoView.getContentDescription() != null) {
                    //设置吸顶栏内容
                    stickyText.setText(String.valueOf(stickyInfoView.getContentDescription()));
                }
                //找到固定在顶部的View的下面一个item的View
                View transInfoView = recyclerView.findChildViewUnder(stickyText.getMeasuredWidth() / 2, stickyText.getMeasuredHeight() + 1);
                if (transInfoView != null && transInfoView.getTag() != null) {
                    //获取该View的tag
                    int transViewStatus = (int) transInfoView.getTag();
                    int dealtY = transInfoView.getTop() - stickyText.getMeasuredHeight();
                    if (transViewStatus == MyAdapter.HAS_STICKY_VIEW) {
                        if (transInfoView.getTop() > 0) {
                            //最上面的itemView没滑出屏幕，给顶部的View设置，
                            //注意setTranslationY移动的ViewGroup，而scrollTo()/scrollBy()移动的是View的内容，如文字、图片等
                            stickyText.setTranslationY(dealtY);
                        } else {
                            //最上面的itemView滑出屏幕，顶部的复位
                            stickyText.setTranslationY(0);
                        }
                    } else if (transViewStatus == MyAdapter.NONE_STICKY_VIEW) {
                        //如果是没有分组，即无吸顶的item，则设置顶部的View移动为0，保持原位置
                        stickyText.setTranslationY(0);
                    }
                }
            }
        });

    }

    /**
     * 假的测试数据
     */
    private void initData() {
        mDataList = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            DataBean dataBean = new DataBean();
            if (i < 3) {
                dataBean.data = "5月4日";
            } else if (i < 8) {
                dataBean.data = "5月5日";
            } else if (i < 15) {
                dataBean.data = "5月6日";
            } else if (i < 20) {
                dataBean.data = "5月7日";
            } else if (i < 30) {
                dataBean.data = "5月8日";
            } else {
                dataBean.data = "5月9日";
            }
            dataBean.des = "我是内容" + i;
            mDataList.add(dataBean);
        }
    }
}
