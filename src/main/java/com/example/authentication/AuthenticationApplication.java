package com.example.authentication;

import com.example.authentication.Repositories.*;
import com.example.authentication.model.*;
import com.example.authentication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import java.util.Date;

import static com.example.authentication.model.TypeOperation.CREDIT;
import static com.example.authentication.model.TypeOperation.DEBIT;

@SpringBootApplication
@EnableMethodSecurity(prePostEnabled = true)
public class AuthenticationApplication {
    @Autowired
    private EmployeRepository employeRepository;
    @Autowired
    private CarteNomRepository carteRepository;
    @Autowired
    private EntrepriseRepository entrepriseRepository;

    public static void main(String[] args) {
        SpringApplication.run(AuthenticationApplication.class, args);
    }
    @Bean
    CommandLineRunner run(UserService userService,
                          OperationRepository operationRepository){
        return args -> {
            userService.saveRole("USER");
            userService.saveRole("ADMIN");
            userService.saveUser("user1","1234");
            userService.saveUser("user2","1234");
            userService.addRoletoUser("user1","USER");
            userService.addRoletoUser("user2","ADMIN");
            Entreprise entreprise=Entreprise.builder().nom("sarl").username("user1").balance((double)100000000.00).build();
            Employe employe=new Employe("adnane","0639799920","f147895",Employe.ddn("1990-01-15"), Sex.HOMME,entreprise);
            Employe employe2=new Employe("adnane2","06397999202","f6692142",Employe.ddn("1990-01-15"), Sex.HOMME,entreprise);
            CarteNominative carteNominative= CarteNominative.builder().employe(employe).build();
            carteNominative.setId("0955593503530306");
            carteNominative.setStatus(Status.ACTIVATED);
            carteNominative.setEntreprise(entreprise);
            Operation operation1 = new Operation(CREDIT, "Credit 1",  (double) 100, new Date());
            operationRepository.save(operation1);
            Operation operation2 = new Operation(CREDIT, "Credit 2",  (double) 50, new Date());
            operationRepository.save(operation2);
            Operation operation3 = new Operation(DEBIT, "Debit 1",  (double) 300, new Date());
            operationRepository.save(operation3);
            carteNominative.getOperations().add(operation1);
            carteNominative.getOperations().add(operation2);
            carteNominative.getOperations().add(operation3);

            entrepriseRepository.save(entreprise);
            employeRepository.save(employe);
            employeRepository.save(employe2);
            carteRepository.save(carteNominative);


        };
    }

}
