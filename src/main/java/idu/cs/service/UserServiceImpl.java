package idu.cs.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import idu.cs.domain.User;
import idu.cs.entity.UserEntity;
import idu.cs.exception.ResourceNotFoundException;
import idu.cs.repositories.UserRepository;



@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired UserRepository repository;
	
	@Override
	public User getUserById(long id) {
		// TODO Auto-generated method stub
		UserEntity entity = null;
		try {
			entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found : " + id));
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		User user = entity.buildDomain();
		return user;
	}

	@Override
	public User getUserByUserId(String userId) {
		// TODO Auto-generated method stub
		// DB, repository에서 가져와 Entity에 저장 ( repository에 findByUserID가 있어야한다. )
		UserEntity entity = repository.findByUserId(userId);
		// entity를 domain으로 변경 ( controller, service에서 사용하기 위함 )
		User user = entity.buildDomain();
		return user;
	}

	@Override
	public List<User> getUsers() {
		// TODO Auto-generated method stub
		List<User> users = new ArrayList<User>();
		List<UserEntity> entities = repository.findAll();
		for(UserEntity entity : entities) { // entity -> domain
			User user = entity.buildDomain();
			users.add(user);
		}
		return users;
	}

	@Override
	public List<User> getUsersByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getUsersByCompany(String company) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getUsersByPage(int index, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveUser(User user) {
		UserEntity entity = new UserEntity();
		// domain-user 객체를 entity-userEntity 생성
		// DB저장을 위해 Entity가 필요함. (repository.save의 매개변수가 entity)
		entity.buildEntity(user);
		repository.save(entity);
	}

	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		UserEntity entity = new UserEntity();
		entity.buildEntity(user);
		repository.save(entity);
	}

	@Override
	public void deleteUser(User user) {
		// TODO Auto-generated method stub

	}

}
