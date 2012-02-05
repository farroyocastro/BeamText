package com.adictosaltrabajo.tutoriales.android.beam;

import android.app.Activity;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcAdapter.OnNdefPushCompleteCallback;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.adictosaltrabajo.tutoriales.android.beam.nfc.NdefMessageFactory;

public class PruebaAndroidBeamActivity extends Activity implements CreateNdefMessageCallback, OnNdefPushCompleteCallback {

	private static final String EMPTY_STRING = "";
	public static final String LOG_TAG = "BeamText";
	private static final int MESSAGE_SENT = 1;
	private EditText textMessage;
	private Button clearMessageBt;
	
	private NfcAdapter nfcAdapter;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        configureTextMessage();
        configureClearButton();
        configureNFC();
    }

	private void configureNFC() {
		Log.d(LOG_TAG, "Configurando NFC para la aplicación");
		nfcAdapter = NfcAdapter.getDefaultAdapter(this);
		checkNFCStatus();
		nfcAdapter.setNdefPushMessageCallback(this, this);
		nfcAdapter.setOnNdefPushCompleteCallback(this, this);
	}

	private void checkNFCStatus() {
		if (nfcAdapter == null) {						
            Toast.makeText(this, getString(R.string.noNFC), Toast.LENGTH_LONG).show();
        }
	}

	private void configureClearButton() {
		clearMessageBt = (Button) findViewById(R.id.btClear);
		clearMessageBt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				textMessage.setText(EMPTY_STRING);
			}
		});
	}

	private void configureTextMessage() {
		textMessage = (EditText) findViewById(R.id.etMessage);
	}

	@Override
	public void onNdefPushComplete(NfcEvent event) {
        mHandler.obtainMessage(MESSAGE_SENT).sendToTarget();
	}
	
	private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case MESSAGE_SENT:
                Toast.makeText(getApplicationContext(), getString(R.string.messageSent), Toast.LENGTH_LONG).show();
                break;
            }
        }
    };        

	@Override
	public NdefMessage createNdefMessage(NfcEvent event) {		
		Log.d(LOG_TAG, "createNdefMessage: " + getMessage());
		return NdefMessageFactory.createNdefMessage(getMessage());
	}

	private String getMessage() {
		return textMessage.getText().toString();
	}
}