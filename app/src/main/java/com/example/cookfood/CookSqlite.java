package com.example.cookfood;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import androidx.annotation.Nullable;

public class CookSqlite extends SQLiteOpenHelper {


    //2.对外提供函数
    private static SQLiteOpenHelper mInstance;
    public static synchronized SQLiteOpenHelper getInstance(Context context){
        if (mInstance == null){
            mInstance = new CookSqlite(context,"cookfoodDB.db",null,1);
        }
        return mInstance;
    }
    //单例模式1.构造函数私有化
    private CookSqlite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //数据库初始化
    //创建表 表的初始化 数据库第一次创建的时候调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建用户信息表
        String sql = "create table users(_id integer primary key autoincrement,phone text, password text, name text, imgurl text, enrolltime text)";
        db.execSQL(sql);
        //创建菜谱信息表
        String sql1 = "create table caipus(_id integer primary key autoincrement, time text, title text, imgurls text, content text, foods text, userid text,likes text,collect text)";
        db.execSQL(sql1);
        //创建动态信息表
        String sql2 = "create table actives(_id integer primary key autoincrement, time text,imgurls text, content text, userid text)";
        db.execSQL(sql2);
        //创建评论信息表
        String sql3 = "create table comments(_id integer primary key autoincrement, time text, content text, userid text, activeid integer, caipuid integer)";
        db.execSQL(sql3);
        //创建收藏信息表
        String sql4 = "create table collects(_id integer primary key autoincrement, userid text, caipuid integer)";
        db.execSQL(sql4);
        //创建菜篮子信息表
        String sql5 = "create table lanzis(_id integer primary key autoincrement, userid text, caipuid integer)";
        db.execSQL(sql5);

