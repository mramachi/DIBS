package repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hello.model.Partner;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {
	List<Partner> findByPartnerID(long id);
}