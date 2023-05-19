package br.com.brunolutterbach.ticketmasterapi.service;

import br.com.brunolutterbach.ticketmasterapi.model.evento.DadosAtualizacaoEvento;
import br.com.brunolutterbach.ticketmasterapi.model.evento.DadosCadastroEvento;
import br.com.brunolutterbach.ticketmasterapi.model.evento.DadosDetalhamentoEvento;
import br.com.brunolutterbach.ticketmasterapi.model.evento.Evento;
import br.com.brunolutterbach.ticketmasterapi.model.ingresso.Ingresso;
import br.com.brunolutterbach.ticketmasterapi.repository.EventoRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class EventoService {

    final EventoRepository repository;
    final IngressoService ingressoService;
    final EmailService emailService;

    public void cadastrar(Evento evento, DadosCadastroEvento dados) {
        repository.save(evento);
        for (int i = 0; i < evento.getQuantidadeIngressos(); i++) {
            var ingresso = new Ingresso();
            ingresso.setEvento(evento);
            ingresso.setValor(dados.valorIngresso());
            ingresso.setDataValidade(LocalDateTime.of(evento.getDataEvento(), evento.getHoraEvento()));
            ingressoService.cadastrar(ingresso);
        }
    }

    public Page<DadosDetalhamentoEvento> listarEventos(Pageable pageable) {
        return repository.findAll(pageable).map(DadosDetalhamentoEvento::new);
    }

    public DadosDetalhamentoEvento buscarPorId(Long id) {
        return new DadosDetalhamentoEvento(repository.findById(id).orElseThrow());
    }

    public DadosAtualizacaoEvento atualizarInformacoes(Long id, DadosAtualizacaoEvento dados) throws Exception {
        var evento = repository.getReferenceById(id);
        evento.atualizar(dados);
        repository.save(evento);
        emailService.enviarEmailAtualizacaoEvento(evento);
        return new DadosAtualizacaoEvento(evento);
    }

    public void deletarEvento(Long id) {
        repository.deleteById(id);
    }
}