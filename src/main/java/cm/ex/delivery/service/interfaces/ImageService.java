package cm.ex.delivery.service.interfaces;

import cm.ex.delivery.entity.Image;
import cm.ex.delivery.response.BasicResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    public BasicResponse addImage(MultipartFile file);

    public Image getImageById(String id);

    public List<Image> listAllImages();

    public BasicResponse removeImage(String imageId);
}
