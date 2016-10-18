package tech.town.app.com.apptowntech.fragment;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import tech.town.app.com.apptowntech.R;
import tech.town.app.com.apptowntech.adapter.HomeCategoryList;
import tech.town.app.com.apptowntech.model.CPost;
import tech.town.app.com.apptowntech.model.ExtraInfo;
import tech.town.app.com.apptowntech.model.HomeCategory;
import tech.town.app.com.apptowntech.model.body.Response;
import tech.town.app.com.apptowntech.model.bookmark.Favourite;
import tech.town.app.com.apptowntech.presenter.FavouritePresenter;
import tech.town.app.com.apptowntech.presenter.HomePresenter;
import tech.town.app.com.apptowntech.utils.AppPref;
import tech.town.app.com.apptowntech.utils.Apputil;
import tech.town.app.com.apptowntech.utils.ItemClickSupport;
import tech.town.app.com.apptowntech.utils.Navigation;
import tech.town.app.com.apptowntech.view.FavouriteMvpView;
import tech.town.app.com.apptowntech.view.HomeMvpView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommonFragment extends Fragment implements HomeCategoryList.OnAddFavClickListener,FavouriteMvpView {

    private RecyclerView recyclerView;
    private  List<CPost> homeCategoryList;
    private HomeCategoryList mAdapter;
    private String mCatID;
    private String mCatName;
    private ProgressDialog mProgressDialog;
    private AppPref mAppPref;
    private boolean isLoadComplete=true;

    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private HomePresenter mHomePresenter;
    private int temp=0;

    private Context context;
    public CommonFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_common, container, false);
        initViews(rootView);
        return  rootView;
    }

    private void initViews(View rootView){
        mAppPref=new AppPref(getActivity());
        recyclerView = (RecyclerView)rootView.findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                CPost cPost=mAdapter.getItem(position);
                Navigation.launchDescription(getActivity(),mCatID,cPost.getPId(),mCatName);
            }
        });

        mHomePresenter=new HomePresenter();
        mHomePresenter.attachView(homeMvpView);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {

                temp =linearLayoutManager.findFirstVisibleItemPosition();
                if(dy > 0) {
                    visibleItemCount =linearLayoutManager.getChildCount();
                    totalItemCount = linearLayoutManager.getItemCount();
                    pastVisiblesItems =linearLayoutManager.findFirstVisibleItemPosition();
                   /* if(mCount==totalItemCount){
                        return;
                    }*/

                    if (isLoadComplete) {
                        if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            isLoadComplete = false;
                            callAPI("down",totalItemCount-1);
                        }
                    }
                 }
                if(dy<0){
                    if(isLoadComplete){
                        if(temp==0){
                            isLoadComplete = false;
                            callAPI("up",0);
                        }
                    }

                }

            }
        });

    }

    private void callAPI(String direction,int pos) {
        mHomePresenter.loadCategory(mCatID,direction,homeCategoryList.get(pos).getPId());
    }

    public void displayNewsCategory(List<CPost> homeCategory,String catId,String categoryName){
         mCatID=catId;
         mCatName=categoryName;
         homeCategoryList=homeCategory;
         mAdapter = new HomeCategoryList(getActivity(),homeCategory,this);
        if(recyclerView==null){
            return;
        }
         recyclerView.setAdapter(mAdapter);

    }

    public void notifydataChange(List<CPost> cPost){
        homeCategoryList.addAll(cPost);
        mAdapter.notifydatachange(cPost);
    }

    @Override
    public void addedFavourite(String pid, int postion) {

        FavouritePresenter favouritePresenter=new FavouritePresenter();
        favouritePresenter.attachView(this);
        if(TextUtils.isEmpty(mAppPref.getAccessToken(getActivity()))) {
            Apputil.showToast(getActivity(),"Please login");
            return;
        }
        if(homeCategoryList.get(postion).isAddedToFavourite()) {
            homeCategoryList.get(postion).setAddedToFavourite(false);
            favouritePresenter.removeFavourite(mAppPref.getAccessToken(getActivity()),pid);
        }else{
            homeCategoryList.get(postion).setAddedToFavourite(true);
            favouritePresenter.addToFavourite(mAppPref.getAccessToken(getActivity()),pid,mCatID);
        }
        mAdapter.notifydatachange(homeCategoryList);

    }

    @Override
    public void Result(Response result) {


        if(result.getReturn().getMessage().equalsIgnoreCase("success")){
            mProgressDialog.dismiss();
            Apputil.showToast(getActivity(),result.getMsg().getUserMessage());

        }
    }

    @Override
    public void showMessage(String message) {
        mProgressDialog.dismiss();
        Apputil.showToast(getActivity(),"Something went wrong.Please try later");
    }

    @Override
    public void showProgressIndicator() {
        mProgressDialog = Apputil.createProgressDialog(getActivity());
        mProgressDialog.show();
    }


    @Override
    public void getBookMarkList(List<Favourite> favouriteList) {

    }
    @Override
    public Context getContext() {
        return getActivity();
    }


    HomeMvpView homeMvpView=new HomeMvpView() {
        @Override
        public void showHomeResult(List<HomeCategory> categoryList, ExtraInfo extraInfo) {
            mProgressDialog.dismiss();
            isLoadComplete = true;

            try{
                ((OnCategoryRefreshResult) context).categoryRefresh(categoryList);
            }catch (ClassCastException cce){

            }

        }

        @Override
        public void showMessage(String message) {
            mProgressDialog.dismiss();
            isLoadComplete = true;
            Apputil.showToast(getActivity(),"Something went wrong.Please try later");
        }

        @Override
        public void showProgressIndicator() {
            mProgressDialog = Apputil.createProgressDialog(getActivity());
            mProgressDialog.show();
        }

        @Override
        public Context getContext() {
            return getActivity();
        }
    };

    public interface OnCategoryRefreshResult {
        void categoryRefresh(List<HomeCategory> homeCategoryList);
    }


    @TargetApi(23)
    @Override public void onAttach(Context context) {
        //This method avoid to call super.onAttach(context) if I'm not using api 23 or more
        //if (Build.VERSION.SDK_INT >= 23) {
        super.onAttach(context);
        onAttachToContext(context);
        //}
    }

    private void onAttachToContext(Context context) {

        this.context=context;
    }

    /*
     * Deprecated on API 23
     * Use onAttachToContext instead
     */
    @SuppressWarnings("deprecation")
    @Override public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < 23) {
            onAttachToContext(activity);
        }
    }

}
