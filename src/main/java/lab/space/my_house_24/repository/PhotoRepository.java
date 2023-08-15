package lab.space.my_house_24.repository;

import lab.space.my_house_24.entity.settingsPage.About;
import lab.space.my_house_24.entity.settingsPage.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends JpaRepository<Photo,Long> {
}
