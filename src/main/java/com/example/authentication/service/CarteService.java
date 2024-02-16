package com.example.authentication.service;

import com.example.authentication.model.Carte;
import com.example.authentication.model.Operation;

import java.util.List;
import java.util.Optional;

public interface CarteService {
    public List<Carte> getCards();
    public Optional<Carte> getCard(String id);
    public float getSolde(String id);
    public List<Operation> getOperations(String id);
    public void addOperationToCard(String id,Operation operation);
}
