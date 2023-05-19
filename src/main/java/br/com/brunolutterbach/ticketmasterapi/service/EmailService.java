package br.com.brunolutterbach.ticketmasterapi.service;

import br.com.brunolutterbach.ticketmasterapi.model.enums.StatusIngresso;
import br.com.brunolutterbach.ticketmasterapi.model.evento.Evento;
import br.com.brunolutterbach.ticketmasterapi.model.ingresso.DadosIngressoQrCode;
import br.com.brunolutterbach.ticketmasterapi.repository.IngressoRepository;
import com.google.zxing.WriterException;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import javax.mail.MessagingException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@AllArgsConstructor
public class EmailService {

    final JavaMailSender mailSender;
    final QRCodeService qrCodeService;
    final IngressoRepository ingressoRepository;

    public void enviarEmailFinalizePgto(String destinatario, String nome, String nomeEvento, LocalDateTime dataEvento, LocalTime horaEvento, BigDecimal valorTotal, String linkPagamento) throws Exception {
        var message = mailSender.createMimeMessage();
        var helper = new MimeMessageHelper(message, true);
        helper.setTo(destinatario);
        helper.setSubject("Finalize o Pagamento");

        String text = lerEmailTemplatePgto(nome, nomeEvento, dataEvento, horaEvento, valorTotal, linkPagamento);
        helper.setText(text, true);

        mailSender.send(message);
    }

    public void enviarEmailConfirmacao(String destinatario, String nome, String nomeEvento, LocalDate dataEvento, LocalTime horaEvento, BigDecimal valorTotal, String endereco, List<DadosIngressoQrCode> ingressosID) throws Exception {
        var message = mailSender.createMimeMessage();
        var helper = new MimeMessageHelper(message, true);
        helper.setTo(destinatario);
        helper.setSubject("Confirmação de Compra");

        String text = lerEmailConfirmacao(nome, nomeEvento, dataEvento.atStartOfDay(), horaEvento, valorTotal, endereco, ingressosID);
        helper.setText(text, true);

        // Gerar QRCode e salvar em arquivo
        var qrCodeBytes = QRCodeService.gerarQRCode(ingressosID);
        var qrCodeFile = new File("qr-code.png");
        var outputStream = new FileOutputStream(qrCodeFile);
        outputStream.write(qrCodeBytes);
        outputStream.close();

        // Anexar QRCode ao e-mail
        var qrCodeSource = new ByteArrayResource(qrCodeBytes);
        helper.addAttachment("qr-code.png", qrCodeSource, "image/png");

        mailSender.send(message);
    }

    public void enviarEmailAtualizacaoEvento(Evento evento) throws MessagingException, IOException, WriterException {
        var message = mailSender.createMimeMessage();
        var helper = new MimeMessageHelper(message, true);
        var endereco = evento.getEnderecoEvento().getLogradouro() + ", " + evento.getEnderecoEvento().getNumero() + " - " + evento.getEnderecoEvento().getCidade();

        var ingressos = ingressoRepository.findByCompradorIsNotNullAndStatusIngresso(StatusIngresso.PAGO);

        for (var ingresso : ingressos) {
            helper.setTo(ingresso.getComprador().getEmail());
            helper.setSubject("Atualização de Evento");
            var text = lerEmailAttEvento(evento.getNome(), evento.getDataEvento(), evento.getHoraEvento(), endereco);
            helper.setText(text, true);
            mailSender.send(message);
        }
    }

    private String lerEmailTemplatePgto(String nome, String nomeEvento, LocalDateTime dataEvento, LocalTime horaEvento, BigDecimal valorTotal, String linkPagamento) throws Exception {
        var resource = new ClassPathResource("email-template.html");

        String emailTemplate = getString(resource);

        var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        var dataEventoFormatada = dataEvento.format(formatter);

        var formatterHora = DateTimeFormatter.ofPattern("HH:mm");
        var horaEventoFormatada = horaEvento.format(formatterHora);

        emailTemplate = emailTemplate.replace("${nome}", nome);
        emailTemplate = emailTemplate.replace("${nomeEvento}", nomeEvento);
        emailTemplate = emailTemplate.replace("${dataEvento}", dataEventoFormatada);
        emailTemplate = emailTemplate.replace("${horaEvento}", horaEventoFormatada);
        emailTemplate = emailTemplate.replace("${valorTotal}", valorTotal.toString());
        emailTemplate = emailTemplate.replace("${linkPagamento}", linkPagamento);

        return emailTemplate;
    }

    private String lerEmailConfirmacao(String nome, String nomeEvento, LocalDateTime dataEvento, LocalTime horaEvento, BigDecimal valorTotal, String endereco, List<DadosIngressoQrCode> ingressosID) throws IOException, WriterException {
        var resource = new ClassPathResource("email-confirmacao.html");

        String emailTemplate = getString(resource);

        var formatterData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        var dataEventoFormatada = dataEvento.format(formatterData);

        var formatterHora = DateTimeFormatter.ofPattern("HH:mm");
        var horaEventoFormatada = horaEvento.format(formatterHora);

        emailTemplate = emailTemplate.replace("${nome}", nome);
        emailTemplate = emailTemplate.replace("${nomeEvento}", nomeEvento);
        emailTemplate = emailTemplate.replace("${dataEvento}", dataEventoFormatada);
        emailTemplate = emailTemplate.replace("${horaEvento}", horaEventoFormatada);
        emailTemplate = emailTemplate.replace("${valorTotal}", valorTotal.toString());
        emailTemplate = emailTemplate.replace("${localEvento}", endereco);

        return emailTemplate;
    }

    private String lerEmailAttEvento(String nomeEvento, LocalDate dataEvento, LocalTime horaEvento, String endereco) throws IOException, WriterException {
        var resource = new ClassPathResource("email-att-evento.html");

        var emailTemplate = getString(resource);

        var formatterData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        var dataEventoFormatada = dataEvento.format(formatterData);

        var formatterHora = DateTimeFormatter.ofPattern("HH:mm");
        var horaEventoFormatada = horaEvento.format(formatterHora);

        emailTemplate = emailTemplate.replace("${nomeEvento}", nomeEvento);
        emailTemplate = emailTemplate.replace("${dataEvento}", dataEventoFormatada);
        emailTemplate = emailTemplate.replace("${horaEvento}", horaEventoFormatada);
        emailTemplate = emailTemplate.replace("${localEvento}", endereco);

        return emailTemplate;
    }

    private static String getString(ClassPathResource resource) throws IOException {
        return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
    }
}
