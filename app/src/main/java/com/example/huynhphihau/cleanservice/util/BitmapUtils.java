package com.example.huynhphihau.cleanservice.util;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by phihau on 6/18/17.
 */

public class BitmapUtils {
    /**
     * decode file to image with sample size
     *
     * @param file
     * @param sampleSize
     * @return
     */
    public static Bitmap decodeFileSize(File file, int sampleSize) {
        FileInputStream fis = null;
        try {
            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = sampleSize;
            o2.inPreferredConfig = Bitmap.Config.RGB_565;

            fis = new FileInputStream(file);
            Bitmap b = BitmapFactory.decodeStream(fis, null, o2);
            return b;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * decode file to bitmap with max size
     *
     * @param photoFilePath
     * @param maxSize
     * @return
     */
    public static Bitmap decodeFile(String photoFilePath, int maxSize) {
        try {
            File file = new File(photoFilePath);
            if (!file.exists()) {
                AppLog.e("AAA", "file not exits");
            }
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(photoFilePath, options);

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, maxSize, maxSize);

            // remove true
            options.inJustDecodeBounds = false;

            // get bitmap
            Bitmap bm = BitmapFactory.decodeFile(photoFilePath, options);

            // Read EXIF Data
            ExifInterface exif = new ExifInterface(photoFilePath);
            String orientString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
            int orientation = orientString != null ? Integer.parseInt(orientString) : ExifInterface.ORIENTATION_NORMAL;
            int rotationAngle = 0;
            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) rotationAngle = 90;
            if (orientation == ExifInterface.ORIENTATION_ROTATE_180) rotationAngle = 180;
            if (orientation == ExifInterface.ORIENTATION_ROTATE_270) rotationAngle = 270;
            // Rotate Bitmap
            Matrix matrix = new Matrix();
            matrix.setRotate(rotationAngle, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);

            // return bitmap with rotate
            return Bitmap.createBitmap(bm, 0, 0, options.outWidth, options.outHeight, matrix, true);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * calculate sample size
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    /**
     * create temp file from bitmap
     *
     * @param context
     * @param bitmap
     * @param name
     * @return
     */
    public static File createImageFromBitmap(Context context, Bitmap bitmap, String name) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes);

        File f = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + File.separator + name + ".png");
        BufferedOutputStream fo = null;
        try {
            f.createNewFile();
            // write the bytes in file
            fo = new BufferedOutputStream(new FileOutputStream(f));
            fo.write(bytes.toByteArray());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fo != null) {
                try {
                    fo.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return f;
    }

    /**
     * delete temp file
     *
     * @param context
     */
    public static void deleteImageTemp(Context context) {
        File f = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        for (String path : f.list()) {
            try {
                File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + File.separator + path);
                Uri uri = Uri.fromFile(file);
                if (file.delete()) {
                    AppLog.e("AAA", "Path delete success:" + path);
                    callBroadCast(context, uri);
                    deleteFileFromMediaStore(context, new File(uri.getPath()));
                }
            } catch (Exception ex) {
                AppLog.e("AAA", "Path delete fail:" + path);
            }
        }
    }

    /**
     * callBroadCast => not working with android 4.0
     *
     * @param context
     * @param uri
     */
    public static void callBroadCast(Context context, Uri uri) {
        MediaScannerConnection.scanFile(context, new String[]{uri.getPath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
            @Override
            public void onScanCompleted(String path, Uri uri) {
                Log.e("AAA", "Scanned " + path + ":");
            }
        });
    }

    /**
     * delete file from media store
     *
     * @param context
     * @param file
     */
    public static void deleteFileFromMediaStore(Context context, final File file) {
        ContentResolver contentResolver = context.getContentResolver();
        String canonicalPath;
        try {
            canonicalPath = file.getCanonicalPath();
        } catch (IOException e) {
            canonicalPath = file.getAbsolutePath();
        }
        final Uri uri = MediaStore.Files.getContentUri("external");
        final int result = contentResolver.delete(uri,
                MediaStore.Files.FileColumns.DATA + "=?", new String[]{canonicalPath});
        if (result == 0) {
            final String absolutePath = file.getAbsolutePath();
            if (!absolutePath.equals(canonicalPath)) {
                contentResolver.delete(uri,
                        MediaStore.Files.FileColumns.DATA + "=?", new String[]{absolutePath});
            }
        }
    }
}
