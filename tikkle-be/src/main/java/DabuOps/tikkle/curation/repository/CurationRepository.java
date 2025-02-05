package DabuOps.tikkle.curation.repository;

import DabuOps.tikkle.curation.entity.Curation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurationRepository extends JpaRepository<Curation, Long> {
    List<Curation> findAllByTagId(Long tagId);
}
