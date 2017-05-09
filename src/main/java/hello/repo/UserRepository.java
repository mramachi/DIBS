package hello.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hello.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findByUserID(long userID);
	User findByFacebookID(long fbID);
	
}
