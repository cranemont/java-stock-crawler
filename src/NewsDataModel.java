import com.google.gson.*;

public class NewsDataModel {
    private final Gson gson;
    private final StockNews news;
    private newsSearchDTO searchDTO;
    

    NewsDataModel(StockNews news) {
        gson = new Gson();
        this.news = news;
    }

    public void updateJson(String jsonString) {
        searchDTO = gson.fromJson(jsonString, newsSearchDTO.class);
        news.updateNewsData(getTitles(), getOriginalLinks(), searchDTO.display);
    }

    private String[] getTitles() {
        if (searchDTO == null) {
            System.err.println("searchDTO is null!");
            System.exit(1);
        }

        String[] titles = new String[searchDTO.display];
        for (int i = 0; i < searchDTO.display; i++) {
            titles[i] = removeTags(searchDTO.items[i].title);
        }
        return titles;
    }

    private String[] getOriginalLinks() {
        if (searchDTO == null) {
            System.err.println("searchDTO is null!");
            System.exit(1);
        }

        String[] originLink = new String[searchDTO.display];
        for (int i = 0; i < searchDTO.display; i++) {
            originLink[i] = searchDTO.items[i].originallink;
        }
        return originLink;
    }

    private String removeTags(String str) {
        return str.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
    }

    private class newsSearchDTO {
        public String lastBuildDate;
        public int total;
        public int start;
        public int display;
        public item[] items;
    
        class item {
            public String title;
            public String originallink;
            public String link;
            public String description;
            public String pubDate;
        }
    }
}
