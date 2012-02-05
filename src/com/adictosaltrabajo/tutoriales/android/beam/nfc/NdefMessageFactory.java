package com.adictosaltrabajo.tutoriales.android.beam.nfc;

import java.nio.charset.Charset;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;

public class NdefMessageFactory {
	
	private static final String MIMETYPE_BEAMTEXT = "application/com.adictosaltrabajo.tutoriales.android.beam";
	private final static Charset CHARSET_UTF8 = Charset.forName("UTF-8");
	private final static Charset CHARSET_US_ASCII = Charset.forName("US-ASCII");
	
	public static NdefMessage createNdefMessage(String message) {
		final NdefRecord[] records = createNfdRecord(message);
		final NdefMessage ndefMessage = new NdefMessage(records);
        return ndefMessage;
	}
	
	private static NdefRecord[] createNfdRecord(String message) {		
		return new NdefRecord[] {
				createMimeRecord(MIMETYPE_BEAMTEXT, message.getBytes(CHARSET_UTF8))
		};
	}

	public static NdefRecord createMimeRecord(String mimeType, byte[] payload) {
        byte[] mimeBytes = mimeType.getBytes(CHARSET_US_ASCII); 
        return new NdefRecord(NdefRecord.TNF_MIME_MEDIA, mimeBytes, new byte[0], payload);
    }

}
