package in.jmkl.dcsms.carrierimage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Main extends Activity {
	private Button btn;
	private SharedPreferences pref;
	private String png = Environment.getExternalStorageDirectory()
			+ "/monabar.png";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		btn = (Button) findViewById(R.id.tbl);
		pref = getSharedPreferences("dcsms", Context.MODE_WORLD_READABLE);
		btn.setOnClickListener(new OnClickListener() {

			public void onClick(View vag) {
				Intent i = new Intent();
				i.setClassName("com.alensw.PicFolder",
						"com.alensw.PicFolder.GalleryActivity");
				i.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(i, 666);

			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == 666) {
				Uri uri = data.getData();
				String i = getDir(uri);
				String dir = getFilesDir() + "/carrier/";
				moveFile(i, dir);
				SharedPreferences.Editor edit = pref.edit();
				Toast.makeText(Main.this, dir, 2).show();
				edit.putString("png", dir + "dcsmscarrier.png");
				edit.commit();
				Intent send = new Intent();
				send.setAction("in.jmkl.dcsms.updateCarrier");
				sendBroadcast(send);

			}
		}

	}

	private String getDir(Uri contentUri) {
		String[] proj = { MediaStore.Images.Media.DATA };
		CursorLoader loader = new CursorLoader(getBaseContext(), contentUri,
				proj, null, null, null);
		Cursor cursor = loader.loadInBackground();
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	private void moveFile(String inputPath, String outputPath) {

		InputStream in = null;
		OutputStream out = null;
		try {

			// create output directory if it doesn't exist
			File dir = new File(outputPath);
			if (!dir.exists()) {
				dir.mkdirs();
				dir.setReadable(true, false);
				dir.setWritable(true, false);
				dir.setExecutable(true, false);

			} else {
				dir.setReadable(true, false);
				dir.setWritable(true, false);
				dir.setExecutable(true, false);;
			}

			in = new FileInputStream(inputPath);
			int n = (int) System.currentTimeMillis();
			out = new FileOutputStream(outputPath + "dcsmscarrier.png");

			byte[] buffer = new byte[1024];
			int read;
			while ((read = in.read(buffer)) != -1) {
				out.write(buffer, 0, read);
			}
			in.close();
			in = null;

			// write the output file
			out.flush();
			out.close();
			out = null;

			File f = new File(outputPath + "dcsmscarrier.png");
			if (f.exists()) {
				f.setReadable(true, false);
				f.setWritable(true, false);
				f.setExecutable(true, false);
			}

		}

		catch (FileNotFoundException fnfe1) {
			Log.e("tag", fnfe1.getMessage());
		} catch (Exception e) {
			Log.e("tag", e.getMessage());
		}

	}

}
