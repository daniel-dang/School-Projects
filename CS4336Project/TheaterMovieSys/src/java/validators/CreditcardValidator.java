/*
This is the credit card validator that it validate if the card is numbered format and is it 16 digit in number
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
@FacesValidator("CreditCardValidator")
public class CreditcardValidator implements Validator {
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        
        String creditCardNum = (String)value;
        
        if (!isANumber(creditCardNum)){
            FacesMessage message = new FacesMessage("Zipcode must be in numeric format. Re-enter a valid zipcode.");
            throw new ValidatorException(message);
        }
        if (creditCardNum.length() != 16){
            FacesMessage message = new FacesMessage("Zipcode must be 16 digit number. Re-enter a valid zipcode.");
            throw new ValidatorException(message);
        }
    }
    
    protected static boolean isANumber(String num) {
        for (char digit : num.toCharArray()) {
            if (!Character.isDigit(digit)) {
                return false;
            }
        }
        return true;
    }
}
