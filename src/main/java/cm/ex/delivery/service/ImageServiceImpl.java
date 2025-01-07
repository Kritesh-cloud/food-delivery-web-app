package cm.ex.delivery.service;

import cm.ex.delivery.entity.Image;
import cm.ex.delivery.repository.ImageRepository;
import cm.ex.delivery.response.BasicResponse;
import cm.ex.delivery.service.interfaces.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public Image addImage(MultipartFile file) throws IOException {
        if (Objects.requireNonNull(file.getOriginalFilename()).isEmpty())
            throw new IllegalArgumentException("Input cannot be blank.");

        String imgFile = Base64.getEncoder().encodeToString(file.getBytes());
        Image image = new Image(file.getOriginalFilename(), imgFile, file.getContentType());
        return imageRepository.save(image);
    }

//    @Override
//    public Optional<Image> getImageById(String id) {
//        if (id.isBlank())
//            throw new IllegalArgumentException("Input cannot be blank.");
//
//        return imageRepository.findById(UUID.fromString(id));
//    }

    @Override
    public byte[] getImageById(String imageId) {
        Optional<Image> imageById = imageRepository.findById(UUID.fromString(imageId));
        if(imageById.isEmpty()) throw new NoSuchElementException("Image not found");
        return Base64.getDecoder().decode(imageById.get().getImage());
    }

    @Override
    public List<Image> listAllImages() {
        List<Image> ImageList = imageRepository.findAll();
        return ImageList.isEmpty() ? new ArrayList<>() : ImageList;
    }

    @Override
    public BasicResponse removeImage(String imageId) {
        Optional<Image> imageCheck = imageRepository.findById(UUID.fromString(imageId));
        if (imageCheck.isEmpty())
            return BasicResponse.builder().status(false).code(404).message("Image not found").build();

        imageRepository.delete(imageCheck.get());
        return BasicResponse.builder().status(true).code(200).message("Image deleted successfully").build();
    }
}
