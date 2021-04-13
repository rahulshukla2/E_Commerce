package com.example.e_comm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper  extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "e_commerce.db";
    public static final String TABLE_NAME = "customer";
    public static final String TABLE_NAME_SELLER = "seller";
    public static final String TABLE_NAME_PRODUCT = "product";
    public static final String TABLE_NAME_CART = "cart";
    public static final String TABLE_NAME_ORDER = "orders";
    public static final String COL_1 = "CustomerID";
    public static final String COL_2 = "name";
    public static final String COL_3 = "password";
    public static final String COL_4 = "mobile_no";
    public static final String SELLER_COL_5 = "company_name";
    public static final String TAG ="MY Activity";





    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE customer (CustomerID INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT NOT NULL , password TEXT NOT NULL,mobile_no TEXT(10) UNIQUE NOT NULL, blocked TEXT NOT NULL)");
        db.execSQL("CREATE TABLE seller (SellerID INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT NOT NULL, password TEXT NOT NULL,mobile_no TEXT(10) UNIQUE NOT NULL, company_name TEXT NOT NULL,blocked TEXT NOT NULL)");
        db.execSQL("CREATE TABLE product (ProductID INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT NOT NULL,price TEXT NOT NULL, category TEXT NOT NULL,quantity TEXT NOT NULL,description TEXT NOT NULL,image BLOB,SellerID INTEGER NOT NULL, FOREIGN KEY(SellerID) REFERENCES SELLER(SellerID))");
        db.execSQL("CREATE TABLE cart (CartID INTEGER PRIMARY KEY AUTOINCREMENT , ProductID INTEGER NOT NULL, CustomerID INTEGER NOT NULL, FOREIGN KEY(CustomerID) REFERENCES CUSTOMER(CustomerID))");
        db.execSQL("CREATE TABLE orders (OrderID INTEGER PRIMARY KEY AUTOINCREMENT , ProductID INTEGER NOT NULL, CustomerID INTEGER NOT NULL, FOREIGN KEY(CustomerID) REFERENCES CUSTOMER(CustomerID))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME_SELLER);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME_PRODUCT);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME_CART);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME_ORDER);


        onCreate((db));
    }

    /* *************************************** table operation ralated with order************************************************************/

    public long placeOrder(long ProductID, long CustomerID){

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String sql = "INSERT INTO ORDERS VALUES(NULL,?,?)";
        SQLiteStatement statement = sqLiteDatabase.compileStatement(sql);
        statement.clearBindings();
        statement.bindLong(1,ProductID);
        statement.bindLong(2,CustomerID);

        long value = statement.executeInsert();
        Log.d(TAG,"Came in add into cart");
        return value;
    }

    public Cursor getOrder(String CustomerID ){

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        //String sql = "SELECT * FROM ORDERS WHERE CUSTOMERID =?";
        String sql = "SELECT PRODUCT.* FROM ORDERS INNER JOIN PRODUCT ON ORDERS.PRODUCTID = PRODUCT.PRODUCTID WHERE CUSTOMERID =?";
        String[] args={CustomerID};

        return sqLiteDatabase.rawQuery(sql,args);
    }


    /* *************************************** table operation ralated with cart************************************************************/

    public long addIntoCart(long ProductID, long CustomerID){

        Log.d(TAG,"Came in add into cart");
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String sql = "INSERT INTO CART VALUES(NULL,?,?)";
        SQLiteStatement statement = sqLiteDatabase.compileStatement(sql);
        statement.clearBindings();
        statement.bindLong(1,ProductID);
        statement.bindLong(2,CustomerID);

        long value = statement.executeInsert();
        return value;
    }

    public Cursor getCartData(String CustomerID ){

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
       // String sql = "SELECT * FROM CART WHERE CUSTOMERID =?";
        String sql = "SELECT PRODUCT.* FROM CART INNER JOIN PRODUCT ON CART.PRODUCTID = PRODUCT.PRODUCTID WHERE CUSTOMERID =?";
        String[] args={CustomerID};

        return sqLiteDatabase.rawQuery(sql,args);
    }

    public boolean deleteFromCart(String ProductID, String CustomerID ){

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String sql = "SELECT * FROM CART WHERE PRODUCTID = ? AND CUSTOMERID =?";
        String[] args={ProductID, CustomerID};
        Cursor cursor = sqLiteDatabase.rawQuery(sql,args);
        if(cursor.getCount()>0){
            long r = sqLiteDatabase.delete("cart","productId=? and customerId=?", args);
            if(r==-1)
                return false;
            return true;
        }
        else
            return false;
    }

    /* *************************************** tables operations related with  Product *************************************************** */

    public long addProduct(String title, String price, String category, String quantity, String desc, byte[] image, long sellerid) {

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String sql = "INSERT INTO PRODUCT VALUES(NULL,?,?,?,?,?,?,?)";
       // String blocked="0";
        SQLiteStatement statement = sqLiteDatabase.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,title);
        statement.bindString(2,price);
        statement.bindString(3,category);
        statement.bindString(4,quantity);
        statement.bindString(5,desc);
        statement.bindBlob(6,image);
        statement.bindLong(7,sellerid);
       // statement.bindString(8,blocked);


         long value = statement.executeInsert();
         return value;
    }

    public Cursor getProduct(String sql){
        SQLiteDatabase sqLiteDatabase =  getReadableDatabase();
        return sqLiteDatabase.rawQuery(sql,null);
    }

    public Cursor getProductBySearch(String sql,String key){
        SQLiteDatabase sqLiteDatabase =  getReadableDatabase();
        return sqLiteDatabase.rawQuery(sql,new String[]{key,key});
    }


    public Cursor getProductBySeller(String sql,String sellerid){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery(sql,new String[]{sellerid});
    }


    public boolean updateProduct(String productId, String title, String price, String category, String quantity, String desc, byte[] image, int sellerid) {

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String sql = "SELECT * FROM PRODUCT WHERE  PRODUCTID = ?";

        ContentValues c = new ContentValues();
        c.put("ProductId",productId);
        c.put("title",title);
        c.put("price",price);
        c.put("category",category);
        c.put("quantity",quantity);
        c.put("description",desc);
        c.put("image",image);
        c.put("SellerID",sellerid);

        Log.d(TAG,"Just above cursor");
        Cursor cursor = sqLiteDatabase.rawQuery(sql,new String[]{productId});
        if(cursor.getCount()>0){
            Log.d(TAG,"Inside if  cursor");
            long r = sqLiteDatabase.update("product",c,"productId=?",new String[]{productId});

            Log.d(TAG,"Inside if  cursor and r is"+r);

            if(r==-1) return false;
            else
            return true;
        }
        else return false;
    }

    public boolean deleteProduct(String productId){

        String sql = "SELECT * FROM PRODUCT WHERE  PRODUCTID = ?";
        SQLiteDatabase sqLiteDatabase =  getWritableDatabase();
        String[] args={productId.toString()};
        Cursor cursor = sqLiteDatabase.rawQuery(sql,args);


        if(cursor.getCount()>0){
            long r = sqLiteDatabase.delete("product","productId=?", args);
            if(r==-1)
                return false;
            return true;
        }
        else
            return false;


    }



    /* ********************************************************tables related with operation on User table ****************************************** */
    public long addCustomer(String name, String password, String mobile_no)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String blocked="0";
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("password",password);
        contentValues.put("mobile_no",mobile_no);
        contentValues.put("blocked",blocked);

        long res = db.insert("customer",null, contentValues);

        db.close();
        return res;
    }

    public boolean checkCustomer(String mobile_no) {
        String[] columns = {COL_1};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COL_4 + "=?";
        String selectionArgs[] = {mobile_no};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        int count = cursor.getCount();
        cursor.close();
        db.close();
        if (count > 0)
        {

            return true;
        }
        else
        {
            return false;
        }


    }


    /*
    public boolean verifyCustomerPassword(String password , String mobile_no) {
        String pass;
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "select password from customer where mobile_no="+mobile_no;
        Cursor cursor=db.rawQuery(selectQuery,null);
        //String selectionArgs[] = {mobile_no};

        pass=cursor.getString(1);
        Log.d(TAG,pass);
        cursor.close();
        db.close();
        if(pass.equals(password)){
            return true;
        }
        else{
            return false;
        }

    }*/

    public boolean verifyCustomerPassword(String mobile_no , String password) {

        SQLiteDatabase db = this.getReadableDatabase();
        String columns[]={COL_1};
        String selection = COL_4 + "=?" + " and " + COL_3 + "=?";
        String selectionArgs[] = {mobile_no,password};

        Cursor cursor = db.query(TABLE_NAME,columns,selection,selectionArgs,null,null,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        if (count > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public Cursor getCustomer(String sql,String mobile_no){
        SQLiteDatabase sqLiteDatabase =  getReadableDatabase();
        return sqLiteDatabase.rawQuery(sql,new String[]{mobile_no});
    }

    public Cursor getCustomers(){


            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            // String sql = "SELECT * FROM CART WHERE CUSTOMERID =?";
            String sql = "SELECT * FROM CUSTOMER";


            return sqLiteDatabase.rawQuery(sql,null);

    }

    public boolean isBlockedCustomer(String mobile_no){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String sql = "SELECT * FROM CUSTOMER WHERE MOBILE_NO=?";
        String args[]={mobile_no};
        Cursor cursor = sqLiteDatabase.rawQuery(sql,args);
        if(cursor.getCount()>0){
            while(cursor.moveToNext()) {
                String blocked = cursor.getString(4);
                if (blocked.equals("0"))
                    return false;
                else if (blocked.equals("1"))
                    return true;
            }
        }
        return false;
    }



    public boolean blockCustomer(String customerid){

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String block="1";
        String sql = "UPDATE CUSTOMER SET BLOCKED=? WHERE CUSTOMERID=?";

        Cursor cursor=sqLiteDatabase.rawQuery(sql,new String[]{block,customerid});

        if(cursor.getCount()>0){
            return true;
        }
        return true;


    }
    public boolean unblockCustomer(String customerid){

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String block="0";
        String sql = "UPDATE CUSTOMER SET BLOCKED=? WHERE CUSTOMERID=?";

        Cursor cursor=sqLiteDatabase.rawQuery(sql,new String[]{block,customerid});
        if(cursor.getCount()>0){
            return true;
        }
        return true;

    }








/* *****************************************************Database operation related with seller's table************************************** */


    /*tables related with operation on User table*/
    public long addSeller(String name, String password, String mobile_no, String company_name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String blocked="0";
        contentValues.put("name",name);
        contentValues.put("password",password);
        contentValues.put("mobile_no",mobile_no);
        contentValues.put("company_name",company_name);
        contentValues.put("blocked",blocked);

        long res = db.insert("seller",null, contentValues);

        if(res!=-1)
        {
            Log.d(TAG,"Successfully added");
        }
        else{
            Log.d(TAG,"Error has occured");
        }
        db.close();
        return res;
    }

    public boolean checkSeller(String mobile_no) {
        String[] columns = {"SellerID"};
        SQLiteDatabase db = getReadableDatabase();
        String selection = COL_4 + "=?";
        String selectionArgs[] = {mobile_no};
        Cursor cursor = db.query(TABLE_NAME_SELLER, columns, selection, selectionArgs, null, null, null);

        int count = cursor.getCount();
        cursor.close();
        db.close();
        if (count > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

/*
    public boolean verifySellerPassword(String password , String mobile_no) {
        String pass;
        SQLiteDatabase db = getReadableDatabase();
        String selectQuery = "select password from seller where mobile_no="+mobile_no;
        Cursor cursor=db.rawQuery(selectQuery,null);
        //String selectionArgs[] = {mobile_no};

        pass=cursor.getString(1);

        cursor.close();
        db.close();
        if(pass.equals(password)){
            return true;
        }
        else{
            return false;
        }

    }

 */
    public boolean verifySellerPassword(String mobile_no , String password) {

        SQLiteDatabase db = this.getReadableDatabase();
        String columns[]={"SellerID"};
        String selection = COL_4 + "=?" + " and " + COL_3 + "=?";
        String selectionArgs[] = {mobile_no,password};

        Cursor cursor = db.query(TABLE_NAME_SELLER,columns,selection,selectionArgs,null,null,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        if (count > 0)
        {
            return true;
        }
        else
        {
            return false;
        }


    }

    public Cursor getSeller(String sql,String mobile_no){
        SQLiteDatabase sqLiteDatabase =  getReadableDatabase();
        return sqLiteDatabase.rawQuery(sql,new String[]{mobile_no});
    }

    public Cursor getSellers(){


        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String sql = "SELECT * FROM SELLER";
        return sqLiteDatabase.rawQuery(sql,null);

    }

    public boolean blockSeller(String sellerid){

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String block="1";
        String sql = "UPDATE SELLER SET BLOCKED=? WHERE SELLERID=?";

        Cursor cursor=sqLiteDatabase.rawQuery(sql,new String[]{block,sellerid});
        if(cursor.getCount()>0){
            return true;
        }
        return true;


    }
    public boolean unblockSeller(String sellerid){

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String block="0";
        String sql = "UPDATE SELLER SET BLOCKED=? WHERE SELLERID=?";

        Cursor cursor=sqLiteDatabase.rawQuery(sql,new String[]{block,sellerid});

        if(cursor.getCount()>0){
            return true;
        }
        return true;


    }
    public boolean isBlockedSeller(String mobile_no){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String sql = "SELECT * FROM SELLER WHERE MOBILE_NO=?";
        String args[]={mobile_no};
        Cursor cursor = sqLiteDatabase.rawQuery(sql,args);
        if(cursor.getCount()>0){

            Log.d(TAG,"Inside cursor getcount()");
            //cursor.moveToFirst();
            while(cursor.moveToNext()) {
                Log.d(TAG,"Inside cursor movenext");
                String blocked = cursor.getString(5);
                if (blocked.equals("0"))
                    return false;
                else if (blocked.equals("1"))
                    return true;
            }
        }
        cursor.close();
        sqLiteDatabase.close();

        return false;
    }
}
