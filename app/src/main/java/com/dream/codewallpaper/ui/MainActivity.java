package com.dream.codewallpaper.ui;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import com.dream.codewallpaper.*;
import com.dream.codewallpaper.service.*;
import com.dream.codewallpaper.view.*;
import android.util.*;

public class MainActivity extends Activity {
	private EditText etText;
	private CodeView code;
	public static char[] text={'0','1','0','1','0','1','0','1','0','1','0','1','0','1','0','1','0','1','0','1'};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		etText = findViewById(R.id.activitymainEditText1);
		code = findViewById(R.id.activitymainCodeView1);
		Log.i("test","test");
    }

	public void setWallpaper(View view) {
		//判断代码雨壁纸是否已经设置
		WallpaperInfo info=WallpaperManager.getInstance(this).getWallpaperInfo();
		boolean isWallpaper=((info != null) && (info.getPackageName().equals(this.getPackageName())) &&
			(info.getServiceName().equals(LiveWallpaperService.class.getCanonicalName())));
		if (!isWallpaper) {
			Intent it=new Intent();
			it.setAction(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
			it.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT
						, new ComponentName(this.getPackageName()
											, LiveWallpaperService.class.getCanonicalName()));
			startActivityForResult(it, 0);
		} else {
			Toast.makeText(this, "壁纸已经设置", Toast.LENGTH_SHORT).show();
		}
	}

	public void onComplete(View view) {
		String str=etText.getText().toString();
		char[] charText=str.toCharArray();
		code.setText(charText);
		text = charText;
		Toast.makeText(this, "设置文本成功", Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO: Implement this method
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 0 && resultCode == RESULT_OK) {
			Toast.makeText(MainActivity.this, "设置壁纸成功", Toast.LENGTH_SHORT).show();
			return;
		}
		Toast.makeText(MainActivity.this, "取消设置壁纸", Toast.LENGTH_SHORT).show();
	}

}
