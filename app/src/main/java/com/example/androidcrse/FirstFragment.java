package com.example.androidcrse;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class FirstFragment extends Fragment {

    public FirstFragment() { }
    Spinner spinner;
    List<String> idlist;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.fragment_first, container, false);
        spinner = view.findViewById(R.id.spinnerid);
        getdata();
        return view;
    }

    private void getdata() {
        Toast.makeText(getContext(), "method called", Toast.LENGTH_SHORT).show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,"http://jsonplaceholder.typicode.com/users", response -> {
            idlist = new ArrayList<>();

            Log.i("res", response);

            try {
                JSONArray jarr = new JSONArray(response);

                for(int i = 0; i < jarr.length(); i++){


                    JSONObject jobj = jarr.getJSONObject(i);
                    idlist.add(jobj.getString("username"));

                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item,idlist);
                spinner.setAdapter(adapter);



            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        },error -> {
            Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);


    }
}