package tech.town.app.com.apptowntech.utils;

import android.content.Context;
import android.content.Intent;

import tech.town.app.com.apptowntech.ui.AccountActivity;
import tech.town.app.com.apptowntech.ui.BookMarkNewsActivity;
import tech.town.app.com.apptowntech.ui.BrowserActivity;
import tech.town.app.com.apptowntech.ui.CategoryListActivity;
import tech.town.app.com.apptowntech.ui.CommentActivity;
import tech.town.app.com.apptowntech.ui.CustomNewsActivity;
import tech.town.app.com.apptowntech.ui.FeedbackActivity;
import tech.town.app.com.apptowntech.ui.HomeActivity;
import tech.town.app.com.apptowntech.ui.NewsDetailActivity;
import tech.town.app.com.apptowntech.ui.PollActivity;
import tech.town.app.com.apptowntech.ui.ProfileActivity;
import tech.town.app.com.apptowntech.ui.RadioActivity;
import tech.town.app.com.apptowntech.ui.ReportActivity;
import tech.town.app.com.apptowntech.ui.VideoActivity;

/**
 * Created by ${="Ashish"} on 11/9/16.
 */
public class Navigation {



    public static  final String CAT_ID="cat_id";
    public static  final String POST_ID="post_id";
    public static  final String CAT_Name="cat_name";

    public static void launchHome(Context context){
        context.startActivity(new Intent(context, HomeActivity.class));
    }
    public static void launchProfile(Context context){

        context.startActivity(new Intent(context, ProfileActivity.class));
    }

    public static void launchReport(Context context){

        context.startActivity(new Intent(context, ReportActivity.class));
    }

    public static void launchDescription(Context context, String catId, String postId, String mCatName){

        context.startActivity(new Intent(context, NewsDetailActivity.class).
                putExtra(Navigation.CAT_ID,catId).
                putExtra(Navigation.POST_ID,postId).
                putExtra(Navigation.CAT_Name,mCatName));
    }
    public static void LaunchYouTubeVideo(Context context,String youTubeUrl,String title){

        /*context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(youTubeUrl)));*/

        context.startActivity(new Intent(context, BrowserActivity.class).putExtra("url",youTubeUrl).putExtra("title",title));

    }
    public static void LaunchAccount(Context context){

        context.startActivity(new Intent(context, AccountActivity.class));

    }
    public static void LaunchPoll(Context context){

        context.startActivity(new Intent(context, PollActivity.class));

    }
    public static void launchCustomizeNews(Context context){

        context.startActivity(new Intent(context, CustomNewsActivity.class));
    }
    public static void launchFeedback(Context context){

        context.startActivity(new Intent(context, FeedbackActivity.class));
    }
    public static void launchBookmark(Context context){

        context.startActivity(new Intent(context, BookMarkNewsActivity.class));
    }
    public static void launchVideo(Context context,String url,String title){


        AppPref appPref=new AppPref(context);
        appPref.saveOriention(false);

        context.startActivity(new Intent(context, VideoActivity.class).putExtra("url",url).putExtra("title",title));
    } public static void launchComment(Context context,String json){

        context.startActivity(new Intent(context, CommentActivity.class).putExtra("list",json));
    }

    public static void launchCategoryList(Context context, String catId, String postId, String mCatName){

        context.startActivity(new Intent(context, CategoryListActivity.class).
                putExtra(Navigation.CAT_ID,catId).
                putExtra(Navigation.POST_ID,postId).
                putExtra(Navigation.CAT_Name,mCatName));
    }
    public static void launchRadio(Context context,String url,String title) {

        context.startActivity(new Intent(context, RadioActivity.class).putExtra("url", url).putExtra("title", title));
    }

}
