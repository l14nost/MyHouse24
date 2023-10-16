package lab.space.my_house_24.repository;

import lab.space.my_house_24.entity.Section;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long>, JpaSpecificationExecutor<Section> {

    List<Section> findAllByHouse_Id(Long id);

    @Query("SELECT DISTINCT s.name FROM Section s WHERE s.name LIKE %:search% ORDER BY s.name ASC")
    Page<String> findAllDistinct(@Param("search") String search, Pageable pageable);
}
