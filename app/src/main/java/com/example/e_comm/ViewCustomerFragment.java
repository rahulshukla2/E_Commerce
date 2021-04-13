package com.example.e_comm;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.e_comm.ui.mycart.MyCartViewModel;

import java.io.Serializable;
import java.util.ArrayList;

public class ViewCustomerFragment extends Fragment {

    private MyCartViewModel myCartViewModel;

    GridView gridView;
    ArrayList<User> list;
    AdminUserListAdapter adapter = null;
    DatabaseHelper db;
    SessionManagement sessionManagement;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseHelper(getActivity());
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myCartViewModel =
                new ViewModelProvider(this).get(MyCartViewModel.class);

        if (container != null) {
            container.removeAllViews();
        }


        View root = inflater.inflate(R.layout.fragment_view_customer, container, false);
        myCartViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gridView = view.findViewById(R.id.grid_customer);
        list = new ArrayList<>();
        adapter = new AdminUserListAdapter(getActivity(), R.layout.admin_users_items, list,"customer");
        gridView.setAdapter(adapter);

        sessionManagement = new SessionManagement(getActivity());

        //get data from sqlite

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                ViewProductFragmentSeller obj = new ViewProductFragmentSeller();
                Bundle args = new Bundle();
                args.putSerializable("product",(Serializable) list.get(position));
                obj.setArguments(args);

                getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, obj).addToBackStack("Details").commit();

            }

        });





        //Cursor cursor = db.getProduct("SELECT * FROM PRODUCT");
        //Cursor cursor = db.getProductBySeller("SELECT * FROM PRODUCT WHERE SELLERID = ?",Integer.toString(sessionManagement.getSession()));
        //Cursor cursor = db.getCartData(Integer.toString(sessionManagement.getSession()));
        Cursor cursor = db.getCustomers();


        list.clear();
       // list.add(new User(500,"2345345650","customer","Ram"));
        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String mobile_no = cursor.getString(3);


            list.add(new User(id,mobile_no,"customer",name));

            adapter.notifyDataSetChanged();
        }
    }
}