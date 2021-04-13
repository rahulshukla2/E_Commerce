package com.example.e_comm;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.e_comm.ui.home.HomeViewModel;

import java.io.Serializable;
import java.util.ArrayList;

public class CustomerSearchFragment extends Fragment {

    private HomeViewModel homeViewModel;


    GridView gridView;
    ArrayList<Product> list;
    ProductListAdapterSeller adapter = null;
    DatabaseHelper db;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseHelper(getActivity());
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        //container.removeAllViews();
        if (container != null) {
            container.removeAllViews();
        }

        View root = inflater.inflate(R.layout.fragment_customer_search, container, false);
        //final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });


        return root;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gridView = view.findViewById(R.id.search_product_grid1);
        list = new ArrayList<>();
        adapter = new ProductListAdapterSeller(getActivity(), R.layout.product_items, list);
        gridView.setAdapter(adapter);


        //get data from sqlite

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                ViewProductFragmentCustomer obj = new ViewProductFragmentCustomer();
                Bundle args = new Bundle();
                args.putSerializable("product",(Serializable) list.get(position));
                obj.setArguments(args);

                getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, obj).addToBackStack("Details").commit();

            }

            });




        Cursor cursor = db.getProductBySearch("SELECT * FROM PRODUCT WHERE TITLE = ?  OR CATEGORY = ?", getArguments().getString("query"));
        list.clear();
        if(cursor.getCount()>0) {


            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String price = cursor.getString(2);
                String category = cursor.getString(3);
                String quantity = cursor.getString(4);
                String desc = cursor.getString(5);
                byte[] image = cursor.getBlob(6);

                list.add(new Product(id, title, price, category, quantity, desc, image));

                adapter.notifyDataSetChanged();


            }
        }else {

            Toast.makeText(getActivity(),"Sorry, No result found!!", Toast.LENGTH_LONG).show();
            getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new CustomerHomeFragment()).addToBackStack("Details").commit();
        }
    }
}