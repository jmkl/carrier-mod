package in.jmkl.dcsms.carrierimage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

public class Carrier extends ImageView {
	private Context context;
	private boolean mAttached;

	public Carrier(Context context) {
		super(context);
		this.context = context;

	}

	public Carrier(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();

		if (!mAttached) {
			mAttached = true;
			IntentFilter filter = new IntentFilter();
			filter.addAction("in.jmkl.dcsms.updateCarrier");
			getContext().registerReceiver(mIntentReceiver, filter, null,
					getHandler());
		}

		updateCarrier();

	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		if (mAttached) {
			getContext().unregisterReceiver(mIntentReceiver);
			mAttached = false;
		}
	}

	private final BroadcastReceiver mIntentReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals("in.jmkl.dcsms.updateCarrier")) {
				Toast.makeText(context, "receive", 2).show();
				updateCarrier();

			}
		}
	};

	protected void updateCarrier() {
		setImageURI(null);
		Context c = null;
		try {
			c = context.createPackageContext("in.jmkl.dcsms.carrierimage", 0);
		} catch (NameNotFoundException e) {	}
		SharedPreferences p = c.getSharedPreferences("dcsms", context.MODE_WORLD_READABLE);	
		if(p.contains("png")){
		setImageURI(Uri.parse(p.getString("png", null)));
		}
		p=null;
	}

}

