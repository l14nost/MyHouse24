package lab.space.my_house_24.repository;

import lab.space.my_house_24.entity.BankBook;
import lab.space.my_house_24.enums.BankBookStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankBookRepository extends JpaRepository<BankBook,Long>, JpaSpecificationExecutor<BankBook> {
    List<BankBook> findAllByApartmentIsNullAndBankBookStatus(BankBookStatus bankBookStatus);

    boolean existsByNumber(String number);

    Optional<BankBook> findBankBookByNumber(String number);
}
