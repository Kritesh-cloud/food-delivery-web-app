package cm.ex.delivery.service.interfaces;

import cm.ex.delivery.entity.Category;
import cm.ex.delivery.response.BasicResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CategoryService {

    public BasicResponse addCategory(String category);

    public BasicResponse addNewCategory(String category, MultipartFile image) throws IOException;

    public List<Category> listCategory();

    public BasicResponse removeCategory(String category);
}
