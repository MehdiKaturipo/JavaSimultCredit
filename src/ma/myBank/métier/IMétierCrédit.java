package ma.myBank.métier;

import ma.myBank.modéle.Crédit;

public interface IMétierCrédit {
    Crédit calculerMensualité(Long idCrédit) throws Exception;
}
