package lab.space.my_house_24.repository;

import lab.space.my_house_24.entity.settingsPage.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}
