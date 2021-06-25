package sendmail.vg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VgApplication {

	public static void main(String[] args) {
		SpringApplication.run(VgApplication.class, args);
	}

}

// docker run -e 'ACCEPT_EULA=Y' -e 'SA_PASSWORD=Passwd87' -p 1433:1433 -d mcr.microsoft.com/mssql/server:2019-latest
// docker ps
// docker exec -it 5e8acc3c8829 /opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P Passwd87