package com.kriger.jewelrystorebackend.controllers;
import com.kriger.jewelrystorebackend.models.Media;
import com.kriger.jewelrystorebackend.responses.BasicResponse;
import com.kriger.jewelrystorebackend.responses.MediaListResponse;
import com.kriger.jewelrystorebackend.responses.MediaResponse;
import com.kriger.jewelrystorebackend.services.MediaService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/media")
public class MediaController {
    private final MediaService mediaService;

    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @PostMapping
    public MediaResponse addMedia(@RequestBody Media media){
        return this.mediaService.addMedia(media);
    }

    @GetMapping("/{id}")
    public MediaResponse getMediaById(@PathVariable Long id){
        return this.mediaService.getMediaById(id);
    }

    @GetMapping("/product/{productId}")
    public MediaListResponse getMediaListByProductId(@PathVariable Long productId){
        return this.mediaService.getMediaListByProductId(productId);
    }

    @DeleteMapping("/{id}")
    public BasicResponse removeMediaById(@PathVariable Long id){
        return this.mediaService.removeMediaById(id);
    }
}