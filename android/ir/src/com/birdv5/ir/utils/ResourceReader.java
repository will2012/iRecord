package com.birdv5.ir.utils;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

/**
 * @author : 
 * date    : 
 * 读取Assets文件夹内的数据
 */
public class ResourceReader {

	/**
	 * 以InputStream方式读取Assets文件夹内的文件。
	 * @param context Android环境上下文
	 * @param fileInAssets 在Assets中的文件路径
	 * @return InputStream对象
	 * @throws IOException
	 */
	public static InputStream readAsInputStream (Context context, String fileInAssets) throws IOException{
		return context.getAssets().open(fileInAssets);
	}

	/**
	 * 将res文件读为Drawable对象。
	 * @param context Android环境上下文
	 * @param resId 资源ID
	 * @return Drawable对象
	 */
	public static Drawable readAsDrawable (Context context, int resId){
		return context.getResources().getDrawable(resId);
	}

	/**
	 * 将res文件读为Bitmap对象
	 * @param context Android环境上下文
	 * @param resId 资源ID
	 * @return Bitmap对象
	 *
	 */
	public static Bitmap readAsBitmap (Context context, int resId){
		return BitmapFactory.decodeResource(context.getResources(), resId);
	}

	/**
	 * 读取RAW中的文件为字符串
	 * @param context Android环境上下文
	 * @param resId 资源ID
	 * @return 字符串内容
	 * @throws NotFoundException
	 * @throws IOException
	 */
	public static String readRawAsString(Context context,int resId) throws NotFoundException, IOException{
		return InputStreamUtility.toString(context.getResources().openRawResource(resId));
	}
}