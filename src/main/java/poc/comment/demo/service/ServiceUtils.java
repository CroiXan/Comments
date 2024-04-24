package poc.comment.demo.service;

import poc.comment.demo.model.ParsedLong;

public class ServiceUtils {

    public ParsedLong validateLong(String intAsStr,String paramName){

        ParsedLong parsedLong = new ParsedLong();

        try {

            parsedLong.setSuccess(true);
            Long parsedInt = Long.parseLong(intAsStr);

            if(parsedInt < 0){
                parsedLong.setSuccess(false);
                parsedLong.setErrorMessage(paramName + " no puede ser negativo");
            }

            parsedLong.setResultLong(parsedInt);
        } catch (Exception e) {
            parsedLong.setErrorMessage(paramName + " no valido");
            parsedLong.setSuccess(false);
        }

        return parsedLong;

    }

}
