package com.example.authentication.service;

import com.example.authentication.Repositories.CarteRepository;
import com.example.authentication.Repositories.OperationRepository;
import com.example.authentication.model.Carte;
import com.example.authentication.model.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarteServiceImp implements CarteService{
    @Autowired
    public CarteRepository carteRepository;
    @Autowired
    public OperationRepository operationRepository;
    @Override
    public List<Carte> getCards() {
        return this.carteRepository.findAll();
    }

    @Override
    public Optional<Carte> getCard(String id) {
        return this.carteRepository.findById(id);
    }

    @Override
    public float getSolde(String id) {
        Carte carte=getCard(id).get();
        return carte.getSolde();
    }

    @Override
    public List<Operation> getOperations(String id) {
        Carte carte=getCard(id).get();
        return (List<Operation>) carte.getOperations();
    }

    @Override
    public void addOperationToCard(String id, Operation operation) {
        Carte carte=getCard(id).get();
        this.operationRepository.save(operation);
        carte.addOperation(operation);

    }
}
