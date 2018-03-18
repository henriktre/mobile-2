package com.example.henriktre.lab2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class A1Activity extends AppCompatActivity {
    RequestQueue queue      = null;
    int       itemLimit     = 0;
    int       frequency     = 0;
    String    rssUrl;
    JSONObject rss;
    JSONArray itemArray = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a1);

        if (queue == null) {
            queue = Volley.newRequestQueue(this);
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            rssUrl = bundle.getString("rssText");
            itemLimit = bundle.getInt("itemLimit");
            frequency = bundle.getInt("frequency");
        }
        getRssData(rssUrl);

        Timer repeat = new Timer ();
        TimerTask Task = new TimerTask() {
            @Override
            public void run () {
                Looper.prepare();
                getRssData(rssUrl);
                Looper.loop();
            }
        };

        repeat.schedule (Task, 0l, 1000*60*frequency);
    }
    public void getRssData(String url) {

        StringRequest req = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                stringToJson(response);
                updateView();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(req);
    }
    private void stringToJson(String xml) {
        try {
            rss = XML.toJSONObject(xml);
        } catch (JSONException e) {
            Log.e("JSON Exception", e.getMessage());
        }

    }
    private void updateView() {
        ArrayList<String> data = new ArrayList<>();
        ListView listview = findViewById(R.id.listView);
        try {
            itemArray = rss.getJSONObject("rss").getJSONObject("channel").getJSONArray("item");
            for(int i = 0; i < itemLimit; i++) {
                String title = itemArray.getJSONObject(i).getString("title");
                data.add(title);
            }


        } catch (JSONException e) {
            Log.e("json error", e.getMessage());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(A1Activity.this, android.R.layout.simple_list_item_1, data);
        listview.setAdapter(adapter);
        addListenerOnListView();
    }
    private void addListenerOnListView() {
        ListView listView = findViewById(R.id.listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(A1Activity.this, A3Activity.class);
                try {
                    intent.putExtra("url", itemArray.getJSONObject(i).getString("link"));
                } catch(JSONException e) {
                    Log.d("json error", e.getMessage());
                }
                startActivity(intent);
            }
        });
    }
}
