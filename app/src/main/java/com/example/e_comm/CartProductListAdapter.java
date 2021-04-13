package com.example.e_comm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class CartProductListAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Product> productsList;
    private String frag;
    DatabaseHelper db;
    @Override
    public int getCount() {
        return productsList.size();
    }

    @Override
    public Object getItem(int position) {
        return productsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public CartProductListAdapter(Context context, int layout, ArrayList<Product> productsList, String frag) {
        this.context = context;
        this.layout = layout;
        this.productsList = productsList;
        this.frag=frag;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView txtTitle, txtPrice;
        Button btn_remove;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;

        ViewHolder holder = new ViewHolder();
        Product product = productsList.get(position);

        SessionManagement sessionManagement = new SessionManagement(context);
        String type1 = sessionManagement.getType();
        if(row==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            row = inflater.inflate(layout, null);

            holder.txtTitle = row.findViewById(R.id.txt_item_title);
            holder.txtPrice = row.findViewById(R.id.txt_item_price);
            holder.btn_remove = row.findViewById(R.id.btn_remove_from_cart2);
            holder.imageView = row.findViewById(R.id.productImage1);

            holder.btn_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    db = new DatabaseHelper(context);
                    boolean status = db.deleteFromCart(Integer.toString(product.getId()),Integer.toString(sessionManagement.getSession()));
                    if(status){
                        Snackbar.make(v,"Product has been removed successfully from the cart", Snackbar.LENGTH_LONG).setAction("Action",null).show();
                    }
                    else{

                        Snackbar.make(v,"Product could not be removed from the cart", Snackbar.LENGTH_LONG).setAction("Action",null).show();
                    }


                }
            });

            if(frag.equals("order")){
                holder.btn_remove.setVisibility(View.INVISIBLE);
            }
            row.setTag(holder);

        }
        else {
            holder = (ViewHolder) row.getTag();
        }

        holder.txtTitle.setText(product.getTitle());
        holder.txtPrice.setText(context.getResources().getString(R.string.Rs)+product.getPrice());


         byte [] productImage = product.getImage();
         Bitmap bitmap = BitmapFactory.decodeByteArray(productImage, 0,productImage.length);
         holder.imageView.setImageBitmap(bitmap);

        return row;
    }
}
