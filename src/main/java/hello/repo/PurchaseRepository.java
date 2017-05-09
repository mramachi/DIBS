package hello.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hello.model.Offer;
import hello.model.Purchase;
import hello.model.User;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
	Purchase findByOfferAndUser(Offer offer, User user);
	//List<Purchase> findByOffersAndUser(List<Offer> offerList, User user);
}