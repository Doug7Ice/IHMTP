package app.workers;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Oui je me suis fait chier...
 *
 * @author anthonyc.alonsolo
 */
public class WorkerActiveDirectoryLDAP {
    static DirContext ldapContext;

    public static void main(String[]args){
        isProf("euler@ldap.forumsys.com");
    }

    public static boolean isProf(String email){
        if (email == null || email.isEmpty()) return false;
        boolean prof = false;
        try {
            connectToDirectory("ldap://ldap.forumsys.com:389", "simple", "read-only-admin", "example.com", "password");
            if (email.split("@")[0] == null || email.split("@")[0].isEmpty()) return false;
            String get = askDirectory(email.split("@")[0]);
            System.out.println(get);
            System.out.println(email);
            prof = get.equals(email);
            System.out.println(prof);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return prof;
    }

    public static void testConntection() {
        try {
            connectToDirectory("ldap://ldap.forumsys.com:389", "simple", "read-only-admin", "example.com", "password");
        } catch (Exception e) {
            e.getStackTrace();

        }

    }

    public static void connectToDirectory(String urlProvider, String authentification, String CNuserConnect, String directory, String password) throws NamingException {
        Hashtable<String, String> ldapEnv = new Hashtable<String, String>(11);
        ldapEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        ldapEnv.put(Context.PROVIDER_URL, urlProvider);
        ldapEnv.put(Context.SECURITY_AUTHENTICATION, authentification);

        ldapEnv.put(Context.SECURITY_PRINCIPAL, "cn=" + CNuserConnect + ",dc=example,dc=com");
        ldapEnv.put(Context.SECURITY_CREDENTIALS, password);
        ldapContext = new InitialDirContext(ldapEnv);
    }

    public static String askDirectory(String name) {
        String retour = "";
        try {
            // Create the search controls
            SearchControls searchCtls = new SearchControls();

            //Specify the attributes to return
            String[] returnedAtts = {"mail"};
            searchCtls.setReturningAttributes(returnedAtts);

            //Specify the search scope
            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

            //specify the LDAP search filter
            String searchFilter = "(uid="+name+")";

            //Specify the Base for the search
            String searchBase = "dc=example,dc=com";
            //initialize counter to total the results
            int totalResults = 0;
            System.out.println("Je cherche : " + returnedAtts[0] + " | de : " + name + " | Sur l'annuaire : " + searchBase);
            // Search for objects using the filter
            NamingEnumeration<SearchResult> answer = ldapContext.search(searchBase, searchFilter, searchCtls);

            //Loop through the search results
            while (answer.hasMoreElements()) {
                totalResults++;
                Attributes attrs = answer.next().getAttributes();
                retour = attrs.get("mail").get().toString();
            }


            System.out.println("Total results: " + totalResults);
            ldapContext.close();
        } catch (Exception e) {
            System.out.println(" Search error: " + e);
            e.printStackTrace();
        }
        return retour;
    }
}
