package com.example.fiap.global.globalSolution.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.fiap.global.globalSolution.model.ImcCalculation;
import com.example.fiap.global.globalSolution.service.ImcCalculationService;



@RestController
@RequestMapping("/api/imc")
public class ImcCalculationController {

    private final ImcCalculationService imcCalculationService;

    @Autowired
    public ImcCalculationController(ImcCalculationService imcCalculationService) {
        this.imcCalculationService = imcCalculationService;
    }

    @PostMapping("/calculate")
    public ResponseEntity<ImcCalculation> calculateImc(@RequestBody ImcCalculation imcCalculation) {
        // Faça o cálculo do IMC usando os dados de imcCalculation
        double imcValue = calculateImcValue(imcCalculation.getPeso(), imcCalculation.getAltura());

        // Atualize o objeto ImcCalculation com o valor do IMC
        imcCalculation.setImc(imcValue);

        // Salve os dados no banco de dados, se necessário
        imcCalculationService.saveImcCalculation(imcCalculation);

        // Retorne o objeto ImcCalculation com o status HTTP 201 (Created)
        return new ResponseEntity<>(imcCalculation, HttpStatus.CREATED);
    }

    private double calculateImcValue(double peso, double altura) {
        // Implemente o cálculo do IMC aqui
        // IMC = peso / (altura * altura)
        return peso / (altura * altura);
    }
}
