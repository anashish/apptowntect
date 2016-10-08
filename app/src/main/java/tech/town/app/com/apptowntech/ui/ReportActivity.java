package tech.town.app.com.apptowntech.ui;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import tech.town.app.com.apptowntech.R;
import tech.town.app.com.apptowntech.model.body.Response;
import tech.town.app.com.apptowntech.presenter.ReportPresenter;
import tech.town.app.com.apptowntech.utils.AppPref;
import tech.town.app.com.apptowntech.utils.Apputil;
import tech.town.app.com.apptowntech.utils.PermissionUtils;
import tech.town.app.com.apptowntech.view.ReportMvpView;

public class ReportActivity extends BaseActivity implements PopupMenu.OnMenuItemClickListener, View.OnClickListener,ReportMvpView {

    private EditText mEditTextTitle;
    private PopupMenu popupMenu;
    private static final int SELECT_FILE = 200;
    private String mImagePath;
    private ImageView mIconLoaded;
    private ImageView mIconClose;
    private EditText mEditTextSubject;
    private EditText mEditTextComment;
    private AppPref appPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.report_title);
        setToolBarBackButton(toolbar);

        appPref=new AppPref(this);

        mEditTextTitle=(EditText)findViewById(R.id.editText_report_category);
        mEditTextSubject=(EditText)findViewById(R.id.editText_report_subject);
        mEditTextComment=(EditText)findViewById(R.id.editText_report_comment);
        mIconLoaded=(ImageView)findViewById(R.id.imageView_camera_icon);
        mIconClose=(ImageView)findViewById(R.id.imageView_close);
        ImageView launchCamera=(ImageView)findViewById(R.id.imageView_launch_camera);
        Button submit=(Button)findViewById(R.id.report_submit);



        mIconClose.setOnClickListener(onClickListener);
        launchCamera.setOnClickListener(onClickListener);
        submit.setOnClickListener(onClickListener);

        mEditTextTitle.setKeyListener(null);

        String[] title=getResources().getStringArray(R.array.report_title);
        mEditTextTitle.setText(title[0]);

        popupMenu = new PopupMenu(this, findViewById(R.id.editText_report_category));

        for(int i=0;i<title.length;i++){
            popupMenu.getMenu().add(Menu.NONE, i, Menu.NONE, title[i]);
        }
        popupMenu.setOnMenuItemClickListener(this);
        mEditTextTitle.setOnClickListener(this);
        popupMenu.setOnMenuItemClickListener(this);
        mEditTextTitle.setOnClickListener(this);

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        mEditTextTitle.setText(item.getTitle());
        return false;
    }

    @Override
    public void onClick(View view) {

        popupMenu.show();
    }
    public void openGalleryPermission() {

        if (!PermissionUtils.getInstance().hasPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            PermissionUtils.getInstance().needPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE, SELECT_FILE, "Select File!");
            // Now you will get callback in onRequestPermissionsResult() method for further code
        } else {
            openGallery();

        }
    }
    private void openGallery() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case SELECT_FILE:

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery();
                } else {
                    Apputil.showToast(this,"You do not have permission");
                }
                break;
        }


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==SELECT_FILE) {

            try {
                if (data != null) {
                    Uri picUri = data.getData();
                    mImagePath = getPath(picUri);
                    Glide.with(this)
                            .load(mImagePath) // Uri of the picture
                            .into(mIconLoaded);
                    mIconClose.setVisibility(View.VISIBLE);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private ProgressDialog mProgressDialog;
    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.imageView_close:
                    mIconClose.setVisibility(View.INVISIBLE);
                    mImagePath="";
                    mIconLoaded.setImageResource(R.color.white);
                    break;
                case R.id.imageView_launch_camera:
                    openGalleryPermission();
                    break;
                case R.id.report_submit:

                    if (TextUtils.isEmpty(appPref.getAccessToken(ReportActivity.this))) {
                        Apputil.showToast(ReportActivity.this, "Please logged in");
                        return;
                    }

                    if(TextUtils.isEmpty(mEditTextSubject.getText().toString())) {
                        mEditTextSubject.setError("Field not not be left blank");
                        return;
                    }if(TextUtils.isEmpty(mEditTextComment.getText().toString())) {
                       mEditTextComment.setError("Field not not be left blank");
                       return;
                     }

                    ReportPresenter reportPresenter=new ReportPresenter();
                    reportPresenter.attachView(ReportActivity.this);
                    reportPresenter.sendFeedback(appPref.
                            getAccessToken(ReportActivity.this),mEditTextSubject.getText().toString(),
                            mEditTextComment.getText().toString(),mEditTextTitle.getText().toString());
                    break;
            }
        }
    };

    public String getPath(Uri uri) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri,
                filePathColumn, null, null, null);
        String picturePath = null;
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();
        }
        return picturePath;
    }

    @Override
    public void Result(Response result) {
        mProgressDialog.dismiss();

        if(result.getReturn().getMessage().equalsIgnoreCase("success")){
            mProgressDialog.dismiss();
            Apputil.showToast(this,result.getMsg().getUserMessage());
        }
    }

    @Override
    public void showMessage(String message) {
        mProgressDialog.dismiss();
        Apputil.showToast(this,"Something went wrong.Please try later");
    }

    @Override
    public void showProgressIndicator() {
        mProgressDialog = Apputil.createProgressDialog(ReportActivity.this);
        mProgressDialog.setCancelable(true);
        mProgressDialog.show();
    }

    @Override
    public Context getContext() {
        return this;
    }
}
