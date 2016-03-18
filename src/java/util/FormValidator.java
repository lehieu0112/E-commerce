package util;


public class FormValidator {
    public boolean isPresent(String c){
        if((c.length()==0)||(c == null)){          
           return false;
        }
        return true;
    }
    public boolean isInterger(String c){
        try{
            int i = Integer.parseInt(c);
            return true;
        }
        catch(NumberFormatException e){           
            return false;
        }
    }
    public boolean isDouble(String c){
            try{
                double i = Double.parseDouble(c);
                return true;
            }
            catch(NumberFormatException e){              
                return false;
            }
    }
   
}
