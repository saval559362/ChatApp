package com.example.chatapp.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class PictureCoder {

    public String toBase64(BitmapDrawable drawableImg) {
        // Получаем изображение из ImageView
        Bitmap bitmap = drawableImg.getBitmap();

        // Записываем изображение в поток байтов.
        // При этом изображение можно сжать и / или перекодировать в другой формат.
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

        // Получаем изображение из потока в виде байтов
        byte[] bytes = byteArrayOutputStream.toByteArray();

        // Кодируем байты в строку Base64 и возвращаем
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    public Bitmap fromBase64(String image) {
        // Декодируем строку Base64 в массив байтов
        byte[] decodedString = Base64.decode(image, Base64.DEFAULT);

        // Декодируем массив байтов в изображение
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        return decodedByte;
    }

}
