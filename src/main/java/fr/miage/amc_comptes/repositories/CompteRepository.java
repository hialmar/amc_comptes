package fr.miage.amc_comptes.repositories;

import fr.miage.amc_comptes.entities.Compte;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Repository de gestion des comptes en banque
 */

public interface CompteRepository extends MongoRepository<Compte,Long> {

    List<Compte> findAllByIdclient(Long idclient);
}
