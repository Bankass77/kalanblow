package ml.kalanblow.gestiondesinscriptions.util;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageUtils {
    public static byte[] resizeImage(byte[] originalImage, int width, int height) throws IOException {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(originalImage);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            Thumbnails.of(inputStream)
                    .size(width, height)
                    .crop(Positions.CENTER)
                    .outputFormat("jpg")
                    .toOutputStream(outputStream);

            return outputStream.toByteArray();
        }
    }
}
