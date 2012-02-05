package com.adictosaltrabajo.tutoriales.android.beam;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ShowMessage extends Activity {

	private static final String LOG_TAG = "ShowMessage";
	
	private Button btClose;
	private Button btReply;
	private TextView tvShowMessage;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showmessage);
		
		configureCloseButton();
		configureReplyButton();
		configureMessateTV();
	}

	private void configureReplyButton() {
		btReply = (Button) findViewById(R.id.btReply);
		btReply.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				launchAndroidBeamActivity();
			}
		});
	}

	protected void launchAndroidBeamActivity() {
		startActivity(new Intent(this, PruebaAndroidBeamActivity.class));
	}

	private void configureMessateTV() {
		tvShowMessage = (TextView) findViewById(R.id.showMessageTV);
	}

	private void configureCloseButton() {
		btClose = (Button) findViewById(R.id.btClose);
		btClose.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				closeActivity();
			}
		});
	}

	protected void closeActivity() {
		this.finish();
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.d(LOG_TAG, "On resume");
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
			processIntent(getIntent());
		}
	}

	@Override
	public void onNewIntent(Intent intent) {
		Log.d(LOG_TAG, "On new Intent");
		setIntent(intent);
	}

	private void processIntent(Intent intent) {
		Parcelable[] rawMsgs = intent
				.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
		NdefMessage msg = (NdefMessage) rawMsgs[0];
		final String message = new String(msg.getRecords()[0].getPayload());		
		Log.d(LOG_TAG, "processIntent: " + message);
		tvShowMessage.setText(message);
	}

}
