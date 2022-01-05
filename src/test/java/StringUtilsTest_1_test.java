import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Тесты в JUnit располагаются в отдельных классах, методы которых,
 * имеющие аннотацию @Test, и возвращающие void, и есть сами тесты.
 * Имя класса может быть в принципе любое, но рекомендуется придерживаться
 * шаблона ИмяТестируемогоКлассаTest, так как это упрощает чтение кода.
 * К тому же обычно средства автоматического запуска тестов, такие как
 * плагин maven maven-surefire-plugin предполагают, что классы с
 * юнит-тестами оканчиваются на *Test
 *
 * Maven традиционно располагает тесты в каталоге src/test, в то
 * время как основной исходный код располагается в src/main.
 * Разумеется это всего лишь договорённость, используемая в maven
 * по умолчанию, и тесты и код можно располагать любым удобным образом.
 *
 * Название тестовых методов так же могут быть любыми, однако для
 * повышения читаемости кода, рекомендуется начинать их с префикса
 * test* и отражать в названии суть теста.
 */

public class StringUtilsTest_1_test {
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

        /**
         * Однако у assertEquals в этом тесте появился дополнительный параметр!
         * Дело в том, что сравнивать числа с плавающей запятой непосредственно
         * друг с другом нельзя, так как они не имеют точного двоичного представления.
         * Обычно числа сравниваются с некоторой погрешностью: можно сказать что
         * 3.1415000000001 эквивалентно 3.1415000000002 с погрешностью до 0.000000000001.
         * И именно эта погрешность передаётся в третий параметр assertEquals для числе
         * с плавающей запятой. Вторая часть теста очевидна — проверяется что для
         * переданного null возвращается NaN.
         */
    }

    @Test
    public void testIsEmpty() {
        assertFalse("Non empty string claimed to be empty", StringUtils.isEmpty("test"));
        assertTrue("Empty string not recogniyed", StringUtils.isEmpty(""));
        assertTrue("whitespaces not recogniyed", StringUtils.isEmpty(" "));

        /**
         * Теперь наоборот — у функций assertTrue и assertFalse не хватает одного аргумента.
         * А всё потому, что эти функции проверяют только логические значения (первая ожидает
         * true, вторая, соответственно, false ), которые и не с чем сравнивать.  В самом же
         * тесте проверяется документированние поведение:  строка с значением очевидно не пуста,
         * строка без каких-либо символов в ней — пуста и, наконец, строка с невидимыми символами
         * тоже признаётся пустой.
         */
    }

    @Test
    public void testToArray() {
        String[] expected = {"t","e", "s", "t"};
        String source = "t:e:s:t";
        assertArrayEquals("wrong array", expected, StringUtils.toArray(source, ':'));
        assertNull(StringUtils.toArray(null, ':'));

        /**
         * В последних двух тестах нам придётся работать с массивами. Массивы нельзя проверить
         * через assertEquals, так как для массивов assertEquals ведёт себя как assertSame:
         *      assertEquals(expected, StringUtils.toArray(source, ':'));
         *      junit.framework.AssertionFailedError: expected:<[Ljava.lang.String;@61b383e9> but was:<[Ljava.lang.String;@5099681b>
         * Поэтому в JUnit предусмотрена специальная функция для сравнения массивов
         * assertArrayEquals, которая сравнивает эквивалентность каждого элемента
         * обоих массивов друг с другом. Разумеется сравниваются между собой элементы
         * с одинаковой позицией в массиве и массивы разной длины сразу признаются не
         * эквивалентными. Надо отметить что обратной функции для assertArrayEquals не предусмотрено.
         * Тест для последней оставшейся функции (joinArray) я предлагаю написать самостоятельно.
         */
        assertEquals(0,StringUtils.toArray(null, ':').length);
    }
}
