package com.example.androidcrse;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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

public class SecondFragement extends Fragment {

    RecyclerView recyclerView;
    EmployeeAdapter employeeAdapter;

    public SecondFragement() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View view =inflater.inflate(R.layout.fragment_second_fragement, container, false);

        recyclerView = view.findViewById(R.id.recyclerview);
        // recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getdata();

        return view;
    }
    private void getdata(){
        Toast.makeText(getContext(), "method called", Toast.LENGTH_SHORT).show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,"http://jsonplaceholder.typicode.com/users", response -> {
            //idlist = new ArrayList<>();

            Log.i("res", response);

            try {

                recyclerView.setVisibility(View.VISIBLE);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setHasFixedSize(true);
                JSONArray jarr = new JSONArray(response);
                List<Employee> list = new ArrayList<>();

                for(int i = 0; i < jarr.length(); i++){

                    Employee adapter = new Employee();


                    JSONObject jobj = jarr.getJSONObject(i);
                    //idlist.add(jobj.getString("username"));
                    adapter.setEmpid(jobj.getString("id"));
                    adapter.setEmpname(jobj.getString("name"));

                    list.add(adapter);


                }


               /* myadapterclass = new Adapterclass(list,getApplicationContext());
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(myadapterclass);
*/


                employeeAdapter = new EmployeeAdapter(list, getContext());
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(employeeAdapter);

            } catch (JSONException e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                // e.printStackTrace();
            }

        },error -> {
            Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }



    public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.MyViewHolder> {

        List<Employee> EmployeeList;

        Context context;


        public EmployeeAdapter(List<Employee> productList, Context context) {
            this.EmployeeList = productList;
            this.context = context;

        }


        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View itemView;

            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_data, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

            final Employee employee = EmployeeList.get(position);

            holder.id.setText(employee.getEmpid());
            holder.name.setText(employee.getEmpname());


        }

        @Override
        public int getItemCount() {

            return EmployeeList.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }


        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView id,name;
            Bundle bundle;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);



                id = itemView.findViewById(R.id.idTv);
                name = itemView.findViewById(R.id.nameTv);
                bundle = new Bundle();

                itemView.setOnClickListener(view -> {

                    String EMPID = EmployeeList.get(getAdapterPosition()).getEmpid();
                    String EMPNAME = EmployeeList.get(getAdapterPosition()).getEmpname();

                    Intent intent = new Intent(context,Datapass.class);
                    bundle.putString("empid", EMPID);
                    bundle.putString("empname", EMPNAME);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                });

            }
        }
    }
}