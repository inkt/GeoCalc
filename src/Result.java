public class Result {
    private String property;
    private String count;
    private String max;
    private String min;
    private String average;
    private String fangcha;
    private String ratio;
    private String std;

    public Result(String property, String count, String max, String min, String average, String fangcha, String ratio, String std) {
        this.property = property;
        this.count = count;
        this.max = max;
        this.min = min;
        this.average = average;
        this.fangcha = fangcha;
        this.ratio = ratio;
        this.std = std;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }

    public String getFangcha() {
        return fangcha;
    }

    public void setFangcha(String fangcha) {
        this.fangcha = fangcha;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }

    public String getStd() {
        return std;
    }

    public void setStd(String std) {
        this.std = std;
    }
}