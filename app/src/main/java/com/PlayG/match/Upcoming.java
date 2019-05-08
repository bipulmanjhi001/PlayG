package com.PlayG.match;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.PlayG.R;
import com.PlayG.activity.MatchRegisteration;
import com.PlayG.adapter.UpcomingAdapter;
import com.PlayG.adapter.ViewUpcomingList;
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
import static android.content.Context.MODE_PRIVATE;

public class Upcoming extends Fragment {

    ListView upcoming_list;
    ProgressBar upcoming_list_pro;
    ArrayList<ViewUpcomingList> viewUpcomingLists;
    UpcomingAdapter adapter;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "Name";
    public static final String tokenss = "tokens";
    ArrayList<String> ids=new ArrayList<String>();
    String tokens;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_upcoming, container, false);

        upcoming_list=(ListView)view.findViewById(R.id.upcoming_list);
        upcoming_list_pro=(ProgressBar)view.findViewById(R.id.upcoming_list_pro);
        viewUpcomingLists=new ArrayList<ViewUpcomingList>();

        SharedPreferences prefs = getActivity().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        tokens = prefs.getString(tokenss, null);

        if (tokens != null) {
            Log.d("tokens",tokens);
            Upcoming_List();
        }
        upcoming_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String getid=ids.get(position);
                Intent intentToDetay = new Intent(getActivity(), MatchRegisteration.class);
                intentToDetay.putExtra("id",getid);
                startActivity(intentToDetay);

            }
        });
        return view;
    }

       private void Upcoming_List(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLS.URL_TOURNAMENT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray userJson = obj.getJSONArray("tournaments");

                            for(int i=0; i<userJson.length(); i++) {

                                JSONObject itemslist = userJson.getJSONObject(i);
                                String id = itemslist.getString("id");
                                ids.add(id);
                                String name = itemslist.getString("name");
                                String toornament_date=itemslist.getString("toornament_date");
                                String toornament_time=itemslist.getString("toornament_time");
                                String team = itemslist.getString("team");
                                String registration_fee=itemslist.getString("registration_fee");

                                 String mode = itemslist.getString("mode");
                                 String type=itemslist.getString("type");
                                 String map = itemslist.getString("map");
                                 String prize_first=itemslist.getString("prize_first");

                                ViewUpcomingList viewUpcomingList = new ViewUpcomingList();
                                viewUpcomingList.setId(id);
                                viewUpcomingList.setName(name);
                                viewUpcomingList.setTournament_date(toornament_date);
                                viewUpcomingList.setToornament_time(toornament_time);
                                viewUpcomingList.setTeam(team);
                                viewUpcomingList.setResgistration(registration_fee);
                                viewUpcomingList.setType(type);
                                viewUpcomingList.setMode(mode);
                                viewUpcomingList.setMap(map);

                                viewUpcomingLists.add(viewUpcomingList);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {

                            upcoming_list_pro.setVisibility(View.GONE);
                            adapter = new UpcomingAdapter(viewUpcomingLists, getActivity());
                            upcoming_list.setAdapter(adapter);
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
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }
}
