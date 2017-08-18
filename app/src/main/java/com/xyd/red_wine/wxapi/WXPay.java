package com.xyd.red_wine.wxapi;

import android.content.Context;


import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.security.MessageDigest;

public class WXPay {
	private IWXAPI api;
	public WXPay(Context context, String appID,
				 String partnerID, String prepayId,
				 String packageValue, String nonceStr,
				 String timeStamp, String sign
	) {
		// TODO Auto-generated constructor stub
		api = WXAPIFactory.createWXAPI(context, null);
		// 将该app注册到微信
		api.registerApp("wxdb713d24bde112f2");
		//api.registerApp(appID);
		pay(appID, partnerID, prepayId, packageValue, nonceStr, timeStamp, sign);
	}
	/*
	 * 将订单信息传入支付第三方
	 */
	private void pay(String appID, String partnerID,
					 String prepayId, String packageValue,
					 String nonceStr, String timeStamp,
					 String sign
	) {
//		//  本地签名(留着备用)
//		String s = "appid=" + appID +
//				"&noncestr=" + nonceStr +
//				"&package=" + packageValue +
//				"&partnerid=" + partnerID +
//				"&prepayid=" + prepayId +
//				"&timestamp=" + timeStamp +
//				"&key=" + "3738591a7785de38995ba8760553ac11";
//		s = MD5(s).toUpperCase();
//
//		sign  = s;
		PayReq request = new PayReq();
		request.appId = appID;
		request.partnerId = partnerID;
		request.prepayId= prepayId;
		request.packageValue = packageValue;
		request.nonceStr= nonceStr;
		request.timeStamp= timeStamp;
		request.sign= sign;

		api.sendReq(request);
	}

	public static String MD5(String s) {
		byte[] buffer = s.getBytes();
		char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(buffer);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}
}
