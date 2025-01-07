package cm.ex.delivery.service.interfaces;

import cm.ex.delivery.entity.Image;
import cm.ex.delivery.response.BasicResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ImageService {

    public Image addImage(MultipartFile file) throws IOException;

    public byte[] getImageById(String imageId);

    public List<Image> listAllImages();

    public BasicResponse removeImage(String imageId);
}
