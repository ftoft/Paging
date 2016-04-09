package com.easv.oe.paging;

/* Uses AsyncTask to create a task away from the main UI thread. This task takes a
    URL string and uses it to create an HttpUrlConnection. Once the connection
    has been established, the AsyncTask downloads the contents of the webpage as
    an InputStream. Finally, the InputStream is converted into a string, which is
    displayed in the UI by the AsyncTask's onPostExecute method.*/

import android.os.AsyncTask;
import android.util.Log;


import com.easv.oe.paging.DALC.BESyllabus;
import com.easv.oe.paging.DALC.SyllabusCollection;
import com.easv.oe.paging.DALC.SyllabusRepository;

import java.util.ArrayList;


public class InitializeTask extends AsyncTask<
        Integer,
        Void,
        ArrayList<BESyllabus>> // output of doInBackground
{

    String TAG = "SYLLA";

    SyllabusCollection m_context;

    public InitializeTask(SyllabusCollection context)
    {
        m_context = context;
    }

    @Override
    protected ArrayList<BESyllabus> doInBackground(Integer... p) {



        Log.d(TAG, "in doInBackGround getPage " + p[0]);
        return m_context.m_repo.getPage(p[0]);

    }
    // onPostExecute displays the results of the AsyncTask.doInBackground().
    // this method is invoked by the GUI thread
    @Override
    protected void onPostExecute(final ArrayList<BESyllabus> s) {


    }

}
