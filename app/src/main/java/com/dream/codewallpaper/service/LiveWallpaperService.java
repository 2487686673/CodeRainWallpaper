package com.dream.codewallpaper.service;
import android.service.wallpaper.*;
import android.service.wallpaper.WallpaperService.*;
import android.os.*;
import android.view.*;
import com.dream.codewallpaper.view.*;
import android.util.*;
import com.dream.codewallpaper.ui.*;
import android.text.*;

/**
 * 动态壁纸服务
 * @author 梦雪
 * @email 2487686673@qq.com
 * @date 2019/07/13.
 */
public class LiveWallpaperService extends WallpaperService {

	@Override
	public WallpaperService.Engine onCreateEngine() {
		// TODO: Implement this method
		return new LiveWallpaperEngine();
	}

	/**
	 *动态壁纸引擎
	 */
	class LiveWallpaperEngine extends WallpaperService.Engine {
		private Runnable runnable;
		private Handler handler;
		private CodeSurface codeSurface;
		private SurfaceHolder surfaceHolder;
		private int screenWidth;
		private int screenHeight;

		public LiveWallpaperEngine() {
			this.surfaceHolder = getSurfaceHolder();
			this.codeSurface = new CodeSurface();
			this.handler = new Handler();
			this.runnable = new Runnable(){

				@Override
				public void run() {
					// TODO: Implement this method
					LiveWallpaperEngine.this.drawView();
				}
			};
			this.handler.post(this.runnable);
			DisplayMetrics dm=getApplicationContext().getResources().getDisplayMetrics();
			screenWidth = dm.widthPixels;
			screenHeight = dm.heightPixels;
			this.codeSurface.setText(MainActivity.text);
		}

		private void drawView() {
			if (codeSurface != null) {
				this.handler.removeCallbacks(this.runnable);
				this.codeSurface.onDraw(this.surfaceHolder, screenWidth, screenHeight);
				if (isVisible()) {
					this.handler.postDelayed(this.runnable, 20l);
				}
			}
		}

		/**
		 * Surface创建时回调
		 */
        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
            this.drawView();
        }

		/**
		 * Surface改变时回调
		 */
        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
            this.drawView();
        }

		/**
		 * 当壁纸可见改变时回调
		 */
        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
            if (this.handler != null) {
				//判断当前壁纸是否可见
                if (visible) {
                    this.handler.removeCallbacks(this.runnable);
                    this.handler.post(this.runnable);
                } else {
                    this.handler.removeCallbacks(this.runnable);
                }
            }
        }

		/**
		 * Surface摧毁时回调
		 */
        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            if (this.handler != null) {
                this.handler.removeCallbacks(this.runnable);
            }
        }

		/**
		 * Service摧毁时回调
		 */
        @Override
        public void onDestroy() {
            super.onDestroy();
            if (this.handler != null) {
                this.handler.removeCallbacks(this.runnable);
            }
        }
	}
}
