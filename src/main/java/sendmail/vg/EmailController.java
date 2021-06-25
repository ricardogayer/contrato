package sendmail.vg;

import ch.qos.logback.classic.pattern.EnsureExceptionHandling;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
@EnableAutoConfiguration
public class EmailController {

    private EmailTemplateService emailTemplateService;

    @Value("${application.email.assunto.prefixo}")
    private String ASSUNTO_PREFIXO;

    @Value("${application.email.template}")
    private String EMAIL_TEMPLATE;

    @Value("${application.email.batch.size}")
    private int BATCH_SIZE;

    final
    EmailServiceImpl emailService;
    private EnsureExceptionHandling templateEngine;

    final
    CertificadoRepository certificadoRepository;

    public EmailController(EmailServiceImpl emailService, CertificadoRepository certificadoRepository, EmailTemplateService emailTemplateService) {
        this.emailService = emailService;
        this.certificadoRepository = certificadoRepository;
        this.emailTemplateService = emailTemplateService;
    }

    @GetMapping("/anexo")
    public void sendMailAnexo() throws MessagingException, TemplateException, IOException {

        List<Certificado> certificados = certificadoRepository.findCertificadoByEnviadoIsNull(PageRequest.of(0, BATCH_SIZE));

        for (Certificado certificado : certificados) {

            Map<String, Object> data = new LinkedHashMap<>();
            data.put("CLIENTE", certificado.getNome());
            data.put("CERTIFICADO", certificado.getCertificado().toString());

            String corpo = emailTemplateService.getCorpoEmail(data, EMAIL_TEMPLATE);

            emailService.sendMessageWithAttachment(certificado.getEmail(),
                    ASSUNTO_PREFIXO + certificado.getCertificado(),
                    corpo,
                    certificado.getArquivo());

            certificado.setEnviado(LocalDateTime.now());
            certificadoRepository.save(certificado);

        }

    }

}
