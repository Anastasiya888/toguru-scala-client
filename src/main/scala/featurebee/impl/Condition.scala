package featurebee.impl

import java.util.Locale

import featurebee.ClientInfo
import featurebee.ClientInfo.Browser.Browser
import featurebee.impl.LocaleSupport._

/**
 * @author Chris Wewerka
 */
sealed trait Condition {
  def applies(clientInfo: ClientInfo): Boolean
}

case object AlwaysOnCondition extends Condition {
  override def applies(clientInfo: ClientInfo): Boolean = true
}

case object AlwaysOffCondition extends Condition {
  override def applies(clientInfo: ClientInfo): Boolean = false
}

case class BrowserCondition(browsers: Set[Browser]) extends Condition {
  override def applies(clientInfo: ClientInfo): Boolean = clientInfo.browser.exists(browsers.contains)
}

case class CultureCondition(cultures: Set[Locale]) extends Condition {
  override def applies(clientInfo: ClientInfo): Boolean = {
    cultures.exists {
      activatingLocale => activatingLocale.lang match {
        case None =>
          clientInfo.culture.exists(clientLocale => activatingLocale.country == clientLocale.country)
        case _ => clientInfo.culture.contains(activatingLocale)
      }
    }
  }
}

case class TrafficDistribution(percentage: Double) extends Condition {
  // TODO implement
  override def applies(clientInfo: ClientInfo): Boolean = ???
}
