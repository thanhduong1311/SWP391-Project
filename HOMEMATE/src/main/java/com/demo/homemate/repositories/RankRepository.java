package com.demo.homemate.repositories;

import com.demo.homemate.entities.Rank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RankRepository extends JpaRepository<Rank, Integer>  {
}
