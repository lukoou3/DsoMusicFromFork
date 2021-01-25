package com.dirror.music.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dirror.music.MyApplication
import com.dirror.music.R
import com.dirror.music.databinding.ActivityAbout2Binding
import com.dirror.music.service.test.TestMediaCodeInfo
import com.dirror.music.ui.dialog.AppInfoDialog
import com.dirror.music.util.Secure
import com.dirror.music.util.UpdateUtil
import com.dirror.music.util.getVisionCode
import com.dirror.music.util.getVisionName

/**
 * 2.0 新版 AboutActivity
 */
class AboutActivity2 : AppCompatActivity() {

    companion object {
        // 官方网站
        private const val WEBSITE = "https://moriafly.xyz"
        // 更新日志网站
        private const val UPDATE_LOG = "https://github.com/Moriafly/DsoMusic/releases"
        private const val WEB_INFO = "https://moriafly.xyz/dirror-music/info.json"
        private const val HISTORY_VERSION = "https://moriafly.xyz/foyou/dsomusic/history-version.html"
    }

    private lateinit var binding: ActivityAbout2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAbout2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initListener()
    }

    /**
     * 界面初始化
     */
    private fun initView() {
        binding.apply {
            val versionType = if (Secure.isDebug()) {
                "测试版"
            } else {
                "正式版"
            }

            try {
                tvVersion.text = resources.getString(R.string.version, getVisionName(), getVisionCode(), versionType)
            } catch (e: Exception) {

            }
        }
        initMediaCodec()
    }

    private fun initListener() {
        binding.apply {
            // 检查更新
            itemCheckForUpdates.setOnClickListener { UpdateUtil.checkNewVersion(this@AboutActivity2, true) }
            // 更新日志
            itemUpdateLog.setOnClickListener { MyApplication.activityManager.startWebActivity(this@AboutActivity2, UPDATE_LOG) }
            // 历史版本
            itemHistoryVersion.setOnClickListener { MyApplication.activityManager.startWebActivity(this@AboutActivity2, HISTORY_VERSION) }
            // 使用开源项目
            itemOpenSourceCode.setOnClickListener { startActivity(Intent(this@AboutActivity2, OpenSourceActivity::class.java)) }
            // ivLogo
            ivLogo.setOnLongClickListener {
                AppInfoDialog(this@AboutActivity2).show()
                return@setOnLongClickListener true
            }
        }
    }

    private fun initMediaCodec() {

        val list = TestMediaCodeInfo.getCodec()
        var str = ""
        list.forEach {
            str += "${it.name}\n"
        }
        binding.tvMediaCodec.text = str
    }

}