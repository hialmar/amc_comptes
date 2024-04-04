package fr.miage.amc_comptes;

import fr.miage.amc_comptes.entities.Compte;
import fr.miage.amc_comptes.repositories.CompteRepository;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.server.observation.ServerRequestObservationContext;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

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
            Compte c = new Compte(0, 1000, 1L);

            compteRepository.save(c);
        }
    }

    @Bean
    @ConditionalOnMissingBean
    ObservationRegistry observationRegistry() {
        PathMatcher pathMatcher = new AntPathMatcher("/");
        ObservationRegistry observationRegistry = ObservationRegistry.create();
        observationRegistry.observationConfig().observationPredicate((name, context) -> {
            if(context instanceof ServerRequestObservationContext) {
                return !pathMatcher.match("/actuator/**", ((ServerRequestObservationContext) context).getCarrier().getRequestURI());
            } else {
                return true;
            }
        });
        return observationRegistry;
    }
}
