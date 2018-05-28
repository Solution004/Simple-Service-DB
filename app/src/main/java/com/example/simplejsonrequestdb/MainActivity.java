package com.example.simplejsonrequestdb;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wingnity.jsonparsingtutorial.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private ArrayList<Actors> actorsList;
    private ActorUpdateAdapter adapter;
    private Context mContext;
    private RecyclerView.LayoutManager mLayoutManager;
    private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;
        actorsList = new ArrayList<Actors>();
        databaseHandler = new DatabaseHandler(mContext);
        new JSONAsyncTask().execute(new String[]{"email", "limit"});

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        mLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new ActorUpdateAdapter(getApplicationContext(), actorsList);
        recyclerView.setAdapter(adapter);
    }


    class JSONAsyncTask extends AsyncTask<String, String, String> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("Loading, please wait");
            dialog.setTitle("Connecting server");
            dialog.show();
            dialog.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... urls) {
            ServiceHandler sh = new ServiceHandler();
            List<NameValuePair> param = new ArrayList<NameValuePair>();
            param.add(new BasicNameValuePair("user_email", urls[0]));
            param.add(new BasicNameValuePair("limit", urls[1]));

            // gettting jsonstr from url
            String jsonstr = sh.makeServiceCall("http://microblogging.wingnity.com/JSONParsingTutorial/jsonActors",
                    ServiceHandler.POST, param);
            return jsonstr;
        }

        protected void onPostExecute(String result) {
            dialog.cancel();
            if (result != null && !result.equals("null")) {
                try {
                    JSONObject jsono = new JSONObject(result);
                    JSONArray jarray = jsono.getJSONArray("actors");
                    actorsList = (ArrayList<Actors>) databaseHandler.getDBList(actorsList);
                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);
                        Actors actor = new Actors();
                        actor.setName(object.getString("name"));
                        actor.setUserId(String.valueOf(i));
                        actor.setDescription(object.getString("description"));
                        actor.setDob(object.getString("dob"));
                        actor.setCountry(object.getString("country"));
                        actor.setHeight(object.getString("height"));
                        actor.setSpouse(object.getString("spouse"));
                        actor.setChildren(object.getString("children"));
                        actor.setImage(object.getString("image"));
//                        actorsList.add(actor);
                        if (actorsList.size() == 0) {
                            databaseHandler.insertData(actor,true);
                        }else{
                            databaseHandler.insertData(actor,false);
                        }
                    }
                    actorsList = (ArrayList<Actors>) databaseHandler.getDBList(actorsList);
                } catch (JSONException e) {
                }
            }
            adapter.notifyDataSetChanged();
        }
    }
}