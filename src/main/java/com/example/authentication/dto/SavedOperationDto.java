package com.example.authentication.dto;

import com.example.authentication.model.TypeOperation;
import lombok.Data;

@Data
public class SavedOperationDto {
    private String cardNumber;
    private String identification;
    private String nom;
    private TypeOperation operationType;
    private int operationCode;
    private  String motif;
    private Double montant;
}
