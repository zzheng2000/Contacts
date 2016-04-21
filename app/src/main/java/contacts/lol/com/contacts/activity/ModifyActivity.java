package contacts.lol.com.contacts.activity;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import contacts.lol.com.contacts.R;

public class ModifyActivity extends Activity {
    private ImageView iv_minus;
    private Button btn_return;
    private ImageView iv_plus;
    private Button btn_save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        initView();
        initData();

    }

    private void initData() {

    }

    private void initView() {
        iv_minus = (ImageView) findViewById(R.id.iv_minus);
        iv_plus = (ImageView) findViewById(R.id.iv_plus);
        btn_return = (Button) findViewById(R.id.btn_return);
        btn_save = (Button) findViewById(R.id.btn_save);


        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用数据库更新接口
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
}
