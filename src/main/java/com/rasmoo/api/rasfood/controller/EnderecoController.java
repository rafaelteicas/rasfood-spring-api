package com.rasmoo.api.rasfood.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rasmoo.api.rasfood.entity.Endereco;
import com.rasmoo.api.rasfood.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/endereco")
@RestController
public class EnderecoController {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    ResponseEntity<List<Endereco>> consultarTodos() {
        return ResponseEntity.status(HttpStatus.OK).body(this.enderecoRepository.findAll());
    }

    @GetMapping("/{id}")
    ResponseEntity<Endereco> consultarPorId(@PathVariable("id") final Integer id) {
        Optional<Endereco> endereco = this.enderecoRepository.findById(id);
        return endereco.map(item ->ResponseEntity.status(HttpStatus.OK).body(item))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PatchMapping("/{id}")
    ResponseEntity<Endereco> atualizar(@PathVariable("id") final Integer id, @RequestBody Endereco endereco) throws JsonMappingException {
        Optional<Endereco> enderecoEncontrado = this.enderecoRepository.findById(id);
        if (enderecoEncontrado.isPresent()) {
            this.objectMapper.updateValue(enderecoEncontrado.get(), endereco);
            return ResponseEntity.status(HttpStatus.OK).body(this.enderecoRepository.save(enderecoEncontrado.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping("/cep/{cep}")
    ResponseEntity<List<Endereco>> consultarPorCep(@PathVariable("cep") final String cep) {
        return ResponseEntity.status(HttpStatus.OK).body(this.enderecoRepository.findByCep(cep));
    }
}