        initDate(db);
    }
    //数据库升级
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void initDate(SQLiteDatabase db){
        db.beginTransaction();
        db.execSQL("insert into users(phone, password, name, imgurl, enrolltime) values('15912345678','1234cook','小神厨',null,null)");
        db.execSQL("insert into users(phone, password, name, imgurl, enrolltime) values('13412345678','123cook','馋猫',null,null)");
        db.execSQL("insert into users(phone, password, name, imgurl, enrolltime) values('13912345678','139cook','米饭挂嘴边',null,null)");
        db.execSQL("insert into users(phone, password, name, imgurl, enrolltime) values('13712345678','123cook','秋天的奶茶',null,null)");
        db.execSQL("insert into users(phone, password, name, imgurl, enrolltime) values('12345678912','123cook','我来啦',null,null)");

        db.execSQL("insert into caipus(time, title, imgurls, content, foods, userid,likes,collect) values('2020.05.21','红烧肉','hongshaorou7;hongshaorou1;hongshaorou2;hongshaorou3;hongshaorou4;hongshaorou5;hongshaorou6;hongshaorou7','这是不用炒糖色却一样好吃的方法，肥而不腻，解馋又美味。;五花肉切麻将大小的块，冲洗干净后，放一汤匙料酒，浸泡一小时。捞出来沥干。;锅里放油煸炒肉块到微黄。;放入干辣椒，草果，八角，姜，炒出香味。;放料酒两汤匙，炒几下，再放老抽，生抽，炒匀。;放入开水，淹没肉，转入砂锅煨两个小时调入盐。煨到酥烂的五花肉，放冰糖大火收汁，晃动锅，不要翻动。;到汤汁均匀的裹在肉上就好了！放点味精提味。;完成！', '五花肉:500克;姜:5片;草果:1个;冰糖:适量;八角:8颗;盐:一茶匙;老抽:三分之一汤匙;生抽:适量;料酒:一汤匙;味精:适量','12345678912','2022','45')");
        db.execSQL("insert into caipus(time, title, imgurls, content, foods, userid,likes,collect) values('2020.05.23','廖师傅的毛氏红烧肉','_1hongshaorou','今天，就做这道廖师傅的“毛氏红烧肉”。这道菜，做起来很简单，只是一般湖南长沙一带的烧法，并且不需要炒糖色，当年，由于为了主席的健康，毛主席的私人保健医生建议厨师不要使用糖色，焦糖对人体无益。具体做法如下;先把五花肉切成三厘米见方的块，把切好的肉凉水下锅。;水开煮3-5分钟捞出。;炒勺上火放少许底油，下入焯过水的五花肉煸炒。;把五花肉块用中小火煸出油，肉块煸至颜色微黄捞出备用。;锅中留底油下入豆豉、八角、桂皮煸炒。;煸出香味儿后下入葱段、姜片和干辣椒煸炒。;爆出香味儿后，倒入煸好的五花肉块炒匀，然后烹入绍酒炒匀。再烹入酱油炒匀。;注入适量的开水，水和肉持平略多一些即可。;注入水后放入冰糖。;盖上锅盖，用小火焖烧40分钟。;肉烧至40分钟后，拣出八角、桂皮和葱姜不要。;用旺火收汁，汤汁将近收净时撒入少许味精炒匀，然后便可出锅。;出锅后，把肉装入保温碗中便可上桌食用。','五花肉:500克;大葱:30克;姜片:20克;绍酒:30克;冰糖:20克;味精:1克;酱油:20克;豆豉:20克;干辣椒:5克;八角:2枚;桂皮:1个;清水:适量','12345678912','22','4')");
        db.execSQL("insert into caipus(time, title, imgurls, content, foods, userid,likes,collect) values('2020.05.28','可乐鸡翅','kelejichi;kelejichi1;kelejichi2;kelejichi3;kelejichi4;kelejichi5',' ;鸡翅冷水下锅煮开，捞出划两刀备用。;平底锅放少许油，下鸡翅煎至两边焦黄。;把鸡翅铲到一边，下姜丝炒香。;倒入可乐，以淹住鸡翅为佳，加点生抽和盐。;大火烧开，转小火烧至汤汁浓郁，大火收汁即可。','鸡翅:适量;可乐:适量;姜丝:适量;生抽:适量;盐:适量;油:适量','12345678912','2309','356')");
        db.execSQL("insert into caipus(time, title, imgurls, content, foods, userid,likes,collect) values('2020.06.08','柠檬可乐鸡翅','_2kelejichi;',null,null,'13712345678','234','46')");
        db.execSQL("insert into caipus(time, title, imgurls, content, foods, userid,likes,collect) values('2020.07.05','无油版可乐鸡翅','_1kelejichi;',null,null,'13912345678','99','4')");
        db.execSQL("insert into caipus(time, title, imgurls, content, foods, userid,likes,collect) values('2020.07.13','土豆红烧肉','_2hongshaorou;',null,null,'13712345678','567','78')");
        db.execSQL("insert into caipus(time, title, imgurls, content, foods, userid,likes,collect) values('2020.07.17','红烧肉','_3hongshaorou;',null,null,'13912345678','667','47')");
        db.execSQL("insert into caipus(time, title, imgurls, content, foods, userid,likes,collect) values('2020.07.30','酸辣土豆丝','suanlatudousi;',null,null,'13712345678','56','5')");
        db.execSQL("insert into caipus(time, title, imgurls, content, foods, userid,likes,collect) values('2020.03.22','美味土豆泥','culiutudousi;',null,null,'13912345678','678','57')");
        db.execSQL("insert into caipus(time, title, imgurls, content, foods, userid,likes,collect) values('2020.06.09','土豆烧牛肉','tudoushaoniurou;',null,null,'12345678912','453','75')");
        db.execSQL("insert into caipus(time, title, imgurls, content, foods, userid,likes,collect) values('2020.10.22','土豆鸡蛋饼','tudoujidanbing;',null,null,'13712345678','678','76')");
        db.execSQL("insert into caipus(time, title, imgurls, content, foods, userid,likes,collect) values('2021.01.27','红烧肉','_4hongshaorou;',null,null,'13712345678','6','0')");
        db.execSQL("insert into caipus(time, title, imgurls, content, foods, userid,likes,collect) values('2021.03.05','红烧肉','_5hongshaorou;',null,null,'13712345678','656','89')");
        db.execSQL("insert into caipus(time, title, imgurls, content, foods, userid,likes,collect) values('2021.02.12','红烧肉','_6hongshaorou;',null,null,'12345678912','24','8')");
        db.execSQL("insert into caipus(time, title, imgurls, content, foods, userid,likes,collect) values('2021.01.12','可乐鸡翅','_3kelejichi;',null,null,'15912345678','4345','567')");
        db.execSQL("insert into caipus(time, title, imgurls, content, foods, userid,likes,collect) values('2021.01.12','可乐鸡翅','_4kelejichi;',null,null,'13712345678','672','78')");
        db.execSQL("insert into caipus(time, title, imgurls, content, foods, userid,likes,collect) values('2021.03.22','可乐鸡翅','_5kelejichi;',null,null,'15912345678','253','34')");
        db.execSQL("insert into caipus(time, title, imgurls, content, foods, userid,likes,collect) values('2021.05.18','土豆炖鸡块','tudoudunjikuai;',null,null,'15912345678','325','42')");
        db.execSQL("insert into caipus(time, title, imgurls, content, foods, userid,likes,collect) values('2021.05.18','醋溜土豆丝','culiutudousi;',null,null,'18112346789','432','3')");
        db.execSQL("insert into caipus(time, title, imgurls, content, foods, userid,likes,collect) values('2021.05.18','芝士奶盖红茶','zhishinaigaihongcha;',null,null,'18112346789','53256','3141')");
        db.execSQL("insert into caipus(time, title, imgurls, content, foods, userid,likes,collect) values('2021.02.23','山药排骨汤','shanyaopaigutang;',null,null,'18112346789','123','31')");
        db.execSQL("insert into caipus(time, title, imgurls, content, foods, userid,likes,collect) values('2021.02.23','银耳拌牛肉','niurou;',null,null,'13912345678','145','42')");
        db.execSQL("insert into caipus(time, title, imgurls, content, foods, userid,likes,collect) values('2021.03.29','荔枝咕咾肉','lizhigulaorou;',null,null,'13712345678','2347','29')");
        db.execSQL("insert into caipus(time, title, imgurls, content, foods, userid,likes,collect) values('2021.04.27','马卡龙','makalong;',null,null,'13912345678','63179','290')");
        db.execSQL("insert into caipus(time, title, imgurls, content, foods, userid,likes,collect) values('2021.05.17','包菜炒粉条','baocaichaofentiao;',null,null,'13712345678','6289','97')");
        db.execSQL("insert into caipus(time, title, imgurls, content, foods, userid,likes,collect) values('2021.04.05','爱尔兰牛排','niupai;',null,null,'13912345678','345','32')");
        db.execSQL("insert into caipus(time, title, imgurls, content, foods, userid,likes,collect) values('2020.09.26','芋圆烧仙草','yuyuanshaoxiancao;',null,null,'13712345678','2601','79')");
        db.execSQL("insert into caipus(time, title, imgurls, content, foods, userid,likes,collect) values('2021.04.27','焦糖蛋糕','jiaotangtangao;',null,null,'18112346789','3901','456')");
        db.execSQL("insert into caipus(time, title, imgurls, content, foods, userid,likes,collect) values('2021.04.26','孜然烤排骨','paigu;',null,null,'13712345678','12689','289')");

        db.setTransactionSuccessful();
        db.endTransaction();
    }
}
