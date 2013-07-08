package info.shelfunit.testing;

import info.shelfunit.testing.MyActor;

import org.apache.james.admin.webapp.beans.UserManagerBean;
import javax.faces.event.ActionEvent;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import org.apache.james.admin.webapp.hibernate.NewHibernateUtil;
import org.apache.james.admin.webapp.hibernate.pojos.Users;
import org.hibernate.Query;
import org.hibernate.Session;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author ericm
 */
public class MyActorTest {

    private String username;
    private String hibernateUserName;

    public UserManagerBeanTest() {
    }

    @BeforeClass
	public static void setUpClass() throws Exception {

    }

    @AfterClass
	public static void tearDownClass() throws Exception {
    }

    @Before
	public void setUp() {
        UUID uuid = UUID.randomUUID();
        this.setUsername( uuid.toString() );
        UUID hibernateUuid = UUID.randomUUID();
        this.setHibernateUserName( hibernateUuid.toString() );
    } // end method setUp

    @After
	public void tearDown() {
    }


    /**
     * Test of digestString method, of class UserManagerBean.
     */
    @Test
	public void testDigestString() throws Exception {
        System.out.println( "digestString" );

        Integer theInt = new Integer( this.hashCode() );
        String pass = theInt.toString();
        String algorithm = "SHA-256";
        UserManagerBean instance = new UserManagerBean();
        String result1 = instance.digestString( pass, algorithm );
        String result2 = instance.digestString( pass, algorithm );
        System.out.println( "Here is the password: " + pass );
        System.out.println( "result1: " + result1 );
        System.out.println( "result2: " + result2 );
        org.junit.Assert.assertEquals( result1, result2 );
    } // end method testDigestString()

    /**
     * Test of addUser method, of class UserManagerBean. Testing the underlying
     * Hibernate API
     */
    @Test
	public void testAddUserHibernate() throws Exception {
        
        System.out.println( "testAddUserHibernate" );
        UserManagerBean instance = new UserManagerBean();

        Session session = NewHibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Integer theInt = new Integer( this.hashCode() );
        String pass = theInt.toString();
        String algorithm = "SHA-256";
        String queryString = "from Users where username like :theName"
	    + " order by username";

        Query q = session.createQuery( queryString );
        q.setString( "theName", this.getHibernateUserName() + "%" );
        List userList = q.list();
        // user does not exist
        org.junit.Assert.assertEquals( 0, userList.size() );

        String passAlgo = instance.digestString( pass, algorithm );

        Users user = new Users();
        user.setUsername( this.getHibernateUserName() );
        user.setPwdAlgorithm( algorithm );
        user.setPwdHash( passAlgo );
        session.save( user );

        session.getTransaction().commit();

        q = session.createQuery( queryString );
        q.setString( "theName", this.getHibernateUserName() + "%" );
        List userList2 = q.list();
        session.close();
        // user does exist
        org.junit.Assert.assertEquals( 1, userList2.size() );
        
    } // end method testAddUserHibernate

    /**
     * Test the UserManagerBean.addUser method.
     * @throws Exception
     */
    @Test
	public void testAddUser() throws Exception {
        
        System.out.println( "testAddUser" );
        UserManagerBean instance = new UserManagerBean();

        Session session = NewHibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Integer theInt = new Integer( this.hashCode() );
        String pass = theInt.toString();
        String algorithm = "SHA-256";
        String queryString = "from Users where username like :theName"
	    + " order by username";

        Query q = session.createQuery( queryString );
        q.setString( "theName", this.getUsername() + "%" );
        List userList = q.list();
        // user does not exist
        org.junit.Assert.assertEquals( 0, userList.size() );

        String passAlgo = instance.digestString( pass, algorithm );

        instance.setUsername( this.getUsername() );
        instance.setPassword( pass );
        instance.setAlgorithm( algorithm );
        String result = instance.addUser();
        org.junit.Assert.assertTrue( result.equalsIgnoreCase( "success" ) );

        session.getTransaction().commit();

        q = session.createQuery( queryString );
        q.setString( "theName", this.getUsername() + "%" );
        List userList2 = q.list();
        session.close();
        // user does exist
        org.junit.Assert.assertEquals( 1, userList2.size() );

        // try adding user again
        try {
            result = instance.addUser();
            org.junit.Assert.assertTrue( result.equalsIgnoreCase( "duplicateUser" ) );
        } catch ( Exception e ) {
            System.out.println( "Intentional exception" );
            System.out.println( e.getMessage() );
        }
      
    } // end method testAddUser

    /**
     * Test of changeUser method, of class UserManagerBean.
     */
    @Test
	public void testChangeUser() {
        System.out.println( "changeUser" );
        UserManagerBean instance = new UserManagerBean();
        // instance.changeUser();
        // TODO review the generated test code and remove the default call to fail.
        // fail( "The test case is a prototype." );
    } // end method testChangeUser

    /**
     * Test of dropUser method, of class UserManagerBean.
     */
    @Test
	public void testDropUser() {
        System.out.println( "dropUser" );
        ActionEvent e = null;
        UserManagerBean instance = new UserManagerBean();
        String expResult = "";
        // String result = instance.dropUser( e );
        // assertEquals( expResult, result );
        // TODO review the generated test code and remove the default call to fail.
        // fail( "The test case is a prototype." );
    } // end method testDropUser

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the hibernateUserName
     */
    public String getHibernateUserName() {
        return hibernateUserName;
    }

    /**
     * @param hibernateUserName the hibernateUserName to set
     */
    public void setHibernateUserName(String hibernateUserName) {
        this.hibernateUserName = hibernateUserName;
    }


} // end class MyActorTest