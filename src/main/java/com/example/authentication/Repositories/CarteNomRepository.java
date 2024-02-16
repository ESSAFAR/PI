package com.example.authentication.Repositories;

import com.example.authentication.model.CarteNominative;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarteNomRepository extends JpaRepository<CarteNominative,String> {
}
