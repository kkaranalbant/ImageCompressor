/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.imagecompressor.controller;

import com.kaan.imagecompressor.dao.ImageSendingRequest;
import com.kaan.imagecompressor.service.ImageCompressService;
import java.io.IOException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author kaan
 */
@RestController
public class ImageCompressController {

    private ImageCompressService imageCompressService ;
    
    public ImageCompressController (ImageCompressService imageCompressService) {
        this.imageCompressService = imageCompressService ;
    }
    
    @PostMapping("/compress")
    public @ResponseBody byte [] compress (@RequestBody ImageSendingRequest imageSendingRequest) {
        try {
            return imageCompressService.compress(imageSendingRequest.getImage()) ;
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        return null ;
    }
    
}
