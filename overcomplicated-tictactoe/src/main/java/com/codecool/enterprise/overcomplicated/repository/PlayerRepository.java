package com.codecool.enterprise.overcomplicated.repository;

import com.codecool.enterprise.overcomplicated.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Integer> {
}
