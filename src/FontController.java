import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.File;

public class FontController {
    static public Font loadFontFile(String Filename) {
        Font font = null;
        String FilePath = "resources/font/" + Filename;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File(FilePath));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return font;
    }

    static public void registerFont(Font font) {
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
