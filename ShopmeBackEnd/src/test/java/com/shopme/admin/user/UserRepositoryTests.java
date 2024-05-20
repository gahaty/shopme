package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void testCreateUserWithOneRole() {
		Role roleAdmin = entityManager.find(Role.class, 2);
		User userGahaty = new User("gahaty@gmail.com","tgabor123","Gábor","Tölcséres");
		userGahaty.addRole(roleAdmin);
		
		User savedUser = repo.save(userGahaty);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateUserWithTwoRole() {
		User userBalint = new User("balint@gmail.com","szbalint123","Bálint","Szilágyi");
		Role roleEditor = new Role(3);
		Role roleAssistant = new Role(5);
		
		userBalint.addRole(roleEditor);
		userBalint.addRole(roleAssistant);
		
		User savedUser = repo.save(userBalint);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testListAllUsers() {
		Iterable<User> listUsers = repo.findAll();
		listUsers.forEach(user -> System.out.println(user));
	}
	
	@Test
	public void testGetUserById() {
		User userNam = repo.findById(2).get();
		System.out.println(userNam);
		assertThat(userNam).isNotNull();
	}
	
	@Test
	public void testUpdateUserDetails() {
		User userNam = repo.findById(2).get();
		userNam.setEnabled(true);
		userNam.setEmail("gahatyEzIsJo@gmail.com");
		repo.save(userNam);
	}
	
	@Test
	public void testUpdateUserRoles() {
		User userBalint = repo.findById(4).get();
		Role roleEditor = new Role(3);
		Role roleSalesPerson = new Role(2);
		
		userBalint.getRoles().remove(roleEditor);
		userBalint.addRole(roleSalesPerson);
		
		repo.save(userBalint);
	}
	
	@Test
	public void testDeleteUser() {
		Integer userID = 4;
		repo.deleteById(userID);
	}
	
	@Test
	public void testGetUserByEmail() {
		String email = "gahaty@gmail.com";
		User user = repo.getUserByEmail(email);
		
		assertThat(user).isNotNull();
	}
	
	@Test
	public void testCountById() {
		Integer id = 2;
		Long countById = repo.countById(id);
		assertThat(countById).isNotNull().isGreaterThan(0);
	}
	
	@Test
	public void testDisableUser() {
		Integer id = 2;
		repo.updateEnableStatus(id, false);
	}
	
	@Test
	public void testEnableUser() {
		Integer id = 2;
		repo.updateEnableStatus(id, true);
	}
	
	@Test
	public void testListFirstPage() {
		int pageNumber = 0;
		int pageSize = 4;
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<User> page = repo.findAll(pageable);
		
		List<User> listUsers = page.getContent();
		listUsers.forEach(user -> System.out.println(user));
		
		assertThat(listUsers.size()).isEqualTo(pageSize);
	}
	
	@Test
	public void testSearchUsers() {
		String keyword = "bruce";
		
		int pageNumber = 0;
		int pageSize = 4;
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<User> page = repo.findAll(keyword, pageable);
		
		List<User> listUsers = page.getContent();
		
		listUsers.forEach(user -> System.out.println(user));
		
		assertThat(listUsers.size()).isGreaterThan(0);
	}
	
};
