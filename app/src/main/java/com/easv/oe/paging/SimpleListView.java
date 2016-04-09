package com.easv.oe.paging;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by oe on 07/04/16.
 */
public class SimpleListView extends ListActivity implements AbsListView.OnScrollListener {

    Aleph0 adapter = new Aleph0();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(adapter);
        getListView().setOnScrollListener(this);
    }

    public void onScroll(AbsListView view,
                         int firstVisible, int visibleCount, int totalCount) {

        boolean loadMore = /* maybe add a padding */
                firstVisible + visibleCount >= totalCount;

        if(loadMore) {
            adapter.count += visibleCount; // or any other amount
            adapter.notifyDataSetChanged();
        }
    }

    public void onScrollStateChanged(AbsListView v, int s) { }

    class Aleph0 extends BaseAdapter {
        int count = 10; /* starting amount */

        public int getCount() { return count; }
        public Object getItem(int pos) { return pos; }
        public long getItemId(int pos) { return pos; }

        public View getView(int pos, View v, ViewGroup p) {
            TextView view = new TextView(SimpleListView.this);
            view.setText("entry " + pos);
            return view;
        }
    }
}