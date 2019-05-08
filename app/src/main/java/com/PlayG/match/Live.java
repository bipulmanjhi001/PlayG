package com.PlayG.match;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.PlayG.R;
import com.PlayG.adapter.LiveAdapter;
import com.PlayG.adapter.ViewLiveList;
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

public class Live extends Fragment {
    ListView live_list;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "Name";
    public static final String tokenss = "tokens";
    String tokens,names;

    ProgressBar live_list_pro;
    ArrayList<ViewLiveList> viewLiveLists;
    LiveAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_live, container, false);

        live_list=(ListView)view.findViewById(R.id.live_list);
        live_list_pro=(ProgressBar)view.findViewById(R.id.live_list_pro);

        SharedPreferences prefs = getActivity().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        tokens = prefs.getString(tokenss, null);

        if (tokens != null) {
            names = prefs.getString(Name, null);
            Live_List();
        }
        viewLiveLists=new ArrayList<ViewLiveList>();

        live_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boolean isAppInstalled = appInstalledOrNot("com.tencent.ig");

                if(isAppInstalled) {
                    Intent LaunchIntent = getActivity().getPackageManager()
                            .getLaunchIntentForPackage("com.tencent.ig");
                    startActivity(LaunchIntent);
                    Toast.makeText(getActivity(), "Application is already installed.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.tencent.ig"));
                    startActivity(intent);
                    Toast.makeText(getActivity(), "Installed PUBG", Toast.LENGTH_SHORT).show();
                }
            }
            private boolean appInstalledOrNot(String uri) {
                PackageManager pm = getActivity().getPackageManager();
                try {
                    pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
                    return true;
                } catch (PackageManager.NameNotFoundException e) {
                }
                return false;
            }
        });
        return view;
    }

    private void Live_List(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLS.URL_LIVE,
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
                                String last_date = itemslist.getString("last_date");
                                String toornament_date = itemslist.getString("toornament_date");
                                String toornament_time = itemslist.getString("toornament_time");
                                String registration_fee = itemslist.getString("registration_fee");
                                String team = itemslist.getString("team");
                                String mode = itemslist.getString("mode");
                                String type = itemslist.getString("type");
                                String map = itemslist.getString("map");
                                String room_id = itemslist.getString("room_id");
                                String password = itemslist.getString("password");
                                String prize_first=itemslist.getString("prize_first");
                                String per_kill=itemslist.getString("per_kill");

                                ViewLiveList viewLiveList = new ViewLiveList();
                                viewLiveList.setId(id);
                                viewLiveList.setName(name);
                                viewLiveList.setLast_date(last_date);
                                viewLiveList.setToornament_date(toornament_date);
                                viewLiveList.setToornament_time(toornament_time);
                                viewLiveList.setRegistration_fee(registration_fee);
                                viewLiveList.setTeam(team);
                                viewLiveList.setMode(mode);
                                viewLiveList.setType(type);
                                viewLiveList.setMap(map);
                                viewLiveList.setRoom_id(room_id);
                                viewLiveList.setPassword(password);
                                viewLiveList.setF_prize(prize_first);
                                viewLiveList.setPer_kill(per_kill);
                                viewLiveLists.add(viewLiveList);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            live_list_pro.setVisibility(View.GONE);
                            adapter = new LiveAdapter(viewLiveLists, getActivity());
                            live_list.setAdapter(adapter);
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
