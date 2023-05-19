package br.com.brunolutterbach.ticketmasterapi.service;

import br.com.brunolutterbach.ticketmasterapi.model.ingresso.Ingresso;
import br.com.brunolutterbach.ticketmasterapi.repository.IngressoRepository;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PayPalService {

    final APIContext apiContext;
    final IngressoRepository ingressoRepository;

    public String createOrder(BigDecimal valorTotal, String returnUrl, String cancelUrl, List<Ingresso> ingressos) throws PayPalRESTException {
        Amount amount = new Amount();
        amount.setCurrency("BRL");
        amount.setTotal(valorTotal.toString());

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        for (Ingresso ingresso : ingressos) {
            transaction.setDescription("Ingresso " + ingresso.getId());
            transaction.setCustom(ingresso.getId().toString());
        }
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("SALE");
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(returnUrl);
        payment.setRedirectUrls(redirectUrls);

        Payment createdPayment = payment.create(apiContext);

        for (Ingresso ingresso : ingressos) {
            ingresso.setPaymentId(createdPayment.getId());
            ingressoRepository.save(ingresso);
        }

        return createdPayment.getLinks().stream()
                .filter(link -> link.getRel().equalsIgnoreCase("approval_url"))
                .findFirst()
                .orElseThrow(() -> new PayPalRESTException("Erro ao criar pagamento"))
                .getHref();
    }

    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);
        return payment.execute(apiContext, paymentExecution);
    }
}


