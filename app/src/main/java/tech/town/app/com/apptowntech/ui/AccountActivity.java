package tech.town.app.com.apptowntech.ui;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tech.town.app.com.apptowntech.R;
import tech.town.app.com.apptowntech.adapter.MyRecyclerAdapter;
import tech.town.app.com.apptowntech.model.Account;
import tech.town.app.com.apptowntech.model.AccountHeader;
import tech.town.app.com.apptowntech.model.login.Login;
import tech.town.app.com.apptowntech.presenter.LoginPresenter;
import tech.town.app.com.apptowntech.utils.AppPref;
import tech.town.app.com.apptowntech.utils.Apputil;
import tech.town.app.com.apptowntech.utils.ItemClickSupport;
import tech.town.app.com.apptowntech.utils.Logger;
import tech.town.app.com.apptowntech.utils.Navigation;
import tech.town.app.com.apptowntech.view.LoginMvpView;

public class AccountActivity extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener,LoginMvpView {


    private CallbackManager callbackManager;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;
    private LoginPresenter mLoginPresenter;
    private String name;
    private String url="";
    private RecyclerView recyclerView;
    private MyRecyclerAdapter adapter;
    private AppPref appPref;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_account);
        callbackManager = CallbackManager.Factory.create();
        appPref=new AppPref(this);
        initGoogleClient();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.account_title);
        setToolBarBackButton(toolbar);
        mLoginPresenter=new LoginPresenter();
        mLoginPresenter.attachView(this);



        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        adapter = new MyRecyclerAdapter(this,getHeader(),getListItems(),googleClickListener,facebookClickListener);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        handleFacebookLogin();

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {


                switch (position){
                    case 1:
                        if (testLogin()) return;
                        Navigation.launchCustomizeNews(AccountActivity.this);
                        break;
                    case 2:
                        if (testLogin()) return;
                        Navigation.launchBookmark(AccountActivity.this);
                        break;
                    case 3:
                        if (testLogin()) return;
                        Navigation.launchReport(AccountActivity.this);
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        if (testLogin()) return;
                        Navigation.launchFeedback(AccountActivity.this);
                        break;
                    case 7:
                        try {
                            //Open the specific App Info page:
                            Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            intent.setData(Uri.parse("package:" + getPackageName()));
                            startActivity(intent);

                        } catch (ActivityNotFoundException e) {
                            //e.printStackTrace();

                            //Open the generic Apps page:
                            Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
                            startActivity(intent);

                        }
                        break;
                    case 8:
                        Apputil.rateMyApp(AccountActivity.this);
                        break;
                    case 9:
                        Apputil.shareMyApp(AccountActivity.this);
                        break;
                    case 10:
                        Navigation.LaunchYouTubeVideo(AccountActivity.this,
                                "http://apptowntechnologies.com/demo/livetoday/pages/aboutus.html",getString(R.string.aboutus));
                        break;
                    case 11:
                        Navigation.LaunchYouTubeVideo(AccountActivity.this,
                                "https://livetoday.online/our-privacy-policy",getString(R.string.rules));
                        break;
                    case 12:
                        Apputil.rateMyApp(AccountActivity.this);
                        break;
                    case 13:
                        logout();
                        break;

                }






            }
        });
    }

    private boolean testLogin() {
        if (TextUtils.isEmpty(appPref.getAccessToken(AccountActivity.this))) {
            Apputil.showToast(AccountActivity.this, "Please logged in");
            return true;
        }
        return false;
    }

    private void handleFacebookLogin() {

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {

                                        try {


                                            URL imageURL = new URL("https://graph.facebook.com/" + object.getString("id") + "/picture?type=small");
                                            name=object.getString("first_name");
                                            url=imageURL.toString();
                                            postUserDetailToServer(object.getString("first_name"), "test@gmail.com", "FB", imageURL.toString());

                                        } catch (MalformedURLException e) {
                                            e.printStackTrace();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }


                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id, first_name, last_name, email,gender,birthday,location");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });
    }

    private void facebookSocialLogin() {


        Logger.d("On facebook called");
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends"));
    }

    private List<Account> getListItems() {
        List<Account>  list=new ArrayList<>();
        int max;

        String[] ar=getResources().getStringArray(R.array.setting_info);
        if(TextUtils.isEmpty(appPref.getAccessToken(this))){
            max=ar.length-1;
        }else{
            max=ar.length;
        }
        Account account=null;
        for(int i=0;i<max;i++){
            account=new Account();
            account.setName(ar[i]);
            list.add(account);
        }
        return  list;
    }

    private AccountHeader getHeader() {

        AccountHeader accountHeader=new AccountHeader();
        accountHeader.setUserTitle("sample Text");
        accountHeader.setImageUrl("https://placeholdit.imgix.net/~text?txtsize=15&txt=image1&w=120&h=120");
        return  accountHeader;
    }

    private void postUserDetailToServer(String first_name, String email, String type, String imageUrl) {
        mProgressDialog = Apputil.createProgressDialog(AccountActivity.this);
        mLoginPresenter.loadPollResult(first_name,email,type,imageUrl);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result==null) {
                return;
            }
            GoogleSignInAccount acct = result.getSignInAccount();
            if (acct != null) {
                name=acct.getDisplayName();
                if(acct.getPhotoUrl()!=null){
                    url=acct.getPhotoUrl().toString();
                }
                postUserDetailToServer(acct.getDisplayName(),acct.getEmail(),"Google",url);
            }
        }else{
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void gPlusSignIn() {

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }
    private void initGoogleClient() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                //  .requestIdToken(getResources().getString(R.string.server_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this ,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void Result(Login result) {
        mProgressDialog.dismiss();
        if(result.getReturn().getMessage().equalsIgnoreCase("success")){


            appPref.saveAccessToke(result.getMsg().getUserId().toString());
            appPref.saveUserIcon(url);
            appPref.saveUserName(name);
            adapter=null;
            adapter = new MyRecyclerAdapter(this,getHeader(),getListItems(),googleClickListener,facebookClickListener);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void showMessage(String message) {
        mProgressDialog.dismiss();

    }

    @Override
    public void showProgressIndicator() {
        mProgressDialog.show();
    }

    @Override
    public Context getContext() {
        return this;
    }

    MyRecyclerAdapter.OnFacebookClickListener facebookClickListener=new MyRecyclerAdapter.OnFacebookClickListener() {
        @Override
        public void onFbClick() {

            facebookSocialLogin();
        }
    };

    MyRecyclerAdapter.OnGoogleClickListener googleClickListener=new MyRecyclerAdapter.OnGoogleClickListener() {
        @Override
        public void onGoogleClick() {

            gPlusSignIn();
        }
    };

    private void  logout(){


        new AlertDialog.Builder(AccountActivity.this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to Logout?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        appPref.saveAccessToke("");


                        LoginManager.getInstance().logOut();
                        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                                new ResultCallback<Status>() {
                                    @Override
                                    public void onResult(Status status) {
                                        // ...
                                    }
                                });

                        adapter=null;
                        adapter = new MyRecyclerAdapter(AccountActivity.this,getHeader(),
                                getListItems(),googleClickListener,facebookClickListener);
                        recyclerView.setAdapter(adapter);

                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();


    }
}
