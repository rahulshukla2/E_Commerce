package com.example.e_comm.ui.mycart;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.e_comm.CartProductListAdapter;
import com.example.e_comm.DatabaseHelper;
import com.example.e_comm.Product;
import com.example.e_comm.ProductListAdapterSeller;
import com.example.e_comm.R;
import com.example.e_comm.SessionManagement;
import com.example.e_comm.ViewProductFragmentSeller;

import java.io.Serializable;
import java.util.ArrayList;

public class MyCartFragment extends Fragment {

    private MyCartViewModel myCartViewModel;

    GridView gridView;
    ArrayList<Product> list;
    CartProductListAdapter adapter = null;
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


        View root = inflater.inflate(R.layout.fragment_my_cart, container, false);
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

        gridView = view.findViewById(R.id.product_grid_cart);
        list = new ArrayList<>();
        adapter = new CartProductListAdapter(getActivity(), R.layout.cart_product_items, list,"cart");
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
        Cursor cursor = db.getCartData(Integer.toString(sessionManagement.getSession()));


        list.clear();
        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String price = cursor.getString(2);
            String category = cursor.getString(3);
            String quantity = cursor.getString(4);
            String desc = cursor.getString(5);
            byte[] image = cursor.getBlob(6);

            list.add(new Product(id,title,price,category,quantity,desc,image));

            adapter.notifyDataSetChanged();



        }
    }
}