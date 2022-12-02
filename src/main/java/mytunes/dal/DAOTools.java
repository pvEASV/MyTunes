package mytunes.dal;

public class DAOTools {
    /**
     * Used to replace ' with '' in a string to make it SQL compatible
     * @param string the string to check
     * @return the string with ' replaced by ''
     */
    public static String validateStringForSQL(String string){
        if (string == null) return null;
        string = string.replace("'", "''");
        return string;
    }
}
