public class NewsDataModel {
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
