package seedu.address.commons.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javafx.scene.paint.Color;


public class LangColorUtilTest {

    @Test
    public void getBackColor_validLanguage_success() {
        Color expected = Color.rgb(53, 114, 165);
        Color back = LangColorUtil.getBackColor("Python");
        Assertions.assertEquals(expected, back);
    }

    @Test
    public void getBackColor_invalidLanguage_default() {
        Color expected = Color.rgb(62, 123, 145);
        Color back = LangColorUtil.getBackColor("abcdefgh");
        Assertions.assertEquals(expected, back);
    }

    @Test
    public void getFontColor_darkBackground_white() {
        Color expected = Color.WHITE;
        Color back = LangColorUtil.getFontColor("Python");
        Assertions.assertEquals(expected, back);
    }

    @Test
    public void getFontColor_lightBackground_black() {
        Color expected = Color.BLACK;
        Color back = LangColorUtil.getFontColor("JavaScript");
        Assertions.assertEquals(expected, back);
    }

    @Test public void getHex_validColor_success() {
        String black = "#000000ff";
        Assertions.assertEquals(black, LangColorUtil.getHex(Color.BLACK));

        String white = "#ffffffff";
        Assertions.assertEquals(white, LangColorUtil.getHex(Color.WHITE));

        String red = "#ff0000ff";
        Assertions.assertEquals(red, LangColorUtil.getHex(Color.RED));
    }

}
