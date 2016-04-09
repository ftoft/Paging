package com.easv.oe.paging.DALC;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by oe on 07/04/16.
 */
public class SyllabusRepository {
    private final String URL = "http://courseplanner-lbilde.rhcloud.com/api/syllabuses";

    private final String TAG = "SYLLA";

    private int m_pageSize = 10;

    ArrayList<BESyllabus> m_syllabuses;

    public SyllabusRepository(){
        m_syllabuses = new ArrayList<>();
    }

    public SyllabusRepository(int pageSize){
        this();
        m_pageSize = pageSize;
    }

    public int pageSize() { return m_pageSize; }

    public ArrayList<BESyllabus> getPage(int idx)
    {
        try {
            String url = URL + "?" + "limit=" + m_pageSize + "&page=" + idx;
            Log.d(TAG, "Calling " + url);
            String result = getContent(url);

            if (result == null)
            {
                Log.d(TAG, "Nothing returned...");
                return null;
            }


            JSONObject jsonMainObject = new JSONObject(result);
            JSONArray array = jsonMainObject.getJSONArray("docs");

            ArrayList<BESyllabus> res = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject d = array.getJSONObject(i);
                int year = d.getInt("year");
                BESyllabus s = new BESyllabus(year, d.getString("title"));
                res.add(s);
            }
            Log.d(TAG, "JOSON objects received " + res.size());
            return res;

        } catch (JSONException e) {
            Log.e(TAG,
                    "There was an error parsing the JSON", e);
        } catch (Exception e)
        {  Log.d(TAG, "General exception in loadAll " + e.getMessage());
        }
        return null;
    }



    /**
     * Get the content of the url as a string. Based on using a scanner.
     * @param urlString - the url must return data typical in either json, xml, csv etc..
     * @return the content as a string. Null is something goes wrong.
     */
    private String getContent(String urlString)
    {
        StringBuilder sb = new StringBuilder();
        try {


            java.net.URL url = new URL(urlString);
            Scanner s = new Scanner(url.openStream());

            while (s.hasNextLine()) {
                String line = s.nextLine();
                sb.append(line);
            }
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return sb.toString();
    }

    private String fromUrlToString(String urlParam)
    {
        BufferedReader reader=null;
        try
        {
            java.net.URL url = new URL(urlParam);
            URLConnection conn = url.openConnection();
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while((line = reader.readLine()) != null)
                sb.append(line + "\n");
            return sb.toString();
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
            Log.d(TAG, "URL malformed", e);
        }
        catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "IO error", e);
        }
        return null;

    }


}

