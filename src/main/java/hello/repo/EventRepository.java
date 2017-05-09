package hello.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hello.model.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
	Event findByEventID(long eventID);
	
	//Deze wordt niet gedefinieerd door de functienaam, maar wel door de @Query
	//zie EventController.searchEvent()
	@Query("SELECT e FROM Event e INNER JOIN e.location l WHERE l.locationCity = ?1 AND e.eventDate = ?2")
	Page<Event> findByCityByDate(String city, Date date, Pageable page);
	
	@Query("SELECT e FROM Event e INNER JOIN e.location l WHERE l.locationCity = ?1")
	Page<Event> findByCity(String city, Pageable page);
	
	@Query("SELECT e FROM Event e WHERE e.eventDate = ?1")
	Page<Event> findByDate(Date date, Pageable page);
		
	@Query("SELECT e FROM Event e WHERE e.partnerID = ?1")
	Page<Event> findByPartnerID( long partnerid, Pageable page);

	@Query("SELECT e FROM Event e WHERE e.partnerID = ?1")
	List<Event> findAllByPartnerID(long partnerid);
	
	
	//List Weekend
	@Query("SELECT e FROM Event e WHERE (e.eventDate >= ?1 and e.eventDate <= (?2)) AND (extract(dow from e.eventDate) = 0	OR extract(dow from e.eventDate)= 6 )")
	List<Event> thisWeekend(Date current_date, Date next_week);
	
	//Page Weekend
	@Query("SELECT e FROM Event e WHERE (e.eventDate >= ?1 and e.eventDate <= ?2) AND (extract(dow from e.eventDate) = 0	OR extract(dow from e.eventDate)= 6 )")
	Page<Event> thisWeekendPage(Date current_date, Date next_week, Pageable page);
	
	@Query("SELECT e FROM Event e WHERE (e.eventDate >= ?1)")
	List<Event> findAllFutureEvents(Date current_date);
	
	@Query("SELECT e FROM Event e WHERE (e.eventDate >= ?1)")
	Page<Event> findAllFutureEvents(Date current_date, Pageable page);
	
	
	//@Query("SELECT e FROM Event")
	//List<Event> findAll();
	
	
	
	
}
