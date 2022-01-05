import org.junit.*;

import java.math.BigInteger;
import java.util.Random;

import static org.junit.Assert.*;

public class StringUtilTests_2_before_after {

    private String testString;
    private String[] testArray;

    private static String veryLargeString;

    /**
     * Любой public void метод, с аннотацией @Before будет вызван
     * автоматически перед запуском каждого теста (то есть столько раз,
     * сколько в классе тестов). Традиционно такой метод называют setUp.
     *
     * Строку и массив, используемые в тестах, я вынес в члены класса и
     * присваиваю им значение перед запуском каждого теста.  Теперь,
     * когда мне надо будет изменить тестовые данные, я смогу это сделать
     * в одном месте — в функции setUp() и все остальные тесты, очевидно,
     * будут использовать новые данные.
     *
     * В случае, если бы я использовал какой-либо большой объект или объект,
     * требующий какой-нибудь обработки после теста (закрытия файла, завершения
     * транзакции, итд.), можно использовать аннотацию @After. Эта аннотация
     * так же требует public void метод, который будет вызван после каждого
     * теста. Традиционно такой метод называют tearDown
     *
     * Обычно аннотации @Before/@After используют для тех тестовых данных,
     * которые изменяются в ходе теста и, следовательно, требуют
     * восстановления исходных значений.
     */
    @Before
    public void setUp() {
        testString =  "T:E:S:T";
        testArray = new String[] {"T", "E", "S", "T"};
    }

    @After
    public void tearDown() {
        testString=""; //Not very useful, but good enough for the example.
    }

    /**
     * Для значений, которые достаточно проинициализировать один раз или
     * инициализация которых слишком медленна, чтобы делать это перед каждым
     * тестом, предусмотрены аннотации @BeforeClass/@AfterClass. Методы,
     * которые ими можно аннотировать, должны быть статическими, а поэтому
     * работать они могут только с статическими членами класса. Я мог бы
     * сделать переменные testString и testArray статическими, но в
     * иллюстративных целях лучше заведу
     * новую тестовую переменную, с помощью которой проверю, как разбираются
     * на массив длинные строки
     *
     * функции, аннотированные @BeforeClass/@AfterClass традиционно
     * называются setUpClass и tearDownClass соответственно
     */

    @BeforeClass
    public static void setUpClass() {
        veryLargeString = new BigInteger(16384, new Random()).toString();
    }

    @AfterClass
    public static void tearDownClass() {
        veryLargeString = "";
    }

    @Test
    public void testToArrayLong() {
        assertEquals("Should not be splitted", veryLargeString, StringUtils.toArray(veryLargeString, ':')[0]);
    }


    @Test
    public void testFromDouble(){
        double source = 3.14;
        String expected = "3.14";

        String actual = StringUtils.fromDouble(source);
        assertEquals("Unexpected string value", expected, actual);
    }

    @Test
    public void testToDouble(){
        assertEquals(3.1415, StringUtils.toDouble("3.1415"), 0.0001);
        assertEquals("Not Nan for null", Double.NaN, StringUtils.toDouble(null), 0.0001);
    }

    @Test
    public void testIsEmpty() {
        assertFalse("Non empty string claimed to be empty", StringUtils.isEmpty("test"));
        assertTrue("Empty string not recogniyed", StringUtils.isEmpty(""));
        assertTrue("whitespaces not recogniyed", StringUtils.isEmpty(" "));
    }

    @Test
    public void testToArray() {
        assertArrayEquals("Wrong array", testArray, StringUtils.toArray(testString, ':'));
        assertNull(StringUtils.toArray(null, ':'));
    }

    @Test
    public void testJoinArray() {
        assertEquals(testString,StringUtils.joinArray(testArray, ':'));
        assertNull(StringUtils.joinArray(null, ':'));
    }
}
