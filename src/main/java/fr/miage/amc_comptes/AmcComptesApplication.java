package fr.miage.amc_comptes;

import fr.miage.amc_comptes.entities.Compte;
import fr.miage.amc_comptes.repositories.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AmcComptesApplication implements CommandLineRunner {

    @Autowired
    CompteRepository compteRepository;

    public static void main(String[] args) {
        SpringApplication.run(AmcComptesApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        assert(compteRepository != null);

        if(!compteRepository.existsById(0L)) {
            Compte c = new Compte(0, 1000, 0L);

            compteRepository.save(c);
        }
    }
}
