package com.example.android53_btvn_day9.uis.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android53_btvn_day9.R;
import com.example.android53_btvn_day9.adapters.CategoriesProductAdapter;
import com.example.android53_btvn_day9.adapters.PhotoViewPager2Adapter;
import com.example.android53_btvn_day9.models.apiheip.DBHelper;
import com.example.android53_btvn_day9.models.objects.CategoriesProduct;
import com.example.android53_btvn_day9.models.objects.Photo;
import com.example.android53_btvn_day9.models.objects.Product;
import com.example.android53_btvn_day9.models.objects.ProductResponse;
import com.example.android53_btvn_day9.uis.IClickListener;
import com.example.android53_btvn_day9.uis.ProductService;
import com.example.android53_btvn_day9.uis.RetrofitClient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import me.relex.circleindicator.CircleIndicator3;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements IClickListener {
    private ViewPager2 mViewPager2;
    private CircleIndicator3 mCircleIndicator3;
    private List<Photo> mListPhoto;
    private RecyclerView rcvHome;
    private CategoriesProductAdapter categoriesProductAdapter;
    private List<CategoriesProduct> listCategoriesProduct;
    List<Product> listAllProduct;
//    private SavePresenter savePresenter;
    static DBHelper dbHelper;
    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (mViewPager2.getCurrentItem() == mListPhoto.size() - 1){
                mViewPager2.setCurrentItem(0);
            }else {
                mViewPager2.setCurrentItem(mViewPager2.getCurrentItem() + 1);
            }
        }
    };



    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewPager2 = view.findViewById(R.id.view_pager);
        mCircleIndicator3 = view.findViewById(R.id.circle);
        mListPhoto = getListPhoto();
        PhotoViewPager2Adapter adapter = new PhotoViewPager2Adapter(mListPhoto);
        mViewPager2.setAdapter(adapter);

        mCircleIndicator3.setViewPager(mViewPager2);
        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mHandler.removeCallbacks(mRunnable);
                mHandler.postDelayed(mRunnable,3000);
            }
        });
        rcvHome=view.findViewById(R.id.rcvHome);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL,false);
        rcvHome.setLayoutManager(linearLayoutManager);

        categoriesProductAdapter=new CategoriesProductAdapter(HomeFragment.this,HomeFragment.this);
        rcvHome.setAdapter(categoriesProductAdapter);
        getData();
        dbHelper=new DBHelper(getActivity());
    }
    private void getData() {
        listCategoriesProduct=new ArrayList<>();
        RetrofitClient.create(ProductService.class).getProducts().enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if(response.isSuccessful()){
                    if(response.code()==200){
                        ProductResponse productResponse=response.body();
                        listAllProduct=productResponse.getProducts();
                        resetData();
                        List<Product> listProductHotDeal=listAllProduct.stream()
                                .filter(product -> product.getRating() >= 4.9 )
                                .collect(Collectors.toList());
                        CategoriesProduct hotDealProduct=new CategoriesProduct("Hot Deals",listProductHotDeal);
                        listCategoriesProduct.add(hotDealProduct);

                        List<Product> listProductPopular=listAllProduct.stream()
                                .filter(product -> product.getStock() <100)
                                .collect(Collectors.toList());
                        CategoriesProduct productPopular=new CategoriesProduct("Most Poplular",listProductPopular);
                        listCategoriesProduct.add(productPopular);

                        List<Product> listProductiPhone=listAllProduct.stream()
                                .filter(product -> product.getCategory().equals("smartphones") && product.getBrand().equals("Apple"))
                                .collect(Collectors.toList());
                        CategoriesProduct iphone=new CategoriesProduct("Iphones",listProductiPhone);
                        listCategoriesProduct.add(iphone);

                        List<Product> listProductiPad=listAllProduct.stream()
                                .filter(product -> product.getCategory().equals("smartphones"))
                                .collect(Collectors.toList());
                        CategoriesProduct ipads=new CategoriesProduct("Ipads",listProductiPad);
                        listCategoriesProduct.add(ipads);

                        List<Product> listProductMac=listAllProduct.stream()
                                .filter(product -> product.getCategory().equals("laptops"))
                                .collect(Collectors.toList());
                        CategoriesProduct macs=new CategoriesProduct("Macs",listProductMac);
                        listCategoriesProduct.add(macs);

                        categoriesProductAdapter.setData(listCategoriesProduct);

                    }
                }
            }
            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Log.d("TAG", "onFailure: ");
            }
        });
    }
    public void resetData(){
        int dem;
        for(int i=0;i<listAllProduct.size();i++){
            dem=0;
            for(int j=0;j<dbHelper.getProducts().size();j++){
                if(listAllProduct.get(i).getId()==dbHelper.getProducts().get(j).getId()){
                    listAllProduct.get(i).setCheck(1);
                    categoriesProductAdapter.notifyDataSetChanged();
                    dem=1;
                }
            }
            if(dem==0){
                listAllProduct.get(i).setCheck(0);
                categoriesProductAdapter.notifyDataSetChanged();
            }

        }
    }

    private List<Photo> getListPhoto(){
        List<Photo> list = new ArrayList<>();
        list.add(new Photo(R.drawable.img_banner));
        list.add(new Photo(R.drawable.img_banner));
        list.add(new Photo(R.drawable.img_banner));
        list.add(new Photo(R.drawable.img_banner));
        return list;
    }

    @Override
    public void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mRunnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        mHandler.postDelayed(mRunnable,3000);
    }

    @Override
    public void onItemClick(int productID) {
        Bundle bundle = new Bundle();
        bundle.putString("product_id", String.valueOf(productID));

        NavController navController = NavHostFragment.findNavController(HomeFragment.this);
        navController.navigate(R.id.action_homeFragment_to_productDetailsFragment, bundle);
    }
    public static List<Product> getProductWish(){
        List<Product> list=new ArrayList<>();
        list=dbHelper.getProducts();
        return list;
    }
}