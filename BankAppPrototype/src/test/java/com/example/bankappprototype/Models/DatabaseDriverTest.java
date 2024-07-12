package com.example.bankappprototype.Models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.mockito.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class DatabaseDriverTest {

    @Mock
    private Connection connMock;
    @Mock
    private Statement stmtMock;
    @Mock
    private PreparedStatement pstmtMock;
    @Mock
    private ResultSet rsMock;
    @Mock
    private Model model;
    private DatabaseDriver databaseDriver;

    /**
     * Setup Mock database for testing
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        databaseDriver = new DatabaseDriver();
        databaseDriver.conn = connMock;
    }

    /**
     * Test getClientData with mockDB
     */
    @Test
    void testGetClientData() throws SQLException {
        when(connMock.createStatement()).thenReturn(stmtMock);
        when(stmtMock.executeQuery(anyString())).thenReturn(rsMock);

        ResultSet result = databaseDriver.getClientData("test@example.com", "password");

        assertNotNull(result);
        verify(stmtMock).executeQuery("SELECT * FROM Client WHERE email='test@example.com' AND Password='password';");
    }

    /**
     * Test getAllTransactionsOfClient with mockDB
     */
    @Test
    void testGetAllTransactionsOfClient() throws SQLException {
        when(connMock.createStatement()).thenReturn(stmtMock);
        when(stmtMock.executeQuery(anyString())).thenReturn(rsMock);

        ResultSet result = databaseDriver.getAllTransactionsOfClient(1);

        assertNotNull(result);
        verify(stmtMock).executeQuery("SELECT * FROM 'Transaction' WHERE Sender ='1' or receiver ='1';");
    }

    /**
     * Test GetAllFriendsOfClient with mockDB
     */
    @Test
    void testGetAllFriendsOfClient() throws SQLException {
        when(connMock.createStatement()).thenReturn(stmtMock);
        when(stmtMock.executeQuery(anyString())).thenReturn(rsMock);

        ResultSet result = databaseDriver.getAllFriendsOfClient(1);

        assertNotNull(result);
        verify(stmtMock).executeQuery("SELECT * FROM 'Friends' WHERE Client ='1' or FriendClient ='1';");
    }

    /**
     * Test GetAllSpaces with mockDB
     */
    @Test
    void testGetAllSpaces() throws SQLException {
        when(connMock.createStatement()).thenReturn(stmtMock);
        when(stmtMock.executeQuery(anyString())).thenReturn(rsMock);

        ResultSet result = databaseDriver.getAllSpaces(1);

        assertNotNull(result);
        verify(stmtMock).executeQuery("SELECT * FROM 'Account' WHERE OWNER ='1' AND MAINACCOUNT = '0';");
    }

    /**
     * Test GetCards with mockDB
     */
    @Test
    void testGetCards() throws SQLException {
        when(connMock.createStatement()).thenReturn(stmtMock);
        when(stmtMock.executeQuery(anyString())).thenReturn(rsMock);
        when(rsMock.getInt(1)).thenReturn(1);

        int account = 1;
        when(databaseDriver.getAccountId("iban")).thenReturn(account);

        ResultSet result = databaseDriver.getCards("iban");

        assertNotNull(result);
        verify(stmtMock).executeQuery("SELECT * FROM 'Card' WHERE ACCOUNT =" + account + ";");
    }

    /**
     * Test GetCard with mockDB
     */
    @Test
    void testGetCard() throws SQLException {
        when(connMock.createStatement()).thenReturn(stmtMock);
        when(stmtMock.executeQuery(anyString())).thenReturn(rsMock);

        ResultSet result = databaseDriver.getCard("cardNum");

        assertNotNull(result);
        verify(stmtMock).executeQuery("SELECT CardLimit, StatusOnline, StatusTerminal FROM Card WHERE CardNumber = 'cardNum';");
    }

    /**
     * Test UpdateCard with mockDB
     */
    @Test
    void testUpdateCard() throws SQLException {
        try (MockedStatic<Model> modelMock = mockStatic(Model.class)) {
            modelMock.when(Model::getInstance).thenReturn(model);
            when(model.getCardNum()).thenReturn("1234-5678-9123-4567");
        }
        when(connMock.createStatement()).thenReturn(stmtMock);
        when(stmtMock.executeQuery(anyString())).thenReturn(rsMock);
        when(rsMock.isBeforeFirst()).thenReturn(true);
        when(rsMock.getInt(1)).thenReturn(1);
        when(rsMock.getInt(2)).thenReturn(5000);
        when(connMock.prepareStatement(anyString())).thenReturn(pstmtMock);

        databaseDriver.updateCard(1000, 1, 1, true, true);

        verify(stmtMock).executeQuery("select Account, CardLimit from Card where Card.CardNumber='null';");
        verify(stmtMock).executeUpdate("UPDATE Card  SET CardLimit =1000, StatusOnline = 1, StatusTerminal = 1 WHERE CardNumber = 'null';");

        verify(pstmtMock, times(3)).executeUpdate();

        verify(pstmtMock, times(3)).setInt(1, 1);
        verify(pstmtMock, times(2)).setDouble(2, 0);
        verify(pstmtMock, times(3)).setString(3, DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now()));
        verify(pstmtMock, times(3)).setString(4, "Card :null");
        verify(pstmtMock, times(1)).setString(5, TransactionTypes.KARTE_ONLINE_STATUS.toString());
        verify(pstmtMock, times(3)).setInt(6, 1);

        verify(pstmtMock, times(1)).setString(5, TransactionTypes.KARTE_BANKOMAT_STATUS.toString());

        verify(pstmtMock, times(1)).setDouble(2, 1000);
        verify(pstmtMock, times(1)).setString(5, TransactionTypes.KARTE_LIMIT_AENDERUNG.toString());
    }

    /**
     * Test GetAccountId with mockDB
     */
    @Test
    void testGetAccountId() throws SQLException {
        when(connMock.createStatement()).thenReturn(stmtMock);
        when(stmtMock.executeQuery(anyString())).thenReturn(rsMock);
        when(rsMock.getInt(1)).thenReturn(1);

        int result = databaseDriver.getAccountId("iban");

        assertEquals(1, result);
        verify(stmtMock).executeQuery("select ID from Account where AccountNumber ='iban';");
    }

    /**
     * Test IbanValid with mockDB
     */
    @Test
    void testIbanValid() throws SQLException {
        when(connMock.createStatement()).thenReturn(stmtMock);
        when(stmtMock.executeQuery(anyString())).thenReturn(rsMock);
        when(rsMock.isBeforeFirst()).thenReturn(true);
        when(rsMock.getInt(1)).thenReturn(1);

        boolean result = databaseDriver.ibanValid("iban");

        assertTrue(result);
        verify(stmtMock).executeQuery("select MainAccount from Account where AccountNumber ='iban';");
    }

    /**
     * Test GetAccountBalance with mockDB
     */
    @Test
    void testGetAccountBalance() throws SQLException {
        when(connMock.createStatement()).thenReturn(stmtMock);
        when(stmtMock.executeQuery(anyString())).thenReturn(rsMock);
        when(rsMock.getDouble(1)).thenReturn(1000.0);

        double result = databaseDriver.getAccountBalance("iban");

        assertEquals(1000.0, result);
        verify(stmtMock).executeQuery("select Balance from Account where AccountNumber ='iban';");
    }

    /**
     * Test GetAdminData with mockDB
     */
    @Test
    void testGetAdminData() throws SQLException {
        when(connMock.createStatement()).thenReturn(stmtMock);
        when(stmtMock.executeQuery(anyString())).thenReturn(rsMock);

        ResultSet result = databaseDriver.getAdminData("username", "password");

        assertNotNull(result);
        verify(stmtMock).executeQuery("SELECT * FROM Admin WHERE Username='username' AND Password='password';");
    }

    /**
     * Test CreateClient with mockDB
     */
    @Test
    void testCreateClient() throws SQLException {
        when(connMock.createStatement()).thenReturn(stmtMock);

        databaseDriver.createClient("John", "Doe", "john@example.com", "password", LocalDate.of(2023, 7, 1));

        verify(stmtMock).executeUpdate("INSERT INTO Client(FirstName, LastName, email, Password, Date, image)VALUES ('John', 'Doe','john@example.com','password', '2023-07-01', 'C:\\Users\\User\\IdeaProjects\\PR_SOFT\\BankAppPrototype\\src\\main\\resources\\Images\\Max_Mustermann.png');");
    }

    /**
     * Test UpdateClient with mockDB
     */
    @Test
    void testUpdateClient() throws SQLException {
        when(connMock.createStatement()).thenReturn(stmtMock);

        databaseDriver.updateClient("John", "Doe", "john@example.com", "newpassword");

        verify(stmtMock).executeUpdate("UPDATE Client SET FirstName = 'John', LastName = 'Doe', Password = 'newpassword'WHERE email = 'john@example.com';");
    }

    /**
     * Test UpdateProfilePic with mockDB
     */
    @Test
    void testUpdateProfilePic() throws SQLException {
        when(connMock.createStatement()).thenReturn(stmtMock);

        databaseDriver.updateProfilePic("newpath/to/image.png", "john@example.com");

        verify(stmtMock).executeUpdate("UPDATE Client SET image = 'newpath/to/image.png' WHERE email = 'john@example.com';");
    }

    /**
     * Test CreateMainAccount with mockDB
     */
    @Test
    void testCreateMainAccount() throws SQLException {
        when(connMock.createStatement()).thenReturn(stmtMock);
        when(rsMock.getInt("seq")).thenReturn(1);
        when(stmtMock.executeQuery(anyString())).thenReturn(rsMock);

        databaseDriver.createMainAccount("1234567890", 1000.0, 500.0);

        verify(stmtMock).executeUpdate("INSERT INTO Account(Owner, AccountNumber, TransactionLimit, Balance, MainAccount)VALUES ('2','1234567890','1000.0','500.0','1')");
    }

    /**
     * Test CreateSpace with mockDB
     */
    @Test
    void testCreateSpace() throws SQLException {
        when(connMock.createStatement()).thenReturn(stmtMock);

        databaseDriver.createSpace("1234567890", 1000.0, 500.0, "MySpace", "space_image.png");

        verify(stmtMock).executeUpdate("INSERT INTO Account(Owner, AccountNumber, TransactionLimit, Balance, MainAccount, space_name, space_image)VALUES ('"+Model.getInstance().getClient().idProperty().getValue()+"','1234567890','1000.0','500.0','0','MySpace','space_image.png')");
    }

    /**
     * Test CreateCard with mockDB
     */
    @Test
    void testCreateCard() throws SQLException {
        when(connMock.createStatement()).thenReturn(stmtMock);
        when(rsMock.getInt(1)).thenReturn(1);
        when(stmtMock.executeQuery(anyString())).thenReturn(rsMock);

        databaseDriver.createCard(1000);

        verify(stmtMock).executeUpdate(anyString());
    }

    /**
     * Test GetAllClientsData with mockDB
     */
    @Test
    void testGetAllClientsData() throws SQLException {
        when(connMock.createStatement()).thenReturn(stmtMock);
        when(stmtMock.executeQuery(anyString())).thenReturn(rsMock);

        ResultSet result = databaseDriver.getAllClientsData();

        assertNotNull(result);
        verify(stmtMock).executeQuery("SELECT  * FROM  Client;");
    }

    /**
     * Test GetLastClientsId with mockDB
     */
    @Test
    void testGetLastClientsId() throws SQLException {
        when(connMock.createStatement()).thenReturn(stmtMock);
        when(stmtMock.executeQuery(anyString())).thenReturn(rsMock);
        when(rsMock.getInt("seq")).thenReturn(1);

        int result = databaseDriver.getLastClientsId();

        assertEquals(1, result);
        verify(stmtMock).executeQuery("SELECT * FROM sqlite_sequence WHERE name='Client';");
    }

    /**
     * Test GetClientEmailByAccountID with mockDB
     */
    @Test
    void testGetClientEmailByAccountID() throws SQLException {
        when(connMock.createStatement()).thenReturn(stmtMock);
        when(stmtMock.executeQuery(anyString())).thenReturn(rsMock);
        when(rsMock.getString(1)).thenReturn("john@example.com");

        String result = databaseDriver.getClientEmailByAccountID(1);

        assertEquals("john@example.com", result);
        verify(stmtMock).executeQuery("select email from Client join Account on client.ID = Account.Owner where Account.ID =1;");
    }

    /**
     * Test GetClientEmailByID with mockDB
     */
    @Test
    void testGetClientEmailByID() throws SQLException {
        when(connMock.createStatement()).thenReturn(stmtMock);
        when(stmtMock.executeQuery(anyString())).thenReturn(rsMock);
        when(rsMock.getString(1)).thenReturn("john@example.com");

        String result = databaseDriver.getClientEmailByID(1);

        assertEquals("john@example.com", result);
        verify(stmtMock).executeQuery("select email from Client where ID =1;");
    }

    /**
     * Test GetClientIDByEMail with mockDB
     */
    @Test
    void testGetClientIDByEMail() throws SQLException {
        when(connMock.createStatement()).thenReturn(stmtMock);
        when(stmtMock.executeQuery(anyString())).thenReturn(rsMock);
        when(rsMock.getInt(1)).thenReturn(1);

        int result = databaseDriver.getClientIDByEMail("john@example.com");

        assertEquals(1, result);
        verify(stmtMock).executeQuery("select ID from Client where email ='john@example.com';");
    }

    /**
     * Test GetCheckingAccountData with mockDB
     */
    @Test
    void testGetCheckingAccountData() throws SQLException {
        when(connMock.createStatement()).thenReturn(stmtMock);
        when(stmtMock.executeQuery(anyString())).thenReturn(rsMock);

        ResultSet result = databaseDriver.getCheckingAccountData(1);

        assertNotNull(result);
        verify(stmtMock).executeQuery("SELECT * FROM Account WHERE Owner='1' AND MainAccount=1;");
    }

    /**
     * Test GetSavingsAccountData with mockDB
     */
    @Test
    void testGetSavingsAccountData() throws SQLException {
        when(connMock.createStatement()).thenReturn(stmtMock);
        when(stmtMock.executeQuery(anyString())).thenReturn(rsMock);

        ResultSet result = databaseDriver.getSavingsAccountData(1);

        assertNotNull(result);
        verify(stmtMock).executeQuery("SELECT * FROM Account WHERE Owner='1' AND MainAccount=0;");
    }

    /**
     * Test GetAccountById with mockDB
     */
    @Test
    void testGetAccountById() throws SQLException {
        when(connMock.createStatement()).thenReturn(stmtMock);
        when(stmtMock.executeQuery(anyString())).thenReturn(rsMock);

        ResultSet result = databaseDriver.getAccountById(1);

        assertNotNull(result);
        verify(stmtMock).executeQuery("SELECT * FROM Account WHERE ID='1';");
    }

    /**
     * Test VerifyCard with mockDB
     */
    @Test
    void testVerifyCard() throws SQLException {
        when(connMock.createStatement()).thenReturn(stmtMock);
        when(stmtMock.executeQuery(anyString())).thenReturn(rsMock);
        when(rsMock.isBeforeFirst()).thenReturn(true);
        when(rsMock.getString(1)).thenReturn("account");

        String result = databaseDriver.verifyCard("cardNumber", "sequenceNumber", "secretNumber");

        assertEquals("account", result);
        verify(stmtMock).executeQuery("select Account from Card where Card.CardNumber='cardNumber' AND Card.SequenceNumber='sequenceNumber' AND card.SecretNumber='secretNumber';");
    }

    /**
     * Test Payment with mockDB
     */
    @Test
    void testPayment() throws SQLException {
        when(connMock.prepareStatement(anyString())).thenReturn(pstmtMock);
        when(connMock.createStatement()).thenReturn(stmtMock);

        boolean result = databaseDriver.payment(1, 100.0, "message", "type", 2);

        assertTrue(result);
        verify(pstmtMock).executeUpdate();
        verify(stmtMock, times(2)).executeUpdate(anyString());
    }

    /**
     * Test AddNewFriend with mockDB
     */
    @Test
    void testAddNewFriend() throws SQLException {
        when(connMock.prepareStatement(anyString())).thenReturn(pstmtMock);

        boolean result = databaseDriver.addNewFriend(1, 2);

        assertTrue(result);
        verify(pstmtMock).executeUpdate();
    }

    /**
     * Test AddSharedSpace with mockDB
     */
    @Test
    void testAddSharedSpace() throws SQLException {
        when(connMock.createStatement()).thenReturn(stmtMock);

        boolean result = databaseDriver.addSharedSpace(1, 2, 3);

        assertTrue(result);
        verify(stmtMock).executeUpdate("UPDATE Friends SET SharedSpace = '3' WHERE Client = '1' AND FriendClient = '2';");
    }

    /**
     * Test GetSpaceByID with mockDB
     */
    @Test
    void testGetSpaceByID() throws SQLException {
        when(connMock.createStatement()).thenReturn(stmtMock);
        when(stmtMock.executeQuery(anyString())).thenReturn(rsMock);

        ResultSet result = databaseDriver.getSpaceByID(1);

        assertNotNull(result);
        verify(stmtMock).executeQuery("SELECT * FROM 'Account' WHERE ID ='1';");
    }

    /**
     * Test GetSharedSpaceMembersBySharedSpaceID with mockDB
     */
    @Test
    void testGetSharedSpaceMembersBySharedSpaceID() throws SQLException {
        when(connMock.createStatement()).thenReturn(stmtMock);
        when(stmtMock.executeQuery(anyString())).thenReturn(rsMock);

        ResultSet result = databaseDriver.getSharedSpaceMembersBySharedSpaceID(1);

        assertNotNull(result);
        verify(stmtMock).executeQuery("SELECT * FROM 'Friends' WHERE SharedSpace ='1';");
    }

    /**
     * Test DeleteFriendsByIDs with mockDB
     */
    @Test
    void testDeleteFriendsByIDs() throws SQLException {
        when(connMock.createStatement()).thenReturn(stmtMock);

        boolean result = databaseDriver.deleteFriendsByIDs(1, 2);

        assertTrue(result);
        verify(stmtMock).executeUpdate("DELETE FROM Friends WHERE Client = 1 AND FriendClient = 2;");
    }

    /**
     * Test DeleteSharedSpaceByIDs with mockDB
     */
    @Test
    void testDeleteSharedSpaceByIDs() throws SQLException {
        when(connMock.createStatement()).thenReturn(stmtMock);

        boolean result = databaseDriver.deleteSharedSpaceByIDs(1, 2);

        assertTrue(result);
        verify(stmtMock).executeUpdate("UPDATE Friends SET SharedSpace = NULL WHERE Client = '1' AND FriendClient = '2';");
    }
}
