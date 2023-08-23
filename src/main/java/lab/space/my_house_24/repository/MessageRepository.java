package lab.space.my_house_24.repository;

import lab.space.my_house_24.entity.Message;
import lab.space.my_house_24.entity.settingsPage.About;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>, JpaSpecificationExecutor<Message> {
}
