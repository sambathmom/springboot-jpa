package com.example.springbootjpa.repository;

import com.example.springbootjpa.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;


public interface BookRepository extends JpaRepository<Book, Long> {

	List<Book> findByTitle(String title);

	// Custom query
	@Query("SELECT b FROM Book b WHERE b.publishDate > :date")
	List<Book> findByPublishedDateAfter(@Param("date") LocalDate date);

	@Query(value = "SELECT 1 FROM pg_sleep(:sleepTime)", nativeQuery = true)
	Integer selectSleep(@Param("sleepTime") int sleepTime);

}