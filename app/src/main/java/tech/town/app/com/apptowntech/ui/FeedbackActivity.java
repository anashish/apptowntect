package tech.town.app.com.apptowntech.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import tech.town.app.com.apptowntech.R;
import tech.town.app.com.apptowntech.model.body.Response;
import tech.town.app.com.apptowntech.presenter.FeedbackPresenter;
import tech.town.app.com.apptowntech.utils.AppPref;
import tech.town.app.com.apptowntech.utils.Apputil;
import tech.town.app.com.apptowntech.utils.Logger;
import tech.town.app.com.apptowntech.view.FeedbackMvpView;

public class FeedbackActivity extends BaseActivity implements FeedbackMvpView{


    private RadioGroup mRadioGroupOne;
    private RadioGroup mRadioGroupTwo;
    private RadioGroup mRadioGroupThree;
    private ProgressDialog mProgressDialog;
    private FeedbackPresenter mFeedbackPresenter;
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.suggestion);
        setToolBarBackButton(toolbar);


        mFeedbackPresenter=new FeedbackPresenter();
        mFeedbackPresenter.attachView(this);

        mRadioGroupOne=(RadioGroup)findViewById(R.id.radio_group_one);
        mRadioGroupTwo=(RadioGroup)findViewById(R.id.radio_group_two);
        mRadioGroupThree=(RadioGroup)findViewById(R.id.radio_group_three);
        Button button=(Button)findViewById(R.id.report_submit);
        mEditText=(EditText)findViewById(R.id.editText_report_comment);


        button.setOnClickListener(onClickListener);

        displaySectionOne();
        displaySectionTwo();
        displaySectionThree();



    }
    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            String value1,value2,value3;
            try {
                 value1 = ((RadioButton)findViewById(mRadioGroupOne.getCheckedRadioButtonId())).getText().toString();
                 value2 = ((RadioButton)findViewById(mRadioGroupTwo.getCheckedRadioButtonId())).getText().toString();
                 value3 = ((RadioButton)findViewById(mRadioGroupTwo.getCheckedRadioButtonId())).getText().toString();
                Logger.d("radio group value "+value1);
                Logger.d("radio group value "+value2);
                Logger.d("radio group value "+value3);
            }catch (Exception e) {
                Apputil.showToast(FeedbackActivity.this,"Incorrect selection");
                return;
            }

            Apputil.hideKeyboard(FeedbackActivity.this);
            if(TextUtils.isEmpty(mEditText.getText().toString())) {
                mEditText.setError("Field can not be left blank");
                return;
            }

            mFeedbackPresenter.sendFeedback(new AppPref(FeedbackActivity.this).
                    getAccessToken(FeedbackActivity.this),
                    value1,value2,value3,mEditText.getText().toString());

        }
    };

    private void displaySectionTwo() {
        for(int i=1;i<6;i++){
            RadioButton radioButton = (RadioButton) getLayoutInflater().inflate(R.layout.radio_button, null);
            radioButton.setText(""+i);
            RadioGroup.LayoutParams params
                    = new RadioGroup.LayoutParams(this, null);
            params.setMargins(10, 10, 10, 10);

            radioButton.setLayoutParams(params);
            mRadioGroupTwo.addView(radioButton);
        }
    }

    private void displaySectionOne() {
        for(int i=1;i<6;i++){
            RadioButton radioButton = (RadioButton) getLayoutInflater().inflate(R.layout.radio_button, null);
            radioButton.setText(""+i);
            RadioGroup.LayoutParams params
                    = new RadioGroup.LayoutParams(this, null);
            params.setMargins(10, 10, 10, 10);

            radioButton.setLayoutParams(params);
            mRadioGroupOne.addView(radioButton);
        }

    }
    private void displaySectionThree() {
        for(int i=1;i<6;i++){
            RadioButton radioButton = (RadioButton) getLayoutInflater().inflate(R.layout.radio_button, null);
            radioButton.setText(""+i);
            RadioGroup.LayoutParams params
                    = new RadioGroup.LayoutParams(this, null);
            params.setMargins(10, 10, 10, 10);

            radioButton.setLayoutParams(params);
            mRadioGroupThree.addView(radioButton);
        }

    }

    @Override
    public void Result(Response result) {
        if(result.getReturn().getMessage().equalsIgnoreCase("success")){
            mProgressDialog.dismiss();
            Apputil.showToast(this,result.getMsg().getUserMessage());
            finish();
        }
    }

    @Override
    public void showMessage(String message) {

        mProgressDialog.dismiss();
        Apputil.showToast(this,"Something went wrong.Please try later");
    }

    @Override
    public void showProgressIndicator() {

        mProgressDialog = Apputil.createProgressDialog(FeedbackActivity.this);
        mProgressDialog.show();
    }

    @Override
    public Context getContext() {
        return this;
    }
}
