package sendmail.vg;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CertificadoRepository extends JpaRepository<Certificado,Long> {

    List<Certificado> findCertificadoByEnviadoIsNull(Pageable pageable);

}
