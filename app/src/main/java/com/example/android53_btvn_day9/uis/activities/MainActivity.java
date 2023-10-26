package com.example.android53_btvn_day9.uis.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android53_btvn_day9.R;
import com.example.android53_btvn_day9.models.objects.Product;
import com.example.android53_btvn_day9.uis.ProductService;
import com.example.android53_btvn_day9.uis.fragments.AccountFragment;
import com.example.android53_btvn_day9.uis.fragments.CartFragment;
import com.example.android53_btvn_day9.uis.fragments.CategoriesFragment;
import com.example.android53_btvn_day9.uis.fragments.HomeFragment;
import com.example.android53_btvn_day9.uis.fragments.WishListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int FRAGMENT_HOME = 1;
    private static final int FRAGMENT_WISH_LIST = 2;
    private static final int FRAGMENT_CATEGORIES = 3;
    private static final int FRAGMENT_ACCOUNT = 4;
    private static final int FRAGMENT_CART = 5;
    private  int currentFragment = FRAGMENT_HOME;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView ;
    private Toolbar mToolbar;
    private BottomNavigationView mBottomNavigationView;
    RecyclerView recyclerView;
    static List<Product> list;
    ProductService productService;
//    static DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        dbHelper=new DBHelper(this);

        mBottomNavigationView=findViewById(R.id.bottom_nav);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,mToolbar,R.string.open_drawer,R.string.close_drawer);
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Add code here to update the UI based on the item selected
                // For example, swap UI fragments here
                if(item.getItemId() == R.id.homeFragment){
                    openHomeFragment();
                    mBottomNavigationView.getMenu().findItem(R.id.homeFragment).setChecked(true);
                } else if (item.getItemId() == R.id.wishlistFragment) {
                    openWishListFragment();
                    mBottomNavigationView.getMenu().findItem(R.id.wishlistFragment).setChecked(true);
                }else if (item.getItemId() == R.id.categoriesFragment) {
                    openCategoriesFragment();
                    mBottomNavigationView.getMenu().findItem(R.id.categoriesFragment).setChecked(true);
                }else if (item.getItemId() == R.id.accountFragment) {
                    openAccountFragment();
                    mBottomNavigationView.getMenu().findItem(R.id.accountFragment).setChecked(true);
                }else if (item.getItemId() == R.id.cartFragment) {
                    openCartFragment();
                    mBottomNavigationView.getMenu().findItem(R.id.cartFragment).setChecked(true);
                }
                // close drawer when item is tapped
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
        loadFragment(new HomeFragment());
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.homeFragment){
                    openHomeFragment();
                    mNavigationView.setCheckedItem(R.id.homeFragment);
                } else if (item.getItemId() == R.id.wishlistFragment) {
                    openWishListFragment();
                    mNavigationView.setCheckedItem(R.id.wishlistFragment);
                }else if (item.getItemId() == R.id.categoriesFragment) {
                    openCategoriesFragment();
                    mNavigationView.setCheckedItem(R.id.categoriesFragment);
                }else if (item.getItemId() == R.id.accountFragment) {
                    openAccountFragment();
                    mNavigationView.setCheckedItem(R.id.accountFragment);
                }else if (item.getItemId() == R.id.cartFragment) {
                    openCartFragment();
                    mNavigationView.setCheckedItem(R.id.cartFragment);
                }
                return true;
            }
        });
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

    }
    private void openHomeFragment(){
        if(FRAGMENT_HOME != currentFragment) {
            loadFragment(new HomeFragment());
            currentFragment = FRAGMENT_HOME;
        }
    }
    private void openWishListFragment(){
        if(FRAGMENT_WISH_LIST != currentFragment){
            loadFragment( new WishListFragment());
            currentFragment = FRAGMENT_WISH_LIST;
        }
    }
    private void openCategoriesFragment(){
        if(FRAGMENT_CATEGORIES != currentFragment){
            loadFragment( new CategoriesFragment());
            currentFragment = FRAGMENT_CATEGORIES;
        }

    }
    private void openAccountFragment(){
        if(FRAGMENT_ACCOUNT!= currentFragment){
            loadFragment( new AccountFragment());
            currentFragment = FRAGMENT_ACCOUNT;
        }
    }
    private void openCartFragment(){
        if(FRAGMENT_CART!= currentFragment){
            loadFragment( new CartFragment());
            currentFragment = FRAGMENT_CART;
        }
    }



    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }

    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment,fragment)
                .commit();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}