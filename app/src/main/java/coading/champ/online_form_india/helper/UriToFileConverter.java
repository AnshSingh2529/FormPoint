package coading.champ.online_form_india.helper;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.OpenableColumns;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class UriToFileConverter {

    public static File convertUriToFile(Context context, Uri uri) {
        String fileName = getFileName(context.getContentResolver(), uri);
        File picturesDirectory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File outputFile = new File(picturesDirectory, fileName);
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            if (inputStream != null) {
                writeInputStreamToFile(inputStream, outputFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return outputFile;
    }

    private static String getFileName(ContentResolver contentResolver, Uri uri) {
        String fileName = "unknown";
        Cursor cursor = null;

        try {
            cursor = contentResolver.query(uri, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                int displayNameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                if (displayNameIndex != -1) {
                    fileName = cursor.getString(displayNameIndex);
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return fileName;
    }

    private static void writeInputStreamToFile(InputStream inputStream, File outputFile) throws IOException {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(outputFile);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        } finally {
            if (fos != null) {
                fos.close();
            }
            inputStream.close();
        }
    }

    public static File convertUriToFile2(Context context, Uri uri) {
        String fileName = getFileName(context.getContentResolver(), uri);
        File outputFile = null;

        try {
            // Create a temporary file in the cache directory
            File cacheDir = context.getCacheDir();
            outputFile = File.createTempFile(fileName, null, cacheDir);

            // Write the content of the InputStream to the temporary file
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            if (inputStream != null) {
                writeInputStreamToFile(inputStream, outputFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return outputFile;
    }
}
