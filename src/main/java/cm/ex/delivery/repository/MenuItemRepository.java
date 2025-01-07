package cm.ex.delivery.repository;

import cm.ex.delivery.entity.MenuCategory;
import cm.ex.delivery.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository  extends JpaRepository<MenuItem, Long> {

    List<MenuItem> findByMenuCategory(MenuCategory menuCategory);

//    @Query("SELECT mi FROM MenuItem mi WHERE mi.menuCategory = :menuCategory ORDER BY mi.itemOrder ASC")
//    List<MenuItem> findByMenuCategoryOrdered(@Param("menuCategory") MenuCategory menuCategory);
}
