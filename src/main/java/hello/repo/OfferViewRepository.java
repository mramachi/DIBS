package hello.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hello.model.OfferView;

@Repository
public interface OfferViewRepository extends JpaRepository<OfferView, Long> {
	OfferView findByOfferViewID(long offerViewID);
	
}
