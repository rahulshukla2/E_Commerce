package com.example.e_comm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ProductListAdapterSeller extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Product> productsList;
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

    public ProductListAdapterSeller(Context context, int layout, ArrayList<Product> productsList) {
        this.context = context;
        this.layout = layout;
        this.productsList = productsList;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView txtTitle, txtPrice, txtQuantity;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;

        ViewHolder holder = new ViewHolder();

        if(row==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            row = inflater.inflate(layout, null);

            holder.txtTitle = row.findViewById(R.id.txt_title1);
            holder.txtPrice = row.findViewById(R.id.txtView1);
            holder.txtQuantity = row.findViewById(R.id.txt_price1);
           // holder.txtTitle = row.findViewById(R.id.txt_title);
            holder.imageView = row.findViewById(R.id.imageView3);
            row.setTag(holder);

        }
        else {
            holder = (ViewHolder) row.getTag();
        }
        Product product = productsList.get(position);
        holder.txtTitle.setText(product.getTitle());
        holder.txtPrice.setText(product.getPrice());
        holder.txtQuantity.setText(product.getQuantity());
         byte [] productImage = product.getImage();
         Bitmap bitmap = BitmapFactory.decodeByteArray(productImage, 0,productImage.length);
         holder.imageView.setImageBitmap(bitmap);

        return row;
    }
}
