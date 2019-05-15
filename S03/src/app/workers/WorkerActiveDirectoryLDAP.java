package app.workers;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.util.Hashtable;

/**
 * Oui je me suis fait chier...
 *
 * @author anthonyc.alonsolo
 */
public class WorkerActiveDirectoryLDAP {
    static DirContext ldapContext;

    public static void testConntection() {
        try {
            connectToDirectory("ldap://ldap.forumsys.com:389", "simple", "read-only-admin", "example.com", "password");
        } catch (Exception e) {
            System.out.println(e.getStackTrace());

        }

    }

    public static void connectToDirectory(String urlProvider, String authentification, String CNuserConnect, String directory, String password) throws NamingException {
        Hashtable<String, String> ldapEnv = new Hashtable<String, String>(11);
        ldapEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        ldapEnv.put(Context.PROVIDER_URL, urlProvider);
        ldapEnv.put(Context.SECURITY_AUTHENTICATION, authentification);
        ldapEnv.put(Context.SECURITY_PRINCIPAL, "cn=" + CNuserConnect + ",dc=" + directory.split(".")[0] + ",dc=" + directory.split(".")[1]);
        ldapEnv.put(Context.SECURITY_CREDENTIALS, password);
        ldapContext = new InitialDirContext(ldapEnv);
    }

    public static Attributes askDirectory() {
        try {
            // Create the search controls
            SearchControls searchCtls = new SearchControls();

            //Specify the attributes to return
            String returnedAtts[] = {"cn"};
            searchCtls.setReturningAttributes(returnedAtts);

            //Specify the search scope
            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

            //specify the LDAP search filter
            String searchFilter = "(uid=euler)";

            //Specify the Base for the search
            String searchBase = "dc=example,dc=com";
            //initialize counter to total the results
            int totalResults = 0;
            System.out.println("Je cherche : " + returnedAtts[0] + " | de : " + searchFilter + " | Sur l'annuaire : " + searchBase);
            // Search for objects using the filter
            NamingEnumeration<SearchResult> answer = ldapContext.search(searchBase, searchFilter, searchCtls);

            //Loop through the search results
            while (answer.hasMoreElements()) {
                SearchResult sr = (SearchResult) answer.next();

                totalResults++;

                System.out.println(">>>" + sr.getName());
                Attributes < = sr.getAttributes();
                System.out.println(">>>>>>" + attrs.get("cn"));
            }

            System.out.println("Total results: " + totalResults);
            ldapContext.close();
        } catch (Exception e) {
            System.out.println(" Search error: " + e);
            e.printStackTrace();
            System.exit(-1);

        }
        return
    }
}
