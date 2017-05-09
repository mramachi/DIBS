package hello.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hello.model.Partner;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {
	Partner findByPartnerID(long partnerID);
	Partner findByPartnerName(String partnerName);

}
