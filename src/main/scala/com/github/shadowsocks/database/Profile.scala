/*******************************************************************************/
/*                                                                             */
/*  Copyright (C) 2016 by Max Lv <max.c.lv@gmail.com>                          */
/*  Copyright (C) 2016 by Mygod Studio <contact-shadowsocks-android@mygod.be>  */
/*                                                                             */
/*  This program is free software: you can redistribute it and/or modify       */
/*  it under the terms of the GNU General Public License as published by       */
/*  the Free Software Foundation, either version 3 of the License, or          */
/*  (at your option) any later version.                                        */
/*                                                                             */
/*  This program is distributed in the hope that it will be useful,            */
/*  but WITHOUT ANY WARRANTY; without even the implied warranty of             */
/*  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the              */
/*  GNU General Public License for more details.                               */
/*                                                                             */
/*  You should have received a copy of the GNU General Public License          */
/*  along with this program. If not, see <http://www.gnu.org/licenses/>.       */
/*                                                                             */
/*******************************************************************************/

package com.github.shadowsocks.database

import java.net.URLEncoder
import java.util.Locale

import android.os.Binder
import android.util.Base64
import com.j256.ormlite.field.{DataType, DatabaseField}

class Profile {
  @DatabaseField(generatedId = true)
  var id: Int = 0

  @DatabaseField
  var name: String = "Untitled"

  @DatabaseField
  var host: String = ""

  // hopefully hashCode = mHandle doesn't change, currently this is true from KitKat to Nougat
  @DatabaseField
  var localPort: Int = 1080 + Binder.getCallingUserHandle.hashCode

  @DatabaseField
  var remotePort: Int = 8388

  @DatabaseField
  var password: String = ""

  @DatabaseField
  var protocol: String = "origin"

  @DatabaseField
  var obfs: String = "plain"

  @DatabaseField
  var obfs_param: String = ""

  @DatabaseField
  var method: String = "aes-256-cfb"

  @DatabaseField
  var route: String = "all"

  @DatabaseField
  var proxyApps: Boolean = false

  @DatabaseField
  var bypass: Boolean = false

  @DatabaseField
  var udpdns: Boolean = false

  @DatabaseField
  var dns: String = "8.8.8.8:53"

  @DatabaseField
  var china_dns: String = "114.114.114.114:53,223.5.5.5:53"

  @DatabaseField
  var ipv6: Boolean = false

  @DatabaseField(dataType = DataType.LONG_STRING)
  var individual: String = ""

  @DatabaseField
  var tx: Long = 0

  @DatabaseField
  var rx: Long = 0

  @DatabaseField
  val date: java.util.Date = new java.util.Date()

  @DatabaseField
  var userOrder: Long = _

  override def toString = "ssr://" + Base64.encodeToString("%s:%d:%s:%s:%s:%s/?obfsparam=%s&remarks=%s".formatLocal(Locale.ENGLISH,
    host, remotePort, protocol, method, obfs, Base64.encodeToString("%s".formatLocal(Locale.ENGLISH,
    password).getBytes, Base64.URL_SAFE | Base64.NO_WRAP), Base64.encodeToString("%s".formatLocal(Locale.ENGLISH,
    obfs_param).getBytes, Base64.URL_SAFE | Base64.NO_WRAP), Base64.encodeToString("%s".formatLocal(Locale.ENGLISH,
    name).getBytes, Base64.URL_SAFE | Base64.NO_WRAP)).getBytes, Base64.URL_SAFE | Base64.NO_WRAP)

  @DatabaseField
  var kcp: Boolean = false

  @DatabaseField
  var kcpPort: Int = 8399

  @DatabaseField
  var kcpcli: String = "--crypt none --mode normal --mtu 1200 --nocomp --dscp 46 --parityshard 0"

  def isMethodUnsafe: Boolean = "table".equalsIgnoreCase(method) || "rc4".equalsIgnoreCase(method)
}
