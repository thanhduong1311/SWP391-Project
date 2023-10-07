package com.demo.homemate.repositories;

import com.demo.homemate.entities.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RankRepository extends JpaRepository<Ranking, Integer>  {
}
