package com.easv.oe.paging;

import android.app.ListActivity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easv.oe.paging.DALC.BESyllabus;
import com.easv.oe.paging.DALC.SyllabusCollection;

public class MainActivity extends ListActivity  implements AbsListView.OnScrollListener {

    String TAG = "SYLLA";


    PagingAdapter m_adapter;
    SyllabusCollection m_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        m_adapter = new PagingAdapter(this);
        m_data = new SyllabusCollection();
        setListAdapter(m_adapter);
        getListView().setOnScrollListener(this);
    }

    public void onScroll(AbsListView view,
                         int firstVisible, int visibleCount, int totalCount) {

        boolean loadMore = /* maybe add a padding */
                firstVisible + visibleCount >= totalCount;

        Log.d(TAG, "onScroll " + firstVisible + " " + visibleCount + " " + totalCount);
        if(loadMore) {
            m_adapter.count += visibleCount; // or any other amount
            Log.d(TAG, "adapter.count=" + m_adapter.count);
            m_adapter.notifyDataSetChanged();
        }
    }

    public void onScrollStateChanged(AbsListView v, int s) { }

    class PagingAdapter extends BaseAdapter {

        int count = 10; /* starting amount */
        Context m_context;

        public PagingAdapter(Context c)
        {
            m_context = c;
        }

        public int getCount() { return count; }

        public Object getItem(int pos) { return pos; }

        public long getItemId(int pos) { return pos; }

        public View getView(int pos, View v, ViewGroup p) {
            if (v == null) {
                LayoutInflater li = (LayoutInflater) m_context.getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                v = li.inflate(R.layout.cell, null);
            }

            Log.d(TAG, "View at pos " + pos + " shown");
            BESyllabus s = m_data.get(pos);
            TextView number = (TextView)v.findViewById(R.id.txtID);
            TextView title =  (TextView)v.findViewById(R.id.txtTitle);

            number.setText("" + s.m_year);
            title.setText(s.m_title);
            return v;
        }
    }
}
