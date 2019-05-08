package com.PlayG.match;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.PlayG.R;
import com.PlayG.adapter.CompletedAdapter;
import com.PlayG.adapter.ViewCompletedList;
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

public class Completed extends Fragment {

    ListView completed_match_list;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "Name";
    public static final String tokenss = "tokens";
    String tokens,names;

    ProgressBar live_list_pro;
    ArrayList<ViewCompletedList> viewCompletedLists;
    CompletedAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_completed, container, false);
        completed_match_list=(ListView)view.findViewById(R.id.completed_match_list);
        live_list_pro=(ProgressBar) view.findViewById(R.id.completed_match_list_pro);

        SharedPreferences prefs = getActivity().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        tokens = prefs.getString(tokenss, null);

        if (tokens != null) {
            names = prefs.getString(Name, null);
            Completed_List();
        }
        viewCompletedLists=new ArrayList<ViewCompletedList>();
        return view;
    }

    private void Completed_List(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLS.URL_COMPLETED,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray userJson = obj.getJSONArray("tournaments");

                            for(int i=0; i<userJson.length(); i++) {

                                JSONObject itemslist = userJson.getJSONObject(i);
                                String id = itemslist.getString("id");
                                String name = itemslist.getString("name");
                                String toornament_date=itemslist.getString("toornament_date");
                                String toornament_time=itemslist.getString("toornament_time");
                                String team = itemslist.getString("team");
                                String registration_fee=itemslist.getString("registration_fee");

                                String mode = itemslist.getString("mode");
                                String type=itemslist.getString("type");
                                String map = itemslist.getString("map");
                                String prize_first=itemslist.getString("prize_first");
                                String per_kill=itemslist.getString("per_kill");

                                ViewCompletedList viewUpcomingList = new ViewCompletedList();
                                viewUpcomingList.setId(id);
                                viewUpcomingList.setName(name);
                                viewUpcomingList.setTournament_date(toornament_date);
                                viewUpcomingList.setToornament_time(toornament_time);
                                viewUpcomingList.setTeam(team);
                                viewUpcomingList.setResgistration(registration_fee);
                                viewUpcomingList.setType(type);
                                viewUpcomingList.setMode(mode);
                                viewUpcomingList.setMap(map);
                                viewUpcomingList.setPrize_first(prize_first);
                                viewUpcomingList.setPer_kill(per_kill);

                                viewCompletedLists.add(viewUpcomingList);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {

                            live_list_pro.setVisibility(View.GONE);
                            adapter = new CompletedAdapter(viewCompletedLists, getActivity());
                            completed_match_list.setAdapter(adapter);
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
