package cm.ex.delivery.repository;

import cm.ex.delivery.entity.MenuCategory;
import cm.ex.delivery.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuItemRepository  extends JpaRepository<MenuItem, Long> {

    List<MenuItem> findByMenuCategory(MenuCategory menuCategory);
}
