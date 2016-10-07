package tech.town.app.com.apptowntech.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import tech.town.app.com.apptowntech.R;
import tech.town.app.com.apptowntech.adapter.CommentList;
import tech.town.app.com.apptowntech.model.body.Response;
import tech.town.app.com.apptowntech.model.itemdetail.ItemComment;
import tech.town.app.com.apptowntech.presenter.CommentPresenter;
import tech.town.app.com.apptowntech.utils.AppPref;
import tech.town.app.com.apptowntech.utils.Apputil;
import tech.town.app.com.apptowntech.view.CommentMvpView;

public class CommentActivity extends AppCompatActivity implements CommentMvpView {

    private TextView mTextViewCounter;
    private EditText mEditTextComment;
    private ProgressDialog mProgressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        mTextViewCounter=(TextView)findViewById(R.id.textView_counter) ;
        mEditTextComment=(EditText)findViewById(R.id.editText_report_comment);
        mEditTextComment.addTextChangedListener(mTextEditorWatcher);

        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);


        Bundle bundle=getIntent().getExtras();
        if(bundle!=null) {
            String json=bundle.getString("list");
            Type type = new TypeToken< List<ItemComment>>() {}.getType();
            List<ItemComment> itemComments = new Gson().fromJson(json, type);
            CommentList adapter=new CommentList(this,itemComments);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(adapter);

        }


        ImageView iconClose=(ImageView)findViewById(R.id.imageView6);
        ImageView iconSend=(ImageView)findViewById(R.id.imageView7);
        iconClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        iconSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppPref appPref=new AppPref(CommentActivity.this);

                CommentPresenter commentPresenter=new CommentPresenter();
                commentPresenter.attachView(CommentActivity.this);
                commentPresenter.sendComment(appPref.getAccessToken(CommentActivity.this),mEditTextComment.getText().toString(),"1");
            }
        });


    }
    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //This sets a textview to the current length
            mTextViewCounter.setText("500/"+(500-s.length()));
        }

        public void afterTextChanged(Editable s) {
        }
    };

    @Override
    public void Result(Response result) {
        mProgressDialog.dismiss();
        Apputil.showToast(this,result.getMsg().getUserMessage());
        finish();
    }

    @Override
    public void showMessage(String message) {

        mProgressDialog.dismiss();
    }

    @Override
    public void showProgressIndicator() {

        mProgressDialog = Apputil.createProgressDialog(CommentActivity.this);
        mProgressDialog.setCancelable(true);
        mProgressDialog.show();
    }

    @Override
    public Context getContext() {
        return this;
    }
}
