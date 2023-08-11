package lab.space.my_house_24.repository;

import lab.space.my_house_24.entity.settingsPage.MainPage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MainPageRepository extends JpaRepository<MainPage, Long> {
}
