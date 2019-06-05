package idu.cs.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import idu.cs.entity.QuestionEntity;
import idu.cs.entity.UserEntity;

@Repository
public interface QeustionRepository extends JpaRepository<QuestionEntity, Long> {
	// findById, save, delete 선언 없이도 구현 가능
	
	// 아래 메소드들은 선언을 해줘야 JPA 규칙에 의해 구현 된다.
	// find - select, By - where, OrderBy - order by, ASC & DESC를 함께 사용 ex) findByNameOrderByIdDESC(String name);

	QuestionEntity findByWriter(String userId); // ByUserId == where (userid = '')
}
