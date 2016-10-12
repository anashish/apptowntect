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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import tech.town.app.com.apptowntech.R;
import tech.town.app.com.apptowntech.adapter.HomeList;
import tech.town.app.com.apptowntech.model.ExtraInfo;
import tech.town.app.com.apptowntech.model.HomeCategory;
import tech.town.app.com.apptowntech.presenter.HomePresenter;
import tech.town.app.com.apptowntech.utils.Apputil;
import tech.town.app.com.apptowntech.utils.DividerItemDecoration;
import tech.town.app.com.apptowntech.utils.ItemClickSupport;
import tech.town.app.com.apptowntech.utils.Logger;
import tech.town.app.com.apptowntech.utils.Navigation;
import tech.town.app.com.apptowntech.view.HomeMvpView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    private RecyclerView recyclerView;
    private  List<HomeCategory> homeCategoryList;
    private HomeList adapter;
    private  Context context;
    private int temp=0;

    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private boolean isLoadComplete=true;

    private HomePresenter mHomePresenter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_home, container, false);
        initViews(rootView);
        return  rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHomePresenter=new HomePresenter();
        mHomePresenter.attachView(homeMvpView);
    }

    private void initViews(View rootView){
        recyclerView = (RecyclerView)rootView.findViewById(R.id.card_recycler_home);
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {


                Navigation.launchDescription(getActivity(),homeCategoryList.get(0).getCId(),homeCategoryList.get(0).getCPost().get(position-1).getPId(),homeCategoryList.get(0).getCName());
            }
        });

        if(homeCategoryList!=null){
            displayHomeCategory(homeCategoryList);
        }

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

    private void callAPI(String up, int i) {

        Logger.d("POSS "+i);

        mHomePresenter.loadCategory(homeCategoryList.get(0).getCId(),up,homeCategoryList.get(0).getCPost().get(i).getPId());

    }

    public void displayHomeCategory(List<HomeCategory> homeCategory){
        homeCategoryList=homeCategory;


        if(recyclerView==null|| getActivity()==null || homeCategory==null || homeCategory.size()==0){
            return;
        }

        adapter = new HomeList(getActivity(),homeCategory.get(0).getCPost(),homeCategory.get(0).getCId(),homeCategory.get(0).getCName());
        recyclerView.setAdapter(adapter);

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

    private ProgressDialog mProgressDialog;
    HomeMvpView homeMvpView=new HomeMvpView() {
        @Override
        public void showHomeResult(List<HomeCategory> categoryList, ExtraInfo extraInfo) {
            mProgressDialog.dismiss();
            isLoadComplete = true;
            homeCategoryList.get(0).getCPost().addAll(categoryList.get(0).getCPost());
            adapter.notifyHomeDataChange(homeCategoryList.get(0).getCPost());

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
}
