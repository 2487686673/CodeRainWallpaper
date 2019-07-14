package com.dream.codewallpaper.view;

/**
 * 代码雨视图
 * @author 梦雪
 * @email 2487686673@qq.com
 * @date 2019/07/13.
 */
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.util.*;
import android.view.*;

public class CodeSurface {
	//显示的文本
	private char[] text = {'0','1','0','1','0','1','0','1','0','1','0','1','0','1','0','1','0','1','0','1'};
	//画笔
	private Paint paint;
	//文本大小
	private float textSize=30f;
	//行
	private int line=60;
	//列
	private int column=60;
	private Cell[][] cells;

	public CodeSurface() {
		//实例化画笔
		paint = new Paint();
		//设置抗锯齿
		paint.setAntiAlias(true);
		//设置画笔颜色
		paint.setColor(Color.WHITE);
		//设置文字大小
		paint.setTextSize(textSize);
		//设置文字对齐方式
		paint.setTextAlign(Paint.Align.LEFT);
		//设置样式
		paint.setStyle(Paint.Style.FILL);
		cells = new Cell[column][line];
		for (int i=0;i < column;i++) {
			for (int j=0;j < line;j++) {
				cells[i][j] = new Cell(i, j);
				cells[i][j].alpha = 0;
				cells[i][j].msg = text[(int)(Math.random() * text.length)] + "";
			}
		}
	}

	public void onDraw(SurfaceHolder holder, float width, float height) {
		// TODO: Implement this method
		Canvas canvas=null;
		try {
			canvas = holder.lockCanvas();
			if (canvas != null) {
				//画布颜色
				canvas.drawColor(Color.BLACK);
				for (int i=0;i < column;i++) {
					for (int j=0;j < line;j++) {
						Cell cell=cells[i][j];
						//根据透明度设置颜色
						if (cell.alpha == 255) {
							paint.setColor(Color.CYAN);
						} else {
							paint.setColor(Color.GREEN);
						}
						//设置透明度
						paint.setAlpha(cell.alpha);
						if (cell.alpha != 0) {
							canvas.drawText(cell.msg, cell.line * 20f, (float)(cell.column * textSize * 1.5 + textSize), paint);
						}
					}
				}
				handler.sendEmptyMessageDelayed(0, 60);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (canvas != null) {
				holder.unlockCanvasAndPost(canvas);
			}
		}
	}

	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			for (int i = 0; i < column; i++) {
				for (int j = line - 1; j >= 0; j--) {
					//1.如果第一行透明度为0，则有几率变为255;
					//2、如果中间行透明度为0，不做处理
					//3、中间行不为0，依次减少一个梯度
					//4、我上面的一个是255，那么我也是255,而他亮度减1
					Cell cell = cells[i][j];
					if (j == 0) {
						if (cell.alpha == 0) {
							if (Math.random() * 10 > 9) {
								cell.alpha = 255;
							}
						} else {
							cell.alpha = cell.alpha - 25 > 0 ?cell.alpha - 25: 0;
						}
					} else if (j > 0 && j <= line - 1) {
						if (cells[i][j - 1].alpha == 255) {
							cell.alpha = 255;
						} else {cell.alpha = cell.alpha - 25 > 0 ?cell.alpha - 25: 0;
						}
					}
				}
			} 
		}
	};

	public void setText(char[] str) {
		this.text = str;
		cells = new Cell[column][line];
		for (int i=0;i < column;i++) {
			for (int j=0;j < line;j++) {
				cells[i][j] = new Cell(i, j);
				cells[i][j].alpha = 0;
				cells[i][j].msg = text[(int)(Math.random() * text.length)] + "";
			}
		}
	}

	class Cell {
		//行
		private int line;
		//列
		private int column;
		//内容
		private String msg;
		//透明度
		private int alpha;
		public Cell(int line, int column) {
			this.line = line;
			this.column = column;
		}
	}
}
