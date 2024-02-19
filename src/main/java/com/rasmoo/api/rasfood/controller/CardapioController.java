package com.rasmoo.api.rasfood.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rasmoo.api.rasfood.entity.Cardapio;
import com.rasmoo.api.rasfood.repository.CardapioRepository;
import com.rasmoo.api.rasfood.repository.specification.CardapioSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequestMapping(value = "/cardapio")
@RestController
public class CardapioController {

    @Autowired
    private CardapioRepository cardapioRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    ResponseEntity<Page<Cardapio>> consultarTodos(@RequestParam("page") Integer page, @RequestParam("size") Integer size,
                                                  @RequestParam(value = "sort", required = false) Sort.Direction sort, @RequestParam(value = "property", required = false) String property) {
        Pageable pageable = Objects.nonNull(sort) ? PageRequest.of(page,size, Sort.by(sort, property)) : PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(this.cardapioRepository.findAll(pageable));
    }

    @GetMapping("/{id}")
    ResponseEntity<Cardapio> consultarPorId(@PathVariable("id") final Integer id,
                                            @RequestParam(value = "page") Integer page, @RequestParam("size") Integer size) {
        Pageable pageable = PageRequest.of(page,size);
        Optional<Cardapio> cardapioOptional = this.cardapioRepository.findById(id);
        if(cardapioOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(cardapioOptional.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping("/{categoria}/disponivel")
    ResponseEntity<List<Cardapio>> consultarPorCategoria(@PathVariable("categoria") final Integer categoria,
                                                                   @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        Pageable pageable = PageRequest.of(page,size);
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.cardapioRepository.findAll(Specification.where(
                CardapioSpec.categoria(categoria).and(CardapioSpec.disponivel(true))
        ),pageable).getContent());
    }

    @GetMapping("/nome/{nome}/disponivel")
    ResponseEntity<List<Cardapio>> consultarTodos(@PathVariable("nome") final String nome,
                                                     @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        Pageable pageable = PageRequest.of(page,size);
        return ResponseEntity.status(HttpStatus.OK).body(
                this.cardapioRepository.findAll(Specification
                .where(CardapioSpec.nome(nome).and(CardapioSpec.disponivel(true))), pageable).getContent());
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

    @PatchMapping(value = "/{id}/img", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<Cardapio> salvarImg(@PathVariable("id") final Integer id, @RequestPart MultipartFile file) throws IOException {
        Optional<Cardapio> cardapioOptional = this.cardapioRepository.findById(id);
        if(cardapioOptional.isPresent()) {
            Cardapio cardapio = cardapioOptional.get();
            cardapio.setImg(file.getBytes());
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
