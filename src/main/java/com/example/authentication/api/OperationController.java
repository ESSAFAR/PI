package com.example.authentication.api;

import com.example.authentication.Repositories.CarteRepository;
import com.example.authentication.Repositories.OperationRepository;
import com.example.authentication.dto.SavedOperationDto;
import com.example.authentication.model.Carte;
import com.example.authentication.model.Operation;
import com.example.authentication.model.TypeOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/operation")
@AllArgsConstructor
public class OperationController {
    private final OperationRepository operationRepository;
    private final CarteRepository carteRepository;

    @PostMapping
    public ResponseEntity<String> saveOperation(@RequestBody SavedOperationDto savedOperationDto){
        Operation operation = Operation.builder()
                .typeOperation(savedOperationDto.getOperationType())
                .description(savedOperationDto.getMotif())
                .montant(savedOperationDto.getMontant())
                .date(new Date())
                .build();

        Carte carte = carteRepository.findById(savedOperationDto.getCardNumber()).get();
        if (operation.getTypeOperation() == TypeOperation.DEBIT  && carte.getEntreprise().getBalance() >= operation.getMontant()){
            carte.setSolde((float) (carte.getSolde() + operation.getMontant()));
            carte.getEntreprise().setBalance(carte.getEntreprise().getBalance() - operation.getMontant());
        }

        else if (operation.getTypeOperation() == TypeOperation.CREDIT && carte.getSolde() >= operation.getMontant()) {
            carte.setSolde((float) (carte.getSolde() - operation.getMontant()));
            carte.getEntreprise().setBalance(carte.getEntreprise().getBalance() + operation.getMontant());
        }
        else {
            return new ResponseEntity<>("Solde insuffisant", HttpStatus.FORBIDDEN);
        }
        operationRepository.save(operation);
        carte.getOperations().add(operation);
        carteRepository.save(carte);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
