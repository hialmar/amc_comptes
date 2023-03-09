package fr.miage.amc_comptes.controllers;

import fr.miage.amc_comptes.entities.Compte;
import fr.miage.amc_comptes.repositories.CompteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Service d'exposition REST des comptes.
 * URL / exposée.
 */
@RestController
@RequestMapping("/")
public class CompteController {

    Logger logger = LoggerFactory.getLogger(CompteController.class);

    // Injection DAO compte
    @Autowired
    private CompteRepository repo;

    /**
     * GET 1 compte
     * @param id id du compte
     * @return Compte converti en JSON
     */
    @GetMapping("{id}")
    public Compte getCompte(@PathVariable("id") long id) {
        Optional<Compte> optionalCompte = repo.findById(id);
        if (optionalCompte.isPresent()) {
            Compte compte = optionalCompte.get();
            logger.info("Compte : demande d'un compte avec id:{}", compte.getId());
            return compte;
        } else {
            logger.info("Compte : compte inexistant avec l'id: {}", id);
            throw new RuntimeException("Le compte d'id "+id+" n'existe pas");
        }

    }

    /**
     * GET liste des comptes d'un client
     * @return liste des comptes en JSON. [] si aucun compte.
     */
    @GetMapping("")
    public Iterable<Compte> getComptes(@RequestParam("client") long id) {
        logger.info("Compte : demande des comptes d'un client avec id:{}", id);
        return repo.findAllByIdclient(id);
    }
    
    /**
     * GET liste des comptes
     * @return liste des comptes en JSON. [] si aucun compte.
     */
    @GetMapping("all")
    public Iterable<Compte> getComptes() {
        logger.info("Compte : demande de la liste des comptes");
        return repo.findAll();
    }

    /**
     * POST un compte
     * @param cpt compte à ajouter (import JSON)
     * @return compte ajouté
     */
    @PostMapping("")
    public Compte postClient(@RequestBody Compte cpt) {
        if (repo.existsById(cpt.getId())) {
            logger.info("Compte : erreur compte déjà présent avec l'id : {}", cpt.getId());
            throw new RuntimeException("Un compte existe déjà avec l'id "+cpt.getId());
        }
        logger.info("Compte : demande CREATION d'un compte avec id : {}", cpt.getId());
        return repo.save(cpt);
    }

}
