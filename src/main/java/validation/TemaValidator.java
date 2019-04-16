package validation;

import domain.Tema;

public class TemaValidator implements Validator<Tema> {

    /**
     * Valideaza o tema
     * @param entity - tema pe care o valideaza
     * @throws ValidationException daca tema nu e valida
     */
    @Override
    public void validate(Tema entity) throws ValidationException {
        //1
        if(entity.getID().equals("") || entity.getID() == null) {
            //6
            throw new ValidationException("Numar tema invalid!");
        }
        //2
        if(entity.getDescriere().equals("")){
            //6
            throw new ValidationException("Descriere invalida!");
        }
        //3
        if(entity.getDeadline() < 1 || entity.getDeadline() > 14) {
            //6
            throw new ValidationException("Deadlineul trebuie sa fie intre 1-14.");
        }
        //4
        if(entity.getPrimire() < 1 || entity.getPrimire() > 14) {
            //6
            throw new ValidationException("Saptamana primirii trebuie sa fie intre 1-14.");
        }
        //5
    }

}
