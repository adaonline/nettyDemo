package login.client;

import io.netty.util.AttributeKey;
import singnalToSignal.session.Session;

public class Attributes {
   public static AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
   public static AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
