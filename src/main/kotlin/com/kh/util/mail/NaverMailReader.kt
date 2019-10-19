package com.kh.util.mail

import java.util.*
import javax.mail.Folder
import javax.mail.Message
import javax.mail.Session
import javax.mail.Store

class NaverMailReader(id: String, pw: String) : MailReader {

    private lateinit var store: Store
    private lateinit var session: Session

    init {
        setSession()
        connect(id, pw)
    }

    private fun setSession() {
        val properties = Properties()
        properties.setProperty("mail.imap.ssl.enable", "true")
        session = Session.getDefaultInstance(properties, null)
    }

    private fun connect(id: String, pw: String) {
        store = session.getStore("imap")
        store.connect("imap.naver.com", 993, id, pw)
    }

    override fun getMessages(): Array<Message> {
        val folder = store.getFolder("INBOX")

        if (!folder.isOpen)
            folder.open(Folder.READ_ONLY)

        return folder.messages
    }

}