package lab.space.my_house_24.repository;

import lab.space.my_house_24.entity.BankBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankBookRepository extends JpaRepository<BankBook,Long>, JpaSpecificationExecutor<BankBook> {
    List<BankBook> findAllByApartmentIsNull();

    boolean existsByNumber(String number);

    Optional<BankBook> findBankBookByNumber(String number);
}
