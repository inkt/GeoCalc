import com.alibaba.excel.annotation.ExcelProperty;

public class Item {
    @ExcelProperty("试验室编号")
    private String id;
    @ExcelProperty("野外编号")
    private String outdoorId;
    @ExcelProperty("土样深度")
    private String depth;
    @ExcelProperty("比重")
    private Float biZhong;
    @ExcelProperty("含水量")
    private Float hanShuiLiang;
    @ExcelProperty("容重")
    private Float rongZhong;
    @ExcelProperty("孔隙比")
    private Float kongXiBi;
    @ExcelProperty("饱和度")
    private Float baoHeDu;
    @ExcelProperty("液限")
    private Float yeXian;
    @ExcelProperty("塑限")
    private Float suXian;
    @ExcelProperty("塑性指数")
    private Float suXingZhiShu;
    @ExcelProperty("液性指数")
    private Float yeXingZhiShu;
    @ExcelProperty("内聚力")
    private Float neiJuLi;
    @ExcelProperty("摩擦力")
    private Float moCaLi;
    @ExcelProperty("压缩系数")
    private Float yaSuoXiShu;
    @ExcelProperty("压缩模量")
    private Float yaSuoMoLiang;
    @ExcelProperty("岩性")
    private String yanXing;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOutdoorId() {
        return outdoorId;
    }

    public void setOutdoorId(String outdoorId) {
        this.outdoorId = outdoorId;
    }

    public String getDepth() {
        return depth;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }

    public Float getBiZhong() {
        return biZhong;
    }

    public void setBiZhong(Float biZhong) {
        this.biZhong = biZhong;
    }

    public Float getHanShuiLiang() {
        return hanShuiLiang;
    }

    public void setHanShuiLiang(Float hanShuiLiang) {
        this.hanShuiLiang = hanShuiLiang;
    }

    public Float getRongZhong() {
        return rongZhong;
    }

    public void setRongZhong(Float rongZhong) {
        this.rongZhong = rongZhong;
    }

    public Float getKongXiBi() {
        return kongXiBi;
    }

    public void setKongXiBi(Float kongXiBi) {
        this.kongXiBi = kongXiBi;
    }

    public Float getBaoHeDu() {
        return baoHeDu;
    }

    public void setBaoHeDu(Float baoHeDu) {
        this.baoHeDu = baoHeDu;
    }

    public Float getYeXian() {
        return yeXian;
    }

    public void setYeXian(Float yeXian) {
        this.yeXian = yeXian;
    }

    public Float getSuXian() {
        return suXian;
    }

    public void setSuXian(Float suXian) {
        this.suXian = suXian;
    }

    public Float getSuXingZhiShu() {
        return suXingZhiShu;
    }

    public void setSuXingZhiShu(Float suXingZhiShu) {
        this.suXingZhiShu = suXingZhiShu;
    }

    public Float getYeXingZhiShu() {
        return yeXingZhiShu;
    }

    public void setYeXingZhiShu(Float yeXingZhiShu) {
        this.yeXingZhiShu = yeXingZhiShu;
    }

    public Float getNeiJuLi() {
        return neiJuLi;
    }

    public void setNeiJuLi(Float neiJuLi) {
        this.neiJuLi = neiJuLi;
    }

    public Float getMoCaLi() {
        return moCaLi;
    }

    public void setMoCaLi(Float moCaLi) {
        this.moCaLi = moCaLi;
    }

    public Float getYaSuoXiShu() {
        return yaSuoXiShu;
    }

    public void setYaSuoXiShu(Float yaSuoXiShu) {
        this.yaSuoXiShu = yaSuoXiShu;
    }

    public Float getYaSuoMoLiang() {
        return yaSuoMoLiang;
    }

    public void setYaSuoMoLiang(Float yaSuoMoLiang) {
        this.yaSuoMoLiang = yaSuoMoLiang;
    }

    public String getYanXing() {
        return yanXing;
    }

    public void setYanXing(String yanXing) {
        this.yanXing = yanXing;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                '}';
    }
}
