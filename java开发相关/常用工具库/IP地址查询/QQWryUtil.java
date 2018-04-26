import com.github.jarod.qqwry.IPZone;
import com.github.jarod.qqwry.QQWry;

import java.io.IOException;
import java.nio.file.Paths;

/*    ip地址映射二进制文件地址  https://github.com/WisdomFusion/qqwry.dat   */

public class QQWryUtil {

	private static QQWry qqWry = null;

	private static final String datPath = "D:\\qqwry.dat-master\\20180415\\qqwry.dat";

	private QQWryUtil() {}

	public static QQWry getInstance() {
		if(qqWry == null) {
			synchronized (QQWryUtil.class) {
				if(qqWry == null) {
					try{
						qqWry = new QQWry(Paths.get(datPath));
					}catch (IOException e) {
						e.printStackTrace();
						return null;
					}
				}
			}
		}
		return qqWry;
	}

	public static void main(String[] argv) {
//		String destIP = "223.104.247.15";
//		String destIP = "202.89.233.100";
		String destIP = "115.239.211.112";
		IPZone ipZone = QQWryUtil.getInstance().findIP(destIP);
		System.out.println(ipZone.getMainInfo());
		System.out.println(ipZone.getSubInfo());
		System.out.println(ipZone.getIp());

	}
}
