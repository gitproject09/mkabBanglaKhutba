package org.mkab.bangla_khutba.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.mkab.bangla_khutba.R;
import org.mkab.bangla_khutba.util.Keys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class PostDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "PostDetailActivity";


    //private CommentAdapter mAdapter;
    private TextView mAuthorView, mTitleView, mBodyView, mMobile, mEmail, mAddress, mOthers;
    //	private EditText mCommentField;
    //private RecyclerView mCommentsRecycler;
    private ImageView memberImage;
    private ImageView imgCall;
    private ImageView imgMessage;
    private ImageView imgEmail;
    private ImageView imgShare;
    private ImageView imgSaveContact;
    // private LinearLayout llShare;
    private String fileName = "";
    private String typeGroup = "";

    private String sMobileNum, sEmailAdd, sTitleName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mTitleView = findViewById(R.id.post_title);
        mBodyView = findViewById(R.id.post_body);
        mEmail = findViewById(R.id.post_email);
        mMobile = findViewById(R.id.post_mobile);
        mAddress = findViewById(R.id.post_address);
        mOthers = findViewById(R.id.post_others);

        imgCall = findViewById(R.id.call);
        imgMessage = findViewById(R.id.message);
        imgEmail = findViewById(R.id.email);
        imgShare = findViewById(R.id.share);
        imgSaveContact = findViewById(R.id.ivAddToContact);
        // llShare = findViewById(R.id.llShare);

        imgCall.setOnClickListener(this);
        imgMessage.setOnClickListener(this);
        imgEmail.setOnClickListener(this);
        imgShare.setOnClickListener(this);
        imgSaveContact.setOnClickListener(this);

        String region = getIntent().getStringExtra(Keys.KEY_REGION);
        String district = getIntent().getStringExtra(Keys.KEY_DISTRICT);
        String majlishName = getIntent().getStringExtra(Keys.KEY_MAJLISH_NAME);
        String name = getIntent().getStringExtra(Keys.KEY_A_NAME);
        String address = getIntent().getStringExtra(Keys.KEY_A_ADDRESS);
        String email = getIntent().getStringExtra(Keys.KEY_A_EMAIL);
        String mobileNo = getIntent().getStringExtra(Keys.KEY_A_MOBILE_NO);

        String chanda = getIntent().getStringExtra(Keys.KEY_A_CHANDA);
        String bookNo = getIntent().getStringExtra(Keys.KEY_A_BOOK_NO);
        String roshidNo = getIntent().getStringExtra(Keys.KEY_A_ROSHID_NO);
        String addDate = getIntent().getStringExtra(Keys.KEY_A_ADD_DATE);
        String expireDate = getIntent().getStringExtra(Keys.KEY_A_EXPIRE_DATE);
        String comment = getIntent().getStringExtra(Keys.KEY_A_COMMENT);

        mTitleView.setText(name);
        mBodyView.setText(majlishName + ", " + district + ", " + region);
        mEmail.setText(email);
        mMobile.setText(mobileNo);
        mAddress.setText(address);
        mOthers.setText("Chanda : " + chanda + "  Roshid No : " + roshidNo + "  Book No : " + bookNo + "\nRenew Date : " + addDate + "   Expire on : " + expireDate);

        sTitleName = name;
        sMobileNum = mobileNo;
        sEmailAdd = email;

        getSupportActionBar().setTitle(name);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.call:
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + sMobileNum));
                if (sMobileNum.equals("")) {
                    Toast.makeText(PostDetailActivity.this, "No Mobile No found", Toast.LENGTH_LONG).show();
                    return;
                }
                if (ActivityCompat.checkSelfPermission(PostDetailActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intent);
                break;

            case R.id.message:
                if (sMobileNum.equals("")) {
                    Toast.makeText(PostDetailActivity.this, "No Mobile No found", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    Intent msgIntent = new Intent(PostDetailActivity.this, MessageSendActivity.class);
                    msgIntent.putExtra(MessageSendActivity.EXTRA_SMS_RECEIVER, sTitleName);
                    msgIntent.putExtra(MessageSendActivity.EXTRA_SMS_ADDRESS, sMobileNum);
                    startActivity(msgIntent);
                }
                break;

            case R.id.email:
                if (sEmailAdd.equals("")) {
                    Toast.makeText(PostDetailActivity.this, "No Email Address found", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    Intent emlInt = new Intent(PostDetailActivity.this, MailSendActivity.class);
                    emlInt.putExtra(MailSendActivity.EXTRA_MAIL_ADDRESS, sEmailAdd);
                    startActivity(emlInt);
                }
                break;

            case R.id.share:

                if (sMobileNum.equals("")) {
                    Toast.makeText(PostDetailActivity.this, "No Mobile No found", Toast.LENGTH_LONG).show();
                    return;
                }
                String sBody = "Name : " + sTitleName + "\n" + "Mobile : " + sMobileNum;
                Intent sIntent = new Intent(Intent.ACTION_SEND);
                sIntent.setType("text/plain");
                sIntent.putExtra(Intent.EXTRA_SUBJECT, "Contacts");
                sIntent.putExtra(Intent.EXTRA_TEXT, sBody);
                //sharingIntent.putExtra(android.content.Intent.ACTION_CALL, "+88" + sMobileNum);
                startActivity(Intent.createChooser(sIntent, "Share this contact using..."));
                break;

            case R.id.ivAddToContact:

                if (sMobileNum.equals("")) {
                    Toast.makeText(PostDetailActivity.this, "No Mobile No found", Toast.LENGTH_LONG).show();
                    return;
                }

                // Creates a new Intent to insert a contact
                Intent saveContactIntent = new Intent(ContactsContract.Intents.Insert.ACTION);
                // Sets the MIME type to match the Contacts Provider
                saveContactIntent.setType(ContactsContract.RawContacts.CONTENT_TYPE);

                saveContactIntent.putExtra(ContactsContract.Intents.Insert.NAME, sTitleName)
                        .putExtra(ContactsContract.Intents.Insert.PHONE, sMobileNum)
                        .putExtra(ContactsContract.Intents.Insert.EMAIL, sEmailAdd)
                        .putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                        .putExtra(ContactsContract.Intents.Insert.EMAIL_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_WORK);

                startActivity(saveContactIntent);
                break;


        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}