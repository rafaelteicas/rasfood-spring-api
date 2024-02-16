package com.rasmoo.api.rasfood.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rasmoo.api.rasfood.dto.CardapioDto;
import com.rasmoo.api.rasfood.entity.Cardapio;
import com.rasmoo.api.rasfood.repository.CardapioRepository;
import com.rasmoo.api.rasfood.repository.projection.CardapioProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping(value = "/cardapio")
@RestController
public class CardapioController {

    @Autowired
    private CardapioRepository cardapioRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    ResponseEntity<List<Cardapio>> consultarTodos() {
        return ResponseEntity.status(HttpStatus.OK).body(this.cardapioRepository.findAll());
    }

    @GetMapping("/{id}")
    ResponseEntity<Cardapio> consultarPorId(@PathVariable("id") final Integer id) {
        Optional<Cardapio> cardapioOptional = this.cardapioRepository.findById(id);
        if(cardapioOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(cardapioOptional.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping("/{categoria}/disponivel")
    ResponseEntity<List<CardapioProjection>> consultarPorCategoria(@PathVariable("categoria") final Integer categoria) {
        List<CardapioProjection> cardapioOptional = this.cardapioRepository.findAllByCategoria(categoria);
        return ResponseEntity.status(HttpStatus.OK).body(cardapioOptional);
    }

    @GetMapping("/nome/{nome}/disponivel")
    ResponseEntity<List<CardapioDto>> consultarTodos(@PathVariable("nome") final String nome) {
        List<CardapioDto> cardapioOptional = this.cardapioRepository.findByNome(nome);
        return ResponseEntity.status(HttpStatus.OK).body(cardapioOptional);
    }

    @PatchMapping("/{id}")
    ResponseEntity<Cardapio> consultarTodos(@PathVariable("id") final Integer id, Cardapio cardapio) throws JsonMappingException {
        Optional<Cardapio> cardapioOptional = this.cardapioRepository.findById(id);
        if(cardapioOptional.isPresent()) {
            objectMapper.updateValue(cardapioOptional.get(), cardapio);
            return ResponseEntity.status(HttpStatus.OK).body(cardapioOptional.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deletarPorId(@PathVariable("id") final Integer id) {
        Optional<Cardapio> cardapioOptional = this.cardapioRepository.findById(id);
        if(cardapioOptional.isPresent()) {
            this.cardapioRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PostMapping
    ResponseEntity<Cardapio> criarCardapio(@RequestBody Cardapio cardapio) {
        Cardapio cardapioNovo = this.cardapioRepository.save(cardapio);
        return ResponseEntity.status(HttpStatus.OK).body(cardapioNovo);
    }
}
