package com.kriger.jewelrystorebackend.responses;
import com.kriger.jewelrystorebackend.models.Media;
import java.util.List;

public class MediaListResponse extends BasicResponse{
    private List<Media> mediaList;

    public MediaListResponse(boolean success, String errorMessage, List<Media> mediaList) {
        super(success, errorMessage);
        this.mediaList = mediaList;
    }

    public List<Media> getMediaList() {
        return mediaList;
    }

    public void setMediaList(List<Media> mediaList) {
        this.mediaList = mediaList;
    }
}
