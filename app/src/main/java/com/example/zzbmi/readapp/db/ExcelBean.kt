//package com.example.zzbmi.readapp.db
//
//import org.greenrobot.greendao.annotation.Entity
//import org.greenrobot.greendao.annotation.Id
//import org.greenrobot.greendao.annotation.Property
//import org.greenrobot.greendao.annotation.Unique
//
///**
// * @author ZhuZiBo
// * @date 2017/12/7
// *  原本想使用kotlin的实体类生成GreenDao的类，结果行不通，根本无法生成其余类，老老实实用java去吧
// */
//@Entity
//class ExcelBean {
//    @Id(autoincrement = true)
//    @Unique
//    var id: Long? = null       //主键自增长，不可重复,作为不同记录对象的标识，传入参数对象时不要传入
//    @Property(nameInDb = "type")
//    var type: String? = null
//    @Property(nameInDb = "content")
//    var content: String? = null
//    @Property(nameInDb = "result")
//    var result: String? = null
//    @Property(nameInDb = "selectA")
//    var selectA: String? = null
//    @Property(nameInDb = "selectB")
//    var selectB: String? = null
//    @Property(nameInDb = "selectC")
//    var selectC: String? = null
//    @Property(nameInDb = "selectD")
//    var selectD: String? = null
//    @Property(nameInDb = "selectE")
//    var selectE: String? = null
//}
