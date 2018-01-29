package com.example.zzbmi.readapp.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author ZhuZiBo
 * @date 2017/12/7
 */
@Entity
public class ExcelBean {
    @Id(autoincrement = true)
    @Unique
    private Long id;      //主键自增长，不可重复,作为不同记录对象的标识，传入参数对象时不要传入
    @Property(nameInDb = "type")
    private String type;
    @Property(nameInDb = "content")
    private String content;
    @Property(nameInDb = "result")
    private String result;
    @Property(nameInDb = "selectA")
    private String selectA;
    @Property(nameInDb = "selectB")
    private String selectB;
    @Property(nameInDb = "selectC")
    private String selectC;
    @Property(nameInDb = "selectD")
    private String selectD;
    @Property(nameInDb = "selectE")
    private String selectE;
    /**内容全拼*/
    @Property(nameInDb = "fullLetter")
    private String fullLetter;
    /**内容简拼*/
    @Property(nameInDb = "shortLetter")
    private String shortLetter;
    @Generated(hash = 115539383)
    public ExcelBean(Long id, String type, String content, String result,
            String selectA, String selectB, String selectC, String selectD,
            String selectE, String fullLetter, String shortLetter) {
        this.id = id;
        this.type = type;
        this.content = content;
        this.result = result;
        this.selectA = selectA;
        this.selectB = selectB;
        this.selectC = selectC;
        this.selectD = selectD;
        this.selectE = selectE;
        this.fullLetter = fullLetter;
        this.shortLetter = shortLetter;
    }
    @Generated(hash = 836708801)
    public ExcelBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getResult() {
        return this.result;
    }
    public void setResult(String result) {
        this.result = result;
    }
    public String getSelectA() {
        return this.selectA;
    }
    public void setSelectA(String selectA) {
        this.selectA = selectA;
    }
    public String getSelectB() {
        return this.selectB;
    }
    public void setSelectB(String selectB) {
        this.selectB = selectB;
    }
    public String getSelectC() {
        return this.selectC;
    }
    public void setSelectC(String selectC) {
        this.selectC = selectC;
    }
    public String getSelectD() {
        return this.selectD;
    }
    public void setSelectD(String selectD) {
        this.selectD = selectD;
    }
    public String getSelectE() {
        return this.selectE;
    }
    public void setSelectE(String selectE) {
        this.selectE = selectE;
    }
    public String getFullLetter() {
        return this.fullLetter;
    }
    public void setFullLetter(String fullLetter) {
        this.fullLetter = fullLetter;
    }
    public String getShortLetter() {
        return this.shortLetter;
    }
    public void setShortLetter(String shortLetter) {
        this.shortLetter = shortLetter;
    }
}
