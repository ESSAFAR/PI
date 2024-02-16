package com.example.authentication.Repositories;

import com.example.authentication.model.Carte;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarteRepository extends JpaRepository<Carte,String> {
}
