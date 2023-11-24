package com.example.fiap.global.globalSolution.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fiap.global.globalSolution.model.ImcCalculation;
import com.example.fiap.global.globalSolution.repository.ImcCalculationRepository;

import javax.transaction.Transactional;

@Service
public class ImcCalculationService {
    private final ImcCalculationRepository imcCalculationRepository;

    @Autowired
    public ImcCalculationService(ImcCalculationRepository imcCalculationRepository) {
        this.imcCalculationRepository = imcCalculationRepository;
    }

    @Transactional
    public ImcCalculation saveImcCalculation(ImcCalculation imcCalculation) {
        return imcCalculationRepository.save(imcCalculation);
    }

    
}
