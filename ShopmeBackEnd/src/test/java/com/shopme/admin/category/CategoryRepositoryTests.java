package com.shopme.admin.category;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Category;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CategoryRepositoryTests {

	@Autowired
	private CategoryRepository repo;

	@Test
	public void testCreateRootCategory() {
		Category category = new Category("Electronics");
		Category savedCategory = repo.save(category);

		assertThat(savedCategory.getId()).isGreaterThan(0);
	}

	@Test
	public void testCreateSubCategory() {
//		Category parent = new Category(1);
//		Category subCategory = new Category("Desktops", parent);
//		Category savedCategory = repo.save(subCategory);
//		
//		assertThat(savedCategory.getId()).isGreaterThan(0);

//		Category parent = new Category(1);
//		Category laptops = new Category("Laptops", parent);
//		Category components = new Category("Computer Components", parent);
//
//		repo.saveAll(List.of(laptops, components));

//		Category parent = new Category(2);
//		Category cameras = new Category("Cameras", parent);
//		Category smartphones = new Category("Smartphones", parent);
//
//		repo.saveAll(List.of(cameras, smartphones));

//		Category parent = new Category(5);
//		Category subCategory = new Category("Memory", parent);
//		Category savedCategory = repo.save(subCategory);
//
//		assertThat(savedCategory.getId()).isGreaterThan(0);
		
//		Category parent = new Category(4);
//		Category subCategory = new Category("Gaming Laptops", parent);
//		Category savedCategory = repo.save(subCategory);
//
//		assertThat(savedCategory.getId()).isGreaterThan(0);
		
		Category parent = new Category(7);
		Category subCategory = new Category("iPhone", parent);
		Category savedCategory = repo.save(subCategory);

		assertThat(savedCategory.getId()).isGreaterThan(0);

	}

	@Test
	public void testGetCategory() {
		Category category = repo.findById(2).get();
		System.out.println(category.getName());

		Set<Category> children = category.getChildren();

		for (Category subCategory : children) {
			System.out.println(subCategory.getName());
		}

		assertThat(children.size()).isGreaterThan(0);
	}

	@Test
	public void testPrintHiearchicalCategories() {
		Iterable<Category> categories = repo.findAll();

		for (Category category : categories) {
			if (category.getParent() == null) {
				System.out.println(category.getName());

				Set<Category> children = category.getChildren();

				for (Category subCategory : children) {
					System.out.println("--" + subCategory.getName());
					printChildren(subCategory, 1);
				}
			}

		}
	}

	private void printChildren(Category parent, int subLevel) {
		int newSubLevel = subLevel + 1;
		Set<Category> children = parent.getChildren();

		for (Category subCategory : children) {
			for (int i = 0; i < newSubLevel; i++) {
				System.out.print("--");
			}
			System.out.println(subCategory.getName());

			printChildren(subCategory, newSubLevel);
		}
	}
}