package com.kriger.jewelrystorebackend.services;
import com.kriger.jewelrystorebackend.dao.MediaDAO;
import com.kriger.jewelrystorebackend.models.Media;
import com.kriger.jewelrystorebackend.responses.BasicResponse;
import com.kriger.jewelrystorebackend.responses.MediaListResponse;
import com.kriger.jewelrystorebackend.responses.MediaResponse;
import org.springframework.stereotype.Service;
import java.util.List;
import static com.kriger.jewelrystorebackend.errors.MediaErrors.*;

@Service
public class MediaService {
    private final MediaDAO mediaDAO;

    public MediaService(MediaDAO mediaDAO){
        this.mediaDAO = mediaDAO;
    }

    public MediaResponse addMedia(Media media) {
        if (media.getProductId() == null)
            return new MediaResponse(false, INVALID_PRODUCT_ID, null);
        if (media.getUrl() == null || media.getUrl().isEmpty())
            return new MediaResponse(false, INVALID_MEDIA_URL, null);

        int rowsAffected = this.mediaDAO.addMedia(media);
        if (rowsAffected == 0)
            return new MediaResponse(false, MEDIA_ADD_FAILED, null);

        return new MediaResponse(true, null, media);
    }

    public MediaResponse getMediaById(Long id) {
        Media media = this.mediaDAO.getMediaById(id);
        if (media == null)
            return new MediaResponse(false, MEDIA_NOT_FOUND, null);

        return new MediaResponse(true, null, media);
    }

    public MediaListResponse getMediaListByProductId(Long productId) {
        List<Media> mediaList = this.mediaDAO.getMediaListByProductId(productId);
        if (mediaList == null || mediaList.isEmpty())
            return new MediaListResponse(false, MEDIA_NOT_FOUND, null);

        return new MediaListResponse(true, null, mediaList);
    }

    public BasicResponse removeMediaById(Long id) {
        int rowsAffected = this.mediaDAO.removeMediaById(id);
        if (rowsAffected == 0)
            return new BasicResponse(false, MEDIA_NOT_FOUND);
        return new BasicResponse(true, null);
    }
}