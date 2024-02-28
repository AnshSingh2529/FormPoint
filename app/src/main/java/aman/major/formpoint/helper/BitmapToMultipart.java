package aman.major.formpoint.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class BitmapToMultipart {
    public static MultipartBody.Part bitmapToMultipart(String path,Bitmap imageBitmap,Context context) {
        if (imageBitmap != null) {
            try {
                // Convert Bitmap to File
                File file = convertBitmapToFile(imageBitmap,context);
                Log.d("bitmapToMultipart", "convertBitmapToFile  :   "+ file.getName() +" file status: "+file.exists());
                Log.d("bitmapToMultipart", "bitmapToMultipart: Value "+path+"Bitmap: "+imageBitmap);
                // Create RequestBody instance from file
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);

                // Create MultipartBody.Part instance from RequestBody
                return MultipartBody.Part.createFormData(path, file.getName(), requestBody);
            } catch (IOException e) {
                Log.d("bitmapToMultipart", "IOException  :   " + e.getMessage());
                e.printStackTrace();
                return null;
            }
        }else {
            return null;
        }
    }

    private static File convertBitmapToFile(Bitmap bitmap, Context context) throws IOException {
        // Create a temporary file to save the image
        File filesDir = context.getApplicationContext().getCacheDir();
        File file = new File(filesDir, Calendar.getInstance().getTimeInMillis() +"_image.png");
        file.createNewFile();

        // Convert bitmap to byte array
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] bitmapData = bos.toByteArray();

        // Write the bytes in file
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(bitmapData);
        fos.flush();
        fos.close();

        return file;
    }
}
