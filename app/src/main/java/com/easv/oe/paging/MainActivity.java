package com.easv.oe.paging;

import android.app.ListActivity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.easv.oe.paging.DALC.BESyllabus;
import com.easv.oe.paging.DALC.SyllabusRepository;

import java.util.List;

public class MainActivity extends ListActivity  implements AbsListView.OnScrollListener {

    String TAG = "SYLLA";


    PagingAdapter m_adapter;
    SyllabusRepository m_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        m_adapter = new PagingAdapter(this);
        m_data = new SyllabusRepository();
        setListAdapter(m_adapter);
        getListView().setOnScrollListener(this);
    }

    public void onScroll(AbsListView view,
                         int firstVisible, int visibleCount, int totalCount) {

        //TODO: Invstegate if this is correct.
        boolean loadMore = /* maybe add a padding */
                firstVisible + visibleCount >= totalCount;

        //Log.d(TAG, "onScroll " + firstVisible + " " + visibleCount + " " + totalCount);
        if(loadMore) {
            //TODO: Need some logic to calculate the page to fetch from the serviec.

            new LoadDataTask().execute(1);
        }
    }



    public void onScrollStateChanged(AbsListView v, int s) { }

    class LoadDataTask extends AsyncTask<Integer, Void, List<BESyllabus>> {

        @Override
        protected List<BESyllabus> doInBackground(Integer... page) {
            return m_data.getPage(page[0]);
        }

        @Override
        protected void onPostExecute(List<BESyllabus> beSyllabuses) {
            m_adapter.addAll(beSyllabuses);
        }
    }

    class PagingAdapter extends ArrayAdapter<BESyllabus> {

        public PagingAdapter(Context context) {
            super(context, R.layout.cell);
        }


        public View getView(int pos, View v, ViewGroup p) {
            if (v == null) {
                LayoutInflater li = (LayoutInflater) getContext().getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                v = li.inflate(R.layout.cell, null);
            }

            Log.d(TAG, "View at pos " + pos + " shown");
            BESyllabus s = getItem(pos);
            TextView number = (TextView)v.findViewById(R.id.txtID);
            TextView title =  (TextView)v.findViewById(R.id.txtTitle);

            number.setText("" + s.m_year);
            title.setText(s.m_title);
            return v;
        }
    }
}
