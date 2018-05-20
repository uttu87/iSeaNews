package com.iseasoft.iseanews.api;

import android.content.Context;

import com.iseasoft.iseanews.api.base.BaseOkHttp;
import com.iseasoft.iseanews.api.base.NewsService;
import com.iseasoft.iseanews.api.base.RetrofitClient;
import com.iseasoft.iseanews.entity.PostDetail;
import com.iseasoft.iseanews.entity.Posts;
import com.iseasoft.iseanews.interfaces.HttpCallback;
import com.iseasoft.iseanews.utils.Define;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Callback;

public class NewsAPI {


    public static NewsService getSOService() {
        return RetrofitClient.getClient(Define.API_BASE_URL).create(NewsService.class);
    }

    public  static void apiEx(Context ctx){

        OkHttpClient okHttpClient = BaseOkHttp.getOkHttpClient();

        Request request = new Request.Builder()
                .url(Define.API_EXAMPLE)
                .build();
    }

    public  static void getListPost(Context ctx, int categoryId, int limit, int offset, Callback<Posts> callback){

        //OkHttpClient okHttpClient = BaseOkHttp.getOkHttpClient();

        //String url = Define.API_GET_LIST_POST + "?category_id=" + categoryId + "&limit=" + limit + "&offset=" + offset;
        //Request request = new Request.Builder()
        //        .url(url)
        //        .build();
        NewsService service = NewsAPI.getSOService();

        service.getPosts(categoryId, limit, offset).enqueue(callback);
    }

    public  static void getListPostTest(Context ctx, int categoryId, int limit, int offset, Callback<Posts> callback){

        //OkHttpClient okHttpClient = BaseOkHttp.getOkHttpClient();

        //String url = Define.API_GET_LIST_POST + "?category_id=" + categoryId + "&limit=" + limit + "&offset=" + offset;
        //Request request = new Request.Builder()
        //        .url(url)
        //        .build();
        NewsService service = NewsAPI.getSOService();

        service.getPostsTest().enqueue(callback);
    }

    public  static void getPostDetail(Context ctx, int postId, HttpCallback callback){

        OkHttpClient okHttpClient = BaseOkHttp.getOkHttpClient();

        String url = Define.API_GET_POST_DETAIL + "?post_id=" + postId;
        Request request = new Request.Builder()
                .url(url)
                .build();
    }

    public  static void getPostDetail(Context ctx, String postId, Callback<PostDetail> callback){

        NewsService service = NewsAPI.getSOService();
        service.getPostDetail(postId).enqueue(callback);
    }
}
