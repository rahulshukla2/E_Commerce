package com.example.e_comm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class AdminUserListAdapter  extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<User> userList;
    private String frag;
    DatabaseHelper db;


    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public AdminUserListAdapter(Context context, int layout, ArrayList<User> userList, String frag) {
        this.context = context;
        this.layout = layout;
        this.userList = userList;
        this.frag=frag;
    }

    private class ViewHolder{
        TextView txtId, txtName;
        Button btn_block,btn_unblock;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;

        AdminUserListAdapter.ViewHolder holder = new AdminUserListAdapter.ViewHolder();
        User user = userList.get(position);

        if(row==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            row = inflater.inflate(layout, null);

            holder.txtId = row.findViewById(R.id.txt_user_id1);
            holder.txtName = row.findViewById(R.id.txt_user_name1);
            holder.btn_block = row.findViewById(R.id.btn_block);
            holder.btn_unblock = row.findViewById(R.id.btn_unblock);


            holder.btn_block.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db = new DatabaseHelper(context);

                    if(frag.equals("customer")){
                        boolean status= db.blockCustomer(Integer.toString(user.getId()));
                        if(status){
                            Snackbar.make(v,"Customer has been blocked", Snackbar.LENGTH_LONG).setAction("Action",null).show();
                        }
                        else{

                            Snackbar.make(v,"Customer could not be blocked", Snackbar.LENGTH_LONG).setAction("Action",null).show();
                        }


                    }
                    else if(frag.equals("seller")){
                        boolean status= db.blockSeller(Integer.toString(user.getId()));
                        if(status){
                            Snackbar.make(v,"Seller has been blocked", Snackbar.LENGTH_LONG).setAction("Action",null).show();
                        }
                        else{

                            Snackbar.make(v,"Seller could not be blocked", Snackbar.LENGTH_LONG).setAction("Action",null).show();
                        }

                    }



                }
            });

            holder.btn_unblock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db = new DatabaseHelper(context);
                    if(frag.equals("customer")){

                        boolean status= db.unblockCustomer(Integer.toString(user.getId()));
                        if(status){
                            Snackbar.make(v,"Customer has been unblocked", Snackbar.LENGTH_LONG).setAction("Action",null).show();
                        }
                        else{

                            Snackbar.make(v,"Customer could not be unblocked", Snackbar.LENGTH_LONG).setAction("Action",null).show();
                        }


                    }
                    else if(frag.equals("seller")){
                        boolean status= db.unblockSeller(Integer.toString(user.getId()));
                        if(status){
                            Snackbar.make(v,"Seller has been unblocked", Snackbar.LENGTH_LONG).setAction("Action",null).show();
                        }
                        else{

                            Snackbar.make(v,"Seller could not be unblocked", Snackbar.LENGTH_LONG).setAction("Action",null).show();
                        }

                    }


                }
            });
           /* holder.btn_block.setOnClickListener(new View.OnClickListener() {
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
            });*/


            row.setTag(holder);

        }
        else {
            holder = (AdminUserListAdapter.ViewHolder) row.getTag();
        }

        holder.txtId.setText(Integer.toString(user.getId()));
        holder.txtName.setText(user.getName());
        return row;
    }
}


