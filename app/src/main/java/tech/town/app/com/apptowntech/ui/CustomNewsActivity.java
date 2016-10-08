package tech.town.app.com.apptowntech.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import tech.town.app.com.apptowntech.R;
import tech.town.app.com.apptowntech.adapter.RecyclerListAdapter;
import tech.town.app.com.apptowntech.helper.OnStartDragListener;
import tech.town.app.com.apptowntech.helper.SimpleItemTouchHelperCallback;
import tech.town.app.com.apptowntech.model.HomeCategory;
import tech.town.app.com.apptowntech.model.body.Response;
import tech.town.app.com.apptowntech.presenter.NewsCustmizePresenter;
import tech.town.app.com.apptowntech.utils.AppPref;
import tech.town.app.com.apptowntech.utils.Apputil;
import tech.town.app.com.apptowntech.view.NewsCostimizeMvpView;

public class CustomNewsActivity extends BaseActivity implements OnStartDragListener,NewsCostimizeMvpView,RecyclerListAdapter.OnChecked {

    private ItemTouchHelper mItemTouchHelper;
    private List<HomeCategory> mCategoryList;
    private RecyclerListAdapter adapter;
    private AppPref appPref;
    private ProgressDialog mProgressDialog;

    public static boolean isNewPrefUpdate=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.custome_news);
        setToolBarBackButton(toolbar);





        appPref=new AppPref(this);

        String json=appPref.getHomeCategoryJson(this);
        Type type = new TypeToken< List< HomeCategory >>() {}.getType();
        mCategoryList = new Gson().fromJson(json, type);
        if(mCategoryList!=null && mCategoryList.size()>0){
            mCategoryList.remove(0);
            mCategoryList.remove(0);
            mCategoryList.remove(0);
        }


        adapter = new RecyclerListAdapter(this, this, mCategoryList,this);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);

        Button button=(Button)findViewById(R.id.update_pref);
        button.setOnClickListener(onClickListener);

    }
    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            StringBuilder stringBuilder=new StringBuilder();
            for(int i=0;i<mCategoryList.size();i++){
                if(mCategoryList.get(i).isSelected()){
                    stringBuilder.append(mCategoryList.get(i).getCId()+",");
                }
            }

            if(TextUtils.isEmpty(stringBuilder.toString())) {
                Apputil.showToast(CustomNewsActivity.this,"Please select some category");
                return;
            }



            NewsCustmizePresenter newsCustmizePresenter=new NewsCustmizePresenter();
            newsCustmizePresenter.attachView(CustomNewsActivity.this);
            newsCustmizePresenter.sendFeedback(appPref.getAccessToken(CustomNewsActivity.this),stringBuilder.toString());
        }
    };

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void Result(Response result) {

        mProgressDialog.dismiss();
        if(result.getReturn().getMessage().equalsIgnoreCase("success")){
            mProgressDialog.dismiss();
            isNewPrefUpdate=true;
            Apputil.showToast(this,result.getMsg().getUserMessage());
            finish();
        }
    }

    @Override
    public void showMessage(String message) {
        mProgressDialog.dismiss();
    }

    @Override
    public void showProgressIndicator() {
        mProgressDialog = Apputil.createProgressDialog(CustomNewsActivity.this);
        mProgressDialog.show();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onSelected(boolean b,int pos) {

        mCategoryList.get(pos).setSelected(b);


    }
}
