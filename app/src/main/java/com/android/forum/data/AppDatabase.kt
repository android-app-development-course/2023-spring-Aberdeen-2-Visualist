package com.android.forum.data

import android.net.Uri
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.android.forum.R
import com.android.forum.base.BaseApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Collections
import java.util.Random


@Database(
    entities = [PostEntity::class,User::class,
        WorksEntity::class,
        DownloadEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun postDao(): PostDao
    abstract fun userDao(): UserDao
    abstract fun worksDao(): WorksDao
    abstract fun downloadDao(): DownloadDao

    companion object {
        private var instance: AppDatabase? = null

        fun getInstance(): AppDatabase {
            if (instance == null) {
                synchronized(AppDatabase::class) {
                    instance = Room.databaseBuilder(
                        BaseApplication.CONTEXT,
                        AppDatabase::class.java,
                        "forum_db"
                    ).addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            GlobalScope.launch(Dispatchers.IO) {
                                instance?.postDao()?.insert(initData())
                            }
                        }
                    })
                        .build()
                }
            }
            return instance as AppDatabase
        }
    }
}

private suspend fun initData(): List<PostEntity> {
    val appDatabase = AppDatabase.getInstance().userDao()
    val uidOne = kotlin.random.Random.nextLong()
    val uidTow = kotlin.random.Random.nextLong()
    appDatabase.insert(User(uidOne,"16666666666","","美团技术团队"," 超过10000人的业界一流工程师队伍"))
    appDatabase.insert(User(uidTow,"17676767676","","网易云音乐技术团队","「用技术」传递音乐美好力量"))
    val data: MutableList<PostEntity> = ArrayList()
    val list = mutableListOf(
        PictureAndContent(R.mipmap.a1, "携手并肩 共同迎接美好明天——习近平主席在中国—中亚峰会上的主旨讲话为构建更加紧密的中国—中亚命运共同体提供行动指南"),
        PictureAndContent(R.mipmap.a2, "在百年变局加速演进的背景下，习近平主席的重要讲话着眼时代发展大势，体现世代友好的人民期盼，必将对深化中国同中亚国家关系、推动构建人类命运共同体产生重要而深远的影响。"),
        PictureAndContent(R.mipmap.a3, "作为今年中国首场重大主场多边外交活动，中国—中亚峰会的举行在中国中亚关系史上树立起一座新的里程碑。"),
        PictureAndContent(R.mipmap.a4, "江苏深入开展学习贯彻习近平新时代中国特色社会主义思想主题教育，紧紧围绕高质量发展这一首要任务，扎实开展调查研究，推进制造业智能化改造和数字化转型，加快构建现代化产业体系。\n" +
                "\n" +
                "江苏在开展主题教育的过程中，认真贯彻落实习近平总书记重要讲话精神，坚持多思多想、学深悟透，以强化理论学习指导发展实践，以深化调查研究推动解决发展难题。\n" +
                "\n"),
        PictureAndContent(R.mipmap.a5, "农业农村部最新农情调度显示，截至目前，全国已春播粮食7.6亿亩，完成八成以上。各粮食主产区正抢抓农时，全力推进播种。"),
        PictureAndContent(R.mipmap.a7, "北京师范大学社会学院教授、中国民间文艺家协会中国节日文化研究中心主任萧放介绍，小满是夏季的第二个节气，标志着夏天真正到来了。“满”，《说文解字》释为“盈溢”，引申义便是充满、饱和、足够。“小满”，便有“满而不足”“满而不盈”的意思。"),
        PictureAndContent(R.mipmap.a6, "谈到习近平主席的主旨讲话，国务委员兼外交部长秦刚指出，这是新时代以来，中国最高领导人首次完整、集中、系统向国际社会阐述对中亚外交政策，得到了中亚各国元首的高度赞同和热烈响应，为构建更加紧密的中国—中亚命运共同体提供了根本遵循和行动指南。"),
        PictureAndContent(R.mipmap.a8, "5月18日，第七届世界智能大会在国家会展中心（天津）举办。大会以“智行天下 能动未来”为主题，全力打造创新资源聚集、产业发展引领、智能体验更优的行业顶级盛会。本届智能科技展共有来自全球492家企业参展，汇集国内外先进技术应用场景，集中展示全球智能领域引领企业及前沿科技成果。展览共设置了信息技术应用创新、人工智能、5G+工业互联网、智能交通、智能制造、智慧生活、数字金融、数字健康、国际与省市等10个主题展示区。快来跟着小编的镜头一起来看看这场科技的盛宴吧!"),
        PictureAndContent(R.mipmap.a9, "昨日，麦卡锡曾表示，尽管双方仍存在很大分歧，他相信美国不会违约；而拜登也在启程前往日本前说道，他也相信各方将继续谈判，最终将达成协议，因为“国会领导人们都同意不会违约”。"),
    )
    val seed = System.currentTimeMillis()
    Collections.shuffle(list, Random(seed))
    list.forEach { pc ->
        val uri =
            Uri.parse("android.resource://" + BaseApplication.CONTEXT.packageName + "/" + pc.mipmap)
                .toString()
        data.add(PostEntity(uri, pc.content, true, System.currentTimeMillis(), 0,uidTow))
    }

    Collections.shuffle(list, Random(seed))
    list.forEach { pc ->
        val uri =
            Uri.parse("android.resource://" + BaseApplication.CONTEXT.packageName + "/" + pc.mipmap)
                .toString()
        data.add(PostEntity(uri, pc.content, true, System.currentTimeMillis(), 0,uidOne))
    }

    return data
}

data class PictureAndContent(
    val mipmap: Int,
    val content: String
)