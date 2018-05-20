package com.iseasoft.iseanews.api.base;

import com.iseasoft.iseanews.entity.PostDetail;
import com.iseasoft.iseanews.entity.Posts;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsService {
    @GET("get_list_post.php?")
    Call<Posts> getPosts(@Query("category_id") int categoryId, @Query("limit") int limit, @Query("offset") int offset);

    //https://api.myjson.com/bins/13w7p3
    @GET("13w7p3")
    Call<Posts> getPostsTest();


    @GET("get_post_detail.php?")
    Call<PostDetail> getPostDetail(@Query("post_id") String postId);

    //@GET("/answers?order=desc&sort=activity&site=stackoverflow")
    //Call<SOAnswersResponse> getAnswers(@Query("tagged") String tags);
}