package com.example.server.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.server.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long>{

	@Query(value="SELECT * FROM board WHERE CATEGORY = ?1", nativeQuery=true)
	Page<Board> findBoardAllByCategory(int id, Pageable pageable);
		
}
