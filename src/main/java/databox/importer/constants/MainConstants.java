package databox.importer.constants;

import java.util.TimeZone;

public class MainConstants {

	public static int DEFAULT_THREAD_DELAY = 60000;

	public static final String parcelServiceUrl = "http://testsvr5.sinergise.com/EV_Javni_Server_test/podatki/";

	public static final String databoxParcelPushToken = "bk4cqpk2jqikzzfwnvlbp";
	public static final String databoxFbPushToken = "7dl2h41h0u5l54sxh87r98";
	public static final String databoxTemperaturePushToken = "1a6ld4fjmdq5jg62xfmyiv";

	public static final String arsoServiceUrl = "https://meteo.arso.gov.si/uploads/probase/www/observ/surface/text/sl/observationAms_KOPER_KAPET-IJA_latest.xml";

	public static final TimeZone DEFAULT_TIME_ZONE = TimeZone.getTimeZone("UTC");

}
