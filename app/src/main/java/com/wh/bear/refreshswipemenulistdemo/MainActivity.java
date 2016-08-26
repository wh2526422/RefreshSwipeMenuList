package com.wh.bear.refreshswipemenulistdemo;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RefreshSwipeMenuListView refresh_list;
    String[] data = {"上海", "北京", "南京", "杭州", "南昌", "武汉", "徐州", "郑州", "云南", "西藏", "开封", "广西", "山西"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refresh_list = (RefreshSwipeMenuListView) findViewById(R.id.refresh_list);
        CityAdapter adapter = new CityAdapter(Arrays.asList(data), this);
        refresh_list.setAdapter(adapter);
    }

    class CityAdapter extends BaseAdapter {
        LinkedList<String> data;
        Context context;

        public CityAdapter(List<String> data, Context context) {
            this.data = new LinkedList<>();
            for (String s : data) {
                this.data.add(s);
            }
            this.context = context;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int i) {
            return data.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int position, View contentView, ViewGroup viewGroup) {
            ViewHolder holder;
            if (contentView == null) {
                contentView = LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
                TextView contry = (TextView) contentView.findViewById(R.id.tv_city);
                TextView setting = (TextView) contentView.findViewById(R.id.tv_top);
                TextView delete = (TextView) contentView.findViewById(R.id.tv_delete);
                holder = new ViewHolder(contry, setting, delete);
                contentView.setTag(holder);
            } else {
                holder = (ViewHolder) contentView.getTag();
            }
            holder.city.setText(data.get(position));
            holder.top.setText("置顶");
            holder.top.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    data.add(0, data.get(position));
                    data.remove(position + 1);
                    notifyDataSetChanged();
                    refresh_list.setSelectItemMenuHide();
                }
            });
            holder.delete.setText("删除");
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    data.remove(position);
                    notifyDataSetChanged();
                    refresh_list.setSelectItemMenuHide();
                }
            });

            return contentView;
        }
    }

}

class ViewHolder {
    TextView city;
    TextView top;
    TextView delete;

    public ViewHolder(TextView city, TextView setting, TextView delete) {
        this.city = city;
        this.top = setting;
        this.delete = delete;
    }
}
