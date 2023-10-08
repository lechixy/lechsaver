package com.lechixy.lechsaver

class Util {
    companion object {
        val igDownloader = listOf("https://snapinsta.app/", "https://saveinsta.app/")
        val ttDownloader = listOf("https://snaptik.app/", "https://ssstik.io/en")
        val ptDownloader = listOf("https://pinterestdownloader.com/")

        // Instagram
        val snapinstaScripts = listOf(
            "document.querySelector('input#url.form-control').value",
            "document.querySelectorAll('a.btn.download-media.flex-center').length",
            "document.querySelectorAll('a.btn.download-media.flex-center')[0].href"
        )
        val saveinstaScripts = listOf(
            "document.querySelector('input#s_input.form-control').value",
            "document.querySelectorAll('a.abutton.is-success.btn-premium.mt-3').length",
            "document.querySelectorAll('a.abutton.is-success.btn-premium.mt-3')[0].href"
        )
        // TikTok
        val snaptikScripts = listOf(
            "document.querySelector('input#url').value",
            "document.querySelector('a.button.download-file.mt-3').href"
        )
        val sstikScripts = listOf(
            "document.querySelector('input#main_page_text').value",
            "document.querySelector('a.pure-button.pure-button-primary.is-center.u-bl.dl-button.download_link.without_watermark.vignette_active.notranslate').href"
        )
        // Pinterest
        val pindownScripts = listOf(
            "document.querySelector('input#download_input.download_input').value",
        )

        fun getInstagramScripts(downloader: Int): List<String> {
            if (downloader == 1) {
                return saveinstaScripts
            }

            // Default return
            return snapinstaScripts
        }

        fun getTiktokScripts(downloader: Int): List<String> {
            if (downloader == 1) {
                return sstikScripts
            }

            // Default return
            return snaptikScripts
        }

        fun getPinterestScripts(downloader: Int): List<String> {
            return pindownScripts
        }

        fun createFilenameForDownload(downloader: String, contentDisposition: String, mimeType: String): String{
            // Tiktok
            if(downloader == ttDownloader[0] || downloader == ttDownloader[1]){
                return contentDisposition.split("=")[1]
            }
            // Instagram
            if(downloader == igDownloader[0] || downloader == igDownloader[1]){
                if(mimeType == "video/mp4"){
                    return contentDisposition.split("\"")[1]
                }

                return contentDisposition.split("=")[1]
            }

            return "not_name_${System.currentTimeMillis()}.lech"
        }
    }
}