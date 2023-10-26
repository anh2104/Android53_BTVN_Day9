package com.example.android53_btvn_day9.models.apiheip;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.android53_btvn_day9.models.objects.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="product.db";
    public static final  int VERSION=1;
    public static final String TABLE_NAME="product";
    public static final String PRODUCT_ID = "product_id";
    public static final String PRODUCT_CHECK = "product_check";
    public static final String PRODUCT_TITLE = "title";
    public static final String PRODUCT_DES = "description";
    public static final String PRODUCT_PRICE = "price";
    public static final String PRODUCT_DISCOUNT = "discountPercentage";
    public static final String PRODUCT_RATING = "rating";
    public static final String PRODUCT_STOCK = "stock";
    public static final String PRODUCT_BRAND = "brand";
    public static final String PRODUCT_CATEGORY = "category";
    public static final String PRODUCT_THUMBNAIL = "thumbnail";
    public static final String PRODUCT_IMAGES = "images";
    public DBHelper(@Nullable Context context) {
        super(context,DATABASE_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE " + TABLE_NAME + "("
                + PRODUCT_ID + " INTEGER NOT NULL PRIMARY KEY,"
                + PRODUCT_CHECK + " INTEGER NOT NULL,"
                + PRODUCT_TITLE + " TEXT NOT NULL,"
                + PRODUCT_DES + " TEXT NOT NULL,"
                + PRODUCT_PRICE + " TEXT NOT NULL,"
                + PRODUCT_DISCOUNT + " TEXT NOT NULL,"
                + PRODUCT_RATING + " TEXT NOT NULL,"
                + PRODUCT_STOCK + " TEXT NOT NULL,"
                + PRODUCT_BRAND + " TEXT NOT NULL,"
                + PRODUCT_CATEGORY + " TEXT NOT NULL,"
                + PRODUCT_THUMBNAIL + " TEXT NOT NULL,"
                + PRODUCT_IMAGES + " TEXT)";
        sqLiteDatabase.execSQL(sql);
    }
    public boolean addProduct(Product product) {
        if (product != null) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(PRODUCT_ID, product.getId());
            contentValues.put(PRODUCT_CHECK, product.getCheck());
            contentValues.put(PRODUCT_TITLE, product.getTitle());
            contentValues.put(PRODUCT_DES, product.getDescription());
            contentValues.put(PRODUCT_PRICE, product.getPrice());
            contentValues.put(PRODUCT_DISCOUNT, product.getDiscountPercentage());
            contentValues.put(PRODUCT_RATING,product.getRating());
            contentValues.put(PRODUCT_STOCK, product.getStock());
            contentValues.put(PRODUCT_BRAND, product.getBrand());
            contentValues.put(PRODUCT_CATEGORY, product.getCategory());
            contentValues.put(PRODUCT_THUMBNAIL, product.getThumbnail());
            // convert list sang gson
            Gson gson = new Gson();
            Type typeToken = new TypeToken<List<String>>(){}.getType();
            String data = gson.toJson(product.getImages(), typeToken);
            contentValues.put(PRODUCT_IMAGES, data);

            long response = db.insert(TABLE_NAME, null, contentValues);
            db.close();

            if (response > -1) {
                return false;
            }
            return  true;
        }
        return false;
    }

    public boolean deleteProduct(int productId){
        if(productId>=0){
            SQLiteDatabase db=getWritableDatabase();

            String whereClause =PRODUCT_ID+" =?";
            String[] whereArg={productId+""};

            db.delete(TABLE_NAME,whereClause,whereArg);
            db.close();
            return true;
        }
        return false;
    }
    @SuppressLint("Range")
    public List<Product> getProducts(){
        SQLiteDatabase db=getReadableDatabase();
        String sql="select * from "+TABLE_NAME;
        List<Product> list=new ArrayList<>();
        Cursor cursor=db.rawQuery(sql,null);
        if(cursor!=null){
            while(cursor.moveToNext()){
                Product productModel=new Product();
                productModel.setId(cursor.getInt(cursor.getColumnIndex(PRODUCT_ID)));
                productModel.setCheck(cursor.getInt(cursor.getColumnIndex(PRODUCT_CHECK)));
                productModel.setTitle(cursor.getString(cursor.getColumnIndex(PRODUCT_TITLE)));
                productModel.setDescription(cursor.getString(2));

                int price=Integer.parseInt(cursor.getString(cursor.getColumnIndex(PRODUCT_PRICE)));
                productModel.setPrice(price);

                double discount=Double.parseDouble(cursor.getString(cursor.getColumnIndex(PRODUCT_DISCOUNT)));
                productModel.setDiscountPercentage(discount);
                double rating=Double.parseDouble(cursor.getString(cursor.getColumnIndex(PRODUCT_RATING)));
                productModel.setRating(rating);

                int stock=Integer.parseInt(cursor.getString(cursor.getColumnIndex(PRODUCT_STOCK)));
                productModel.setStock(stock);

                productModel.setBrand(cursor.getString(cursor.getColumnIndex(PRODUCT_BRAND)));
                productModel.setCategory(cursor.getString(cursor.getColumnIndex(PRODUCT_CATEGORY)));
                productModel.setThumbnail(cursor.getString(cursor.getColumnIndex(PRODUCT_THUMBNAIL)));

                Gson gson =new Gson();
                Type typeToken = new TypeToken<List<String>>(){}.getType();
                List<String> data =gson.fromJson(cursor.getString(cursor.getColumnIndex(PRODUCT_IMAGES)),typeToken);
                productModel.setImages(data);
                list.add(productModel);
            }
            return list;
        }
        return null;
    }
    @SuppressLint("Range")
    public Product getProductsByProductId(int productId){
        SQLiteDatabase db=getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + PRODUCT_ID + " = '" + productId + "'";
        Product product=new Product();
        Cursor cursor=db.rawQuery(sql,null);
        if(cursor!=null){
            while(cursor.moveToNext()){
                product.setId(cursor.getInt(cursor.getColumnIndex(PRODUCT_ID)));
                product.setId(cursor.getInt(cursor.getColumnIndex(PRODUCT_CHECK)));
                product.setTitle(cursor.getString(cursor.getColumnIndex(PRODUCT_TITLE)));
                product.setDescription(cursor.getString(2));

                int price=Integer.parseInt(cursor.getString(cursor.getColumnIndex(PRODUCT_PRICE)));
                product.setPrice(price);

                double discount=Double.parseDouble(cursor.getString(cursor.getColumnIndex(PRODUCT_DISCOUNT)));
                product.setDiscountPercentage(discount);

                int stock=Integer.parseInt(cursor.getString(cursor.getColumnIndex(PRODUCT_STOCK)));
                product.setStock(stock);

                product.setBrand(cursor.getString(cursor.getColumnIndex(PRODUCT_BRAND)));
                product.setCategory(cursor.getString(cursor.getColumnIndex(PRODUCT_CATEGORY)));
                product.setThumbnail(cursor.getString(cursor.getColumnIndex(PRODUCT_THUMBNAIL)));

                Gson gson =new Gson();
                Type typeToken = new TypeToken<List<String>>(){}.getType();
                List<String> data =gson.fromJson(cursor.getString(cursor.getColumnIndex(PRODUCT_IMAGES)),typeToken);
                product.setImages(data);
            }
            return product;
        }
        return null;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
