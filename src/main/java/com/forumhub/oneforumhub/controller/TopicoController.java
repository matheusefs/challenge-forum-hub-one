package com.forumhub.oneforumhub.controller;

import com.forumhub.oneforumhub.domain.topico.*;
import com.forumhub.oneforumhub.domain.usuario.Usuario;
import com.forumhub.oneforumhub.domain.usuario.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // ========================
    // POST /topicos - Cadastrar
    // ========================
    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoTopico> cadastrar(
            @RequestBody @Valid DadosCadastroTopico dados,
            @AuthenticationPrincipal Usuario usuarioLogado,
            UriComponentsBuilder uriBuilder) {

        if (topicoRepository.existsByTituloAndMensagem(dados.titulo(), dados.mensagem())) {
            return ResponseEntity.badRequest().build();
        }

        var topico = new Topico(dados.titulo(), dados.mensagem(), usuarioLogado, dados.curso());
        topicoRepository.save(topico);

        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoTopico(topico));
    }

    // ========================
    // GET /topicos - Listar
    // ========================
    @GetMapping
    public ResponseEntity<Page<DadosListagemTopico>> listar(
            @RequestParam(required = false) String curso,
            @RequestParam(required = false) Integer ano,
            @PageableDefault(size = 10, sort = "dataCriacao") Pageable paginacao) {

        Page<Topico> topicos;

        if (curso != null && ano != null) {
            topicos = topicoRepository.findByCursoContainingIgnoreCaseAndDataCriacaoYear(
                    curso, ano, paginacao);
        } else {
            topicos = topicoRepository.findAll(paginacao);
        }

        return ResponseEntity.ok(topicos.map(DadosListagemTopico::new));
    }

    // ========================
    // GET /topicos/{id} - Detalhar
    // ========================
    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoTopico> detalhar(@PathVariable Long id) {
        var optionalTopico = topicoRepository.findById(id);

        if (!optionalTopico.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new DadosDetalhamentoTopico(optionalTopico.get()));
    }

    // ========================
    // PUT /topicos/{id} - Atualizar
    // ========================
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DadosDetalhamentoTopico> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid DadosAtualizacaoTopico dados) {

        var optionalTopico = topicoRepository.findById(id);

        if (!optionalTopico.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        var topico = optionalTopico.get();

        if (dados.titulo() != null && dados.mensagem() != null) {
            if (topicoRepository.existsByTituloAndMensagem(dados.titulo(), dados.mensagem())) {
                return ResponseEntity.badRequest().build();
            }
        }

        topico.atualizar(dados.titulo(), dados.mensagem(), dados.curso());
        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }

    // ========================
    // DELETE /topicos/{id} - Excluir
    // ========================
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        var optionalTopico = topicoRepository.findById(id);

        if (!optionalTopico.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        topicoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
