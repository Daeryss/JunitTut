import org.junit.*;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.core.Is.is;

/**
 * JUnit включает в себя небольшое подмножество Hamcrest матчеров,
 * но полностью совместим с остальными матчерами Hamcrest, поэтому
 * добавим их к проекту
 */

public class StringUtilTests_3_matchers {

    private String testString;
    private String[] testArray;
    private static String veryLargeString;

    @Before
    public void setUp() {
        testString =  "T:E:S:T";
        testArray = new String[] {"T", "E", "S", "T"};
    }

    @After
    public void tearDown() {
        testString=""; //Not very useful, but good enough for the example.
    }

    @BeforeClass
    public static void setUpClass() {
        veryLargeString = new BigInteger(16384, new Random()).toString();
    }

    @AfterClass
    public static void tearDownClass() {
        veryLargeString = "";
    }

    /**
     * is(closeTo()) гораздо удобнее и позволяет не помнить о третьем
     * параметре assertEquals при сравнении чисел с плавающей запятой.
     */
    @Test
    public void testFromDouble(){
        assertThat(
                StringUtils.fromDouble(3.1415),
                is("3.1415")
        );
    }

    @Test
    public void testToDouble(){
        //assertEquals(3.1415, StringUtils.toDouble("3.1415"), 0.0001);
        assertThat(
                StringUtils.toDouble("3.1415"),
                is(closeTo(3.1415, 0.0001))
        );
        //assertEquals("Not Nan for null", Double.NaN, StringUtils.toDouble(null), 0.0001);
        assertThat(
                StringUtils.toDouble(null),
                is(Double.NaN)
        );
    }

    @Test
    public void testIsEmpty() {
        assertFalse("Non empty string claimed to be empty", StringUtils.isEmpty("test"));
        assertTrue("Empty string not recogniyed", StringUtils.isEmpty(""));
        assertTrue("whitespaces not recogniyed", StringUtils.isEmpty(" "));
    }

    /**
     * Можно забыть и о assertArrayEquals: hamcrest и assertThat достаточно умны,
     * чтобы сравнить массивы. Нечёткое сравнение массивов прекрасно иллюстрирует
     * возможности матчеров:
     */
    @Test
    public void testToArray() {
        String[] expected = {"t", "e", "s", "t"};
        String source = "t:e:s:t";
        assertArrayEquals("wrong array", expected, StringUtils.toArray(source, ':'));
        assertNull(StringUtils.toArray(null, ':'));
        assertEquals(0,StringUtils.toArray(null, ':').length);

        assertThat(StringUtils.toArray(testString, ':'), is(testArray));
        assertThat(StringUtils.toArray(null, ':').length, is(0));

        assertThat(
                Arrays.asList(StringUtils.toArray(testString, ':')),
                containsInAnyOrder("T", "T", "S", "E")
        );
    }

    @Test
    public void testJoinArray() {
        //assertEquals(testString, StringUtils.joinArray(testArray, ':'));
        assertThat(StringUtils.joinArray(testArray, ':'), is(testString));
        assertNull(StringUtils.joinArray(null,':'));
    }
}
