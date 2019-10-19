package com.kh.util.mail

import javax.mail.Message

interface MailReader {
    fun getMessages(): Array<Message>
}