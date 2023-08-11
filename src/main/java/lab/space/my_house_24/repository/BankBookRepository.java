package lab.space.my_house_24.repository;

import lab.space.my_house_24.entity.BankBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankBookRepository extends JpaRepository<BankBook,Long> {
}
