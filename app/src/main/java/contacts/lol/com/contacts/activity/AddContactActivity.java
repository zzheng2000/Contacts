package contacts.lol.com.contacts.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.File;

import contacts.lol.com.contacts.R;
import contacts.lol.com.contacts.clipCircleImage.ClipHeaderActivity;

public class AddContactActivity extends Activity {

    //用于截图
    private static final int RESULT_CAPTURE = 100;
    private static final int RESULT_PICK = 101;
    private static final int CROP_PHOTO = 102;
    private File tempFile;
    ImageView iv_head_icon;
    private ImageView iv_minus;
    private Button btn_return;
    private ImageView iv_plus;
    private Button btn_save;
    private EditText et_address;
    private EditText et_emnail;
    private EditText et_lastname;
    private EditText et_firstname;
    private EditText et_intimacy;
    private EditText et_phone;

    //用于截图

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        initView();
        initData(savedInstanceState);

    }





    private void initView() {
        iv_minus = (ImageView) findViewById(R.id.iv_minus);
        iv_plus = (ImageView) findViewById(R.id.iv_plus);
        btn_return = (Button) findViewById(R.id.btn_return);
        btn_save = (Button) findViewById(R.id.btn_save);
        iv_head_icon = (ImageView) findViewById(R.id.iv_head_icon);
        et_lastname = (EditText) findViewById(R.id.et_lastname);
        et_firstname = (EditText) findViewById(R.id.et_firstname);
        et_intimacy = (EditText) findViewById(R.id.et_intimacy);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_emnail = (EditText) findViewById(R.id.et_emnail);
        et_address = (EditText) findViewById(R.id.et_address);



        iv_head_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChooseDialog();
            }
        });

        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用数据库保存接口
                finish();
            }
        });

        //亲密度
        iv_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        iv_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


    private void initData(Bundle savedInstanceState) {


        //从数据库初始化数据



    if (savedInstanceState != null && savedInstanceState.containsKey("tempFile")) {
        tempFile = (File) savedInstanceState.getSerializable("tempFile");
    }else{
        tempFile = new File(checkDirPath(Environment.getExternalStorageDirectory().getPath()+"/clipHeaderLikeQQ/image/"),
                System.currentTimeMillis() + ".jpg");
    }
}

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("tempFile", tempFile);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        switch (requestCode) {
            case RESULT_CAPTURE:
                if (resultCode == RESULT_OK) {
                    starCropPhoto(Uri.fromFile(tempFile));
                }
                break;
            case RESULT_PICK:
                if (resultCode == RESULT_OK) {
                    Uri uri = intent.getData();

                    starCropPhoto(uri);
                }

                break;
            case CROP_PHOTO:
                if (resultCode == RESULT_OK) {

                    if (intent != null) {
                        setPicToView(intent);
                    }
                }
                break;

            default:
                break;
        }
    }

    private void showChooseDialog() {
        new AlertDialog.Builder(this)
                .setCancelable(true)
                .setItems(new String[]{"相机", "相册"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
                            startActivityForResult(intent, RESULT_CAPTURE);
                        } else {
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(Intent.createChooser(intent, "请选择图片"), RESULT_PICK);
                        }
                    }
                }).show();
    }


    /**
     * 打开截图界面
     * @param uri 原图的Uri
     */
    public void starCropPhoto(Uri uri) {

        if (uri == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(this, ClipHeaderActivity.class);
        intent.setData(uri);
        intent.putExtra("side_length", 200);//裁剪图片宽高
        startActivityForResult(intent, CROP_PHOTO);

        //调用系统的裁剪
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.setDataAndType(uri, "image/*");
//        intent.putExtra("crop", "true");
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
//        // outputX outputY 是裁剪图片宽高
//        intent.putExtra("outputX", crop);
//        intent.putExtra("outputY", crop);
//        intent.putExtra("return-data", true);
//        intent.putExtra("noFaceDetection", true);
//        startActivityForResult(intent, CROP_PHOTO);
    }

    private void setPicToView(Intent picdata) {
        Uri uri = picdata.getData();

        if (uri == null) {
            return;
        }

        iv_head_icon.setImageURI(uri);

    }


    /**
     *
     * @param dirPath
     * @return
     */
    private static String checkDirPath(String dirPath) {
        if (TextUtils.isEmpty(dirPath)) {
            return "";
        }

        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dirPath;
    }



}
