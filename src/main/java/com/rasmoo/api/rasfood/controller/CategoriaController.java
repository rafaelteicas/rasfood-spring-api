package com.rasmoo.api.rasfood.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rasmoo.api.rasfood.entity.Categoria;
import com.rasmoo.api.rasfood.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/categoria")
@RestController
public class CategoriaController {
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    ResponseEntity<List<Categoria>> consultarTodas() {
        return ResponseEntity.status(HttpStatus.OK).body(categoriaRepository.findAll());
    }

    @GetMapping("/{id}")
    ResponseEntity<Categoria> consultarPorId(@PathVariable("id") final Integer id) {
        Optional<Categoria> categoria = this.categoriaRepository.findById(id);
        return categoria.map(item -> ResponseEntity.status(HttpStatus.OK).body(item))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PatchMapping("/{id}")
    ResponseEntity<Categoria> atualizar(@PathVariable("id") final Integer id, @RequestBody Categoria categoria) throws JsonMappingException {
        Optional<Categoria> categoriaEncontrada = this.categoriaRepository.findById(id);
        if(categoriaEncontrada.isPresent()) {
            this.objectMapper.updateValue(categoriaEncontrada.get(), categoria);
            return ResponseEntity.status(HttpStatus.OK).body(this.categoriaRepository.save(categoriaEncontrada.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> remover(@PathVariable("id") final Integer id) {
        Optional<Categoria> categoria = this.categoriaRepository.findById(id);
        if(categoria.isPresent()) {
            this.categoriaRepository.deleteById(id);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PostMapping
    ResponseEntity<Categoria> criarCategoria(@RequestBody Categoria categoria) {
        Categoria categoriaNova = this.categoriaRepository.save(categoria);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(categoriaNova);
    }

}
