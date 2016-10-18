package tech.town.app.com.apptowntech.service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import tech.town.app.com.apptowntech.model.HomeResultInfo;
import tech.town.app.com.apptowntech.model.body.Response;
import tech.town.app.com.apptowntech.model.bookmark.FavouriteResult;
import tech.town.app.com.apptowntech.model.itemdetail.DetailPageResult;
import tech.town.app.com.apptowntech.model.login.Login;
import tech.town.app.com.apptowntech.model.poll.PollResult;
import tech.town.app.com.apptowntech.utils.Constant;

/**
 * Created by ${="Ashish"} on 9/9/16.
 */
public interface ApiService {


    @GET("index.php?d=API&c=Launch&m=CatInfo&platform=Android&home_ver=1&format=json")
    Observable<List<HomeResultInfo>> getHomeJson(@Query("user_id") String userId,@Query("home_ver") String version);

    @GET("index.php?d=API&c=Launch&m=refresh&platform=Android&format=json")
    Observable<List<HomeResultInfo>> getCategoryList(@Query("c_id") String catId,@Query("dir") String direction,@Query("index") String index);

    @GET("index.php?d=API&c=Launch&m=AddFav&platform=Android")
    Observable<List<Response>> addFavourite(@Query("user_id") String userId , @Query("p_id") String pid,@Query("c_id") String catId);

    @GET("index.php?d=API&c=Launch&m=RemFav&platform=Android")
    Observable<List<Response>>  removeFavourite(@Query("user_id") String userId ,@Query("p_id") String pid);


    @GET("index.php?d=API&c=Launch&m=ListFav&platform=Android")
    Observable<List<FavouriteResult>> getList(@Query("user_id") String userId);


    @GET("index.php?d=API&c=Launch&m=ItemDetail&platform=Android")
    Observable<List<DetailPageResult>> getDetailPage(@Query("cat_id") String itemId , @Query("item_id") String catId);

    @GET("index.php?d=API&c=Launch&m=ListPoll&platform=Android&home_ver=1&format=")
    Observable<List<PollResult>> getPollList();


    @GET("index.php?d=API&c=Launch&m=SubmitPoll&platform=Android")
    Observable<List<Response>> submitPoll(@Query("user_id") String userId ,@Query("poll_id") String pollId ,
                                         @Query("poll_tag") String tag);


    @GET("index.php?d=API&c=Launch&m=LTLogin&platform=Android")
    Observable<List<Login>> postUserDetails(@Query("name") String name ,
                                            @Query("email") String email,
                                            @Query("signup_type") String type,
                                            @Query("user_image") String imageUrl);

    @GET("index.php?d=API&c=Launch&m=insertFeedback&platform=Android")
    Observable<List<Response>> postFeedback(@Query("user_id") String userId ,@Query("f1") String name ,
                                            @Query("f2") String email,
                                            @Query("f3") String type,
                                            @Query("f_comment") String imageUrl);

    @GET("index.php?d=API&c=Launch&m=ReportNews&platform=Android")
    Observable<List<Response>> postReport(@Query("user_id") String userId ,@Query("f1") String f1 ,
                                         @Query("f2") String f2,
                                         @Query("f3") String f3);

    @GET("index.php?d=API&c=Launch&m=AddComment&platform=Android")
    Observable<List<Response>> postComment(@Query("user_id") String userId ,@Query("p_comment") String f1 ,
                                       @Query("p_id") String f2);

    @GET("index.php?d=API&c=Launch&m=updatePref&platform=Android")
    Observable<List<Response>> updatePref(@Query("user_id") String userId ,@Query("u_pref") String f1);








    class Factory {

        public static ApiService create() {

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .writeTimeout(1,TimeUnit.MINUTES)
                    .readTimeout(1, TimeUnit.MINUTES)
                    .addInterceptor(interceptor)
                    .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                    .addNetworkInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Chain chain) throws IOException {
                            Request.Builder builder = chain.request().newBuilder();
                            Request request = builder.build();

                            return chain.proceed(request);
                        }


                    }).build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(ApiService.class);
        }
    }

    static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Response originalResponse = chain.proceed(chain.request());
            int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
    };

}
