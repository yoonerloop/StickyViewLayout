package cn.com.stickyrecycleview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * date：2018/5/18 on 14:15
 */

class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    //无吸顶
    public static final int NONE_STICKY_VIEW = 0;
    //有吸顶
    public static final int HAS_STICKY_VIEW = 1;
    //第一个
    public static final int FIRST_STICKY_VIEW = 2;

    private Context ctx;
    private ArrayList<DataBean> mDataList;

    public MyAdapter(Context ctx, ArrayList<DataBean> list) {
        this.ctx = ctx;
        this.mDataList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_list, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DataBean dataBean = mDataList.get(position);
        if (position == 0) {
            //第0个展示吸顶栏，设置tag:FIRST_STICKY_VIEW
            holder.stickyText.setVisibility(View.VISIBLE);
            holder.stickyText.setText(dataBean.data);
            holder.itemView.setTag(FIRST_STICKY_VIEW);
        } else {
            if (!TextUtils.equals(dataBean.data, mDataList.get(position - 1).data)) {
                //和上一个分组的依据不同，展示吸顶栏，设置tag:HAS_STICKY_VIEW
                holder.stickyText.setVisibility(View.VISIBLE);
                holder.stickyText.setText(dataBean.data);
                holder.itemView.setTag(HAS_STICKY_VIEW);
            } else {
                //其他的，隐藏分组栏
                holder.stickyText.setVisibility(View.GONE);
                holder.itemView.setTag(NONE_STICKY_VIEW);
            }
        }
        holder.textView.setText(dataBean.des);
        // ContentDescription 用来记录并获取要吸顶展示的信息
        holder.itemView.setContentDescription(dataBean.data);
    }


    @Override
    public int getItemCount() {
        return mDataList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public TextView stickyText;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_text);
            stickyText = itemView.findViewById(R.id.sticky_text);
        }
    }
}