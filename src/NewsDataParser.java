import com.google.gson.*;

public class NewsDataParser {
    private final Gson gson;
    private NewsDataModel model;

    NewsDataParser() {
        gson = new Gson();
    }

    public NewsDataModel getDataModel() {
        return model;
    }

    public void parseJson(String jsonString) {
        model = gson.fromJson(jsonString, NewsDataModel.class);

        for (int i = 0; i < model.display; i++) {
            model.items[i].title = removeTags(model.items[i].title);
            model.items[i].description = removeTags(model.items[i].description);
        }
    }

    private String removeTags(String str) {
        String removed = str;
        removed = removed.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
        removed = removed.replaceAll("&quot;", "\"");
        return removed;
    }
}
