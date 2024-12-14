package cm.ex.delivery.service;

import cm.ex.delivery.entity.Category;
import cm.ex.delivery.repository.CategoryRepository;
import cm.ex.delivery.response.BasicResponse;
import cm.ex.delivery.service.interfaces.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public BasicResponse addCategory(String category) {
        if (category.isBlank())
            throw new IllegalArgumentException("Input cannot be blank.");

        Optional<Category> categoryCheck = categoryRepository.findByName(category);
        if (categoryCheck.isPresent())
            return BasicResponse.builder().status(true).code(200).message("This category is already added").build();

        Category newCategory = new Category(category);
        return BasicResponse.builder().status(true).code(200).message("New category added successfully").build();
    }

    @Override
    public List<Category> listCategory() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList.isEmpty() ? new ArrayList<>() : categoryList;
    }

    @Override
    public BasicResponse removeCategory(String category) {
        Optional<Category> categoryCheck = categoryRepository.findByName(category);
        if (categoryCheck.isEmpty())
            return BasicResponse.builder().status(false).code(404).message("Category not found").build();

        categoryRepository.delete(categoryCheck.get());
        return BasicResponse.builder().status(true).code(200).message("Category deleted successfully").build();
    }
}
