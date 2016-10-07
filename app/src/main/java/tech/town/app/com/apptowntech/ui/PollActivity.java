package tech.town.app.com.apptowntech.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.List;

import tech.town.app.com.apptowntech.R;
import tech.town.app.com.apptowntech.adapter.PollAdapter;
import tech.town.app.com.apptowntech.model.body.Response;
import tech.town.app.com.apptowntech.model.poll.PollList;
import tech.town.app.com.apptowntech.model.poll.PollResult;
import tech.town.app.com.apptowntech.presenter.PollPresenter;
import tech.town.app.com.apptowntech.utils.AppPref;
import tech.town.app.com.apptowntech.utils.Apputil;
import tech.town.app.com.apptowntech.utils.Logger;
import tech.town.app.com.apptowntech.view.PollMvpView;

public class PollActivity extends BaseActivity implements PollMvpView,PollAdapter.OnSubmitClickListener {

    private ProgressDialog mProgressDialog;
    private TextView mTitleNumber;
    private ViewPager mPager;
    private List<PollList> mPollList;
    private int mPosition=1;
    private PollPresenter pollPresenter;
    private PollAdapter pollAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.poll_title);
        setToolBarBackButton(toolbar);
        mTitleNumber=(TextView)findViewById(R.id.pagenumber);
        pollPresenter=new PollPresenter();
        pollPresenter.attachView(this);
        pollPresenter.loadPollResult();
        mPager=(ViewPager)findViewById(R.id.poll_pager);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                mPosition=position+1;
                mTitleNumber.setText("<  "+mPosition+" / "+mPollList.size()+"  >");

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void showResult(PollResult pollResult) {
        mProgressDialog.dismiss();
        if(pollResult==null){
            return;
        }

        mPollList=pollResult.getPollList();
        mTitleNumber.setText("<  "+mPosition+" / "+mPollList.size()+"  >");

        pollAdapter=new PollAdapter(this,pollResult.getPollList(),this);
        mPager.setAdapter(pollAdapter);

    }

    @Override
    public void showMessage(String message) {
        mProgressDialog.dismiss();
    }

    @Override
    public void showProgressIndicator() {
        mProgressDialog = Apputil.createProgressDialog(PollActivity.this);
        mProgressDialog.show();
    }

    @Override
    public void submitPollResult(Response result) {
        mProgressDialog.dismiss();
        if(result.getReturn().getMessage().equalsIgnoreCase("success")){
            mProgressDialog.dismiss();
            Apputil.showToast(this,result.getMsg().getUserMessage());
            pollAdapter.notidatachange(true);
            mPager.setAdapter(pollAdapter);
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onSubmit(String value, int pos) {
        Logger.d(value);

        pollPresenter.submitPoll(new AppPref(this).getAccessToken(this),""+pos,value);
    }
}
