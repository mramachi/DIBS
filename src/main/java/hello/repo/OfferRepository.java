package hello.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hello.model.Event;
import hello.model.Offer;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
	Offer findByOfferID(long offerID);
	List<Offer> findByevent(int eventname);
	List<Offer> findByOfferPartnerID(long partnerID);
	List<Offer> findByEvent(Event e);
}