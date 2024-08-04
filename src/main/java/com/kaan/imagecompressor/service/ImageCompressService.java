/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.imagecompressor.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import org.springframework.stereotype.Service;

/**
 *
 * @author kaan
 */
@Service
public class ImageCompressService {

    private static final Random RANDOM;

    private static final byte FILE_NAME_LENGTH;

    private static final byte DIGIT;

    private static final byte LOWER_CASE;

    private static final byte UPPER_CASE;

    static {
        RANDOM = new Random();
        FILE_NAME_LENGTH = 15;
        UPPER_CASE = 3;
        LOWER_CASE = 2;
        DIGIT = 1;
    }

    public byte[] compress(byte[] originalImageBytes) throws IOException {
        String originalFileName = createRandomFileName();
        String compressedFileName = createRandomFileName();
        File originalFile = new File(originalFileName);
        File compressedFile = new File(compressedFileName);
        originalFile.createNewFile();
        compressedFile.createNewFile();
        FileOutputStream originalFileFos = new FileOutputStream(originalFile) ;
        ImageOutputStream imageOS = ImageIO.createImageOutputStream(originalFileFos);
        imageOS.write(originalImageBytes);
        imageOS.close();
        originalFile = new File (originalFileName) ;
        BufferedImage originalImage = ImageIO.read(originalFile);
        ImageWriter imageWriter = ImageIO.getImageWritersByFormatName("jpeg").next();
        OutputStream fos = new FileOutputStream(compressedFile);
        imageWriter.setOutput(ImageIO.createImageOutputStream(fos));
        ImageWriteParam params = imageWriter.getDefaultWriteParam();
        params.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        params.setCompressionQuality(0.5f);
        imageWriter.write(null, new IIOImage(originalImage, null, null), params);
        compressedFile = new File (compressedFileName);
        FileInputStream fis = new FileInputStream(compressedFile) ;
        return fis.readAllBytes() ;
    }

    private String createRandomFileName() {
        StringBuilder sb = new StringBuilder();
        while (sb.length() != FILE_NAME_LENGTH) {
            int choice = RANDOM.nextInt(DIGIT, UPPER_CASE + 1);
            if (choice == DIGIT) {
                sb.append(RANDOM.nextInt(0, 10));
            } else if (choice == LOWER_CASE) {
                sb.append((char) (RANDOM.nextInt(97, 123)));
            } else {
                sb.append((char) (RANDOM.nextInt(65, 91)));
            }
            if (sb.length() == FILE_NAME_LENGTH) {
                File file = new File(sb.toString());
                if (file.exists()) {
                    sb.delete(0, sb.length() - 1);
                }
            }
        }
        return sb.toString();
    }

}
