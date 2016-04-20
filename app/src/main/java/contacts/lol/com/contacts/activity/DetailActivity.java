package contacts.lol.com.contacts.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import contacts.lol.com.contacts.R;
import contacts.lol.com.contacts.popupwindow.PopupWindowDeleteContact;

public class DetailActivity extends Activity {

    String mContact_id; //contact
    String mRawContact_id;  //contact

    String mLast_time_contact;
    String mDisplay_name ; //rawContact  display_name
    String mPhone_number;// data
    String mEmail;  //data
    int mIntimacy;//亲密度
    String mContact_count; //contact   times_contact
    String mAddress; //data
    String mGroup;  //data
    Bitmap mContact_icon;//null



    private Button btn_modify;
    private Button btn_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initView();
        initdata();
    }

    private void initdata() {
        testReadAllContacts();

    }



    private void initView() {

     btn_modify = (Button)findViewById(R.id.btn_modify);
        btn_delete = (Button)findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                show_delete_popupWindow();
            }
        });
        btn_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, ModifyActivity.class);
                startActivity(intent);

            }
        });
    }

    public void show_delete_popupWindow(){



        PopupWindowDeleteContact takePhotoPopWin = new PopupWindowDeleteContact(this);
            //showAtLocation(View parent, int gravity, int x, int y)
        //Gravity.BOTTOM展示在底部
            takePhotoPopWin.showAtLocation(findViewById(R.id.ll_activity_detail), Gravity.BOTTOM, 0, 0);



    }




    /*
     * 读取联系人的信息
     */
    public void testReadAllContacts() {
        Cursor cursor = this.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        int contactIdIndex = 0;
        int nameIndex = 0;

        if(cursor.getCount() > 0) {
            contactIdIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
        }
        while(cursor.moveToNext()) {
            String contactId = cursor.getString(contactIdIndex);
            String name = cursor.getString(nameIndex);
            Log.i("contacts", contactId);
            Log.i("contacts", name);

            /*
             * 查找该联系人的phone信息
             */

            Cursor phones = this.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId,
                    null, null);
            int phoneIndex = 0;
            if(phones.getCount() > 0) {
                phoneIndex = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            }
            while(phones.moveToNext()) {
                String phoneNumber = phones.getString(phoneIndex);
                Log.i("contacts", phoneNumber);
            }

            /*
             * 查找该联系人的email信息
             */
            Cursor emails = this.getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=" + contactId,
                    null, null);
            int emailIndex = 0;
            if(emails.getCount() > 0) {
                emailIndex = emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA);
            }
            while(emails.moveToNext()) {
                String email = emails.getString(emailIndex);
                Log.i("contacts", email);
            }

        }
    }
}
