package com.PlayG.menuActivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.PlayG.R;
import com.PlayG.activity.Dashboard;
import com.PlayG.adapter.EventAdapter;
import com.PlayG.adapter.EventList;
import com.PlayG.api.URLS;
import com.PlayG.model.VolleySingleton;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Events extends AppCompatActivity {

    ImageView back_players;
    ListView events_list;

    ProgressBar event_list_pro;
    ArrayList<EventList> eventLists;
    EventAdapter adapter;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "Name";
    public static final String tokenss = "tokens";
    ArrayList<String> ids=new ArrayList<String>();
    String tokens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        back_players=(ImageView)findViewById(R.id.back_players);
        back_players.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent top_player=new Intent(Events.this, Dashboard.class);
                startActivity(top_player);
                finish();
            }
        });
        events_list=(ListView)findViewById(R.id.events_list);
        event_list_pro=(ProgressBar)findViewById(R.id.event_list_pro);
        eventLists=new ArrayList<EventList>();

        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        tokens = prefs.getString(tokenss, null);

        if (tokens != null) {
            Events_List();
        }
    }

    private void Events_List(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLS.URL_EVENTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray userJson = obj.getJSONArray("Events");

                            for(int i=0; i<userJson.length(); i++) {

                                JSONObject itemslist = userJson.getJSONObject(i);
                                String id = itemslist.getString("id");
                                ids.add(id);
                                String title = itemslist.getString("title");
                                String image = itemslist.getString("image");
                                String event_date=itemslist.getString("event_date");
                                String type=itemslist.getString("type");
                                String event_time = itemslist.getString("event_time");
                                String awards=itemslist.getString("awards");

                                String description = itemslist.getString("description");
                                String location=itemslist.getString("location");
                                String entry_fee = itemslist.getString("entry_fee");

                                EventList eventList = new EventList();
                                eventList.setId(id);
                                eventList.setTitle(title);
                                eventList.setPhoto(image);
                                eventList.setDate(event_date);
                                eventList.setTime(event_time);
                                eventList.setAwards(awards);
                                eventList.setDescription(description);
                                eventList.setType(type);
                                eventList.setLocation(location);
                                eventList.setFee(entry_fee);

                                eventLists.add(eventList);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {

                            event_list_pro.setVisibility(View.GONE);
                            adapter = new EventAdapter(eventLists, Events.this);
                            events_list.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                        }catch (NullPointerException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("token",tokens);
                return params;
            }
        };
        VolleySingleton.getInstance(Events.this).addToRequestQueue(stringRequest);
    }
    @Override
    public void onBackPressed() {
        backButtonHandler();
        return;
    }

    public void backButtonHandler() {
        Intent forgot=new Intent(Events.this, Dashboard.class);
        startActivity(forgot);
        finish();
    }
}
