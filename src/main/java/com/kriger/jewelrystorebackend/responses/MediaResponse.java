package com.kriger.jewelrystorebackend.responses;
import com.kriger.jewelrystorebackend.models.Media;

public class MediaResponse extends BasicResponse{
    private Media media;

    public MediaResponse(boolean success, String errorMessage, Media media) {
        super(success, errorMessage);
        this.media = media;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }
}
