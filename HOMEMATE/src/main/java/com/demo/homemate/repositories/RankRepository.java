package com.demo.homemate.repositories;

import com.demo.homemate.entities.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RankRepository extends JpaRepository<Ranking, Integer>  {

    Ranking findById(int id);

}
