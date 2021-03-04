package com.example.e_comm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_comm.ui.home.HomeFragment;

import java.io.Serializable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewProductFragemetSeller#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewProductFragemetSeller extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;






    TextView txt_title1, txt_price1, txt_category1,txt_desc1, txt_quantity1;
    Button btn_edit, btn_delete;
    ImageView img;
    AlertDialog.Builder builder;
    DatabaseHelper db;

    public ViewProductFragemetSeller() {
        // Required empty public constructor
    }


    public static ViewProductFragemetSeller newInstance(String param1, String param2) {
        ViewProductFragemetSeller fragment = new ViewProductFragemetSeller();
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
        return inflater.inflate(R.layout.fragment_view_product_fragemet_seller, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

       // txt_category = view.findViewById(R.id.txt_category);
        txt_desc1 = view.findViewById(R.id.txt_desc1);
        txt_price1 = view.findViewById(R.id.txt_price1);
        txt_quantity1 = view.findViewById(R.id.txt_quantity1);
        txt_title1 = view.findViewById(R.id.txt_title1);
        btn_delete = view.findViewById(R.id.btn_delete);
        btn_edit = view.findViewById(R.id.btn_edit);
        img = view.findViewById(R.id.imageView3);
        builder = new AlertDialog.Builder(getContext());

        Product product = (Product)getArguments().getSerializable("product");
        Bitmap bitmap = BitmapFactory.decodeByteArray(product.getImage(),0,product.getImage().length);
        img.setImageBitmap(bitmap);
        txt_title1.setText(product.getTitle());
        txt_price1.setText(this.getContext().getResources().getString(R.string.Rs)+product.getPrice());
        txt_quantity1.setText(product.getQuantity());
        txt_desc1.setText(product.getDesc());

        btn_delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                builder.setMessage("Do you want to delete this product.")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id){
                               // finish();

                                if(db.deleteProduct(Integer.toString(product.getId()).trim())){
                                    Toast.makeText(getContext(),"Product is deleted successfully",
                                            Toast.LENGTH_SHORT).show();
                                    getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment()).addToBackStack(null).commit();
                                } else {
                                    Toast.makeText(getContext(),"Product can not be deleted",
                                            Toast.LENGTH_SHORT).show();
                                }

                                dialog.dismiss();
                                //Toast.makeText(getContext(),"you choose yes action for alertbox",
                                        //Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();


                                Toast.makeText(getContext(),"you choose No action for alertbox",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle("Delete Product");
                alert.show();

            }
        });

        btn_edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                SellerEditProductFragment obj = new SellerEditProductFragment();
                Bundle args = new Bundle();
                args.putSerializable("product1",product);
                obj.setArguments(args);

                getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, obj).addToBackStack("Details").commit();


            }
        });



        super.onViewCreated(view, savedInstanceState);
    }
}