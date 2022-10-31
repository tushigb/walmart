package com.miu.wallmart

import java.io.Serializable;

class User(
    var firstname: String = "",
    var lastname: String = "",
    var email: String = "",
    var password: String = ""
) : Serializable {

}