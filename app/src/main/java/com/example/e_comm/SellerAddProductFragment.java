package com.example.e_comm;



import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import java.io.FileNotFoundException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;


public class SellerAddProductFragment  extends Fragment {

    EditText txt_title, txt_price, txt_category,txt_desc, txt_quantity;
    Button btn_choose, btn_addProduct;
    ImageView img;
    DatabaseHelper db;
    final int REQUEST_CODE_GALLERY = 999;
    private static final int SELECT_PHOTO = 100;
    public static final String TAG="SellerHomeAct";
    SessionManagement sessionManagement;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseHelper(getActivity());
    }



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

       // container.removeAllViews();
        if (container != null) {
            container.removeAllViews();
        }

        View root = inflater.inflate(R.layout.fragment_add_product_seller, container, false);
        //final TextView textView = root.findViewById(R.id.text_home);

        return root;
    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txt_category = view.findViewById(R.id.txt_category);
        txt_desc = view.findViewById(R.id.txt_desc);
        txt_price = view.findViewById(R.id.txt_price);
        txt_quantity = view.findViewById(R.id.txt_quantity);
        txt_title = view.findViewById(R.id.txt_title);
        btn_addProduct = view.findViewById(R.id.btn_editProduct);;
        btn_choose = view.findViewById(R.id.btn_choose);
        img = view.findViewById(R.id.imageView2);
        img.setImageResource(R.drawable.ic_baseline_mood_24);

        sessionManagement = new SessionManagement(getActivity());

        btn_choose.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                Log.d(TAG,"Came in onRequest");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
        });

        btn_addProduct.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {

                long status=0;
                //Snackbar.make(v,"Product has been added successfully", Snackbar.LENGTH_SHORT).setAction("Action",null).show();
                try{
                       status= db.addProduct(
                                txt_title.getText().toString().trim(),
                                txt_price.getText().toString().trim(),
                                txt_category.getText().toString().trim(),
                                txt_quantity.getText().toString().trim(),
                                txt_desc.getText().toString().trim(),
                                imageViewToByte(img),
                               sessionManagement.getSession()

                        );
                       if(status==-1)
                       {
                           Snackbar.make(v,"Product has not been added successfully", Snackbar.LENGTH_LONG).setAction("Action",null).show();
                           return;
                       }
                    Snackbar.make(v,"Product has been added successfully", Snackbar.LENGTH_LONG).setAction("Action",null).show();
                    txt_title.setText("");
                    txt_price.setText("");
                    txt_category.setText("");
                    txt_quantity.setText("");
                    txt_desc.setText("");
                    img.setImageResource(R.drawable.ic_baseline_mood_24);



                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        });


    }

    private byte[] imageViewToByte(ImageView img) {
        Bitmap bitmap = ((BitmapDrawable)img.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte [] byteArray = stream.toByteArray();
        return byteArray;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


       // if(requestCode == SELECT_PHOTO){
        //    if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                Log.d(TAG,"Came in onRequest");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);

          /*  }
            else {
                Toast.makeText(getActivity(),"You don't have permission to access EXTERNAL STORAGE",Toast.LENGTH_LONG);
            }
            return;
        }*/
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            Log.d(TAG,"Came in onActivity below uri");
            try{
                InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                img.setImageBitmap(bitmap);
                Log.d(TAG,"Came in onActivity in try");

            } catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
        else if(data == null)
        {
            Toast.makeText(getActivity(), "here the data is null", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
/*
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
            else {
                Toast.makeText(getActivity(),"You don't have permission to access EXTERNAL STORAGE",Toast.LENGTH_LONG);
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();

            try{
                InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                img.setImageBitmap(bitmap);

            } catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }




 */

}
