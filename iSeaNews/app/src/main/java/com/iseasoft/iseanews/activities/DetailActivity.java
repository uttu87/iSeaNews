package com.iseasoft.iseanews.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iseasoft.iseanews.api.NewsAPI;
import com.iseasoft.iseanews.entity.Post;
import com.iseasoft.iseanews.entity.PostDetail;
import com.iseasoft.iseanews.R;
import com.iseasoft.iseanews.utils.LogUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    ImageView imgBack;
    WebView wbContent;
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle bundle = getIntent().getExtras();
        Post post = (Post) bundle.getSerializable("post");

        imgBack = findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        wbContent = findViewById(R.id.wb_content);
        tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(post.getPostTitle());

        getPostDetail(post.getPostId());

    }

    private void getPostDetail(String postId){
        NewsAPI.getPostDetail(this, postId, new Callback<PostDetail>() {
            @Override
            public void onResponse(Call<PostDetail> call,final Response<PostDetail> response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(response.isSuccessful()){
                            PostDetail postDetail = response.body();
                            LogUtil.d("DetailActivity", postDetail.toString());

                            String data = "<html><head><title></title><style>*{max-width: 100%;}</style></head><body>" + postDetail.getPostContent() + "/<body><html>";
                            wbContent.loadData(data, "text/html; charset=utf-8", "utf-8");
                        } else {
                            LogUtil.d("DetailActivity", "Error Load Data");
                            Toast.makeText(getApplicationContext(), "Kết nối mạng có vấn đề, vui lòng kiểm tra lại!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }

            @Override
            public void onFailure(Call<PostDetail> call, Throwable t) {
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
