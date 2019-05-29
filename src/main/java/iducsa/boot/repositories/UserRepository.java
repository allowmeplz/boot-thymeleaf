package iducsa.boot.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import iducsa.boot.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	List<User> findByName(String name);
	List<User> findByCompany(String company);
	User findByUserId(String userId); // ByUserId == where (userid = '')
}
