package com.iseasoft.iseanews.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iseasoft.iseanews.adapter.MenuAdapter;
import com.iseasoft.iseanews.adapter.PostAdapter;
import com.iseasoft.iseanews.api.NewsAPI;
import com.iseasoft.iseanews.entity.MenuEntity;
import com.iseasoft.iseanews.adapter.AdapterListener;
import com.iseasoft.iseanews.entity.Post;
import com.iseasoft.iseanews.entity.Posts;
import com.iseasoft.iseanews.R;
import com.iseasoft.iseanews.utils.LogUtil;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Context mContext = MainActivity.this;
    private ImageView imgMenu;
    private RecyclerView rvMenu;
    private RecyclerView rvPost;
    private TextView tvTitle;
    private SwipeRefreshLayout swipeRefreshLayout;

    private int categoryId;
    private MenuAdapter menuAdapter;
    private PostAdapter postAdapter;

    private ArrayList<MenuEntity> menuEntities = new ArrayList<MenuEntity>(0);
    private ArrayList<Post> mPosts = new ArrayList<Post>(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MenuEntity menuThoiSu = new MenuEntity();
        menuThoiSu.setId(1);
        menuThoiSu.setTitle("Thời sự");
        menuThoiSu.setSelected(true);

        MenuEntity menuTheThao = new MenuEntity();
        menuTheThao.setId(2);
        menuTheThao.setTitle("Thể thao");

        MenuEntity menuKinhTe = new MenuEntity();
        menuKinhTe.setId(3);
        menuKinhTe.setTitle("Kinh tế");

        MenuEntity menuChinhTri = new MenuEntity();
        menuChinhTri.setId(4);
        menuChinhTri.setTitle("Chính trị");

        menuEntities.add(menuThoiSu);
        menuEntities.add(menuTheThao);
        menuEntities.add(menuKinhTe);
        menuEntities.add(menuChinhTri);

        categoryId = 1;
        tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(menuThoiSu.getTitle());

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPosts.clear();
                postAdapter.notifyDataSetChanged();
                getListPost();
            }
        });

        imgMenu =(ImageView) findViewById(R.id.img_menu);

        // configure the SlidingMenu
        final SlidingMenu menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen._12sdp);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen._60sdp);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.layout_menu);

        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.toggle();
            }
        });

        menuAdapter = new MenuAdapter(menuEntities, new AdapterListener() {
            @Override
            public void onItemClickListener(Object object, int pos, RecyclerView.ViewHolder holder) {

                for(int i = 0; i < menuEntities.size(); ++i){
                    MenuEntity entity = menuEntities.get(i);
                    entity.setSelected(false);
                }

                MenuEntity menuEntity = (MenuEntity) object;
                menuEntity.setSelected(true);
                tvTitle.setText(menuEntity.getTitle());
                categoryId = menuEntity.getId();
                menuAdapter.notifyDataSetChanged();
                menu.toggle();

                mPosts.clear();
                postAdapter.notifyDataSetChanged();
                getListPost();
            }
        });

        rvMenu = (RecyclerView) menu.findViewById(R.id.rv_menu);

        rvMenu.setLayoutManager(new LinearLayoutManager(this));
        rvMenu.setItemAnimator(new DefaultItemAnimator());
        rvMenu.setAdapter(menuAdapter);

        postAdapter = new PostAdapter(mPosts, new AdapterListener() {
            @Override
            public void onItemClickListener(Object object, int pos, RecyclerView.ViewHolder holder) {

                LogUtil.d("MainActivity", "onItemClick position: " + pos);
                LogUtil.d("MainActivity", "onItemClick mPosts.size(): " + mPosts.size());
                if(object != null){
                    Post post = (Post)object;
                    Intent intent = new Intent(mContext, DetailActivity.class);
                    intent.putExtra("post", post);
                    startActivity(intent);
                } else {
                    getListPost();
                }

            }
        });

        rvPost = (RecyclerView) findViewById(R.id.rv_post);
        rvPost.setLayoutManager(new LinearLayoutManager(this));
        rvPost.setItemAnimator(new DefaultItemAnimator());
        rvPost.setAdapter(postAdapter);

        getListPost();
    }

    private void getListPost(){
        NewsAPI.getListPost(this, categoryId, 3, mPosts.size(), new Callback<Posts>() {
            @Override
            public void onResponse(Call<Posts> call, final Response<Posts> response) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        if(response.isSuccessful()) {
                            //postAdapter.updatePosts(response.body().getPosts());
                            //mPosts.clear();
                            mPosts.addAll(response.body().getPosts());
                            postAdapter.notifyDataSetChanged();

                            LogUtil.d("MainActivity", "posts loaded from API");
                        }else {
                            int statusCode  = response.code();
                            // handle request errors depending on status code

                            LogUtil.d("MainActivity", "posts loaded from API error code: " + statusCode);
                        }

                    }
                });

            }

            @Override
            public void onFailure(Call<Posts> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Kết nối mạng có vấn đề, vui lòng kiểm tra lại!!!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}
