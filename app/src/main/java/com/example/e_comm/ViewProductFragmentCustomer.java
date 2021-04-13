package com.example.e_comm;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewProductFragmentCustomer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewProductFragmentCustomer extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;






    TextView txt_title1, txt_price1, txt_category1,txt_desc1, txt_quantity1;
    Button btn_addToCart, btn_buyNow;
    ImageView img;

    DatabaseHelper db;
    SessionManagement sessionManagement;

    public ViewProductFragmentCustomer() {
        // Required empty public constructor
    }


    public static ViewProductFragmentCustomer newInstance(String param1, String param2) {
        ViewProductFragmentCustomer fragment = new ViewProductFragmentCustomer();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseHelper(getActivity());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_product_customer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

       // txt_category = view.findViewById(R.id.txt_category);
        txt_desc1 = view.findViewById(R.id.txt_desc1);
        txt_price1 = view.findViewById(R.id.txt_price1);
        txt_quantity1 = view.findViewById(R.id.txt_quantity1);
        txt_title1 = view.findViewById(R.id.txt_user_id1);
        btn_addToCart = view.findViewById(R.id.btn_addToCart);
        btn_buyNow = view.findViewById(R.id.btn_buyNow);
        img = view.findViewById(R.id.imageView3);

        sessionManagement = new SessionManagement(getActivity());



        Product product = (Product)getArguments().getSerializable("product");
        Bitmap bitmap = BitmapFactory.decodeByteArray(product.getImage(),0,product.getImage().length);
        img.setImageBitmap(bitmap);
        txt_title1.setText(product.getTitle());
        txt_price1.setText(this.getContext().getResources().getString(R.string.Rs)+product.getPrice());
        txt_quantity1.setText(product.getQuantity());
        txt_desc1.setText(product.getDesc());

        btn_addToCart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                try {


                    long r = db.addIntoCart(product.getId(), sessionManagement.getSession());
                    if (r == -1) {
                        Snackbar.make(v,"Product has not been added to the cart successfully", Snackbar.LENGTH_LONG).setAction("Action",null).show();
                        return;
                    }
                    Snackbar.make(v,"Product has been added successfully", Snackbar.LENGTH_LONG).setAction("Action",null).show();

                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

        btn_buyNow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                try {


                    long r = db.placeOrder(product.getId(), sessionManagement.getSession());
                    if (r == -1) {
                        Snackbar.make(v,"Sorry!! Order not placed", Snackbar.LENGTH_LONG).setAction("Action",null).show();
                        return;
                    }
                    Snackbar.make(v,"Order has been placed successfully", Snackbar.LENGTH_LONG).setAction("Action",null).show();

                } catch(Exception e){
                    e.printStackTrace();
                }



            }
        });



        super.onViewCreated(view, savedInstanceState);
    }
}