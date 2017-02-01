/*
this is the zipcode validator and it will verify if the user have 5 digit in their zipcode and is it number.
 */
package validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Daniel Dang
 */
@FacesValidator("ZipcodeValidator")
public class ZipCodeValidator implements Validator{
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String zipcode = (String)value;
        
        if (!isANumber(zipcode)){
            FacesMessage message = new FacesMessage("Zipcode must be in numeric format. Re-enter a valid zipcode.");
            throw new ValidatorException(message);
        }
        if (zipcode.length() != 5){
            FacesMessage message = new FacesMessage("Zipcode must be 5 digit number. Re-enter a valid zipcode.");
            throw new ValidatorException(message);
        }
    }
    
    protected static boolean isANumber(String num) {
        try {
            Integer.parseInt(num);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
