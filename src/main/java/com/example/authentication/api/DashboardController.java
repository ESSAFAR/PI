package com.example.authentication.api;

import com.example.authentication.Repositories.EntrepriseRepository;
import com.example.authentication.dto.CarteDto;
import com.example.authentication.dto.OperationDto;
import com.example.authentication.model.Carte;
import com.example.authentication.model.CarteNominative;
import com.example.authentication.model.Entreprise;
import com.example.authentication.model.Operation;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/dashboard")
public class DashboardController {
    public final EntrepriseRepository entrepriseRepository;


    @GetMapping("/balance")
    public double getBalanceGlobale(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Entreprise entreprise =  entrepriseRepository.findByUsername(authentication.getName());

        return entreprise.getBalance();
    }

    @GetMapping("/transactions")
    public List<OperationDto> getListeTransaction(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Entreprise entreprise =  entrepriseRepository.findByUsername(authentication.getName());
        List<OperationDto> transactions = new ArrayList<>();
        for(Carte carte : entreprise.getCartes()){
            for(Operation transaction : carte.getOperations()){
                transactions.add(new OperationDto(transaction, (CarteNominative) carte));
            }
        }

        return transactions;
    }

    @GetMapping("/listcartes")
    public List<CarteDto> getListeCartes() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Entreprise entreprise =  entrepriseRepository.findByUsername(authentication.getName());
        List<Carte> cartes = entreprise.getCartes();
        System.out.println("test");
        return cartes.stream().map(carte -> new CarteDto((CarteNominative) carte)).collect(Collectors.toList());
    }

}
