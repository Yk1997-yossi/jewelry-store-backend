package com.kriger.jewelrystorebackend.services;

import com.kriger.jewelrystorebackend.dao.MediaDAO;
import com.kriger.jewelrystorebackend.dao.ProductDAO;
import com.kriger.jewelrystorebackend.models.Media;
import com.kriger.jewelrystorebackend.models.Product;
import com.kriger.jewelrystorebackend.responses.BasicResponse;
import com.kriger.jewelrystorebackend.responses.MediaListResponse;
import com.kriger.jewelrystorebackend.responses.MediaResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

import static com.kriger.jewelrystorebackend.errors.MediaErrors.*;

@Service
public class MediaService {
    private final MediaDAO mediaDAO;
    private final ProductDAO productDAO;

    public MediaService(MediaDAO mediaDAO,ProductDAO productDAO){
        this.mediaDAO = mediaDAO;
        this.productDAO = productDAO;
    }

    public MediaResponse addMedia(Media media){
        if(media == null)
            return new MediaResponse(false,INVALID_MEDIA,null);

        if(media.getProductId() == null || media.getProductId() <= 0)
            return new MediaResponse(false,INVALID_PRODUCT_ID,null);

        Product product = this.productDAO.getProductById(media.getProductId());
        if(product == null)
            return new MediaResponse(false,PRODUCT_NOT_FOUND,null);

        if(!isValidMediaUrl(media.getUrl()))
            return new MediaResponse(false,INVALID_MEDIA_URL,null);

        if(!isValidMediaType(media.getMediaType()))
            return new MediaResponse(false,INVALID_MEDIA_TYPE,null);

        media.setUrl(media.getUrl().trim());
        media.setMediaType(media.getMediaType().trim().toUpperCase(Locale.ROOT));

        int rowsAffected = this.mediaDAO.addMedia(media);
        if(rowsAffected == 0)
            return new MediaResponse(false,MEDIA_ADD_FAILED,null);

        return new MediaResponse(true,null,media);
    }

    public MediaResponse getMediaById(Long id){
        if(id == null || id <= 0)
            return new MediaResponse(false,INVALID_MEDIA_ID,null);

        Media media = this.mediaDAO.getMediaById(id);
        if(media == null)
            return new MediaResponse(false,MEDIA_NOT_FOUND,null);

        return new MediaResponse(true,null,media);
    }

    public MediaListResponse getMediaListByProductId(Long productId){
        if(productId == null || productId <= 0)
            return new MediaListResponse(false,INVALID_PRODUCT_ID,null);

        Product product = this.productDAO.getProductById(productId);
        if(product == null)
            return new MediaListResponse(false,PRODUCT_NOT_FOUND,null);

        List<Media> mediaList = this.mediaDAO.getMediaListByProductId(productId);
        if(mediaList == null || mediaList.isEmpty())
            return new MediaListResponse(false,EMPTY_MEDIA_LIST,null);

        return new MediaListResponse(true,null,mediaList);
    }

    public BasicResponse removeMediaById(Long id){
        if(id == null || id <= 0)
            return new BasicResponse(false,INVALID_MEDIA_ID);

        Media media = this.mediaDAO.getMediaById(id);
        if(media == null)
            return new BasicResponse(false,MEDIA_NOT_FOUND);

        int rowsAffected = this.mediaDAO.removeMediaById(id);
        if(rowsAffected == 0)
            return new BasicResponse(false,MEDIA_DELETE_FAILED);

        return new BasicResponse(true,null);
    }

    private boolean isValidMediaUrl(String url){
        if(url == null || url.trim().isEmpty())
            return false;

        String normalizedUrl = url.trim();

        if(normalizedUrl.length() > 512)
            return false;

        return normalizedUrl.startsWith("http://") ||
                normalizedUrl.startsWith("https://");
    }

    private boolean isValidMediaType(String mediaType){
        if(mediaType == null || mediaType.trim().isEmpty())
            return false;

        String normalizedMediaType = mediaType.trim().toUpperCase(Locale.ROOT);

        return normalizedMediaType.equals("IMAGE") ||
                normalizedMediaType.equals("VIDEO");
    }
}