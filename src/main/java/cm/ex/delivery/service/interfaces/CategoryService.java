package cm.ex.delivery.service.interfaces;

import cm.ex.delivery.entity.Category;
import cm.ex.delivery.response.BasicResponse;

import java.util.List;

public interface CategoryService {

    public BasicResponse addCategory(String category);

    public List<Category> listCategory();

    public BasicResponse removeCategory(String category);
}
