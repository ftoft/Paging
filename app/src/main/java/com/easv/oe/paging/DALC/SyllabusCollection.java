package com.easv.oe.paging.DALC;

import android.os.AsyncTask;
import android.util.Log;

import com.easv.oe.paging.InitializeTask;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by ole on 08/04/16.
 */
public class SyllabusCollection {

    String TAG = "SYLLA";

    public SyllabusRepository m_repo;

    ArrayList<BESyllabus> m_cache;

    public SyllabusCollection()
    {
        m_repo = new SyllabusRepository();
        m_cache = new ArrayList<>();
    }

    public BESyllabus get(int idx)
    {

        if (idx >= m_cache.size())
        {
            int p = (idx / m_repo.pageSize()) + 1;
            InitializeTask task = new InitializeTask(this);
            Log.d(TAG, "Loading page " + p);
            task.execute(p);

            ArrayList<BESyllabus> s = null;
            try {
                s = task.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            m_cache.addAll(s);
            Log.d(TAG, "Loading completed...");
        }
        return m_cache.get(idx);
    }
}
