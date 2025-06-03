package dao;

import Model.Municipality;
import exceptions.DataAccessException;

import java.util.ArrayList;
import java.util.List;

/**
 * DAO “demo” che si comporta come DbMunicipalityDao, ma
 * legge/scrive solo in memoria (RAM). Non esegue query SQL
 * e non chiude connessioni, PreparedStatement, né ResultSet.
 */
public class DemoMunicipalityDao extends MunicipalityDao {

    private final List<Municipality> municipalities = new ArrayList<>();
    private static DemoMunicipalityDao instance;

    /**
     * Restituisce l’istanza singleton di DemoMunicipalityDao,
     * inizializzandola al primo accesso con dati fittizi.
     */
    public static DemoMunicipalityDao getInstance() {
        if (instance == null) {
            instance = new DemoMunicipalityDao();
        }
        return instance;
    }

    /**
     * Costruttore privato: popola la “tabella” in RAM con alcuni dati di esempio.
     */
    private DemoMunicipalityDao() {
        municipalities.add(new Municipality("Milano",    "Milano", "MI001", "Lombardia"));
        municipalities.add(new Municipality("Grottaferrata","Roma",  "GR001","Lazio"));
        municipalities.add(new Municipality("Frascati",   "Roma",   "FR001","Lazio"));
        // Aggiungi altri se necessario…
    }

    /**
     * Cerca tutti i comuni il cui nome contiene (case‐insensitive) la stringa passata.
     * Se il parametro è null, restituisce lista vuota.
     */
    @Override
    public List<Municipality> getMunicipalityByName(String nameFragment) {
        try {
            if (nameFragment == null || nameFragment.trim().isEmpty()) {
                return List.of();
            }
            String needle = nameFragment.trim().toLowerCase();
            List<Municipality> result = new ArrayList<>();
            for (Municipality m : municipalities) {
                if (m.getName().toLowerCase().contains(needle)) {
                    result.add(m);
                }
            }
            return result;
        } catch (Exception ex) {
            throw new DataAccessException("Errore cercando comuni per nome (demo)", ex);
        }
    }

    /**
     * Restituisce il Comune il cui codice corrisponde esattamente (case‐insensitive)
     * a quello passato, oppure null se non trovato.
     */
    @Override
    public Municipality getMunicipalityByCode(String code) {
        try {
            if (code == null || code.trim().isEmpty()) {
                return null;
            }
            String target = code.trim();
            for (Municipality m : municipalities) {
                if (m.getCodice().equalsIgnoreCase(target)) {
                    return m;
                }
            }
            return null;
        } catch (Exception ex) {
            throw new DataAccessException("Errore cercando comune per codice (demo)", ex);
        }
    }

    /**
     * Restituisce il Comune il cui nome **esatto** (case‐insensitive) e la cui regione
     * (case‐insensitive) corrispondono ai parametri, oppure null se non trovato.
     */
    @Override
    public Municipality getMunicipalityByNameAndRegion(String name, String region) {
        try {
            if (name == null || region == null || name.isEmpty() || region.isEmpty()) {
                return null;
            }
            String nameTarget   = name.trim();
            String regionTarget = region.trim();
            for (Municipality m : municipalities) {
                if (m.getName().equalsIgnoreCase(nameTarget)
                        && m.getRegion().equalsIgnoreCase(regionTarget)) {
                    return m;
                }
            }
            return null;
        } catch (Exception ex) {
            throw new DataAccessException("Errore cercando comune per nome e regione (demo)", ex);
        }
    }
}


